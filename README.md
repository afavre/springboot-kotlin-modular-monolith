# springboot-kotlin-modular-monolith

A modular Spring Boot & Kotlin monolith designed for MVPs, with clean architecture and boundaries—ready to evolve into
microservices.

---

## 🚀 Overview

This project demonstrates a way to build an MVP when domain boundaries are still emerging.  
Start with a modular monolith using clean architecture principles, so you can break it down into
independent microservices later if needed.
The project uses a simplified version of the Hexagonal/Domain Driven Architecture pattern to keep the Core Business
logic clear of the Framework used.

- **Kotlin + Spring Boot** for modern JVM development.
- **Multi-module Gradle structure** keeps clear boundaries between features.
- **Shared libraries** for models, clients, and utilities.
- **Feature modules** communicate through client interfaces, not direct calls.
- **Production-ready**: test coverage (JaCoCo), clean separation, and flexible evolution.

Notes:

- This setup is more complex than a typical Spring Boot App with a single Gradle module, but it is simpler and more
  cost-effective than creating multiple microservices while boundaries are still evolving.
- If using Java, you can use the module visibility feature to enforce boundaries. But with Kotlin, it is harder to
  enforce, so using Gradle modules is safer.

---

## 💡 Why Modular Monolith First?

- **Rapid MVP delivery** without premature network overhead.
- **Domain boundaries can evolve naturally**—not locked by microservice APIs.
- **Easier refactoring**: Move code between modules as you learn.
- **When ready, break out modules as microservices** with minimal changes.

---

## 🗂 Project Structure

- **Main Application**: Wires together modules, acts as the entry point.
- **Feature Modules**: Each module owns its domain logic and only exposes client interfaces.
- **Shared Libraries**: Common code (DTOs, utility functions, API contracts).
- **No direct inter-module dependencies**—modules communicate via clients/interfaces.
- **Ready for microservice split**: Just swap client implementations when boundaries become clear.

```
root/
├── main-app/         # Application entrypoint
├── accounts/         # Feature/domain module
├── activities/       # Another feature/domain module
├── shared-domains/   # Shared DTOs/entities (optional if not broken down in micro-services yet)
├── shared-clients/   # Client interfaces for inter-module calls
├── e2e-tests/        # E2E Tests for all modules
```

In this example, the **accounts** module provides a complete example of a feature module with its own domain logic, API,
in
memory persistence and the different layers of testing.

The **activities** module is a simplified version to show how to interact with another module via a client interface.


---

## 🏛 Architecture

This "simplified" Hexagonal Architecture helps separate the core business logic from the framework and infrastructure
code.
Each modules follow this pattern:

- api: Controllers/APIs
- config: Configuration classes (SpringBoot related, wiring everything together)
- domain: Business logic, domain models, services, repositories interfaces
- infrastructure: Implementations of repository interfaces, external service clients, etc.

It is important to note that the `domain` layer does not depend on any other layers, ensuring a clean separation of
concerns.
The domain layer should be tested with unit tests only, while the `api` and `infrastructure` layers will mostly have
integration tests.

## 🛠️ Tech Stack

### Core

- Spring Boot
- Kotlin
- Gradle
- Logback

### Testing

- Kotest
- Mockk

### Tools

- Gradle Version Catalog: Centralized dependency management (inside the project)
- Makefile

## 🧪 Testing & Coverage

- Each module runs its own tests (integration/controller tests and unit tests).
- E2E tests are in a separate module running the whole application.
- JaCoCo provides code coverage per module.

---

## 🏁 How to Run

```sh
make build        # Build all modules
make test         # Run all tests
make run          # Run the main application
make coverage     # Generate coverage reports
```

---

## ✨ Roadmap & Customization

- [ ] Logging JSON
- [ ] Opentracing
- [ ] Github Actions CI/CD

