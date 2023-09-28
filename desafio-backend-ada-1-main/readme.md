# DESAFIO 1

## Como Executar a Aplicação

- Para executar a aplicação, é recomendado utilizar a IDE `Intellij IDEA` da Jetbrains, que cuidará de realizar o processo de compilação e configuração das dependências através do `Gradle`. A partir deste momento, basta executar a classe DesafioBackendApplication.

- Com o servidor rodando, é possível acessar a documentação da API na interface do `Swagger` através da URL: <http://localhost:8080/swagger-ui.html>. Lá estão documentadas as funcionalidades requisitadas no desafio, podendo conferir os valores de entrada, retorno e testar cada um dos endpoints.

## Como Executar os Testes com Cobertura

- Utilizando a `Intellij`, basta clicar com o botão direito do mouse sob o diretório `test` e selecionar a opção `More Run/Debug -> Run '...' With Coverage`.

## Decisões Técnicas

- Decidi desacoplar os métodos de listagem e a fonte de persistência para `PessoaJuridica` e `PessoaFisica`, levando em consideração que utilizando um banco de dados relacional, estas entidades seriam armazenadas em duas tabelas diferentes, com suas propriedades pertinentes.

- Manti os limites arquiteturais bem separados, buscando facilitar na hora de injetar dependências e mocks para os testes unitários, também conformando com o Single Responsibility Principle do SOLID.
