# url-checker

Foi desenvolvido um microsserviço de validação de ameaças em potencial, com o objetivo de descartar URLs legítimas utilizando uma whitelist de regras. A whitelist é formada por regras aplicáveis a clientes específicos e por regras globais, aplicáveis a todos os clientes. O microsserviço foi desenvolvido em Java e tem integração com o message broker RabbitMQ e com o banco de dados MySQL. Os três componentes (microsserviço, RabbitMQ e MySQL) são executados em containers Docker.

O código desenvolvido adota os design patterns Factory e Strategy para possibilitar o desenvolvimento de diversos tipos de regras, podendo customizar suas validações (quando inseridas) e sua execução (quando a URL é validada). Inicialmente a aplicação suporta apenas expressões regulares, utilizando a síntaxe nativa do Java. Para tal, existem duas operações, uma de inserçao de uma expressão regular, e outra para aplicação de regras em uma URL, mais detalhadas logo abaixo.

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
