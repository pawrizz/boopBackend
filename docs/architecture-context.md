# Boop Care – Architecture Context (FROZEN)

> ⚠️ This document defines **non-negotiable architectural rules**  
> Any change here must be an **explicit architecture decision**.

---

## 1. Fixed Tech Stack (LOCKED)

The following stack is fixed and must not be re-evaluated:

- **Backend:** Java 17
- **Framework:** Spring Boot
- **Build Tool:** Maven (multi-module)
- **Frontend:** React (Web)
- **Database:** PostgreSQL
- **ORM:** Hibernate (via Spring Data JPA)

---

## 2. Project Structure Principles

- `boop-care` is a **parent / aggregator Maven project**
- Parent project contains **NO Java source code**
- Parent project controls:
    - Module aggregation
    - Dependency versions
    - Plugin versions

All runtime code lives inside child modules.

---

## 3. Architecture Direction

### Current State
- Modular Monolith
- Single Spring Boot application
- Multiple domain modules

### Future State
- Can scale to microservices
- Domain modules can become independent services
- No business-logic rewrite required

---

## 4. Identity Model (LOCKED)

### Single User Model
- There is **ONLY ONE User entity**
- No separate Doctor / Caretaker / Supplier user tables

All access is controlled using:
- **Roles** (domain access)
- **Permissions** (actions)

---

## 5. Roles vs Permissions (CRITICAL)

### Roles = Domain Access (HARD BOUNDARY)

A role defines **which domain(s)** a user can access.

Examples:
- ROLE_PET_OWNER
- ROLE_DOCTOR
- ROLE_CARETAKER
- ROLE_SUPPLIER
- ROLE_DELIVERY
- ROLE_ADMIN_CARETAKER
- ROLE_ADMIN_SUPPLIER
- ROLE_SUPER_ADMIN

> A user CANNOT access data outside their role domain.

---

### Permissions = Actions Inside Domain

Permissions define **what actions are allowed inside that domain**.

Examples:
- READ
- WRITE
- DELETE
- ORDER_UPDATE
- APPOINTMENT_FULFILL

> Permissions NEVER expand scope beyond role boundaries.

---

### Golden Rule

> **Role defines WHERE you can act**  
> **Permission defines WHAT you can do there**

---

## 6. Admin Model (FINAL)

Admins are **users with admin roles**, not separate user types.

### Super Admin
- Full access across all domains
- Can add/remove any role
- Can add/remove any permission

### Domain Admins
Examples:
- Admin Caretaker
- Admin Supplier

Capabilities:
- Manage users only in their domain
- Add/remove domain-specific permissions
- Cannot access other domains

---

## 7. Delete Rules (STRICT)

- Delete access is **permission-based**
- Delete is **not global**, even for admins

Rules:
- Orders → soft delete only
- Appointments → soft delete only
- Medical history → immutable
- Temporary/config data → hard delete allowed if permitted

---

## 8. Domain Isolation Rule

A user:
- Can access **only** the domain(s) defined by their role
- Can perform **only** actions defined by permissions

No permission can bypass domain isolation.

---

## 9. Core Use Cases (AGREED)

### Pet Owner
- CRUD pet profiles
- Order pet supplies (e-commerce)
- Book doctor / caretaker appointments
- Final approval for all add-ons and orders

### Caretaker
- Fulfill assigned appointments
- Add add-ons to existing bookings
- Bring orders during appointments
- Cannot finalize orders (owner approval required)

### Supplier
- View assigned orders
- Pack and ship
- No order modification

### Delivery
- Deliver orders
- Contact pet owner
- No other access

---

## 10. Medical & Doctor Flow (LOCKED)

- Doctor can create medical records and prescriptions
- Prescriptions may be:
    - Entered directly in the app
    - Uploaded as images (paper prescriptions)

### Medical History Rules
- Accessible only to:
    - Pet Owner
    - Doctors
- Shareable between doctors (with owner consent)
- Immutable once saved

---

## 11. AI Model Separation (CRITICAL)

AI is a **helper system**, not an actor.

AI CAN:
- Extract prescription data from images
- Structure medical data
- Prepare draft carts or insights

AI CANNOT:
- Approve orders
- Prescribe medicines
- Modify core data autonomously
- Make medical or business decisions

> **AI assists, humans decide.**

---

## 12. Order & Fulfillment Rules

- Orders can be initiated by:
    - Pet Owner
    - Doctor (initiate only)
    - Caretaker (initiate only)

- Orders are finalized **only by Pet Owner**

Fulfillment rules:
- Doctor appointment → fulfilled only by assigned doctor
- Caretaker service → fulfilled only by assigned caretaker
- Delivery → fulfilled only by assigned delivery partner

---

## 13. Authorization vs Business Rules

- **Authorization (roles & permissions):**
    - Who can attempt an action

- **Business rules (domain logic):**
    - Whether the action is valid for this entity

These MUST remain separate.

---

## 14. Microservices Readiness

When scaled:
- Identity & auth remain centralized
- Services trust tokens (roles + permissions)
- Each service enforces its own business rules
- No service reads another service’s database

---

## 15. Non-Negotiable Principles (SUMMARY)

- One User model
- Roles define domain scope
- Permissions define actions
- Domain isolation is absolute
- Admins are scoped, not omnipotent
- Medical data is immutable
- AI never owns truth
- Design monolith as future microservices

---

## STATUS

✅ Architecture rules **FROZEN**  
✅ Safe for coding  
✅ Safe for AI-assisted development  
✅ Safe for future microservice scaling
