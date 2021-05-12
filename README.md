# Assignment 2

See the API documentation on: <SERVER-URI>/swagger.html
  
For example: http://localhost:8080/swagger.html

1. CRUD for these classes (5% for pagination)

- Staff [Done]

- Customer (Search API) [In-progress | #Contact person]

- Order [In-progress]

- Inventory receiving note

- Inventory delivery note

- Sales invoice (Search API)

1.1. Some additional REST API:

- List all inventory note, sale invoice by a period: start date and end date

- All sales invoice by a customer and by a sale staff in a period: start date and end date. 

The company also needs to know the following statistical data:

- Revenue, revenue by a customer, revenue by a sale staff. Input params: start date and end date.  Revenue = total value of all sales invoices in a period. Total value of an invoice = all products quantity * price.

- Inventory of all products in warehouse at a particular date

2. Students write unit tests for all the API that they build. Ensure that unit tests will provide at least 100% code coverage. 

3. Student writes a design report that has the following sections: