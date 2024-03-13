POST http://localhost:3000/api/users
{
    "username": "test@test.com",
    "password": "Test@468"
}
HTTP 201
[Asserts]
jsonpath "$.token" exists

