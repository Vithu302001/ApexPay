# ApexPay Roadmap — 2-Day Full Stack Sprint

## Completed So Far

- OOP, Collections, Repository Pattern, Service Layer, Constructor DI
- Streams, Lambdas, Exception Handling

## Day 7 — Core Java Polish + Spring Boot Foundation

| Task | Topics Covered |
|------|---------------|
| 1. Refactor repos to use `Optional<T>` | Optional, Generics |
| 2. Add concurrency simulation (ExecutorService) | Concurrency, Thread Safety |
| 3. Add File I/O (export/import accounts) | File I/O, BufferedReader/Writer |
| 4. Bootstrap Spring Boot app | @SpringBootApplication, Maven config |
| 5. Create REST controllers for Account + Transaction | REST, @RestController, @RequestMapping |
| 6. Add DTOs + Validation | DTO pattern, @Valid, Bean Validation |

## Day 8 — Database + DevOps

**Interview Goal Statement:**
> Built a Spring Boot transaction processing application with layered architecture, JPA persistence, JWT authentication, transaction management, Docker containerization, CI/CD, and cloud deployment.

**Final Target Architecture:**
```
Controller
↓
DTO
↓
Service
↓
Repository
↓
Database

Security
├── JWT Filter
├── Authentication
└── Authorization
```

| Task | Topics Covered |
|------|---------------|
| 1. Replace in-memory repos with Spring Data JPA + H2 | JPA, @Entity, Repository interfaces |
| 2. Add @Transactional support | Transaction management, rollback |
| 3. Add JWT Authentication | Spring Security, JWT, Filter chain |
| 4. Add Global Exception Handling | @ControllerAdvice, ErrorResponse |
| 5. Dockerize the app | Dockerfile, docker-compose |
| 6. Set up CI/CD (GitHub Actions) | CI/CD pipeline, automated build |
| 7. Deploy (Render / Railway / Fly.io) | Cloud deployment |

### JWT Scope — Keep It Simple

**Implement:**
- Register endpoint (`POST /api/auth/register`)
- Login endpoint (`POST /api/auth/login`)
- JWT token generation + validation (jjwt library)
- Protect `/api/accounts/**` and `/api/transactions/**` with JWT filter
- Single role: `USER`

**Do NOT implement (out of scope):**
- ❌ Refresh tokens
- ❌ OAuth2 / Social login
- ❌ Multi-role RBAC
- ❌ Password reset flow
- ❌ Email verification
- ❌ Token revocation / blacklist

**Success Criterion:** A working, deployable Spring Boot app with REST APIs, JPA, JWT, Docker, and deployment — rather than a half-finished collection of advanced features.

### Day 8 Execution Order

1. Spring Data JPA + Database
2. @Transactional
3. JWT Authentication
4. Global Exception Handling
5. Docker
6. GitHub Actions
7. Deployment

## Side Tracks

### NeetCode 150 (Self-Paced)
- All solutions in Java, auto-synced to `neetcode-submissions/`
- Current: 6/150 (Arrays & Hashing)
- Full tracking: [interview-progress.md](../../../Career/interview-progress.md#neetcode-150--self-paced)
- **Agent instruction:** At every session start, run `git pull` on `neetcode-submissions/` to sync latest submissions from neetcode.io, then check for new folders.
