# Homeopathy Hospital Management System - Backend Documentation

## 📋 Table of Contents
1. [System Overview](#system-overview)
2. [Technology Stack](#technology-stack)
3. [Architecture](#architecture)
4. [Database Schema](#database-schema)
5. [Module Documentation](#module-documentation)
6. [API Documentation](#api-documentation)
7. [Security Implementation](#security-implementation)
8. [Testing Strategy](#testing-strategy)
9. [Deployment](#deployment)
10. [Configuration](#configuration)

## 🏥 System Overview

The Homeopathy Hospital Management System is a comprehensive Spring Boot backend application designed to manage all aspects of a homeopathy hospital's operations. The system provides robust APIs for patient management, doctor scheduling, appointment booking, inventory management, prescription handling, billing, and administrative functions.

### Key Features:
- **User Management**: Multi-role authentication (Admin, Doctor, Patient, Staff)
- **Appointment System**: Complete appointment lifecycle management
- **Inventory Management**: Medicine stock, batch tracking, supplier management
- **Prescription System**: Digital prescription creation and dispensing
- **Billing & Payments**: Invoice generation and payment processing
- **Questionnaire System**: Patient health assessment questionnaires
- **Activity Logging**: Comprehensive audit trail
- **Email Notifications**: Automated email services
- **File Management**: Advertisement and document uploads

## 🛠 Technology Stack

### Core Technologies:
- **Java 17+** - Primary programming language
- **Spring Boot 3.x** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Primary database
- **H2 Database** - Testing database
- **Maven** - Build tool and dependency management

### Additional Technologies:
- **JWT (JSON Web Tokens)** - Stateless authentication
- **Flyway** - Database migration
- **RabbitMQ** - Message queuing for payments
- **Spring Mail** - Email services
- **Cloudinary** - Cloud file storage
- **JUnit 5** - Unit and integration testing
- **MockMvc** - Web layer testing

## 🏗 Architecture

### Layered Architecture:
```
┌─────────────────────────────────────┐
│           Controllers               │  ← REST API Endpoints
├─────────────────────────────────────┤
│            Services                 │  ← Business Logic
├─────────────────────────────────────┤
│          Repositories               │  ← Data Access Layer
├─────────────────────────────────────┤
│           Database                  │  ← PostgreSQL/H2
└─────────────────────────────────────┘
```

### Package Structure:
```
com.G19.hospital/
├── config/           # Configuration classes
├── controller/       # REST controllers
├── DTO/             # Data Transfer Objects
├── exceptions/      # Custom exceptions
├── model/           # Entity classes
├── repository/      # Data access repositories
├── security/        # Security configuration
├── service/         # Business logic services
└── util/            # Utility classes
```

## 🗄 Database Schema

### Core Entities:

#### 1. User Management
```sql
-- Users table (extends BaseEntity)
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(20),
    is_active BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Roles table
CREATE TABLE roles (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

-- User-Role mapping
CREATE TABLE user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id)
);
```

#### 2. Doctor Management
```sql
-- Doctor details
CREATE TABLE doctor_details (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    specialization VARCHAR(100),
    experience_years INTEGER,
    consultation_fee DECIMAL(10,2)
);

-- Doctor schedules
CREATE TABLE doctor_schedules (
    schedule_id BIGINT PRIMARY KEY,
    doctor_id BIGINT REFERENCES users(id),
    schedule_date DATE,
    start_time TIME,
    end_time TIME,
    max_appointments INTEGER,
    is_active BOOLEAN
);
```

#### 3. Patient Management
```sql
-- Patient details
CREATE TABLE patient_details (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    age INTEGER,
    gender VARCHAR(10),
    address TEXT,
    emergency_contact VARCHAR(20)
);
```

#### 4. Appointment System
```sql
-- Booking appointments
CREATE TABLE booking_appointments (
    id BIGINT PRIMARY KEY,
    patient_id BIGINT REFERENCES users(id),
    doctor_id BIGINT REFERENCES users(id),
    schedule_id BIGINT REFERENCES doctor_schedules(schedule_id),
    appoint_date DATE,
    status VARCHAR(20),
    token VARCHAR(255),
    created_at TIMESTAMP
);

-- Appointment history
CREATE TABLE appointment_history (
    id BIGINT PRIMARY KEY,
    appointment_id BIGINT REFERENCES booking_appointments(id),
    status VARCHAR(20),
    notes TEXT,
    created_at TIMESTAMP
);
```

#### 5. Inventory Management

##### Core Inventory:
```sql
-- Categories
CREATE TABLE categories (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    is_active BOOLEAN
);

-- Inventory items
CREATE TABLE inventory_items (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    category_id BIGINT REFERENCES categories(id),
    unit_price DECIMAL(10,2),
    reorder_level INTEGER,
    is_active BOOLEAN
);

-- Suppliers
CREATE TABLE suppliers (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(255),
    address TEXT
);
```

##### Stock & Batch Tracking:
```sql
-- Batches
CREATE TABLE batches (
    id BIGINT PRIMARY KEY,
    item_id BIGINT REFERENCES inventory_items(id),
    batch_number VARCHAR(100),
    expiry_date DATE,
    quantity INTEGER,
    cost_price DECIMAL(10,2),
    supplier_id BIGINT REFERENCES suppliers(id)
);

-- Stock levels
CREATE TABLE stock_levels (
    id BIGINT PRIMARY KEY,
    item_id BIGINT REFERENCES inventory_items(id),
    current_quantity INTEGER,
    reserved_quantity INTEGER,
    last_updated TIMESTAMP
);

-- Stock adjustments
CREATE TABLE stock_adjustments (
    id BIGINT PRIMARY KEY,
    item_id BIGINT REFERENCES inventory_items(id),
    adjustment_type VARCHAR(20),
    quantity INTEGER,
    reason TEXT,
    adjusted_by BIGINT REFERENCES users(id),
    adjusted_at TIMESTAMP
);
```

##### Purchase & Receipt:
```sql
-- Purchase orders
CREATE TABLE purchase_orders (
    id BIGINT PRIMARY KEY,
    supplier_id BIGINT REFERENCES suppliers(id),
    order_date DATE,
    expected_delivery DATE,
    status VARCHAR(20),
    total_amount DECIMAL(10,2),
    created_by BIGINT REFERENCES users(id)
);

-- Goods receipts
CREATE TABLE goods_receipts (
    id BIGINT PRIMARY KEY,
    purchase_order_id BIGINT REFERENCES purchase_orders(id),
    receipt_date DATE,
    received_by BIGINT REFERENCES users(id),
    notes TEXT
);
```

#### 6. Prescription System:
```sql
-- Prescriptions
CREATE TABLE prescriptions (
    id BIGINT PRIMARY KEY,
    patient_id BIGINT REFERENCES users(id),
    doctor_id BIGINT REFERENCES users(id),
    prescription_date DATE,
    diagnosis TEXT,
    notes TEXT,
    status VARCHAR(20)
);

-- Prescription items
CREATE TABLE prescription_items (
    id BIGINT PRIMARY KEY,
    prescription_id BIGINT REFERENCES prescriptions(id),
    item_id BIGINT REFERENCES inventory_items(id),
    dosage VARCHAR(100),
    frequency VARCHAR(100),
    duration VARCHAR(100),
    quantity INTEGER
);

-- Dispense transactions
CREATE TABLE dispense_transactions (
    id BIGINT PRIMARY KEY,
    prescription_id BIGINT REFERENCES prescriptions(id),
    dispensed_by BIGINT REFERENCES users(id),
    dispensed_at TIMESTAMP,
    total_amount DECIMAL(10,2),
    status VARCHAR(20)
);
```

#### 7. Billing & Payment:
```sql
-- Invoices
CREATE TABLE invoices (
    id BIGINT PRIMARY KEY,
    patient_id BIGINT REFERENCES users(id),
    invoice_date DATE,
    due_date DATE,
    total_amount DECIMAL(10,2),
    status VARCHAR(20),
    created_by BIGINT REFERENCES users(id)
);

-- Invoice items
CREATE TABLE invoice_items (
    id BIGINT PRIMARY KEY,
    invoice_id BIGINT REFERENCES invoices(id),
    dispense_id BIGINT REFERENCES dispense_transactions(id),
    item_name VARCHAR(255),
    quantity INTEGER,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2)
);

-- Payments
CREATE TABLE payments (
    id BIGINT PRIMARY KEY,
    invoice_id BIGINT REFERENCES invoices(id),
    amount DECIMAL(10,2),
    payment_date TIMESTAMP,
    payment_method VARCHAR(50),
    transaction_id VARCHAR(255),
    status VARCHAR(20)
);
```

#### 8. Questionnaire System:
```sql
-- Question sets
CREATE TABLE question_sets (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    is_active BOOLEAN
);

-- Questions
CREATE TABLE questions (
    id BIGINT PRIMARY KEY,
    question_set_id BIGINT REFERENCES question_sets(id),
    question_text TEXT,
    question_type VARCHAR(50),
    is_required BOOLEAN
);

-- Answers
CREATE TABLE answers (
    id BIGINT PRIMARY KEY,
    question_id BIGINT REFERENCES questions(id),
    patient_id BIGINT REFERENCES users(id),
    response TEXT,
    submitted_at TIMESTAMP
);
```

#### 9. Supporting Entities:
```sql
-- Activity logs
CREATE TABLE activity_logs (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    action VARCHAR(100),
    entity_type VARCHAR(50),
    entity_id BIGINT,
    details TEXT,
    ip_address VARCHAR(45),
    created_at TIMESTAMP
);

-- Advertisements
CREATE TABLE advertisements (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    image_url VARCHAR(500),
    is_active BOOLEAN,
    created_at TIMESTAMP
);

-- Verification tokens
CREATE TABLE verification_tokens (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    token VARCHAR(255),
    expiry_date TIMESTAMP
);
```

## 📦 Module Documentation

### 1. Authentication & Authorization Module

**Purpose**: Handles user authentication, authorization, and role-based access control.

**Key Components**:
- `AdminAuthenticationController` - Admin-specific authentication
- `DoctorAuthenticationController` - Doctor authentication and profile management
- `PatientAuthenticationController` - Patient registration and authentication
- `AuthController` - General authentication endpoints
- `SecurityConfigurer` - Spring Security configuration
- `JwtTokenProvider` - JWT token generation and validation

**Features**:
- Multi-role authentication (Admin, Doctor, Patient, Staff)
- JWT-based stateless authentication
- Password encryption using BCrypt
- Email verification system
- Role-based endpoint protection

### 2. Appointment Management Module

**Purpose**: Manages the complete appointment lifecycle from scheduling to completion.

**Key Components**:
- `BookingAppointmentController` - Appointment booking and management
- `ScheduleController` - Doctor schedule management
- `AppointmentHistoryController` - Appointment history tracking

**Features**:
- Doctor schedule creation and management
- Patient appointment booking
- Appointment status tracking (SCHEDULED, CONFIRMED, COMPLETED, CANCELLED)
- Token-based appointment identification
- Appointment history and analytics

### 3. Inventory Management Module

**Purpose**: Comprehensive inventory management for medicines and medical supplies.

#### 3.1 Core Inventory
- `CategoryController` - Medicine category management
- `InventoryItemController` - Medicine item management
- `SupplierController` - Supplier information management

#### 3.2 Stock & Batch Tracking
- `BatchController` - Medicine batch management
- `StockLevelController` - Stock level monitoring
- `StockAdjustmentController` - Stock adjustments and corrections

#### 3.3 Purchase & Receipt
- `PurchaseOrderController` - Purchase order management
- `GoodsReceiptController` - Goods receipt processing

**Features**:
- Medicine categorization and cataloging
- Batch tracking with expiry dates
- Stock level monitoring with reorder alerts
- Supplier management
- Purchase order processing
- Goods receipt management

### 4. Prescription Management Module

**Purpose**: Digital prescription creation, management, and dispensing.

**Key Components**:
- `PrescriptionController` - Prescription creation and management
- `PrescriptionItemController` - Prescription item management
- `DispenseTransactionController` - Medicine dispensing

**Features**:
- Digital prescription creation
- Medicine dosage and frequency specification
- Prescription status tracking
- Medicine dispensing with batch tracking
- Prescription history

### 5. Billing & Payment Module

**Purpose**: Invoice generation, payment processing, and financial management.

**Key Components**:
- `InvoiceController` - Invoice generation and management
- `InvoiceItemController` - Invoice item management
- `PaymentController` - Payment processing
- `OverdueReminderController` - Payment reminder system

**Features**:
- Automated invoice generation from prescriptions
- Multiple payment methods support
- Payment status tracking
- Overdue payment reminders
- Financial reporting

### 6. Questionnaire System Module

**Purpose**: Patient health assessment through structured questionnaires.

**Key Components**:
- `QuestionSetController` - Questionnaire management
- `QuestionController` - Question management
- `AnswerController` - Answer collection and analysis

**Features**:
- Customizable questionnaire creation
- Multiple question types (text, multiple choice, etc.)
- Patient response collection
- Health assessment analytics

### 7. Administrative Module

**Purpose**: System administration and monitoring.

**Key Components**:
- `ActivityLogController` - System activity monitoring
- `AdvertisementController` - Advertisement management
- `EmailController` - Email notification system

**Features**:
- Comprehensive activity logging
- Advertisement management
- Email notification system
- System monitoring and analytics

## 🔌 API Documentation

### Authentication Endpoints

#### Admin Authentication
```
POST /api/admin/register          - Admin registration
POST /api/admin/login             - Admin login
POST /api/admin/addProfile        - Add admin profile
GET  /api/admin/{adminId}         - Get admin details
```

#### Doctor Authentication
```
POST /api/doctor/register         - Doctor registration
POST /api/doctor/login            - Doctor login
POST /api/doctor/addProfile       - Add doctor profile
GET  /api/doctor/{doctorId}       - Get doctor details
```

#### Patient Authentication
```
POST /api/patient/register        - Patient registration
POST /api/patient/login           - Patient login
POST /api/patient/addProfile      - Add patient profile
GET  /api/patient/{patientId}     - Get patient details
```

### Appointment Endpoints

```
POST /api/bookingAppointments/byStaff     - Book appointment (staff)
POST /api/bookingAppointments/byPatient   - Book appointment (patient)
GET  /api/bookingAppointments/patient/{patientId}  - Get patient appointments
GET  /api/bookingAppointments/doctor/{doctorId}    - Get doctor appointments
PUT  /api/bookingAppointments/{id}/status - Update appointment status
```

### Schedule Endpoints

```
POST /api/schedule/create/{date}  - Create doctor schedule
GET  /api/schedule/doctor/{doctorId} - Get doctor schedules
PUT  /api/schedule/{id}           - Update schedule
DELETE /api/schedule/{id}         - Delete schedule
```

### Inventory Endpoints

#### Core Inventory
```
GET  /api/categories              - Get all categories
POST /api/categories              - Create category
PUT  /api/categories/{id}         - Update category
DELETE /api/categories/{id}       - Delete category

GET  /api/inventory-items         - Get all inventory items
POST /api/inventory-items         - Create inventory item
PUT  /api/inventory-items/{id}    - Update inventory item
DELETE /api/inventory-items/{id}  - Delete inventory item
```

#### Stock Management
```
GET  /api/batches                 - Get all batches
POST /api/batches                 - Create batch
PUT  /api/batches/{id}            - Update batch

GET  /api/stock-levels            - Get stock levels
POST /api/stock-adjustments       - Create stock adjustment
```

### Prescription Endpoints

```
GET  /api/prescriptions           - Get all prescriptions
POST /api/prescriptions           - Create prescription
GET  /api/prescriptions/{id}      - Get prescription by ID
PUT  /api/prescriptions/{id}      - Update prescription

POST /api/prescriptions/{id}/dispense - Dispense prescription
```

### Billing Endpoints

```
GET  /api/invoices                - Get all invoices
POST /api/invoices                - Create invoice
GET  /api/invoices/{id}           - Get invoice by ID
PUT  /api/invoices/{id}           - Update invoice

POST /api/payments                - Record payment
GET  /api/payments/invoice/{invoiceId} - Get invoice payments
```

### Questionnaire Endpoints

```
GET  /api/question-sets           - Get all question sets
POST /api/question-sets           - Create question set
GET  /api/question-sets/{id}      - Get question set by ID
POST /api/question-sets/{id}/submit - Submit answers
```

## 🔐 Security Implementation

### Authentication Flow:
1. **User Registration**: User registers with email/phone and password
2. **Email Verification**: Verification token sent to user's email
3. **Login**: User provides credentials, JWT token generated
4. **Token Validation**: All subsequent requests validated using JWT

### Authorization:
- **Role-based Access Control**: Different endpoints accessible based on user roles
- **Method-level Security**: `@PreAuthorize` annotations for fine-grained control
- **CORS Configuration**: Cross-origin resource sharing properly configured

### Security Features:
- **Password Encryption**: BCrypt password hashing
- **JWT Tokens**: Stateless authentication with configurable expiration
- **CORS Protection**: Configured for frontend integration
- **Input Validation**: Comprehensive request validation
- **SQL Injection Prevention**: JPA/Hibernate parameterized queries

## 🧪 Testing Strategy

### Test Coverage:
- **Unit Tests**: Individual component testing
- **Integration Tests**: Controller layer testing with MockMvc
- **Repository Tests**: Data access layer testing
- **Service Tests**: Business logic testing

### Test Structure:
```
src/test/java/com/G19/hospital/
├── controller/           # Controller integration tests
├── service/             # Service unit tests
├── repository/          # Repository tests
├── integration/         # End-to-end tests
└── TestBase.java        # Base test class with common setup
```

### Test Features:
- **H2 In-Memory Database**: Isolated test environment
- **Test Data Builders**: Reusable test data creation
- **MockMvc Testing**: Web layer testing
- **Comprehensive Cleanup**: Test data isolation

## 🚀 Deployment

### Prerequisites:
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher
- RabbitMQ (for payment processing)

### Configuration:
1. **Database Setup**: Create PostgreSQL database
2. **Environment Variables**: Configure database connection
3. **Email Configuration**: Set up SMTP settings
4. **File Storage**: Configure Cloudinary or local storage
5. **RabbitMQ**: Set up message queue for payments

### Build and Run:
```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run

# Or using the JAR file
java -jar target/hospital-management-0.0.1-SNAPSHOT.jar
```

### Docker Deployment:
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/hospital-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/app.jar"]
```

## ⚙ Configuration

### Application Properties:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/HomeopathyDB
spring.datasource.username=postgres
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8000

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

# JWT Configuration
security.jwt.token.secret-key=your-secret-key
security.jwt.token.expiration=31536000000

# File Upload Configuration
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB

# RabbitMQ Configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### Environment-Specific Configuration:
- **Development**: H2 in-memory database, detailed logging
- **Testing**: H2 database, test-specific configurations
- **Production**: PostgreSQL, optimized logging, security hardening

## 📊 Performance Considerations

### Database Optimization:
- **Indexing**: Proper indexes on frequently queried columns
- **Connection Pooling**: HikariCP for database connection management
- **Query Optimization**: Efficient JPA queries and pagination

### Caching Strategy:
- **Application-level Caching**: Caching frequently accessed data
- **Database Query Caching**: Hibernate second-level cache
- **Static Resource Caching**: Static content caching

### Scalability:
- **Horizontal Scaling**: Stateless design supports multiple instances
- **Load Balancing**: Ready for load balancer integration
- **Microservices Ready**: Modular design for future microservices migration

## 🔧 Maintenance and Monitoring

### Logging:
- **Structured Logging**: JSON format logs for better parsing
- **Log Levels**: Configurable logging levels
- **Audit Trail**: Comprehensive activity logging

### Health Checks:
- **Database Connectivity**: Health check endpoints
- **External Services**: Email, file storage health monitoring
- **Application Metrics**: Performance and resource monitoring

### Backup Strategy:
- **Database Backups**: Regular PostgreSQL backups
- **Configuration Backups**: Environment-specific configurations
- **File Backups**: Uploaded files and documents

## 🤝 Integration Points

### External Services:
- **Email Service**: SMTP integration for notifications
- **File Storage**: Cloudinary for advertisement images
- **Payment Gateway**: RabbitMQ integration for payment processing
- **SMS Gateway**: Ready for SMS notification integration

### API Integration:
- **RESTful APIs**: Standard REST endpoints
- **JSON Responses**: Consistent JSON response format
- **Error Handling**: Standardized error responses
- **API Versioning**: Ready for API versioning

## 📈 Future Enhancements

### Planned Features:
- **Real-time Notifications**: WebSocket integration
- **Mobile App API**: Mobile-specific endpoints
- **Advanced Analytics**: Business intelligence dashboard
- **Multi-language Support**: Internationalization
- **Advanced Reporting**: Comprehensive reporting system

### Technical Improvements:
- **Microservices Architecture**: Service decomposition
- **Event Sourcing**: Event-driven architecture
- **API Gateway**: Centralized API management
- **Container Orchestration**: Kubernetes deployment

---

## 📞 Support and Contact

For technical support, bug reports, or feature requests, please contact the development team.

**Documentation Version**: 1.0  
**Last Updated**: December 2024  
**System Version**: Homeopathy Hospital Management System v1.0 