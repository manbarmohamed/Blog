# 📚 Blog Management System

A full-featured **Blog Management System** built with role-based access control to manage users, posts, categories, tags, likes, and comments. This system supports visitor interactions, registered users' engagement, and administrator control over platform content and users.

---

## 🧾 Table of Contents

* [Features](#features)
* [Actors & Use Cases](#actors--use-cases)
* [Class Model](#class-model)
* [Technologies Used](#technologies-used)
* [Installation](#installation)
* [Database Structure](#database-structure)
* [Project Structure](#project-structure)
* [License](#license)

---

## ✅ Features

* 👤 User Registration & Authentication
* 🔐 Role-based access (Admin, Registered User, Visitor)
* 📝 Post Creation, Editing, and Deletion
* 🗂️ Category & Tag Management
* 💬 Commenting System
* ❤️ Like Feature
* 🔍 Search & Filter Posts
* 🧾 Password Reset & Profile Management

---

## 👥 Actors & Use Cases

### 👤 Administrator

* Manage Users
* Manage Posts (Create, Edit, Delete)
* Manage Tags
* Manage Categories

### 🧑‍💻 Registered User

* Login / Logout
* Manage Profile
* Like / Comment on Posts
* Reset Password
* Search & Filter Content
* Read Posts

### 👀 Visitor

* Register
* Read Posts
* Search & Filter Content

> All use cases that require account actions (like liking, commenting, managing posts) include the `Login` use case.

---

## 🧩 Class Model

### Main Entities

| Class      | Description                                                                                                                     |
| ---------- | ------------------------------------------------------------------------------------------------------------------------------- |
| `User`     | Contains personal info, credentials, role (ADMIN, USER), and associated posts, comments, and likes.                             |
| `Post`     | Core blog entity. Includes title, content, status (`DRAFT`, `PUBLISHED`, `ARCHIVED`), tags, category, comments, and view count. |
| `Category` | Categorizes posts. One post belongs to one category.                                                                            |
| `Tag`      | Tags are many-to-many with posts.                                                                                               |
| `Comment`  | Made by users on posts. Includes timestamps.                                                                                    |
| `Like`     | Tracks which users liked which posts.                                                                                           |

### Relationships

* **User** → **Post**: one-to-many
* **User** → **Comment**: one-to-many
* **User** → **Like**: one-to-many
* **Post** → **Comment**: one-to-many
* **Post** → **Category**: many-to-one
* **Post** → **Tag**: many-to-many

---

## 🛠️ Technologies Used

* Java 17
* Spring Boot
* Spring Security
* JPA / Hibernate
* MySQL or PostgreSQL
* Lombok
* MapStruct (optional for DTOs)
* Swagger/OpenAPI (for API documentation)
* Docker (Docker-compose)
* Junit/Mockito


---

## 🧪 Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/blog-management-system.git
   cd blog-management-system
   ```

2. **Configure the database**
   Update your `application.properties` or `application.yml`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/blogdb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```

3. **Run the application**

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the app**

   * API Docs: `http://localhost:8080/swagger-ui.html`
   * Admin Panel (if implemented): `http://localhost:8080/admin`

---

## 🗄️ Database Structure

### Enumerations

* `Role`: `ADMIN`, `USER`
* `PostStatus`: `DRAFT`, `PUBLISHED`, `ARCHIVED`

### Sample Entities

* `User`: id, username, password, role, isActive, timestamps
* `Post`: id, title, content, status, views, category, tags
* `Category`: id, name, description
* `Tag`: id, name
* `Comment`: id, content, timestamps
* `Like`: id, timestamps

---

## 🗂️ Project Structure

```bash
src/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── security/
└── config/
```

---


