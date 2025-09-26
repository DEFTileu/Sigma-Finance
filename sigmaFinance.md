# Sigma Finance API Documentation

## 🔹 Overview

Sigma Finance is an online bank system with three types of accounts: SAVINGS, CURRENT, and BONUS. Users can manage accounts, perform transactions, receive notifications, generate reports, and securely log in. This system is designed to follow SOLID principles in its architecture.

---

## 🔹 SOLID Principles Usage

| Principle                       | Where Used in Sigma Finance                                                                                                                                                                                                                                              |
| ------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **SRP (Single Responsibility)** | Each service has a single responsibility: `UserService` handles users, `AccountService` handles accounts, `TransactionService` handles transactions, `NotificationService` handles notifications, `ReportService` handles reports, `AuthService` handles authentication. |
| **OCP (Open/Closed)**           | Adding new account types (e.g., SAVINGS, CURRENT, BONUS) or new transaction types without modifying existing services.                                                                                                                                                   |
| **LSP (Liskov Substitution)**   | Any subclass of `FinancialAccount` (SAVINGS, CURRENT, BONUS) can be used in `TransactionService` methods (`deposit`, `withdraw`, `transfer`) without breaking functionality.                                                                                             |
| **ISP (Interface Segregation)** | Focused interfaces: `Transactionable`, `Notifiable`, `Reportable` allow services to implement only the methods they need.                                                                                                                                                |
| **DIP (Dependency Inversion)**  | High-level services like `TransactionService` depend on abstractions (`Notifiable`) instead of concrete implementations (`EmailNotifier`, `SmsNotifier`).                                                                                                                |

---

## 🔹 Entities

### User

* `userId` (UUID)
* `name` (String)
* `email` (String)
* `password` (String)  // hashed
* `accounts` (List<FinancialAccount>)

### FinancialAccount

* `accountId` (UUID)
* `accountNumber` (String)
* `accountType` (Enum: SAVINGS, CURRENT, BONUS)
* `balance` (Double)
* `currency` (Enum: KZT, USD, EUR)
* `createdAt` (LocalDateTime)
* `status` (Enum: ACTIVE, BLOCKED, CLOSED)
* `owner` (User)
* `transactions` (List<Transaction>)
* `limitPerTransaction` (Double)

### Transaction

* `transactionId` (UUID)
* `transactionType` (Enum: DEPOSIT, WITHDRAW, TRANSFER, BONUS)
* `amount` (Double)
* `currency` (Enum)
* `sourceAccount` (FinancialAccount)
* `targetAccount` (FinancialAccount)
* `createdAt` (LocalDateTime)
* `status` (Enum: PENDING, COMPLETED, FAILED)
* `description` (String)

---

## 🔹 Services Overview

* **UserService**: registration, authentication, and profile management (SRP)
* **AccountService**: creation and management of accounts (SRP, OCP)
* **TransactionService**: deposit, withdraw, transfer, bonus credit (SRP, LSP, DIP)
* **NotificationService**: sending transaction notifications (Email, SMS, Push) (ISP, DIP)
* **ReportService**: generating transaction and account reports (SRP, ISP)
* **AuthService**: login, logout, JWT token generation and validation (SRP, DIP)

---

## 🔹 Endpoints

### 1. Authentication

#### Register User

* **POST** `/api/auth/register`
* **Request Body:**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword"
}
```

* **Response:**

```json
{
  "userId": "uuid",
  "name": "John Doe",
  "email": "john@example.com"
}
```

#### Login User

* **POST** `/api/auth/login`
* **Request Body:**

```json
{
  "email": "john@example.com",
  "password": "securePassword"
}
```

* **Response:**

```json
{
  "token": "jwt_token",
  "expiresIn": 3600
}
```

### 2. User Endpoints

#### Get User Profile

* **GET** `/api/users/{userId}`
* **Response:**

```json
{
  "userId": "uuid",
  "name": "John Doe",
  "email": "john@example.com",
  "accounts": [
    { "accountId": "uuid", "accountNumber": "12345678", "accountType": "CURRENT", "balance": 1000.0 }
  ]
}
```

### 3. Account Endpoints

#### Create Account

* **POST** `/api/accounts`
* **Request Body:**

```json
{
  "userId": "uuid",
  "accountType": "SAVINGS",
  "currency": "KZT"
}
```

* **Response:**

```json
{
  "accountId": "uuid",
  "accountNumber": "12345678",
  "accountType": "SAVINGS",
  "balance": 0.0,
  "currency": "KZT",
  "status": "ACTIVE"
}
```

#### Get Accounts of User

* **GET** `/api/users/{userId}/accounts`
* **Response:**

```json
[
  { "accountId": "uuid", "accountNumber": "12345678", "accountType": "CURRENT", "balance": 1000.0 },
  { "accountId": "uuid", "accountNumber": "87654321", "accountType": "BONUS", "balance": 50.0 }
]
```

### 4. Transaction Endpoints

#### Deposit

* **POST** `/api/transactions/deposit`
* **Request Body:**

```json
{
  "accountId": "uuid",
  "amount": 500.0
}
```

* **Response:**

```json
{
  "transactionId": "uuid",
  "transactionType": "DEPOSIT",
  "amount": 500.0,
  "status": "COMPLETED",
  "balance": 1500.0
}
```

#### Withdraw

* **POST** `/api/transactions/withdraw`
* **Request Body:**

```json
{
  "accountId": "uuid",
  "amount": 200.0
}
```

* **Response:**

```json
{
  "transactionId": "uuid",
  "transactionType": "WITHDRAW",
  "amount": 200.0,
  "status": "COMPLETED",
  "balance": 1300.0
}
```

#### Transfer

* **POST** `/api/transactions/transfer`
* **Request Body:**

```json
{
  "sourceAccountId": "uuid",
  "targetAccountId": "uuid",
  "amount": 300.0
}
```

* **Response:**

```json
{
  "transactionId": "uuid",
  "transactionType": "TRANSFER",
  "amount": 300.0,
  "status": "COMPLETED",
  "sourceBalance": 1000.0,
  "targetBalance": 500.0
}
```

#### Bonus Credit

* **POST** `/api/transactions/bonus`
* **Request Body:**

```json
{
  "accountId": "uuid",
  "amount": 50.0
}
```

* **Response:**

```json
{
  "transactionId": "uuid",
  "transactionType": "BONUS",
  "amount": 50.0,
  "status": "COMPLETED",
  "balance": 50.0
}
```

### 5. Report Endpoints

#### Get Account Transactions

* **GET** `/api/accounts/{accountId}/transactions`
* **Response:**

```json
[
  { "transactionId": "uuid", "transactionType": "DEPOSIT", "amount": 500.0, "status": "COMPLETED", "createdAt": "2025-09-25T12:00:00" },
  { "transactionId": "uuid", "transactionType": "WITHDRAW", "amount": 200.0, "status": "COMPLETED", "createdAt": "2025-09-25T13:00:00" }
]
```

#### Generate Report for User

* **GET** `/api/users/{userId}/report?from=2025-09-01&to=2025-09-25`
* **Response:**

```json
{
  "userId": "uuid",
  "reportPeriod": "2025-09-01 to 2025-09-25",
  "accounts": [
    { "accountId": "uuid", "accountType": "CURRENT", "transactionsCount": 5, "totalDeposits": 1500.0, "totalWithdraws": 700.0 },
    { "accountId": "uuid", "accountType": "BONUS", "transactionsCount": 2, "totalDeposits": 50.0, "totalWithdraws": 0.0 }
  ]
}
```
