# 🎓 Full Assignment: **Build the `focus-guardian` App**

### *(A Real-Life Inspired Project to Beat Social Media Addiction)*

---

## 🌍 **Story Background: A Mom and Her Son**

Meet **Sarah**, a single mom of two, who works full-time and studies part-time. Her 14-year-old son, **Liam**, used to be a bright and curious kid. But lately, he’s been glued to his phone — spending hours scrolling on TikTok, chatting on WhatsApp, and watching endless YouTube shorts. His grades are slipping, he’s skipping meals, and he’s losing sleep.

Sarah wants to help Liam — not by banning phones — but by teaching him **digital discipline**. She decides to build a simple app with her son. Every time he **resists the urge to scroll**, he can record a success. He can write **why** he stayed focused, and the app will record it for him — to celebrate wins and reflect on slips.

You are going to build that app: **Focus Guardian** 💡

---

## 🧑‍💻 **Your Mission**

Design and build a **Spring Boot + PostgreSQL** app that helps users **track their digital discipline**. You’ll let users submit "focus entries" where they:

* Say **how** they stayed off social media
* Record whether they were successful
* View all past entries
* See basic **analytics** like total entries and success rate

---

## 🛠️ Project Details

### 🔷 Project Name: `focus-guardian`

Create a new Spring Boot Maven project **manually** (no Spring Initializr).

### 📚 Technologies:

* Java 17+
* IntelliJ IDEA Community Edition
* PostgreSQL (via `psql` CLI)
* Spring Boot (Web + JPA)
* Postman or `curl` for testing

---

## 📋 Assignment Requirements

### ✅ Step-by-Step Assignment (with Hints)

### 🔹 **Step 1: Create a Maven Project**

> **Objective:** Set up a blank Spring Boot project manually.

#### 📝 What to do:

* Open IntelliJ → New Project → Maven
* GroupId: `com.example`
* ArtifactId: `focus-guardian`
* Name: `focus-guardian`
* Java version: 17+

#### 💡 Hints:

* You’re creating this **without Spring Initializr** on purpose — this builds confidence in manual setups.
* Choose Maven, not Gradle, to match instructions.
* Once created, check for `pom.xml` and `src/main/java`.

---

### 🔹 **Step 2: Configure `pom.xml`**

> **Objective:** Add dependencies and plugins for Spring Boot and PostgreSQL.

#### 📝 What to do:

* Replace your existing `pom.xml` with one that includes:

    * Spring Boot starter parent
    * `spring-boot-starter-web`
    * `spring-boot-starter-data-jpa`
    * PostgreSQL JDBC driver
    * Spring Boot Maven plugin

#### 💡 Hints:

* Don’t forget the `<parent>` section for Spring Boot — this gives you auto-version management.
* `spring-boot-starter-web` adds support for `@RestController`, `@RequestMapping`, etc.
* `spring-boot-starter-data-jpa` enables working with databases using Java classes (ORM).
* PostgreSQL dependency gives your app the "driver" it needs to talk to the database.
* After updating `pom.xml`, click **Maven > Reload** in IntelliJ.

---

### 🔹 **Step 3: Create Package Structure**

> **Objective:** Organize code into logical layers.

#### 📝 What to do:

Under `src/main/java/com/example/focusguardian`, create:

```
model       # Holds the FocusEntry class
repo        # Holds the interface for database operations
web         # Holds the REST controller
```

#### 💡 Hints:

* Right-click the package and choose `New > Package`.
* Keep names lowercase.
* This mirrors **MVC layering**: `model`, `controller`, `data`.

---

### 🔹 **Step 4: Create the `FocusEntry` Entity**

> **Objective:** Create the Java class that maps to your PostgreSQL table.

#### 📝 What to do:

* Fields:

    * `Long id`
    * `String reason`
    * `Boolean status`
    * `LocalDateTime createdAt`

* Use JPA annotations:

    * `@Entity`
    * `@Id`
    * `@GeneratedValue`
    * `@Column`
    * `@PrePersist` to set `createdAt`

#### 💡 Hints:

* `@Entity` = “This Java class becomes a database table.”
* `@Id` and `@GeneratedValue` = auto-increment ID
* `@PrePersist` = method that runs before saving to DB

  ```java
  @PrePersist
  protected void onCreate() {
      createdAt = LocalDateTime.now();
  }
  ```

---

### 🔹 **Step 5: Create the Repository**

> **Objective:** Create a simple interface to access the database.

#### 📝 What to do:

* Create `FocusEntryRepository.java` in `repo`
* Extend `CrudRepository<FocusEntry, Long>`

#### 💡 Hints:

* No implementation needed — Spring generates it automatically.
* You can now use `.save()`, `.findAll()`, `.findById()`, `.deleteById()`.

---

### 🔹 **Step 6: Create the REST Controller**

> **Objective:** Create endpoints to submit and retrieve focus entries.

#### 📝 What to do:

* Create `FocusEntryController.java` in `web`
* Map it to `/focus`
* Add endpoints:

    * `POST /focus` to submit entry
    * `GET /focus/{id}` to get one entry
    * `GET /focus` to list all entries
    * `GET /focus/stats` for analytics

