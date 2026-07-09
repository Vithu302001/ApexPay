# ApexPay Progress

## Schedule

Full-stack ApexPay in 2 days. See [roadmap.md](roadmap.md) for details.

Current: **Day 7 ✅** — Core Java Polish + Spring Boot Foundation

## Completed

### Day 7 Tasks

| # | Task | Topics Covered | Status |
|---|------|---------------|--------|
| 1 | Refactor repos to use `Optional<T>` | Optional, Generics | ✅ |
| 2 | Add concurrency simulation (ExecutorService) | Concurrency, Thread Safety | ✅ |
| 3 | Add File I/O (export/import accounts) | File I/O, BufferedReader/Writer | ✅ |
| 4 | Bootstrap Spring Boot app | @SpringBootApplication, Maven config | ✅ |
| 5 | Create REST controllers for Account + Transaction | REST, @RestController, @RequestMapping | ✅ |
| 6 | Add DTOs + Validation | DTO pattern, @Valid, Bean Validation | ✅ |

### Previously Completed

- OOP
- Collections Framework
- Repository Pattern
- Service Layer
- Constructor Dependency Injection
- Streams (including TransactionRepository.findByAccount)
- Lambdas
- Exception Handling (AccountNotFoundException, InsufficientFundsException)

## Implemented Components

### Models
- Account
- Transaction (Abstract)
- DepositTransaction
- WithdrawalTransaction
- TransferTransaction

### Repositories
- BaseRepository<T, ID> (generic interface with Optional<T>)
- AccountRepository implements BaseRepository<Account, String>
- TransactionRepository implements BaseRepository<Transaction, String>

### Services
- BankService (uses Optional.orElseThrow, provides REST-friendly methods)
- ConcurrencySimulation (ExecutorService, fixed thread pool, AtomicInteger)
- FilePersistenceService (BufferedReader/Writer, CSV export/import, try-with-resources)

### Controllers (REST API)
- AccountController — CRUD, deposit, withdraw, transfer
- TransactionController — list all, filter by account, largest

### DTOs
- CreateAccountRequest (@NotBlank, @Size)
- AccountResponse
- DepositRequest (@Positive)
- WithdrawRequest (@Positive)
- TransferRequest (@NotBlank, @Positive)
- TransactionResponse

### Exceptions
- AccountNotFoundException
- InsufficientFundsException

### Application
- ApexPayApplication (@SpringBootApplication entry point)
- Main (console demo for Optional, Generics, Concurrency, File I/O)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/accounts` | Create account |
| GET | `/api/accounts` | List all accounts |
| GET | `/api/accounts/{id}` | Get account by ID |
| POST | `/api/accounts/{id}/deposit` | Deposit money |
| POST | `/api/accounts/{id}/withdraw` | Withdraw money |
| POST | `/api/accounts/{id}/transfer` | Transfer money |
| DELETE | `/api/accounts/{id}` | Delete account |
| GET | `/api/transactions` | List all transactions |
| GET | `/api/transactions?accountId=X` | Filter by account |
| GET | `/api/transactions/largest` | Largest transaction |

## Architecture

```
ApexPayApplication (@SpringBootApplication)
│
├── AccountController  (REST, /api/accounts)
├── TransactionController (REST, /api/transactions)
│
├── BankService
│   ├── ConcurrencySimulation
│   └── FilePersistenceService
│
├── AccountRepository implements BaseRepository<Account, String>
├── TransactionRepository implements BaseRepository<Transaction, String>
│
└── Models (Account, Transaction hierarchy)
```

## Day 8 Tasks (Next)

**Interview Goal:**
> Built a Spring Boot transaction processing application with layered architecture, JPA persistence, JWT authentication, transaction management, Docker containerization, CI/CD, and cloud deployment.

| # | Task | Topics |
|---|------|--------|
| 1 | Spring Data JPA + H2 | JPA, @Entity, Repository interfaces |
| 2 | @Transactional support | Transaction management, rollback |
| 3 | JWT Authentication | Spring Security, JWT, Filter chain |
| 4 | Global Exception Handling | @ControllerAdvice, ErrorResponse |
| 5 | Dockerize | Dockerfile, docker-compose |
| 6 | CI/CD (GitHub Actions) | Automated build + test |
| 7 | Deploy (Render / Railway / Fly.io) | Cloud deployment |

### JWT Scope Summary

**In:** Register, Login, JWT generation/validation, Protect endpoints, Role: USER
**Out:** Refresh tokens, OAuth2, Social login, Multi-role RBAC, Password reset, Email verification

**Success Criterion:** A working, deployable Spring Boot app with REST APIs, JPA, JWT, Docker, and deployment — not a half-finished collection of advanced features.

## Side Tracks

### NeetCode 150
See [interview-progress.md](../../../Career/interview-progress.md#neetcode-150--self-paced) for full tracking.
**Progress:** 6/150 solved in Java (syncs from neetcode.io → `neetcode-submissions/`)

> **Agent instruction:** At every session start, run `git -C /home/vithu/Desktop/Interview-Preperation/neetcode-submissions pull` then list the directory to check for new problems.
