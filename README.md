# springboot-apache-shardsphere
Fun repo to explore application level sharding, read-write splitting and data masking with Apache ShardingSphere and Spring Boot


# CURL Commands for Review API

## Set Base URL
Export the base URL environment variable:
```bash
export base_url="http://localhost:8070"
```

---

## 1. Get All Reviews
**Endpoint:** `GET /api/v1/reviews`

```bash
curl -X GET $base_url/api/v1/reviews
```

---

## 2. Get Reviews by Author
**Endpoint:** `GET /api/v1/reviews/filter?author={author}`

```bash
curl -X GET "$base_url/api/v1/reviews/filter?author=John Doe"
```

---

## 3. Get Reviews by Course ID
**Endpoint:** `GET /api/v1/reviews/filter?courseId={courseId}`

```bash
curl -X GET "$base_url/api/v1/reviews/filter?courseId=101"
```

---

## 4. Get Reviews by Created Date
**Endpoint:** `GET /api/v1/reviews/filter?date={date}`

Replace `{date}` with the desired date in `YYYY-MM-DD` format.

```bash
curl -X GET "$base_url/api/v1/reviews/filter?date=2024-12-01"
```

---

## 5. Get Review by ID
**Endpoint:** `GET /api/v1/reviews/{id}`

Replace `{id}` with the desired review ID.

```bash
curl -X GET $base_url/api/v1/reviews/1
```

---

## 6. Create a New Review
**Endpoint:** `POST /api/v1/reviews`

**Request Body Example:**
```json
{
    "text": "This is a great course!",
    "author": "John Doe",
    "authorTelephone": "123456789",
    "authorEmail": "john@example.com",
    "invoiceCode": "INV12345",
    "courseId": 101
}
```

**`curl` Command:**
```bash
curl -X POST -H "Content-Type: application/json" \
-d '{
    "text": "This is a great course!",
    "author": "John Doe",
    "authorTelephone": "123456789",
    "authorEmail": "john@example.com",
    "invoiceCode": "INV12345",
    "courseId": 101
}' $base_url/api/v1/reviews
```

---

## 7. Delete Review by ID
**Endpoint:** `DELETE /api/v1/reviews/{id}`

Replace `{id}` with the review ID you want to delete.

```bash
curl -X DELETE $base_url/api/v1/reviews/1
```

---
### Notes:
1. **Authorization**: If your app requires authentication, add headers like `Authorization: Bearer {token}`.
2. **Base URL**: Ensure `$base_url` points to the correct application URL.
3. **JSON Validation**: Ensure the `POST` request body matches the expected `Review` model structure.

### Reference 

- https://dzone.com/articles/the-power-of-shardingsphere-with-spring-boot
- https://jxausea.medium.com/spring-boot-integration-sharding-jdbc-quickstart-demo-a767443e164c
- https://github.com/Yufeng0918/sharding-sphere-learning/tree/master
- https://github.com/thefeeling/apache-sharding-sphere/tree/master