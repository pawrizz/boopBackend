You are working inside the "boop-care" repository.

Follow these NON-NEGOTIABLE rules:

ARCHITECTURE
- This is a MONOLITH-FIRST, MICROservice-ready system.
- There is ONLY ONE Spring Boot application: boop-app.
- All other modules (order, user, auth, appointment, medical, common) are plain Spring modules.
- Parent project (boop-care) has NO Java code.

TECH STACK (FIXED)
- Java 17
- Spring Boot
- Maven multi-module
- Hibernate (Spring Data JPA)
- PostgreSQL
- React is used only on frontend (ignore frontend unless asked)

MODULE RULES
- boop-app:
    - Contains @SpringBootApplication
    - Contains application.yml
    - Depends on all other modules
- Feature modules (order, user, auth, appointment, medical):
    - NO main() method
    - NO application.yml
    - Can contain @Component, @Service, @Repository, @Configuration
    - Must be self-contained by domain
- common:
    - Shared utilities only
    - NO business logic

SECURITY & ACCESS RULES (STRICT)
- Single User model only
- Roles define DOMAIN access
- Permissions define ACTIONS within domain
- Permissions must NEVER expand scope beyond role
- Admins are scoped (Super Admin + Domain Admins)
- Delete is permission-based and often soft-delete
- Medical data is IMMUTABLE

AI RULES
- AI is helper-only
- No approvals, no prescriptions, no autonomous writes
- AI can only extract, suggest, or prepare drafts

CODING RULES
- Do NOT introduce new frameworks
- Do NOT create cross-module dependencies (except boop-app)
- Do NOT bypass domain boundaries
- Write clean, simple, production-grade Java code

If unsure, ASK instead of assuming.

Before generating code:
- Identify the correct module
- Respect domain boundaries
- Keep future microservice extraction in mind
