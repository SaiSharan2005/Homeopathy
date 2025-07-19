# Hospital Management System - Test Suite

This directory contains comprehensive tests for the Hospital Management System backend API.

## Test Structure

### Controller Tests
All controller tests are located in `src/test/java/com/G19/hospital/controller/` and follow a consistent pattern:

- **Authentication Controllers**
  - `AuthControllerTest.java` - User registration and login
  - `AdminAuthenticationControllerTest.java` - Admin operations
  - `DoctorAuthenticationControllerTest.java` - Doctor operations
  - `PatientAuthenticationControllerTest.java` - Patient operations

- **Appointment Management Controllers**
  - `BookingAppointmentControllerTest.java` - Appointment booking and management
  - `ScheduleControllerTest.java` - Doctor schedules
  - `AppointmentSlotControllerTest.java` - Slot management
  - `DoctorTimingControllerTest.java` - Doctor timing

- **Inventory Management Controllers**
  - `CategoryControllerTest.java` - Inventory categories
  - `InventoryItemControllerTest.java` - Inventory items
  - `PrescriptionControllerTest.java` - Prescription management
  - `InvoiceControllerTest.java` - Billing and invoicing

- **Communication Controllers**
  - `EmailControllerTest.java` - Email functionality
  - `EmailVerificationControllerTest.java` - Email verification
  - `AdvertisementControllerTest.java` - Advertisement management

- **System Management Controllers**
  - `ActivityLogControllerTest.java` - Activity logging
  - `QuestionSetControllerTest.java` - Questionnaire management
  - `SubmissionControllerTest.java` - Form submissions

- **Reporting Controllers**
  - `AppointmentHistoryControllerTest.java` - Appointment history
  - `DailyAppointmentSummaryControllerTest.java` - Daily summaries

## Test Configuration

### Test Database
- Uses H2 in-memory database for testing
- Configuration: `src/test/resources/application-test.properties`
- Database is created and destroyed for each test

### Test Base Class
All controller tests extend `TestBase.java` which provides:
- MockMvc setup for HTTP testing
- Common test data builders
- JSON serialization utilities
- Database cleanup utilities

## Running Tests

### Run All Tests
```bash
# Using Maven
mvn test

# Using Maven wrapper
./mvnw test

# Using the provided batch file (Windows)
run-tests.bat
```

### Run Specific Test Classes
```bash
# Run all controller tests
mvn test -Dtest="*ControllerTest"

# Run specific controller test
mvn test -Dtest="AuthControllerTest"

# Run tests with specific pattern
mvn test -Dtest="*Authentication*Test"
```

### Run Tests with Coverage
```bash
# Generate coverage report
mvn test jacoco:report

# View coverage report
# Open target/site/jacoco/index.html in browser
```

## Test Categories

### Unit Tests
- Individual controller method testing
- Mock service layer dependencies
- Fast execution

### Integration Tests
- Full request-response cycle testing
- Real database interactions
- Service layer integration

### API Tests
- HTTP endpoint testing
- Request/response validation
- Error handling verification

## Test Data Management

### Test Data Builders
Located in `TestBase.java`:
- `createTestUser()` - Create test users
- `createTestRole()` - Create test roles
- `createTestDoctorDetails()` - Create doctor details
- `createTestPatientDetails()` - Create patient details
- `createTestCategory()` - Create inventory categories
- `createTestInventoryItem()` - Create inventory items
- `createTestBookingAppointment()` - Create appointments
- `createTestPrescription()` - Create prescriptions

### Database Cleanup
Each test method:
1. Cleans up all test data in `@BeforeEach`
2. Creates fresh test data for the test
3. Verifies cleanup in `@AfterEach`

## Test Patterns

### Success Tests
```java
@Test
void testCreateEntity_Success() throws Exception {
    // Given
    String request = createValidRequest();
    
    // When & Then
    mockMvc.perform(post("/api/endpoint")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
}
```

### Error Tests
```java
@Test
void testCreateEntity_InvalidData() throws Exception {
    // Given
    String invalidRequest = createInvalidRequest();
    
    // When & Then
    mockMvc.perform(post("/api/endpoint")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequest))
            .andExpect(status().isBadRequest());
}
```

### NotFound Tests
```java
@Test
void testGetEntity_NotFound() throws Exception {
    mockMvc.perform(get("/api/endpoint/999"))
            .andExpect(status().isNotFound());
}
```

## Test Coverage

### API Endpoints Covered
- ✅ All CRUD operations
- ✅ Authentication and authorization
- ✅ Input validation
- ✅ Error handling
- ✅ Pagination and sorting
- ✅ Search functionality
- ✅ Bulk operations
- ✅ Status updates
- ✅ Statistics and reporting

### Test Scenarios
- ✅ Happy path scenarios
- ✅ Error scenarios
- ✅ Edge cases
- ✅ Invalid input handling
- ✅ Authentication failures
- ✅ Authorization failures
- ✅ Database constraint violations

## Continuous Integration

### GitHub Actions
Tests are automatically run on:
- Pull requests
- Push to main branch
- Scheduled runs

### Test Reports
- JUnit test results
- Coverage reports
- Performance metrics

## Best Practices

### Test Naming
- Use descriptive test names
- Follow pattern: `test[Method]_[Scenario]`
- Include expected outcome in name

### Test Organization
- Group related tests in same class
- Use `@Nested` for logical grouping
- Keep tests independent

### Data Management
- Use unique test data
- Clean up after each test
- Avoid test data conflicts

### Assertions
- Test both success and failure cases
- Verify response structure
- Check error messages
- Validate business logic

## Troubleshooting

### Common Issues

1. **Database Connection Errors**
   - Check H2 configuration
   - Verify test properties file
   - Ensure no port conflicts

2. **Test Failures**
   - Check test data setup
   - Verify endpoint URLs
   - Check JSON request format

3. **Slow Test Execution**
   - Use `@DirtiesContext` sparingly
   - Optimize database operations
   - Use appropriate test scopes

### Debug Mode
```bash
# Run tests with debug output
mvn test -Dspring.profiles.active=test -Dlogging.level.com.G19.hospital=DEBUG
```

## Contributing

### Adding New Tests
1. Create test class extending `TestBase`
2. Follow existing naming conventions
3. Include both success and failure tests
4. Add to `AllControllerTests.java` documentation
5. Update this README if needed

### Test Guidelines
- Write tests for all public endpoints
- Test both valid and invalid inputs
- Verify error responses
- Maintain test independence
- Use meaningful test data 