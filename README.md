# url-checker

Foi desenvolvido um microsserviço de validação de ameaças em potencial, com o objetivo de descartar URLs legítimas utilizando uma whitelist de regras. A whitelist é formada por regras aplicáveis a clientes específicos e por regras globais, aplicáveis a todos os clientes. O microsserviço foi desenvolvido em Java e tem integração com o message broker RabbitMQ e com o banco de dados MySQL. Os três componentes (microsserviço, RabbitMQ e MySQL) são executados em containers Docker.

## Operações
### 1. Inserção de expressão regular na whitelist
Ao receber uma mensagem de requisição da fila configurada pela variável $INSERTION_QUEUE, a aplicação insere na sua base de dados um novo registro. Formato da mensagem de requisição:

*{"client": <string/nullable>, "regex": <string>}*

Uma requisição com o campo "client" nulo (null) é como uma inserção na whitelist global, aplicável a todos os clientes.

### 2. Validação de URL
Ao receber uma mensagem de requisição da fila configurada pela variável $VALIDATION_QUEUE, a aplicação utiliza a whitelist apropriada para determinar se a URL é válida ou não. Formato da mensagem de requisição:

*{"client": <string>, "url": <string>, "correlationId": <integer>}*

O campo "client" é utilizado para selecionar as regras aplicáveis, tanto globais quanto específicas do próprio cliente.
Após a validação, a resposta (ocorrência ou não de match) é enviada para o exchange configurada pela variável $RESPONSE_EXCHANGE com a routing key $RESPONSE_ROUTING_KEY.

Formato da mensagem de resposta:

*{"match": <boolean>, "regex": <string/nullable>, "correlationId": <integer>}*

O campo "match" tem o valor true caso ao menos uma regra da whitelist seja compatível com a URL fornecida na requisição. O campo regex contém a expressão que foi correspondida. Caso nenhuma expressão regular da whitelist seja compatível com a URL, o campo "match" retorna o valor false, e o campo "regex" é nulo (null). Em caso de múltiplas expressões regulares compatíveis, o campo "regex" retorna apenas a primeira encontrada

O campo "correlationId" é utilizado para correlacionar uma mensagem de requisição com uma mensagem de resposta.

## Base de dados
As expressões regulares inseridas através da operação de inserção são persistidas no banco de dados MySQL.

## Build e execução
A partir do diretório raiz do projeto, é possível fazer o build e inicializar os três componentes com os seguintes comandos, executados em sequência:

*mvn clean install*

*docker-compose up*

A imagem Docker do microsserviço é construída a partir de um Dockerfile, utilizando como base a imagem openjdk:8-jre-alpine.

# Implementação

Os principais frameworks utilizados foram:
1) Spring Boot: para setup inicial da aplicação e das bibliotecas necessárias;
2) Lombok: para a geração de código, como getters, setters, logs e do design pattern builder. A anotação @ToString está sendo utilizada, mas caso seja necessário ocultar dados sensíveis do log, então deve-se adotar a implementação manual avaliando caso a caso. Um cuidado que foi tomado está em evitar o uso de @Setter em classes onde tais métodos não são necessários;
3) Model Mapper: a ideia aqui foi evitar acoplamento entre os contratos de mensageria definidos no rabbit, e a camuda de serviço da aplicação. Desta forma, foram criados beans específicios de request e response para cada fila, convertendo-os utilizando este framework para objetos conhecidos da camada de serviço. Assim evitando possíveis quebras de contrato acidentais. A escolha deste framework diante de outros é devido a seu teste de performance, estando entre os dois melhores avaliados: https://www.baeldung.com/java-performance-mapping-frameworks
4) Jackson: as annotations @JsonProperty foram utilizadas de forma a evitar que possívei refatoração (troca de nome de variáveis dos beans de contrato) quebrem o contrato definidos na camada de mensageria;
5) Bean Validation: Para a validação de campos obrigatórios utilizou-se as anotações da específicação Bean Validation, utilizando o contexto do Spring para sua execução;
6) Spring Data: para persistência e consulta aos dados no BD;
7) Apache Commons: foram utilizadas as bibliotecas lang 3 e collection 4 para métodos utilitários de verificações relacionadas a strings e listas;
8) Spring: principalmente para injeção de dependência na camada de serviço;
9) JUnit e Mockito: para os testes.

