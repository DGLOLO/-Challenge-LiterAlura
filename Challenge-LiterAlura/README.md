# Challenge LiterAlura - Consumindo a API Gutendex

Este projeto implementa um cliente Java para consumir a API Gutendex, que fornece acesso ao catálogo de mais de 70 mil livros do Project Gutenberg.

## 🚀 Funcionalidades Implementadas

### 1. **HttpClient** - Cliente para Requisições
- Configuração robusta do cliente HTTP
- Suporte a redirecionamentos
- Headers personalizados (Accept, User-Agent)
- Tratamento de erros HTTP

### 2. **HttpRequest** - Construção de Solicitações
- Configuração de parâmetros de busca
- Headers customizados para APIs REST
- Suporte a diferentes tipos de requisição

### 3. **HttpResponse** - Gerenciamento de Respostas
- Tratamento de códigos de status HTTP
- Extração do corpo da resposta (JSON)
- Validação de respostas bem-sucedidas

### 4. **Jackson 2.16** - Análise Avançada de JSON
- **JsonNode** para navegação flexível
- **Path expressions** para extração precisa
- **Filtros avançados** por critérios
- **Ordenação dinâmica** de resultados
- **Validação de estrutura** JSON
- **Estatísticas detalhadas** dos dados
- **Conversão bidirecional** JsonNode ↔ Objeto

### 5. **Persistência com PostgreSQL** - Banco de Dados
- **JPA/Hibernate** para mapeamento objeto-relacional
- **Repositórios Spring Data** para operações CRUD
- **Relacionamentos** entre Livros e Autores
- **Consultas personalizadas** com JPQL
- **Transações** para integridade dos dados

### 6. **Interface de Usuário** - Menu Interativo
- **CommandLineRunner** para execução automática
- **Menu numerado** com 9 opções principais
- **Validação de entrada** do usuário
- **Tratamento de erros** robusto
- **Scanner** para captura de dados

## 🏗️ Arquitetura do Sistema

```
src/main/java/Danilo/Challenge_LiterAlura/
├── model/                          # Modelos de dados
│   ├── Livro.java                 # Entidade principal do livro
│   ├── Autor.java                 # Informações do autor
│   └── Formatos.java              # Formatos disponíveis
├── service/                        # Camada de serviços
│   ├── ConsumoApi.java            # Cliente HTTP
│   ├── ConverteDados.java         # Conversor JSON
│   ├── IConverteDados.java        # Interface do conversor
│   ├── LivroService.java          # Lógica de negócio
│   ├── JsonAnalyserService.java   # Análise avançada de JSON
│   ├── PersistenciaService.java   # Persistência de dados
│   └── CatalogoService.java       # Serviço principal integrado
├── repository/                     # Camada de persistência
│   ├── LivroRepository.java       # Repositório de livros
│   └── AutorRepository.java       # Repositório de autores
├── interface/                      # Interface de usuário
│   └── InterfaceUsuario.java      # Menu interativo
└── ChallengeLiterAluraApplication.java
```

## 📚 Operações Disponíveis

### **API e Conversão**
```java
// Busca por título
List<Livro> livros = livroService.buscarLivrosPorTitulo("Dom Quixote");

// Busca por autor
List<Livro> livros = livroService.buscarLivrosPorAutor("Machado de Assis");

// Análise avançada de JSON
String titulo = jsonAnalyser.extractValue(json, "results[0].title");
String filtrado = jsonAnalyser.filterBooksByCriteria(json, "title", "Quixote");
```

### **Persistência e Consultas**
```java
// Salvar livro na base
Livro livro = catalogoService.buscarESalvarLivro("Dom Quixote");

// Consultas no banco
List<Livro> livros = catalogoService.listarLivrosSalvos();
List<Autor> autores = catalogoService.listarAutores();
Long quantidade = catalogoService.contarLivrosPorIdioma("pt");
```

### **Funcionalidades Avançadas**
```java
// Autores vivos em um ano
List<Autor> autores = catalogoService.buscarAutoresVivosEmAno(1900);

// Estatísticas do catálogo
String stats = catalogoService.obterEstatisticas();

// Livros por downloads
List<Livro> populares = catalogoService.listarLivrosPorDownloads();
```

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem principal
- **Spring Boot 3.5.4** - Framework base
- **HttpClient** - Cliente HTTP nativo do Java
- **Jackson** - Processamento de JSON
- **Maven** - Gerenciamento de dependências

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- PostgreSQL instalado e configurado

### Configuração do Banco
1. Crie um banco PostgreSQL chamado `liter_alura`
2. Configure usuário e senha no `application.properties`
3. Ou ajuste as configurações conforme seu ambiente

### Execução
```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação principal (com interface)
mvn spring-boot:run

# Executar demonstrações específicas (se necessário)
# mvn exec:java -Dexec.mainClass="Danilo.Challenge_LiterAlura.JacksonDemo"

# Ou executar via IDE
# Execute ChallengeLiterAluraApplication.java para a aplicação completa
```

## 📖 Exemplo de Uso

```java
// Inicializar serviços
ConsumoApi consumoApi = new ConsumoApi();
ConverteDados conversor = new ConverteDados();
LivroService livroService = new LivroService(consumoApi, conversor);

// Buscar livros
List<Livro> livros = livroService.buscarLivrosPorTitulo("Dom Quixote");
livros.forEach(livro -> {
    System.out.println("Título: " + livro.getTitulo());
    System.out.println("Downloads: " + livro.getDownloads());
});
```

## 🔧 Configurações da API

- **URL Base**: `https://gutendex.com/books/`
- **Formato**: JSON
- **Rate Limiting**: Sem limitações conhecidas
- **Suporte**: CORS habilitado

## 📝 Estrutura dos Dados

### Livro
- ID único
- Título
- Lista de autores
- Idiomas disponíveis
- Contador de downloads
- Formatos disponíveis (HTML, EPUB, PDF, etc.)

### Autor
- Nome
- Ano de nascimento
- Ano de falecimento

## 🎯 Próximos Passos

1. **Persistência**: Implementar banco de dados
2. **Cache**: Adicionar cache para requisições
3. **Validação**: Implementar validação de dados
4. **Testes**: Adicionar testes unitários
5. **API REST**: Criar endpoints REST
6. **Interface Web**: Desenvolver interface gráfica

## 📄 Licença

Este projeto é parte do Challenge LiterAlura da Alura. 