#### 💡 Hints:

* Annotate with `@RestController` and `@RequestMapping("/focus")`
* Use `@PostMapping`, `@GetMapping`
* Inject the repository using constructor injection
* Use `@RequestBody` to accept JSON input
* Use `@PathVariable` to extract ID from the URL

---

### 🔹 **Step 7: Configure PostgreSQL in `application.properties`**

> **Objective:** Replace H2 with PostgreSQL

#### 📝 What to do:

In `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/focus_guardian
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

#### 💡 Hints:

* `ddl-auto=update` means Spring will create or update the `focus_entry` table automatically.
* Make sure the DB name in the URL matches what you created in PostgreSQL.
* Password must match what you set during PostgreSQL installation.

---

### 🔹 **Step 8: Create the PostgreSQL Database**

> **Objective:** Set up the actual database using `psql` CLI

#### 📝 What to do:

```bash
psql -U postgres
CREATE DATABASE focus_guardian;
\q
```

#### 💡 Hints:

* You don’t need to create tables — Hibernate will do it automatically on first app start.
* If you get a connection error, double-check port (usually 5432), username, and password.

---

### 🔹 **Step 9: Test the Application Using Postman**

> **Objective:** Verify the API works

#### 📝 What to do:

* Send a `POST /focus` request with:

```json
{
  "reason": "Put my phone on airplane mode during study time",
  "status": true
}
```

* Try `GET /focus/1` to view that entry
* Try `GET /focus` to view all entries

#### 💡 Hints:

* Use headers: `Content-Type: application/json`
* IDs are auto-generated (start from 1)
* Status can be `true` or `false`

---

### 🔹 **Step 10: Add Analytics Endpoint**

> **Objective:** Help Liam see how he’s doing with a success rate summary.

#### 📝 What to do:

Create `/focus/stats` that returns JSON like:

```json
{
  "total": 5,
  "successes": 3,
  "failures": 2,
  "successRate": 60.0
}
```

#### 💡 Hints:

* Use `.findAll()` to get the list
* Use Java Stream to count `true` and `false` statuses
* `successRate = (successes / total) * 100`

---

### 🔹 **Step 11 (Final): Create the Spring Boot Main Application Class**

> 🧠 **Objective:** Define the starting point of your Spring Boot application — where everything begins when you run the project.

---

## 📝 What to Do:

1. Inside your base package `com.example.focusguardian`, create a new Java class:

**Class Name:** `FocusGuardianApplication`

2. Add the required annotations and `main()` method to launch the Spring Boot app.

---

## 💡 Hints and Explanations:

---

### 📁 📌 Where should I place this class?

Put it inside:

```
src/main/java/com/example/focusguardian/FocusGuardianApplication.java
```

This should be in the **same package or above all other packages** (like `model`, `repo`, `web`). Why?

> ✅ Because Spring Boot uses `@ComponentScan` to look for all components starting from the package where your main class lives.
> Placing it at the root ensures **everything gets auto-discovered**.

---

### 🧱 What should be inside this class?

Your main class should:

* Be marked with `@SpringBootApplication` (required for auto-configuration).
* Have a `main(String[] args)` method to start the app using `SpringApplication.run(...)`.

---

### 🔍 What does `@SpringBootApplication` do?

It’s a **shortcut for three annotations**:

```java

@Configuration          // Marks this class as a configuration source
@EnableAutoConfiguration // Enables Spring Boot's auto-config logic
@ComponentScan         // Automatically scans this package and subpackages

```

Together, they:

* Register your controller, entity, and repository
* Connect to the database
* Start the embedded web server (Tomcat)

---

### ✅ Final Code (What It Should Look Like):

```java
package com.example.focusguardian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 🚀 This is the main entry point of your Spring Boot app.
 * It launches everything: web server, database connections, and all Spring components.
 */
@SpringBootApplication
public class FocusGuardianApplication {

    public static void main(String[] args) {
        SpringApplication.run(FocusGuardianApplication.class, args);
    }
}
```

---

### 🧪 After You Create This Class:

* ✅ Right-click the file → Run `FocusGuardianApplication.main()`
* You should see output like:

  ```
  Tomcat started on port(s): 8080
  Started FocusGuardianApplication in 2.345 seconds
  ```

That means your application is up and running 🎉

---

## ✨ Bonus Challenges (Optional)

* Filter entries: `/focus/success` and `/focus/failure`
* Add a `category` field (`study`, `family`, `health`)
* Add sorting or pagination

---

## 📝 Submission Instructions

Students should submit:

* Their entire IntelliJ project
* Sample JSON requests used for testing
* Screenshot of PostgreSQL table (via `psql`)
* Sample `/focus/stats` result

---

## 💬 Teaching Philosophy

| Why this matters     | How it helps students                               |
| -------------------- | --------------------------------------------------- |
| Real story & empathy | Students relate and reflect on digital habits       |
| REST + PostgreSQL    | Foundational backend skill                          |
| Boolean logic        | Think in true/false conditions (clean model design) |
| Analytics            | Start thinking about data as insight                |
| Manual setup         | Builds strong fundamentals for real-world apps      |


