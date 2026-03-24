# Cineflow API Documentation

## User Registration

Description: Registers a new user in the system.

Endpoint: POST/users/register

Headers: 
Content-Type: application/json

Request:
{
    "name": "Himanshu",
    "email": "himanshu@gmail.com",
    "password": "1234"
}

Response:
{
    "id": 1,
    "name": "Himanshu",
    "email": "himanshu@gmail.com"
}

Status Codes:
 - 201 CREATED -> User registered
 - 400 BAD REQUEST -> Missing/invalid fields
 - 409 CONFLICT -> Email already exists

Validation:
 - Email must be unique
 - All fields are required

Error Response:
{
    "message": "Email already exists"
}