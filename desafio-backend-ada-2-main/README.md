# DESAFIO 2

## Como Executar a Aplicação

- Para executar a aplicação, é recomendado utilizar a IDE `Intellij IDEA` da Jetbrains, que cuidará de realizar o processo de compilação e configuração das dependências através do `Gradle`. A partir deste momento, basta executar a classe DesafioBackendApplication.

- Com o servidor rodando, é possível acessar a documentação da API na interface do `Swagger` através da URL: <http://localhost:8080/swagger-ui.html>. Lá estão documentadas as funcionalidades requisitadas no desafio, podendo conferir os valores de entrada, retorno e testar cada um dos endpoints.

## Como Executar os Testes com CobertuA

- Utilizando a `Intellij`, basta clicar com o botão direito do mouse sob o diretório `test` e selecionar a opção `More Run/Debug -> Run '...' With Coverage`.

## Decisões Técnicas

- Criei uma interface de Fila genérica, levando em consideração que uma estrutura de dados deve ser agnóstica de seu tipo, por assim dizer. Também decidir manter a implementação da Fila completa dentro do repositório de Clientes, já que as operações dos dois estão intimamente relacionadas, com a camada de Serviço delegando o que precisa para que haja o retorno ao Controller.

- Utilizei nas operações de enqueue e dequeue o operador de resto da divisão (ou módulo) `%`, ajudando na implementação de uma Array Circular para conformar com o esquema FIFO pedido no desafio.
