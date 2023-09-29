# DESAFIO 4

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

## Débito Técnico Identificado

- O maior débito técnico identificado na aplicação, inclusive sendo uma falha de segurança bem grave, é não exigir autenticação dos usuários que utilizam o sistema. Como são realizados cadastros de pessoas físicas e jurídicas, existe manipulação de dados sensíveis, estes que devem estar de acordo com todos os parâmetros da LGPD (Lei Geral de Proteção de Dados), por exemplo. A falha em guardar corretamente e não expor esses dados pode acarretar em uma multa salgada para a Cielo, o que não é interessante para nenhum dos envolvidos no ocorrido.

- Como resolução para este problema, implementei o sistema de registro, login e logout de usuários internos do sistema utilizando o `Spring Security`. Por motivos de demonstração e simplicidade, optei por realizar todo o processo in-memory, porém é importante ressaltar que em produção estes dados de autenticação estariam sendo persistidos em um banco de dados ou provedor de OAuth/SSO, além de contar com a disponibilização de tokens JWT para manter a comunicação entre o cliente e o servidor Stateless.
