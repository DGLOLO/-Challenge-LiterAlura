# 📚 Challenge LiterAlura

## 🎯 Descrição do Projeto

O **Challenge LiterAlura** é uma aplicação Java Spring Boot que consome a API pública [Gutendex](https://gutendex.com/) para buscar e gerenciar informações sobre livros e autores. O projeto demonstra o uso de tecnologias modernas de desenvolvimento Java, incluindo Spring Data JPA, banco de dados H2, e consumo de APIs REST.

## 🚀 Funcionalidades Implementadas

### 🔍 Busca e Consulta na API
- **Busca de livros por título** - Pesquisa livros na API Gutendex
- **Listagem de livros mais populares** - Ordenados por número de downloads
- **Busca por idioma** - Filtra livros por código de idioma (en, es, fr, pt)
- **Busca por autor** - Encontra livros de autores específicos
- **Estatísticas da API** - Informações sobre total de livros e distribuição por idioma

### 💾 Persistência de Dados
- **Banco de dados H2** - Armazenamento em memória com console web
- **Entidades JPA** - Modelagem de Livro e Autor com relacionamentos
- **Repositórios Spring Data** - Queries personalizadas e derived queries
- **Salvamento de livros** - Conversão de DTOs para entidades e persistência

### 📊 Estatísticas e Relatórios
- **Contagem por idioma** - Quantidade de livros em cada idioma
- **Top livros mais baixados** - Ranking dos livros mais populares
- **Autores vivos em determinado ano** - Consulta histórica por período
- **Estatísticas do banco local** - Resumo completo dos dados salvos

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17+** - Linguagem de programação
- **Spring Boot 3.5.4** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Hibernate** - ORM (Object-Relational Mapping)
- **H2 Database** - Banco de dados em memória

### Dependências
- **Jackson** - Serialização/deserialização JSON
- **Spring Boot Starter Web** - Aplicação web
- **Spring Boot Starter Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados

## 🏗️ Arquitetura do Projeto

### Estrutura de Pacotes
```
src/main/java/Danilo/Challenge_LiterAlura/
├── ChallengeLiterAluraApplication.java    # Classe principal
├── dto/                                  # Data Transfer Objects
│   ├── RespostaApiDTO.java              # Resposta da API
│   ├── LivroDTO.java                    # DTO do livro
│   ├── AutorDTO.java                    # DTO do autor
│   └── FormatosDTO.java                 # DTO dos formatos
├── model/                                # Entidades JPA
│   ├── Livro.java                       # Entidade Livro
│   └── Autor.java                       # Entidade Autor
├── repository/                           # Repositórios JPA
│   ├── LivroRepository.java             # Repositório de livros
│   └── AutorRepository.java             # Repositório de autores
└── service/                              # Serviços de negócio
    ├── ConsumoApi.java                  # Consumo da API externa
    └── LivroService.java                # Lógica de negócio
```

### Padrões de Design
- **DTO Pattern** - Separação entre dados da API e entidades do banco
- **Repository Pattern** - Abstração da camada de persistência
- **Service Layer** - Lógica de negócio centralizada
- **Command Line Runner** - Interface de usuário via console

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Conexão com internet (para consumir a API)

### Passos para Execução

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd Challenge-LiterAlura
```

2. **Compile o projeto**
```bash
./mvnw.cmd clean compile
```

3. **Execute a aplicação**
```bash
./mvnw.cmd spring-boot:run
```

4. **Acesse o console H2** (opcional)
```
URL: http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:liter_alura
Username: sa
Password: (deixe em branco)
```

## 📱 Como Usar

### Menu Principal
A aplicação apresenta um menu interativo com as seguintes opções:

1. **Buscar livros por título (API)** - Pesquisa livros na API externa
2. **Listar livros mais populares (API)** - Top livros por downloads
3. **Buscar livros por idioma (API)** - Filtra por idioma
4. **Buscar livros por autor (API)** - Encontra livros de autores específicos
5. **Mostrar estatísticas da API** - Resumo dos dados da API
6. **Salvar livros da API no banco local** - Persiste dados no banco
7. **Estatísticas do banco local** - Informações dos dados salvos
8. **Buscar livros no banco local** - Consultas locais
9. **Buscar autores vivos em determinado ano** - Consulta histórica
0. **Sair** - Encerra a aplicação

### Exemplos de Uso

#### Buscar Livros por Título
```
Escolha uma opção: 1
Digite o título ou parte do título: Moby Dick
```

#### Salvar Livros no Banco
```
Escolha uma opção: 6
Quantos livros deseja salvar? (1-20): 10
```

#### Consultar Autores Vivos
```
Escolha uma opção: 9
Digite o ano: 1850
```

## 🗄️ Modelo de Dados

### Entidade Livro
- **id** - Identificador único (auto-gerado)
- **titulo** - Título do livro
- **autor** - Relacionamento com Autor (ManyToOne)
- **idiomas** - Lista de idiomas disponíveis
- **downloads** - Número de downloads
- **apiId** - ID original da API externa

### Entidade Autor
- **id** - Identificador único (auto-gerado)
- **nome** - Nome do autor
- **anoNascimento** - Ano de nascimento
- **anoFalecimento** - Ano de falecimento (pode ser null)
- **livros** - Lista de livros do autor (OneToMany)

## 🔧 Configurações

### application.properties
```properties
# Configurações do banco H2
spring.datasource.url=jdbc:h2:mem:liter_alura
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Configurações JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Porta da aplicação
server.port=8081
```

## 📊 API Externa

### Gutendex API
- **URL Base**: https://gutendex.com/books/
- **Formato**: JSON
- **Autenticação**: Não requerida
- **Rate Limit**: Não especificado

### Estrutura da Resposta
```json
{
  "count": 76493,
  "next": "https://gutendex.com/books/?page=2",
  "previous": null,
  "results": [
    {
      "id": 2701,
      "title": "Moby Dick; Or, The Whale",
      "authors": [...],
      "languages": ["en"],
      "download_count": 115502,
      "formats": {...}
    }
  ]
}
```

## 🧪 Testes e Validações

### Validações Implementadas
- **Ano válido** - Entre 1000 e 2100 para consultas históricas
- **Quantidade de livros** - Entre 1 e 20 para salvamento
- **Entrada numérica** - Validação de tipos de entrada
- **Tratamento de erros** - Try-catch em todas as operações

### Tratamento de Exceções
- **NumberFormatException** - Entrada inválida
- **IOException** - Problemas de conexão com API
- **JsonProcessingException** - Erro no processamento JSON
- **RuntimeException** - Erros gerais da aplicação

## 🎯 Objetivos do Desafio

Este projeto foi desenvolvido para demonstrar:

1. **Consumo de APIs REST** - Integração com serviços externos
2. **Persistência de dados** - Uso de JPA e banco de dados
3. **Arquitetura limpa** - Separação de responsabilidades
4. **Tratamento de erros** - Robustez da aplicação
5. **Interface de usuário** - Menu interativo via console
6. **Queries personalizadas** - Uso de JPQL e derived queries
7. **Streams Java** - Processamento funcional de dados
8. **Relacionamentos JPA** - Mapeamento objeto-relacional

## 🚀 Melhorias Futuras

- **Interface web** - Substituir console por interface gráfica
- **Cache Redis** - Melhorar performance de consultas
- **Testes unitários** - Cobertura de testes
- **Documentação API** - Swagger/OpenAPI
- **Logs estruturados** - Logback com JSON
- **Métricas** - Actuator e Prometheus
- **Docker** - Containerização da aplicação

## 👨‍💻 Desenvolvedor

**Danilo** - Desenvolvedor Java Full Stack


---

**⭐ Se este projeto foi útil, considere dar uma estrela!** 