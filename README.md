# Sistema de Gestão de Vendas

O sistema de gestão de vendas é um software destinado ao controle de suas vendas por meio de uma aplicação simples e agradável. Oferece funcionalidades para cadastrar vendas, consultar vendas, cadastrar produtos e cadastrar clientes, todas com interfaces intuitivas e uma ótima experiência de uso.

## Como Executar o Projeto

Para rodar o projeto, siga os seguintes passos:

1. Faça o clone do repositório em sua máquina.
2. Dentro da classe `DatabaseService`, configure o banco de dados escolhido, o usuário e a senha necessários para criar as tabelas automaticamente.

   ```java
   public class DatabaseService {
       // Configure o URL, usuário e senha do banco de dados
       String url = "jdbc:postgresql://localhost:5432/SeuBancoDeDados";
       String user = "SeuUsuario";
       String password = "SuaSenha";
       // ...
   }
   ```

3. Existem duas formas de compilar e executar o projeto:
   - Utilizando a IDE de sua escolha.
   - Através do terminal (cmd) navegando até a classe `Main` dentro do diretório do projeto:

     ```
     C:\Caminho\Para\O\Repositorio\vr-vendas\src
     ```

     Em seguida, digite os comandos:
     - `javac Main` para compilar o arquivo.
     - `java Main` para executar o programa.

   Certifique-se de que o seu banco de dados esteja em execução e configurado corretamente.

## Documentação das Services

A seguir, detalhamos as classes que compõem o sistema e suas funcionalidades:

### Classe `ClienteService`

A classe `ClienteService` é responsável por interagir com o banco de dados para realizar operações relacionadas a informações de clientes. Ela oferece as seguintes funcionalidades:

1. `getAllClientes()`: Recupera uma lista de todos os clientes do banco de dados e os retorna como uma lista de objetos `Cliente`.

2. `cadastrarCliente(String nome)`: Permite adicionar um novo cliente ao banco de dados. Você fornece o nome do cliente como parâmetro, e ele insere o cliente no banco de dados.

3. `getClienteById(int clienteId)`: Recupera um cliente específico pelo seu ID no banco de dados e o retorna como um objeto `Cliente`.

Certifique-se de ter uma conexão com o banco de dados configurada antes de usar esta classe, pois ela depende de uma conexão com o banco de dados obtida por meio da classe `DatabaseService`.

### Classe `DatabaseService`

A classe `DatabaseService` é responsável por gerenciar a conexão com um banco de dados PostgreSQL. Ela oferece as seguintes funcionalidades:

1. `connectToDatabase()`: Estabelece uma conexão com o banco de dados PostgreSQL usando as credenciais fornecidas (URL, usuário e senha). Em caso de sucesso, exibe uma mensagem de confirmação.

2. `createTables()`: Cria as tabelas no banco de dados, se ainda não existirem. Verifica a existência das tabelas e as cria, se necessário, além de definir chaves estrangeiras para estabelecer relações entre as tabelas.

3. `getConnection()`: Retorna a conexão com o banco de dados, que pode ser utilizada por outras partes da aplicação para executar consultas.

4. `main(String[] args)`: O método `main` é executado quando a classe é inicializada. Ele inicia a conexão com o banco de dados, verifica se a conexão foi bem-sucedida e chama o método `createTables()` para criar as tabelas no banco de dados quando a aplicação é iniciada.

Certifique-se de que as informações de conexão, como URL, usuário e senha do banco de dados, estejam configuradas corretamente antes de usar esta classe. Ela é fundamental para estabelecer e gerenciar a conexão com o banco de dados PostgreSQL em sua aplicação Java.

### Classe `ProdutoService`

A classe `ProdutoService` é responsável por gerenciar operações relacionadas a produtos em um banco de dados. Ela oferece as seguintes funcionalidades:

1. `getAllProdutos()`: Recupera todos os produtos do banco de dados e os retorna como uma lista de objetos `Produto`, contendo informações como ID, descrição, preço e quantidade.

2. `cadastrarProduto(String descricao, double preco, int quantidade)`: Permite adicionar um novo produto ao banco de dados. Você fornece a descrição, preço e quantidade do produto como parâmetros, e ele insere o produto no banco de dados.

3. `getProdutoById(int id)`: Recupera um produto específico pelo seu ID a partir do banco de dados e o retorna como um objeto `Produto`.

4. `atualizarQuantidadeProduto(int produtoId, int quantidadeVendida)`: Atualiza a quantidade de um produto após uma venda. Calcula a nova quantidade com base na quantidade vendida e atualiza o banco de dados.

A classe utiliza a conexão com o banco de dados obtida a partir da classe `DatabaseService` para executar operações relacionadas aos produtos.

Certifique-se de que a conexão com o banco de dados esteja configurada e ativa antes de utilizar esta classe. Ela facilita a interação com dados de produtos em sua aplicação Java, permitindo a recuperação, inserção e atualização de informações de produtos.

### Classe `VendaService`

A classe `VendaService` é responsável por gerenciar operações relacionadas a vendas em um banco de dados. Ela oferece as seguintes funcionalidades:

1. `cadastrarVenda(Venda venda, List<ItemVenda> itensVenda)`: Permite cadastrar uma nova venda no banco de dados, juntamente com os itens vendidos. Realiza diversas ações, incluindo a inserção da venda, obtenção do ID gerado, inserção dos itens vendidos, atualização da quantidade de produtos no estoque e a confirmação das alterações no banco de dados.

2. `getAllVendas()`: Recupera todas as vendas registradas no banco de dados e as retorna como uma lista de objetos `Venda`.

3. `getItensVenda(int vendaId)`: Recupera os itens vendidos relacionados a uma venda específica a partir do banco de dados e os retorna como uma lista de objetos `ItemVenda`.

4. `getVendaById(int vendaId)`: Obtém informações sobre uma venda específica

 com base no ID da venda.

5. `estornarVenda(int vendaId)`: Permite estornar uma venda previamente registrada no banco de dados. Realiza ações como verificar se a venda já foi estornada anteriormente, atualizar o status da venda para "estornada" no banco de dados e atualizar a quantidade de produtos no estoque.

A classe utiliza a conexão com o banco de dados obtida a partir da classe `DatabaseService` para executar operações relacionadas às vendas.

Certifique-se de que a conexão com o banco de dados esteja configurada e ativa antes de utilizar esta classe. Ela facilita a interação com dados de vendas e itens de venda em sua aplicação Java, permitindo o cadastro, consulta e estorno de vendas.

 ##

 Desenvolvido por Pedro Bonini :)
