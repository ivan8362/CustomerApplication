# CustomerApplication
Two Spring boot applications:
- This one stores information about customers
- The second stores information about employees (https://github.com/ivan8362/EmployeeApplication)

Both applications expose REST API for CRUD operations for the entities which the application stores (1st app has CRUD API for customers,
the second app has CRUD API for employees).
Customers have name, ID description, email, address.
Employees have Display name, ID, customer ID which has the employee, first name, last name, email.
When client tries to create an employee for a company which does not exist, app should throw an exception.
When client tries to delete a customer, all the employees of the customer should be removed as well.
I use Mongo DB as persistent storage.
There is no UI, Postman with REST queries can be used to test the app.
Unit tests are written for all the API endpoints. (JUnit, Mockito).