O código desenvolvido adota os design patterns Factory e Strategy, utilizando Spring, para possibilitar o desenvolvimento de diversos tipos de regras, podendo customizar suas validações (quando inseridas) e sua execução (quando a URL é validada). Inicialmente a aplicação suporta apenas expressões regulares, utilizando a síntaxe nativa do Java. Para tal, existem duas operações, uma de inserçao de uma expressão regular, e outra para aplicação de regras em uma URL, mais detalhadas logo abaixo. Também criou-se uma interface chamada CrudTemplateService para definir as assinaturas e validações relacionadas as classes de serviço persistentes (que salvam dados no BD).

Para a organização de pacotes decidiu-se neste não utilizar o tradicional modelo em camadas (model, service, etc.), e sim o modelo orientado a domínio, ou seja, as classes relacionadas ao mesmo domínio estão agrupadas sob a mesma hierarquia.

A parte de logs recebeu um cuidado especial, buscando sempre manter um tracking claro de cada etapa de execução de cada processo da aplicação. Para tal, também está sendo impresso a thread, permitindo identificar todos os logs pertencentes ao mesmo processo. Futuramente pretende-se adotar o uso de Mapped Diagnostic Context (MDC) para que este tracking seja possível por meio de um hash gerado em um interceptor.

Quanto ao tratamento de exceções, buscou-se isolar o código ao máximo, permitindo um log, e eventualmente algum tratamento mais adequado, para cada etapa do processo. Exemplo: conversão de um objeto em JSON, isolado em um método com tratamento específico de try/catch e log apropriado em caso de erro, informando o motivo, os inputs, e o que será feito na sequencia (processo será abortado? pulado? etc.)

### Modificações da proposta (problema) inicial
O problema inicial especificava o tratamento apenas de expressões regulares. Por querer realizar algo mais flexível, e mostrar o uso de alguns design patterns, optei por ter regras genéricas com um campo de tipo. O único tipo existente atualmente na aplicação é o de expressão regular, mas diante da eventual necessidade de se expandir o suporte a outros formatos, como por exemplo uma Spring Expression Languae (sPEL), para validação das URLs, foi adotada uma solução genérica, onde apenas com a criação de um novo tipo, do seu validador (opcional, pois há um default implementado que apenas verifica se o campo não está em branco, para ser utilizado no momento da inserção na whitelist) e do seu executor (chamado de evaluator na aplicação, onde defini-se como a regra será executada) possibilita este suporte de forma transparente e flexível.

Para manter suporte ao contrato, onde existe um campo "regex", o que não permite a flexibilidade sobre o tipo de regra, optou-se por possuir um consumidor específico para expressões regulares, havendo a conversão do objeto do contrato de entrada para um objeto que segue as conversões e negócio (o que incluir os campos "tipo" e se a regra é ou não global).

### Pontos analisados e não desenvolvidos
Tentou-se tratar diretamente o objeto Message do RabbitMQ, realizando posteriormente a deserialização do body da mensagem de acordo com o content-type, e assim possibilitando o tratamento devido a uma eventual exceção que possa surgir durante este processo, como por exemplo um erro de sintaxe no JSON enviado. Entretanto, pontos relacionados a performance da implementação manual deste processo, assim como o aumento da complexidade do código me fizeram a evitar este caminho, deixando tal responsabilidade de deserializar o conteúdo e convertê-lo no respectivo objeto Java para o framework do Rabbit. O ponto negativo desta abordagem é o excesso de erros exibidos ao enviar um conteúdo inválido e/ou não suportado, algumas vezes com mensagens de erros não claras nos logs.

Seria interessante também o desenvolvimento de um teste integrado, utilizando Rest Assured, através de outra máquina no docker, que simulasse o envio de mensagens e verificasse os resultados obtidos. No entanto, não foi realizado nesta versão inicial.

### Testes
A cobertura de testes está em apenas 54%, pois foram realizados alguns poucos cenários de testes, com o objetivo de demonstrar parte do meu conhecimento no uso do Mockito e AssertJ.
