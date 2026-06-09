\# 📋 Task Manager (Gerenciador de Tarefas — Versão GUI \& JDBC)



!\[Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)

!\[JavaFX](https://img.shields.io/badge/JavaFX-🚀-blue?style=for-the-badge)

!\[JUnit 5](https://img.shields.io/badge/JUnit\_5-✅-red?style=for-the-badge)

!\[Status](https://img.shields.io/badge/Status-Concluído-Green?style=for-the-badge)



O \*\*TaskManager\*\* é uma aplicação desktop robusta desenvolvida em Java. Evoluído de uma versão de console, o sistema agora conta com uma interface gráfica amigável (GUI), persistência de dados em banco relacional, arquitetura escalável utilizando Generics e cobertura de testes automatizados.



\## 🎯 Objetivo do Projeto

Consolidar conceitos avançados do ecossistema Java, transformando uma aplicação simples em um sistema real de mercado: estruturado, testável, persistente e com foco na experiência do usuário.



\## 🛠️ Tecnologias e Conceitos Aplicados



\### 1. Interface Gráfica (JavaFX \& FXML)

\* \*\*Design Desacoplado:\*\* Telas desenhadas em arquivos `.fxml` com o auxílio do \*\*Scene Builder\*\*, separando a lógica de apresentação da lógica de negócio através de `Controllers` (`@FXML`).

\* \*\*Componentes Utilizados:\*\* Estruturas de layout integradas (`VBox`, `HBox`, `GridPane`, `BorderPane`) para renderizar componentes como `TableView`, `TextField`, `TextArea` e manipulação de eventos via `setOnAction()`.

\* \*\*Estilização:\*\* Visual customizado utilizando folhas de estilo `.css`.



\### 2. Persistência de Dados (JDBC)

\* \*\*Isolamento de Camadas:\*\* Implementação do padrão \*\*DAO (Data Access Object)\*\* através da classe `TarefaDAO` para isolar as operações de banco de dados.

\* \*\*Segurança:\*\* Uso rigoroso de `PreparedStatement` e `ResultSet` para manipulação de dados, mitigando completamente vulnerabilidades de \*\*SQL Injection\*\*.

\* \*\*Integridade:\*\* Controle transacional manual utilizando `commit()` e `rollback()` para garantir a atomicidade das operações.



\### 3. Generics e Reutilização de Código

\* Criação de estruturas genéricas para manipulação de coleções de tarefas, aplicando conceitos de wildcards (`?`, `? extends T`, `? super T`) para garantir máxima flexibilidade e reaproveitamento do código backend.



\### 4. Testes Automatizados (JUnit 5)

\* Implementação de rotinas de teste automatizadas para validação de regras de negócio.

\* \*\*Escopo de Testes:\*\* Criação de tarefas, fluxos de conclusão e validação do ciclo de vida do DAO utilizando banco de dados em memória para testes isolados.

\* \*\*Estrutura:\*\* Uso das diretivas `@Test`, `@BeforeEach` e `@AfterEach` para setup e tear down do ambiente de testes.



\## ✨ Funcionalidades

\* \[x] \*\*Interface Visual Completa:\*\* Listagem de tarefas em tabelas interativas.

\* \[x] \*\*CRUD Completo:\*\* Cadastro, edição, remoção e atualização de status de tarefas diretamente na tela.

\* \[x] \*\*Confirmações de Segurança:\*\* Telas de alerta antes de exclusões críticas.

\* \[x] \*\*Persistência Real:\*\* Seus dados ficam salvos de forma permanente no banco de dados.



\## 🚀 Como Executar o Projeto



\### Pré-requisitos

\* \*\*Java JDK 17\*\* (ou superior)

\* \*\*JavaFX SDK\*\* configurado na sua IDE ou via gerenciador de dependências (Maven/Gradle)

\* Banco de Dados Relacional (Configurado na classe `Conexao.java`)



\### Execução local

1\. Clone o repositório:

&#x20;  ```bash

&#x20;  git clone \[https://github.com/joao-arrais7/Task\_Manager\_Java.git](https://github.com/joao-arrais7/Task\_Manager\_Java.git)



2\. Abra o projeto na sua IDE de preferência (IntelliJ IDEA ou Eclipse) com suporte a JavaFX configurado.



3\. Certifique-se de que a biblioteca do Driver JDBC está inclusa no Classpath.



4\. Execute a classe MainApplication.java.



📁 Arquitetura do Projeto

Task\_Manager\_Java/

├── src/

│   ├── main/

│   │   ├── java/

│   │   │   └── controller/     # Controladores das telas (.java)

│   │   │   └── dao/            # Camada de persistência (TarefaDAO.java, Conexao.java)

│   │   │   └── model/          # Classes de entidade (Tarefa.java)

│   │   │   └── MainApplication # Ponto de entrada do JavaFX

│   │   └── resources/          # Arquivos visuais (.fxml e .css)

│   └── test/

│       └── java/               # Testes automatizados com JUnit 5



✒️ Autor

Desenvolvido por João Arrais.

