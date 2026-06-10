# 📋 TaskManager — Gerenciador de Tarefas

## 📌 Descrição
O TaskManager é uma aplicação desktop desenvolvida em Java com interface
gráfica JavaFX, persistência de dados em banco MySQL via JDBC, estruturas
genéricas com Generics e testes automatizados com JUnit 5.

Este projeto foi desenvolvido como entrega da Unidade 5 | Capítulo 1 do
módulo intermediário do curso Capacita iRede — Residência em TIC 20.

---

## 👩‍💻 Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|-----------|--------|-----------|
| Java | JDK 25 | Linguagem principal |
| JavaFX | 25 | Interface gráfica |
| MySQL | 8.0 | Banco de dados |
| JDBC | — | Conexão com banco |
| JUnit | 5.10.2 | Testes automatizados |
| Maven | 3.x | Gerenciamento de dependências |

---

## 🏗️ Estrutura do Projeto
TaskManager/
├── src/
│   ├── main/
│   │   ├── java/com/taskmanager/
│   │   │   ├── MainApplication.java      # Ponto de entrada JavaFX
│   │   │   ├── model/
│   │   │   │   └── Tarefa.java           # Entidade principal
│   │   │   ├── db/
│   │   │   │   └── Conexao.java          # Gerencia conexão JDBC
│   │   │   ├── dao/
│   │   │   │   ├── GenericDAO.java       # Interface genérica (Generics)
│   │   │   │   └── TarefaDAO.java        # CRUD completo
│   │   │   └── controller/
│   │   │       ├── MainController.java   # Tela principal
│   │   │       └── FormController.java   # Tela de cadastro/edição
│   │   └── resources/com/taskmanager/
│   │       ├── main.fxml                 # Layout tela principal
│   │       ├── form.fxml                 # Layout formulário
│   │       └── style.css                 # Estilização
│   └── test/java/com/taskmanager/
│       ├── TarefaTest.java               # Testes da entidade
│       ├── TarefaDAOTest.java            # Testes do DAO
│       └── TarefaDAOTestHelper.java      # Helper para testes
├── pom.xml                               # Dependências Maven
└── README.md                             # Este arquivo

---

## 🧩 Conceitos Aplicados

### 1. Generics
- Interface `GenericDAO<T>` com métodos genéricos: `inserir`, `atualizar`,
  `deletar`, `buscarPorId` e `listarTodos`
- `TarefaDAO` implementa `GenericDAO<Tarefa>` para operações específicas

### 2. JavaFX — Interface Gráfica
- `MainApplication` estende `Application` e inicia a aplicação
- **Tela Principal:** lista todas as tarefas com opções de marcar, editar
  e remover
- **Tela de Formulário:** cadastro e edição de tarefas com validação
- Componentes usados: `TableView`, `TextField`, `TextArea`, `CheckBox`,
  `Button`, `Label`
- Layouts: `BorderPane`, `VBox`, `HBox`
- Separação entre lógica e visual com arquivos `.fxml`
- Estilização com arquivo `.css`

### 3. JDBC — Banco de Dados
- Conexão com MySQL via `DriverManager`
- Operações CRUD com `PreparedStatement` e `ResultSet`
- Proteção contra SQL Injection com `PreparedStatement`
- Padrão DAO para isolar a persistência
- Tabela `tarefas` com campos: `id`, `titulo`, `descricao`, `concluida`

### 4. JUnit 5 — Testes Automatizados
- Testes unitários da classe `Tarefa`
- Testes do DAO usando SQLite em memória
- Anotações: `@Test`, `@BeforeEach`, `@AfterEach`, `@DisplayName`
- Assertions: `assertEquals`, `assertTrue`, `assertFalse`, `assertNull`,
  `assertNotNull`, `assertThrows`

---

## 🗄️ Configuração do Banco de Dados

### 1. Criar o banco
```sql
CREATE DATABASE IF NOT EXISTS taskmanager_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

### 2. A tabela é criada automaticamente ao rodar o projeto
```sql
CREATE TABLE IF NOT EXISTS tarefas (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    titulo    VARCHAR(100) NOT NULL,
    descricao TEXT,
    concluida BOOLEAN NOT NULL DEFAULT FALSE
);
```

---

## ⚙️ Como Configurar e Rodar

### Pré-requisitos
- JDK 25 instalado
- MySQL 8.0 rodando
- Maven instalado (ou usar o embutido do IntelliJ)

### Passo 1 — Clonar/Extrair o projeto
```bash
# Extraia o ZIP em uma pasta de sua preferência
```

### Passo 2 — Configurar a senha do banco
Abra o arquivo:Altere a linha:
```java
private static final String SENHA = "sua_senha_aqui";
```

### Passo 3 — Criar o banco de dados
```sql
CREATE DATABASE IF NOT EXISTS taskmanager_db;
```

### Passo 4 — Rodar o projeto
```bash
mvn javafx:run
```

### Passo 5 — Rodar os testes
```bash
mvn test
```

---

## 🖥️ Funcionalidades

- ✅ Listar todas as tarefas
- ✅ Adicionar nova tarefa
- ✅ Editar tarefa existente
- ✅ Remover tarefa com confirmação
- ✅ Marcar tarefa como concluída
- ✅ Buscar tarefa por título
- ✅ Contador de tarefas no rodapé

---

## 🧪 Testes Implementados

| Teste | Descrição |
|-------|-----------|
| `deveCriarTarefaComTituloCorreto` | Verifica criação da tarefa |
| `deveMarcarTarefaComoConcluida` | Verifica conclusão |
| `deveAtualizarTitulo` | Verifica atualização |
| `deveInserirTarefa` | Verifica inserção no banco |
| `deveBuscarTarefaPorId` | Verifica busca por ID |
| `deveBuscarTarefaPorTitulo` | Verifica busca por título |
| `deveDeletarTarefa` | Verifica remoção |
| `deveRetornarListaVazia` | Verifica listagem vazia |

---

