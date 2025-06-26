# 🚗 Car Rental System

A full-stack web application for renting and managing cars, built using **Spring Boot (Java)** and **React**.

---

## 🧩 Features

- User Registration & Login (JWT-based)
- View & Filter Cars by Make, Price, etc.
- Rent Cars and View Rental History
- Admin Controls for Car/Booking Management
- Address & Profile Management
- Role-based Access Control

---

## 🛠️ Tech Stack

**Backend:**
- Java 17, Spring Boot, Spring Security
- Hibernate/JPA
- MySQL/PostgreSQL
- Docker (optional)

**Frontend:**
- React, Axios
- React Router
- Tailwind CSS (or CSS modules)

---

## 📁 Project Structure

Car-Rental-System/

├── Backend/ # Spring Boot app

│ └── src/main/java/com/carrental/whybuy/

├── Frontend/ # React app

│ └── src/

└── README.md

---

## env example:

- DB_URL=jdbc:mysql://localhost:3306/carrental
- DB_USERNAME=root
- DB_PASSWORD=yourpassword
- JWT_SECRET=your_jwt_secret

---

## 🔒 Security
**🔐 Do NOT commit .env or service keys:**

Add to .gitignore:
.env, *.json, .DS_Store

💡 Tips
Use Postman to test APIs

Use Docker to containerise the backend

Consider integrating Stripe for payment

---

## 📬 Author
Developed by Dinesh Sathunuri
GitHub: dinesh-sathunuri


