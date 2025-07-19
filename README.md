# Homeopathy Hospital Management System - Backend Documentation

## 📋 Table of Contents
1. [System Overview](#system-overview)
2. [Technology Stack](#technology-stack)
3. [Architecture](#architecture)
4. [Database Schema](#database-schema)
5. [Complete API Documentation](#complete-api-documentation)
6. [Module Documentation](#module-documentation)
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

### Core Entity Tables:

#### 1. User Management
| Table | Description | Key Fields |
|-------|-------------|------------|
| **users** | User accounts and authentication | id, username, email, password, phone, is_active |
| **roles** | System roles (ADMIN, DOCTOR, PATIENT, STAFF) | id, name |
| **user_roles** | Many-to-many user-role mapping | user_id, role_id |

#### 2. Doctor Management
| Table | Description | Key Fields |
|-------|-------------|------------|
| **doctor_details** | Doctor professional information | id, user_id, specialization, experience_years, consultation_fee |
| **doctor_schedules** | Doctor availability schedules | schedule_id, doctor_id, schedule_date, start_time, end_time, max_appointments |
| **doctor_timings** | Doctor working hours | id, doctor_id, day_of_week, start_time, end_time |

#### 3. Patient Management
| Table | Description | Key Fields |
|-------|-------------|------------|
| **patient_details** | Patient personal information | id, user_id, age, gender, address, emergency_contact |

#### 4. Appointment System
| Table | Description | Key Fields |
|-------|-------------|------------|
| **booking_appointments** | Appointment bookings | id, patient_id, doctor_id, schedule_id, appoint_date, status, token |
| **appointment_history** | Appointment status changes | id, appointment_id, status, notes, created_at |
| **daily_appointment_summary** | Daily appointment statistics | id, date, total_appointments, completed, cancelled, missed |

#### 5. Inventory Management

##### Core Inventory:
| Table | Description | Key Fields |
|-------|-------------|------------|
| **categories** | Medicine categories | id, name, description, is_active |
| **inventory_items** | Medicine and supplies | id, name, description, category_id, unit_price, reorder_level |
| **suppliers** | Medicine suppliers | id, name, contact_person, phone, email, address |
| **warehouses** | Storage locations | id, name, location, capacity |

##### Stock & Batch Tracking:
| Table | Description | Key Fields |
|-------|-------------|------------|
| **batches** | Medicine batch information | id, item_id, batch_number, expiry_date, quantity, cost_price, supplier_id |
| **stock_levels** | Current stock quantities | id, item_id, current_quantity, reserved_quantity |
| **stock_adjustments** | Stock corrections | id, item_id, adjustment_type, quantity, reason, adjusted_by |

##### Purchase & Receipt:
| Table | Description | Key Fields |
|-------|-------------|------------|
| **purchase_orders** | Purchase order management | id, supplier_id, order_date, expected_delivery, status, total_amount |
| **goods_receipts** | Received goods tracking | id, purchase_order_id, receipt_date, received_by, notes |
| **goods_receipt_items** | Individual received items | id, receipt_id, item_id, quantity_received, batch_number |

#### 6. Prescription System:
| Table | Description | Key Fields |
|-------|-------------|------------|
| **prescriptions** | Patient prescriptions | id, patient_id, doctor_id, prescription_date, diagnosis, notes, status |
| **prescription_items** | Prescribed medicines | id, prescription_id, item_id, dosage, frequency, duration, quantity |
| **dispense_transactions** | Medicine dispensing | id, prescription_id, dispensed_by, dispensed_at, total_amount, status |
| **return_transactions** | Medicine returns | id, dispense_id, returned_by, return_date, reason, refund_amount |

#### 7. Billing & Payment:
| Table | Description | Key Fields |
|-------|-------------|------------|
| **invoices** | Patient invoices | id, patient_id, invoice_date, due_date, total_amount, status |
| **invoice_items** | Invoice line items | id, invoice_id, dispense_id, item_name, quantity, unit_price, total_price |
| **payments** | Payment records | id, invoice_id, amount, payment_date, payment_method, transaction_id, status |
| **payment_terms** | Payment terms and conditions | id, name, description, days_allowed |

#### 8. Questionnaire System:
| Table | Description | Key Fields |
|-------|-------------|------------|
| **question_sets** | Health assessment questionnaires | id, name, description, is_active |
| **questions** | Individual questions | id, question_set_id, question_text, question_type, is_required |
| **answers** | Patient responses | id, question_id, patient_id, response, submitted_at |

#### 9. Supporting Entities:
| Table | Description | Key Fields |
|-------|-------------|------------|
| **activity_logs** | System activity tracking | id, user_id, action, entity_type, entity_id, details, ip_address |
| **advertisements** | Hospital advertisements | id, title, description, image_url, is_active |
| **verification_tokens** | Email verification | id, user_id, token, expiry_date |

## 🔌 Complete API Documentation

### Authentication & User Management

#### Admin Authentication (`/api/admin`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/register` | Register new admin | StaffDTO | User |
| `POST` | `/createMyProfile` | Create admin profile | StaffDTO | User |
| `PUT` | `/updateMyProfile` | Update admin profile | StaffDTO | User |
| `PUT` | `/updateProfileById/{id}` | Update specific admin | StaffDTO | User |
| `GET` | `/all` | Get all admin users | - | List<User> |
| `GET` | `/{id}` | Get admin by ID | - | User |
| `DELETE` | `/delete/{id}` | Delete admin user | - | String |
| `GET` | `/{userId}/roles` | Get user roles | - | Set<String> |
| `PUT` | `/{userId}/roles` | Update user roles | List<String> | String |
| `DELETE` | `/{userId}/roles` | Remove user role | roleName param | String |
| `GET` | `/role/{roleName}/users` | Get users by role | - | List<User> |
| `GET` | `/staff-roles` | Get all staff users | - | List<User> |

#### Doctor Authentication (`/api/doctor`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/register` | Register new doctor | DoctorDTO | User |
| `POST` | `/addProfile/{username}` | Add doctor profile | DoctorDTO | User |
| `GET` | `/me` | Get current doctor | - | User |
| `POST` | `/createMyProfile` | Create doctor profile | DoctorDTO | User |
| `PUT` | `/updateMyProfile` | Update doctor profile | DoctorDTO | User |
| `PUT` | `/updateProfileById/{id}` | Update specific doctor | DoctorDTO | User |
| `GET` | `/{doctorId}` | Get doctor by ID | - | User |
| `GET` | `/byId/{id}` | Get doctor by user ID | - | User |
| `GET` | `/search` | Search doctors | search param | List<User> |
| `GET` | `/all` | Get all doctors | - | List<User> |
| `GET` | `/availableDoctors` | Get available doctors | - | List<User> |
| `GET` | `/count` | Get doctor count | - | Long |

#### Patient Authentication (`/api/patient`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/register` | Register new patient | PatientRegisterDTO | User |
| `POST` | `/login` | Patient login | LoginDTO | User |
| `POST` | `/profile` | Create patient profile | PatientDTO | User |
| `GET` | `/me` | Get current patient | - | User |
| `POST` | `/addProfile/{username}` | Add patient profile | PatientDTO | User |
| `GET` | `/{patientId}` | Get patient by ID | - | User |
| `POST` | `/CreateProfile` | Create patient profile | PatientDTO | User |
| `PUT` | `/updateProfile/{id}` | Update patient profile | PatientDTO | User |
| `PUT` | `/updateMyProfile` | Update current patient | PatientDTO | User |
| `GET` | `/search` | Search patients | search param | List<User> |
| `GET` | `/count` | Get patient count | - | Long |
| `GET` | `/all` | Get all patients | - | List<User> |

#### General Authentication (`/api/auth`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/register` | General user registration | RegisterDTO | User |
| `POST` | `/login` | General user login | LoginDTO | User |
| `POST` | `/addProfilePic` | Add profile picture | MultipartFile | String |
| `GET` | `/me` | Get current user | - | User |

#### Email Verification (`/api/verify`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/request` | Request email verification | EmailRequestDTO | String |
| `POST` | `/confirm` | Confirm email verification | TokenDTO | String |

### Appointment Management

#### Booking Appointments (`/api/bookingAppointments`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create appointment (patient) | BookingAppointmentDTO | BookingAppointment |
| `POST` | `/byStaff` | Create appointment (staff) | BookingAppointmentDTO | BookingAppointment |
| `PUT` | `/{id}` | Update appointment | BookingAppointmentDTO | BookingAppointment |
| `DELETE` | `/{id}` | Cancel appointment | - | Void |
| `POST` | `/completed-appointment/{token}` | Mark appointment completed | - | BookingAppointment |
| `GET` | `/` | Get all appointments (paginated) | page, size params | Page<BookingAppointment> |
| `GET` | `/byId/{id}` | Get appointment by ID | - | BookingAppointment |
| `GET` | `/doctor/{doctorId}` | Get doctor appointments (paginated) | page, size params | Page<BookingAppointment> |
| `GET` | `/doctor/my-appointments` | Get current doctor appointments | page, size params | Page<BookingAppointment> |
| `GET` | `/patient/{patientId}` | Get patient appointments (paginated) | page, size params | Page<BookingAppointment> |
| `GET` | `/patient/my-appointments` | Get current patient appointments | page, size params | Page<BookingAppointment> |
| `GET` | `/schedule/{scheduleId}` | Get schedule appointments (paginated) | page, size params | Page<BookingAppointment> |
| `GET` | `/token/{token}` | Get appointment by token | - | BookingAppointment |
| `GET` | `/count` | Get appointment count | - | Long |

#### Schedule Management (`/api/schedule`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/create/{date}` | Create doctor schedule | - | DoctorSchedule |
| `GET` | `/doctor/{doctorId}` | Get doctor schedules | - | List<DoctorSchedule> |
| `GET` | `/doctor/{doctorId}/date/{date}` | Get doctor schedule by date | - | DoctorSchedule |
| `GET` | `/doctor/date/{date}` | Get current doctor schedule | - | DoctorSchedule |
| `GET` | `/byId/{scheduleId}` | Get schedule by ID | - | DoctorSchedule |
| `GET` | `/available/{date}` | Get available schedules | - | List<DoctorSchedule> |
| `POST` | `/book/{scheduleId}` | Book schedule slot | - | String |

#### Appointment History (`/api/appointmentHistory`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/add` | Add appointment history | AppointmentHistory | AppointmentHistory |
| `GET` | `/all` | Get all appointment history | - | List<AppointmentHistory> |

#### Daily Summary (`/daily-summary`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/save` | Save daily summary | DailyAppointmentSummary | DailyAppointmentSummary |
| `GET` | `/date/{date}` | Get summary by date | - | DailyAppointmentSummary |
| `DELETE` | `/delete/{id}` | Delete summary | - | String |

#### Appointment Slots (`/api/create-appointment-slots`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/date/{date}` | Create appointment slots | - | String |

### Inventory Management

#### Core Inventory

##### Categories (`/api/categories`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create category | CategoryDto | Category |
| `GET` | `/` | Get all categories | - | List<Category> |
| `GET` | `/{id}` | Get category by ID | - | Category |
| `PUT` | `/{id}` | Update category | CategoryDto | Category |
| `DELETE` | `/{id}` | Delete category | - | String |

##### Inventory Items (`/api/inventory-items`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create inventory item | InventoryItemDto | InventoryItem |
| `GET` | `/` | Get all inventory items | - | List<InventoryItem> |
| `GET` | `/{id}` | Get item by ID | - | InventoryItem |
| `PUT` | `/{id}` | Update inventory item | InventoryItemDto | InventoryItem |
| `DELETE` | `/{id}` | Delete inventory item | - | String |
| `PATCH` | `/{id}/stock` | Update item stock | StockUpdateDto | InventoryItem |

##### Suppliers (`/api/suppliers`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create supplier | SupplierDto | Supplier |
| `GET` | `/` | Get all suppliers | - | List<Supplier> |
| `GET` | `/{supplierId}` | Get supplier by ID | - | Supplier |
| `PUT` | `/{supplierId}` | Update supplier | SupplierDto | Supplier |
| `DELETE` | `/{supplierId}` | Delete supplier | - | String |

##### Warehouses (`/api/warehouses`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create warehouse | WarehouseDto | Warehouse |
| `GET` | `/` | Get all warehouses | - | List<Warehouse> |
| `GET` | `/{warehouseId}` | Get warehouse by ID | - | Warehouse |
| `PUT` | `/{warehouseId}` | Update warehouse | WarehouseDto | Warehouse |
| `DELETE` | `/{warehouseId}` | Delete warehouse | - | String |

#### Stock & Batch Tracking

##### Batches (`/api/batches`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create batch | CreateBatchDto | Batch |
| `GET` | `/` | Get all batches | - | List<Batch> |
| `GET` | `/{id}` | Get batch by ID | - | Batch |
| `GET` | `/inventory/{inventoryItemId}` | Get batches by item | - | List<Batch> |
| `GET` | `/status/{status}` | Get batches by status | - | List<Batch> |
| `GET` | `/inventory/{inventoryItemId}/status/{status}` | Get item batches by status | - | List<Batch> |
| `PUT` | `/{id}` | Update batch | CreateBatchDto | Batch |
| `DELETE` | `/{id}` | Delete batch | - | String |

##### Stock Levels (`/api/stock-levels`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create stock level | StockLevelDto | StockLevel |
| `GET` | `/` | Get all stock levels | - | List<StockLevel> |
| `GET` | `/{id}` | Get stock level by ID | - | StockLevel |
| `GET` | `/batch/{batchId}` | Get stock by batch | - | StockLevel |
| `GET` | `/warehouse/{warehouseId}` | Get stock by warehouse | - | List<StockLevel> |
| `PUT` | `/{id}` | Update stock level | StockLevelDto | StockLevel |
| `DELETE` | `/{id}` | Delete stock level | - | String |

##### Stock Adjustments (`/api/stock-adjustments`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create stock adjustment | CreateStockAdjustmentDto | StockAdjustment |
| `GET` | `/` | Get all adjustments | - | List<StockAdjustment> |
| `GET` | `/{id}` | Get adjustment by ID | - | StockAdjustment |
| `GET` | `/stock-level/{stockLevelId}` | Get adjustments by stock level | - | List<StockAdjustment> |
| `PUT` | `/{id}` | Update adjustment | CreateStockAdjustmentDto | StockAdjustment |
| `DELETE` | `/{id}` | Delete adjustment | - | String |

#### Purchase & Receipt

##### Purchase Orders (`/api/purchase-orders`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create purchase order | CreatePurchaseOrderDto | PurchaseOrder |
| `GET` | `/` | Get all purchase orders | - | List<PurchaseOrder> |
| `GET` | `/{id}` | Get purchase order by ID | - | PurchaseOrder |
| `PUT` | `/{id}` | Update purchase order | CreatePurchaseOrderDto | PurchaseOrder |
| `DELETE` | `/{id}` | Delete purchase order | - | String |
| `GET` | `/supplier/{supplierId}` | Get orders by supplier | - | List<PurchaseOrder> |
| `GET` | `/status/{status}` | Get orders by status | - | List<PurchaseOrder> |
| `PATCH` | `/{id}/status` | Update order status | StatusUpdateDto | PurchaseOrder |
| `DELETE` | `/{orderId}/items/{itemId}` | Delete order item | - | String |

##### Goods Receipts (`/api/goods-receipts`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create goods receipt | CreateGoodsReceiptDTO | GoodsReceipt |
| `GET` | `/` | Get all goods receipts | - | List<GoodsReceipt> |
| `GET` | `/{id}` | Get receipt by ID | - | GoodsReceipt |
| `PUT` | `/{id}` | Update goods receipt | CreateGoodsReceiptDTO | GoodsReceipt |
| `DELETE` | `/{id}` | Delete goods receipt | - | String |
| `GET` | `/by-purchase-order/{purchaseOrderId}` | Get receipts by purchase order | - | List<GoodsReceipt> |

##### Goods Receipt Items (`/api/goods-receipts/items`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/receipt/{receiptId}` | Add item to receipt | CreateGoodsReceiptItemDTO | GoodsReceiptItem |
| `GET` | `/{itemId}` | Get receipt item by ID | - | GoodsReceiptItem |
| `GET` | `/receipt/{receiptId}` | Get items by receipt | - | List<GoodsReceiptItem> |
| `DELETE` | `/{itemId}` | Delete receipt item | - | String |

### Prescription Management

#### Prescriptions (`/api/prescriptions`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create prescription | CreatePrescriptionDto | Prescription |
| `GET` | `/` | Get all prescriptions | - | List<Prescription> |
| `GET` | `/{id}` | Get prescription by ID | - | Prescription |
| `PUT` | `/{id}` | Update prescription | CreatePrescriptionDto | Prescription |
| `DELETE` | `/{id}` | Delete prescription | - | String |
| `GET` | `/booking/{bookingId}` | Get prescription by booking | - | Prescription |
| `GET` | `/token/{token}` | Get prescription by token | - | Prescription |
| `GET` | `/doctor/{doctorId}` | Get prescriptions by doctor | - | List<Prescription> |
| `GET` | `/patient/{patientId}` | Get prescriptions by patient | - | List<Prescription> |
| `GET` | `/patient/{patientId}/instructions` | Get patient instructions | - | List<Prescription> |

#### Prescription Items (`/api/prescriptions/items`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create prescription item | CreatePrescriptionItemDto | PrescriptionItem |
| `GET` | `/` | Get all prescription items | - | List<PrescriptionItem> |
| `GET` | `/{id}` | Get item by ID | - | PrescriptionItem |
| `GET` | `/prescription/{prescriptionId}` | Get items by prescription | - | List<PrescriptionItem> |
| `PUT` | `/{id}` | Update prescription item | CreatePrescriptionItemDto | PrescriptionItem |
| `DELETE` | `/{id}` | Delete prescription item | - | String |

#### Return Transactions (`/api/return-transactions`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create return transaction | CreateReturnTransactionDto | ReturnTransaction |
| `GET` | `/` | Get all return transactions | - | List<ReturnTransaction> |
| `GET` | `/{id}` | Get return by ID | - | ReturnTransaction |
| `PUT` | `/{id}` | Update return transaction | CreateReturnTransactionDto | ReturnTransaction |
| `DELETE` | `/{id}` | Delete return transaction | - | String |

### Billing & Payment

#### Invoices (`/api/invoices`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create invoice | CreateInvoiceDto | Invoice |
| `GET` | `/` | Get all invoices | - | List<Invoice> |
| `GET` | `/{id}` | Get invoice by ID | - | Invoice |
| `PUT` | `/{id}` | Update invoice | CreateInvoiceDto | Invoice |
| `DELETE` | `/{id}` | Delete invoice | - | String |

#### Invoice Items (`/api/invoices/{invoiceId}/items`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Add item to invoice | CreateInvoiceItemDto | InvoiceItem |
| `GET` | `/` | Get all invoice items | - | List<InvoiceItem> |
| `GET` | `/all` | Get all invoice items | - | List<InvoiceItem> |
| `GET` | `/{itemId}` | Get invoice item by ID | - | InvoiceItem |
| `PUT` | `/{itemId}` | Update invoice item | CreateInvoiceItemDto | InvoiceItem |
| `DELETE` | `/{itemId}` | Delete invoice item | - | String |

#### Payments (`/api/payments`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Record payment | CreatePaymentDto | Payment |
| `GET` | `/` | Get all payments | - | List<Payment> |
| `GET` | `/{id}` | Get payment by ID | - | Payment |
| `GET` | `/invoice/{invoiceId}` | Get payments by invoice | - | List<Payment> |
| `PUT` | `/{id}` | Update payment | CreatePaymentDto | Payment |
| `DELETE` | `/{id}` | Delete payment | - | String |

#### Payment Terms (`/api/payment-terms`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create payment terms | PaymentTermsDto | PaymentTerms |
| `GET` | `/` | Get all payment terms | - | List<PaymentTerms> |
| `GET` | `/{id}` | Get payment terms by ID | - | PaymentTerms |
| `PUT` | `/{id}` | Update payment terms | PaymentTermsDto | PaymentTerms |
| `DELETE` | `/{id}` | Delete payment terms | - | String |

### Questionnaire System

#### Question Sets (`/api/question-sets`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create question set | QuestionSetDto | QuestionSet |
| `GET` | `/` | Get all question sets | - | List<QuestionSet> |
| `GET` | `/{id}` | Get question set by ID | - | QuestionSet |
| `GET` | `/{id}/submissions` | Get submissions by question set | - | List<Answer> |
| `POST` | `/{id}/submit` | Submit answers | QuestionAnswerDto | String |

### Administrative Functions

#### Activity Logs (`/api/activity-log`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/` | Create activity log | ActivityLog | ActivityLog |
| `GET` | `/` | Get all activity logs | - | List<ActivityLog> |
| `GET` | `/{id}` | Get activity log by ID | - | ActivityLog |
| `PUT` | `/{id}` | Update activity log | ActivityLog | ActivityLog |
| `DELETE` | `/{id}` | Delete activity log | - | String |

#### Advertisements (`/api/ads`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `GET` | `/` | Get all advertisements | - | List<Advertisement> |
| `POST` | `/` | Create advertisement | MultipartFile + form data | Advertisement |
| `GET` | `/{id}` | Get advertisement by ID | - | Advertisement |
| `PUT` | `/{id}` | Update advertisement | MultipartFile + form data | Advertisement |
| `DELETE` | `/{id}` | Delete advertisement | - | String |
| `PATCH` | `/select/{id}` | Select advertisement | - | String |
| `GET` | `/active` | Get active advertisements | - | List<Advertisement> |
| `GET` | `/{id}/status` | Get advertisement status | - | String |

#### Email Services (`/api/sendEmail`)
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `GET` | `/` | Send email | token param | String |

## 📦 Module Documentation

### 1. Authentication & Authorization Module

**Purpose**: Handles user authentication, authorization, and role-based access control.

**Key Components**:
- `AdminAuthenticationController` - Admin-specific authentication
- `DoctorAuthenticationController` - Doctor authentication and profile management
- `PatientAuthenticationController` - Patient registration and authentication
- `AuthController` - General authentication endpoints
- `EmailVerificationController` - Email verification system
- `SecurityConfigurer` - Spring Security configuration
- `JwtTokenProvider` - JWT token generation and validation

**Features**:
- Multi-role authentication (Admin, Doctor, Patient, Staff)
- JWT-based stateless authentication
- Password encryption using BCrypt
- Email verification system
- Role-based endpoint protection
- Profile picture upload support

### 2. Appointment Management Module

**Purpose**: Manages the complete appointment lifecycle from scheduling to completion.

**Key Components**:
- `BookingAppointmentController` - Appointment booking and management
- `ScheduleController` - Doctor schedule management
- `AppointmentHistoryController` - Appointment history tracking
- `DailyAppointmentSummaryController` - Daily appointment statistics
- `AppointmentSlotController` - Appointment slot creation

**Features**:
- Doctor schedule creation and management
- Patient appointment booking
- Appointment status tracking (SCHEDULED, CONFIRMED, COMPLETED, CANCELLED)
- Token-based appointment identification
- Appointment history and analytics
- Daily appointment summaries
- Automated appointment slot creation

### 3. Inventory Management Module

**Purpose**: Comprehensive inventory management for medicines and medical supplies.

#### 3.1 Core Inventory
- `CategoryController` - Medicine category management
- `InventoryItemController` - Medicine item management
- `SupplierController` - Supplier information management
- `WarehouseController` - Warehouse management

#### 3.2 Stock & Batch Tracking
- `BatchController` - Medicine batch management
- `StockLevelController` - Stock level monitoring
- `StockAdjustmentController` - Stock adjustments and corrections

#### 3.3 Purchase & Receipt
- `PurchaseOrderController` - Purchase order management
- `GoodsReceiptController` - Goods receipt processing
- `GoodsReceiptItemController` - Goods receipt item management

**Features**:
- Medicine categorization and cataloging
- Batch tracking with expiry dates
- Stock level monitoring with reorder alerts
- Supplier management
- Purchase order processing
- Goods receipt management
- Warehouse management
- Stock adjustments and corrections

### 4. Prescription Management Module

**Purpose**: Digital prescription creation, management, and dispensing.

**Key Components**:
- `PrescriptionController` - Prescription creation and management
- `PrescriptionItemController` - Prescription item management
- `ReturnTransactionController` - Medicine returns

**Features**:
- Digital prescription creation
- Medicine dosage and frequency specification
- Prescription status tracking
- Medicine dispensing with batch tracking
- Prescription history
- Medicine return processing

### 5. Billing & Payment Module

**Purpose**: Invoice generation, payment processing, and financial management.

**Key Components**:
- `InvoiceController` - Invoice generation and management
- `InvoiceItemController` - Invoice item management
- `PaymentController` - Payment processing
- `PaymentTermsController` - Payment terms management

**Features**:
- Automated invoice generation from prescriptions
- Multiple payment methods support
- Payment status tracking
- Payment terms management
- Financial reporting

### 6. Questionnaire System Module

**Purpose**: Patient health assessment through structured questionnaires.

**Key Components**:
- `QuestionSetController` - Questionnaire management

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
- Advertisement management with image uploads
- Email notification system
- System monitoring and analytics

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

**Documentation Version**: 2.0  
**Last Updated**: December 2024  
**System Version**: Homeopathy Hospital Management System v1.0

---

## 📊 API Summary Statistics

### Total Endpoints: **150+**
- **Authentication**: 25 endpoints
- **Appointment Management**: 35 endpoints
- **Inventory Management**: 45 endpoints
- **Prescription Management**: 15 endpoints
- **Billing & Payment**: 20 endpoints
- **Administrative**: 15 endpoints

### HTTP Methods Distribution:
- **GET**: 60% (Read operations)
- **POST**: 25% (Create operations)
- **PUT**: 10% (Update operations)
- **DELETE**: 5% (Delete operations)

### Response Formats:
- **JSON**: 95% of responses
- **String**: 5% (Simple confirmations)
- **File Downloads**: Image and document downloads 