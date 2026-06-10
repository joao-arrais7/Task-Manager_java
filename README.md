# 📋 TaskManager

![Java](https://img.shields.io/badge/Java-JDK%2025-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-25-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-red)
![JUnit](https://img.shields.io/badge/JUnit-5.10.2-green)

A desktop task management application built with Java and JavaFX.

---

## 📌 About

TaskManager is a personal project developed to practice and apply
core Java concepts including graphical interfaces, database persistence,
generic programming and automated testing.

---

## 🚀 Technologies

| Technology | Version |
|-----------|---------|
| Java | JDK 25 |
| JavaFX | 25 |
| MySQL | 8.0 |
| Maven | 3.x |
| JUnit | 5.10.2 |

---

## ✨ Features

- ✅ List all tasks
- ✅ Add new task
- ✅ Edit existing task
- ✅ Delete task with confirmation
- ✅ Mark task as completed
- ✅ Search task by title
- ✅ Task status counterTaskManager/

 ---

## 🏗️ Project Structure
├── src/
│   ├── main/
│   │   ├── java/com/taskmanager/
│   │   │   ├── MainApplication.java
│   │   │   ├── model/
│   │   │   │   └── Tarefa.java
│   │   │   ├── db/
│   │   │   │   └── Conexao.java
│   │   │   ├── dao/
│   │   │   │   ├── GenericDAO.java
│   │   │   │   └── TarefaDAO.java
│   │   │   └── controller/
│   │   │       ├── MainController.java
│   │   │       └── FormController.java
│   │   └── resources/com/taskmanager/
│   │       ├── main.fxml
│   │       ├── form.fxml
│   │       └── style.css
│   └── test/java/com/taskmanager/
│       ├── TarefaTest.java
│       ├── TarefaDAOTest.java
│       └── TarefaDAOTestHelper.java
├── pom.xml
└── README.md---

## ⚙️ Getting Started

### Prerequisites
- JDK 25
- MySQL 8.0
- Maven 3.x

### Clone the repository
```bash
git clone https://github.com/joao-arrais7/Task-Manager_java.git
cd Task-Manager_java
```

### Configure database credentials
Rename `config.properties.example` to `config.properties` and fill in:
```properties
db.url=jdbc:mysql://localhost:3306/taskmanager_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.usuario=root
db.senha=your_password_here
```

### Create the database
```sql
CREATE DATABASE IF NOT EXISTS taskmanager_db;
```

### Run the application
```bash
mvn javafx:run
```

### Run the tests
```bash
mvn test
```

---

## ⚠️ Security

Database credentials are stored in `config.properties` which is
**not committed to this repository**.

To configure locally, rename `config.properties.example` to
`config.properties` and fill in your credentials.

---

## 👤 Author

**João Paulo Da Cunha Arrais**

[![GitHub](https://img.shields.io/badge/GitHub-joao--arrais7-black?logo=github)](https://github.com/joao-arrais7)

---

