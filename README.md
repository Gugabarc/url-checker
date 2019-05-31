# url-checker

Foi desenvolvido um microsserviço de validação de ameaças em potencial, com o objetivo de descartar URLs legítimas utilizando uma whitelist de regras. A whitelist é formada por regras aplicáveis a clientes específicos e por regras globais, aplicáveis a todos os clientes. O microsserviço foi desenvolvido em Java e tem integração com o message broker RabbitMQ e com o banco de dados MySQL. Os três componentes (microsserviço, RabbitMQ e MySQL) são executados em containers Docker.

Os principais frameworks utilizados foram:
1) Spring Boot: para setup inicial da aplicação e das bibliotecas necessárias;
2) Lombok: para a geração de código, como getters, setters, logs e do design pattern builder. A anotação @ToString foi evitada por enquanto, devido a possíveis nullpointers que possam surgir no código gerado. No contexto atual isto não ocorreria, mas optou-se por desde já implementar os toStrings;
3) Model Mapper: a ideia aqui foi evitar acoplamento entre os contratos de mensageria definidos no rabbit, e a camuda de serviço da aplicação. Desta forma, foram criados beans específicios de request e response para cada fila, convertendo-os utilizando este framework para objetos conhecidos da camada de serviço. Assim evitando possíveis quebras de contrato acidentais;
4) Jackson: as annotations @JsonProperty foram utilizadas de forma a evitar que possívei refatoração (troca de nome de variáveis dos beans de contrato) quebrem o contrato definidos na camada de mensageria;
5) Bean Validation: Para a validação de campos obrigatórios utilizou-se as anotações da específicação Bean Validation, utilizando o contexto do Spring para sua execução;
6) Spring Data: para persistência e consulta aos dados no BD;
7) Apache Commons: foram utilizadas as bibliotecas lang 3 e collection 4 para métodos utilitários de verificações relacionadas a strings e listas;
8) Spring: principalmente para injeção de dependência na camada de serviço;
9) JUnit e Mockito: para os testes.

O código desenvolvido adota os design patterns Factory e Strategy, utilizando Spring, para possibilitar o desenvolvimento de diversos tipos de regras, podendo customizar suas validações (quando inseridas) e sua execução (quando a URL é validada). Inicialmente a aplicação suporta apenas expressões regulares, utilizando a síntaxe nativa do Java. Para tal, existem duas operações, uma de inserçao de uma expressão regular, e outra para aplicação de regras em uma URL, mais detalhadas logo abaixo. Também criou-se uma interface chamada CrudTemplateService para definir as assinaturas e validações relacionadas as classes de serviço persistentes (que salvam dados no BD).

Para a organização de pacotes decidiu-se neste não utilizar o tradicional modelo em camadas (model, service, etc.), e sim o modelo orientado a domínio, ou seja, as classes relacionadas ao mesmo domínio estão agrupadas sob a mesma hierarquia.

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

A imagem Docker do microsserviço é construída a partir de um Dockerfile, utilizando como base a imagem openjdk:8-jre-alpine .
