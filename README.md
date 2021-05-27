# Assignment 2

See the API documentation on: <SERVER-URI>/swagger.html
  
For example: http://localhost:8080/swagger.html

## Members

- Truong Duc Khai - s3818074
  
- Chung Quan Tin - s3818074

## Notes

### Insert foreign key in POST / PATCH request

Example 1: Insert order detail with product as foreign key 

```
{
    "product":{
        "id": <product-id>
    },
    // other fields
}
```

Example 2: Insert order with order details as foreign keys
```
    {
        "orderDetails":[
            {
                "id": <orderDetail1-id>
            },
            {
                "id": <orderDetail2-id>
            }
        ]
         // other fields
    }
```

### Insert date in POST / PATCH request

Follow this format: yyyy-MM-dd

Example: Insert order date
```
{
    "date": "2020-01-01"
    // other fields
}
```

### Provide date in GET request parameters
Follow this format: yyyy-MM-dd

Example: Get sales invoices from 2020-01-01 to 2021-01-01 
```
http://localhost:8080/sales-invoices?start=2020-01-01&end=2021-01-01
```