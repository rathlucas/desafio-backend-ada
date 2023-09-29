# DESAFIO 3

## Como Executar a Aplicação

- Antes de executar a aplicação, é necessário adicionar algumas variáveis ao arquivo `application.properties`, dentro do diretório `resources`. Essas váriaveis serão responsáveis por configurar a comunicação com o `AWS SQS`, sendo elas:

  - spring.cloud.aws.credentials.access-key=`exemplo`
  - spring.cloud.aws.credentials.secret-key=`exemplo`
  - spring.cloud.aws.region.static=`us-east-1`
  - spring.cloud.aws.sqs.endpoint=`exemplo`
  - spring.cloud.aws.sqs.endpoint=`exemplo`
  - sqs.queue.name`nome-da-fila`

- Para executar a aplicação, é recomendado utilizar a IDE `Intellij IDEA` da Jetbrains, que cuidará de realizar o processo de compilação e configuração das dependências através do `Gradle`. A partir deste momento, basta executar a classe DesafioBackendApplication.

- Com o servidor rodando, é possível acessar a documentação da API na interface do `Swagger` através da URL: <http://localhost:8080/swagger-ui.html>. Lá estão documentadas as funcionalidades requisitadas no desafio, podendo conferir os valores de entrada, retorno e testar cada um dos endpoints.

## Como Executar os Testes com Cobertura

- Utilizando a `Intellij`, basta clicar com o botão direito do mouse sob o diretório `test` e selecionar a opção `More Run/Debug -> Run '...' With Coverage`.

## Decisões Técnicas

- Desenhei um diagrama da arquitetura da integração com a AWS, de uma visão geral mais abstraída para que fique de mais fácil compreensão. Esse diagrama pode ser encontrado no mesmo diretório que esta documentação.

- Criei um Serviço específico `AwsSqsQueueService`, responsável por abstrair os detalhes do envio e recebimento de mensagens da Fila SQS.
