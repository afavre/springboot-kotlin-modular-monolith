# Spring Boot Kotlin Hexagonal Architecture Template

This repository demonstrates a clean implementation of **hexagonal architecture** (also known as ports and adapters pattern) using Spring Boot and Kotlin. It provides a solid foundation for building maintainable, testable, and flexible applications.

## Architecture Overview

Hexagonal architecture promotes the separation of concerns by organizing code into distinct layers:

```
┌─────────────────────────────────────────────────────────────┐
│                    Infrastructure Layer                     │
│  ┌─────────────────┐                   ┌─────────────────┐  │
│  │   REST API      │                   │   Repository    │  │
│  │   (Adapters)    │                   │   (Adapters)    │  │
│  └─────────────────┘                   └─────────────────┘  │
│           │                                       │          │
│           └───────────────┐       ┌───────────────┘          │
└───────────────────────────┼───────┼──────────────────────────┘
                            │       │
┌───────────────────────────┼───────┼──────────────────────────┐
│                          │       │                          │
│         Application Layer (Ports & Services)                │
│                          │       │                          │
│  ┌─────────────────┐     │       │     ┌─────────────────┐  │
│  │     Ports       │◄────┘       └────►│    Services     │  │
│  │  (Interfaces)   │                   │  (Use Cases)    │  │
│  └─────────────────┘                   └─────────────────┘  │
│                            │       │                        │
└────────────────────────────┼───────┼────────────────────────┘
                             │       │
┌────────────────────────────┼───────┼────────────────────────┐
│                           │       │                        │
│                     Domain Layer                           │
│                           │       │                        │
│  ┌─────────────────┐     │       │     ┌─────────────────┐ │
│  │    Entities     │◄────┘       └────►│ Business Logic  │ │
│  │ (Core Models)   │                   │   (Rules)       │ │
│  └─────────────────┘                   └─────────────────┘ │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### Layer Responsibilities

#### 1. Domain Layer (`domain/`)
- **Purpose**: Contains the core business logic and rules
- **Contents**: Entities, value objects, domain services
- **Dependencies**: None (completely isolated)
- **Example**: `Product` entity with business validation and methods

#### 2. Application Layer (`application/`)
- **Purpose**: Orchestrates business workflows and defines contracts
- **Contents**: 
  - **Ports** (`ports/`): Interfaces defining what the domain needs from the outside world
  - **Services** (`services/`): Use cases and business workflows
- **Dependencies**: Only depends on the domain layer
- **Example**: `ProductRepository` port and `ProductService` with business workflows

#### 3. Infrastructure Layer (`infrastructure/`)
- **Purpose**: Implements technical details and external integrations
- **Contents**: 
  - **Adapters** (`adapters/`): Concrete implementations of ports
  - **Configuration** (`configuration/`): Spring configuration classes
- **Dependencies**: Depends on application and domain layers
- **Example**: `InMemoryProductRepository` adapter and `ProductController` REST API

## Project Structure

```
src/
├── main/kotlin/com/example/hexagonal/
│   ├── domain/
│   │   └── Product.kt                    # Domain entities and business logic
│   ├── application/
│   │   ├── ports/
│   │   │   └── ProductRepository.kt      # Port interfaces
│   │   └── services/
│   │       └── ProductService.kt         # Application services (use cases)
│   └── infrastructure/
│       └── adapters/
│           ├── InMemoryProductRepository.kt  # Repository adapter
│           ├── ProductController.kt          # REST API adapter
│           └── ProductDTOs.kt               # Data transfer objects
├── test/kotlin/                         # Comprehensive tests for all layers
└── resources/
    └── application.properties           # Application configuration
```

## Key Benefits

### 1. **Testability**
- Domain logic can be tested in isolation
- Easy to mock external dependencies
- Clear separation between business logic and infrastructure

### 2. **Flexibility**
- Easy to swap implementations (e.g., in-memory → database)
- Business logic is independent of frameworks
- Can add new adapters without changing core logic

### 3. **Maintainability**
- Clear boundaries between layers
- Single responsibility principle
- Dependency inversion principle

## Example Usage

### 1. Running the Application

```bash
./gradlew bootRun
```

The application starts on `http://localhost:8080`

### 2. API Endpoints

#### Create a Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "Gaming laptop",
    "price": 1299.99,
    "category": "ELECTRONICS"
  }'
```

#### Get All Products
```bash
curl http://localhost:8080/api/products
```

#### Get Product by ID
```bash
curl http://localhost:8080/api/products/{id}
```

#### Get Products by Category
```bash
curl http://localhost:8080/api/products/category/ELECTRONICS
```

#### Get Expensive Products
```bash
curl http://localhost:8080/api/products/expensive
```

#### Update Product Price
```bash
curl -X PUT http://localhost:8080/api/products/{id}/price \
  -H "Content-Type: application/json" \
  -d '{"price": 999.99}'
```

#### Delete Product
```bash
curl -X DELETE http://localhost:8080/api/products/{id}
```

### 3. Running Tests

```bash
./gradlew test
```

Tests cover all layers:
- **Domain tests**: Business logic validation
- **Application tests**: Service behavior with mocked dependencies  
- **Infrastructure tests**: Adapter implementations
- **Integration tests**: Full application context

## Domain Model Example

The `Product` entity demonstrates domain-driven design principles:

```kotlin
data class Product(
    val id: ProductId,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: ProductCategory,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    // Business validation in constructor
    init {
        require(name.isNotBlank()) { "Product name cannot be blank" }
        require(price >= BigDecimal.ZERO) { "Product price must be non-negative" }
    }

    // Business methods
    fun updatePrice(newPrice: BigDecimal): Product { /* ... */ }
    fun isExpensive(threshold: BigDecimal = BigDecimal("100.00")): Boolean { /* ... */ }
}
```

## Port-Adapter Examples

### Port (Interface)
```kotlin
interface ProductRepository {
    fun save(product: Product): Product
    fun findById(id: ProductId): Product?
    fun findAll(): List<Product>
    // ... other methods
}
```

### Adapter (Implementation)
```kotlin
@Repository
class InMemoryProductRepository : ProductRepository {
    private val products = ConcurrentHashMap<ProductId, Product>()
    
    override fun save(product: Product): Product {
        products[product.id] = product
        return product
    }
    // ... other implementations
}
```

## Technology Stack

- **Kotlin** - Modern JVM language
- **Spring Boot 3.1.5** - Application framework
- **Spring Web** - REST API support
- **Spring Actuator** - Production monitoring
- **Kotest** - Testing framework
- **MockK** - Mocking library for Kotlin
- **Gradle** - Build tool

## Building for Production

```bash
# Build JAR
./gradlew build

# Run JAR
java -jar build/libs/spring-boot-kotlin-template-0.0.1-SNAPSHOT.jar
```

## Extending the Architecture

### Adding a New Port
1. Define the interface in `application/ports/`
2. Implement it in `infrastructure/adapters/`
3. Use it in your application services

### Adding a New Entity
1. Create the entity in `domain/`
2. Add business methods and validation
3. Create corresponding services and adapters

### Adding a New Adapter
1. Implement the existing port interface
2. Add any adapter-specific configuration
3. Register with Spring (using `@Component`, `@Repository`, etc.)

This template provides a solid foundation for building scalable, maintainable applications following hexagonal architecture principles.