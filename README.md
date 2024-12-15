# APACHE SHARDING-SPHERE

- Open-source distributed database solution including independent products such as `Sharding-JDBC`, `Sharding-Proxy`, and `Sharding-Sidecar`.
- Provides functionality for distributed transactions and database orchestration.

![Apache ShardingSphere](https://shardingsphere.apache.org/document/current/img/shardingsphere-hybrid.png)

- We will explore `Sharding-JDBC` among the three solutions.

## Sharding?

- Traditional solutions storing all data in a single centralized node struggle to meet the requirements of large-scale internet data scenarios in terms of performance, availability, and operational costs.
- Relational databases primarily use B+ tree indexes. When the data volume exceeds a threshold, the deeper the index, the more disk I/O accesses are needed, degrading query performance.
- A central database becomes a system bottleneck under high concurrent requests.
- Simple single-node or master-slave structures struggle to handle pressure, making database availability critical to the entire system.
- As data volume exceeds the threshold of database instances, DBA operational pressure increases, making data backup and recovery times uncontrollable.
- Sharding improves performance and availability by splitting data within a database into multiple tables and databases based on specific rules.

### Vertical Sharding

![Vertical Sharding](https://i.imgur.com/5aauDmX.png)

- Persisting data into separate databases by table.
- Even after `Vertical Sharding`, if the table data volume exceeds the threshold, it is recommended to further handle it using `Horizontal Sharding`.

### Horizontal Sharding

![Horizontal Sharding](https://i.imgur.com/EIsF68I.png)

- Also called `transverse sharding`, it splits data into multiple databases or tables based on specific fields and rules. Each shard contains only a part of the data.

## Sharding-JDBC

- Works with JDBC-based implementations like `JPA`, `Hibernate`, `Mybatis`, and `Spring JDBC Template`, or JDBC directly.
- Supports database connection pool implementations like `DBCP` and `HikariCP`.
- Compatible with databases like `MySQL`, `Oracle`, `SQLServer`, and `PostgreSQL`.
- Configurable using `Java`, `YAML`, `Spring XML Config`, and `Spring Boot Starter`.
- An implementation named `io.shardingsphere` exists, closely resembling Apache's implementation.

![Sharding-JDBC Architecture](https://i.imgur.com/2vmVbYE.png)

- Applications logically view a single table, but it maintains connections to individual databases.
- A mapping rule for the specified sharding field must be provided. Sharding can be done using algorithms for `=`, `>=`, `<=`, `>`, `<`, `BETWEEN`, and `IN`.

### Sharding-Algorithm & Sharding-Strategy

- Definitions for `specific fields` and `specific rules` are needed, defined via sharding algorithms and strategies.
- Basic algorithms are provided, but detailed strategies and key mappings can be implemented as needed.

#### Algorithm-Based Sharding Strategy Mapping

| Algorithm                     | Strategy                  | Description                                                                                   |
|-------------------------------|---------------------------|-----------------------------------------------------------------------------------------------|
| PreciseShardingAlgorithm      | StandardShardingStrategy  | Uses a single sharding key with `=` and `IN`.                                                 |
| RangeShardingAlgorithm        | StandardShardingStrategy  | Uses a single sharding key for ranges like `BETWEEN AND`, `>`, `<`, `>=`, `<=`.               |
| ComplexKeysShardingAlgorithm  | ComplexShardingStrategy   | Uses composite keys for sharding fields.                                                      |
| HintShardingAlgorithm         | HintShardingStrategy      | Shards based on database hints.                                                               |

#### Sharding Strategies

| Strategy                     | Algorithm                                   | Description                                                                                |
|------------------------------|---------------------------------------------|--------------------------------------------------------------------------------------------|
| StandardShardingStrategy     | PreciseShardingAlgorithm, RangeShardingAlgorithm | Strategy for a single sharding key.                                                        |
| ComplexShardingStrategy      | ComplexShardingAlgorithm                   | Strategy for composite keys.                                                               |
| InlineShardingStrategy       | X                                           | Uses Groovy expressions for mapping. Example: `t_user_$->{u_id % 8}` -> `t_user_0` to `t_user_7` |
| HintShardingStrategy         | X                                           | Strategy based on database hints.                                                         |
| NoneShardingStrategy         | X                                           | Used when no strategy is referenced.                                                      |

- Using auto-increment values from DB may cause issues with sharding algorithms. It's common to use unique server-generated values like [SNOWFLAKE](https://charsyam.wordpress.com/2012/12/26/%EC%9E%85-%EA%B0%9C%EB%B0%9C-global-unique-object-id-%EC%83%9D%EC%84%B1-%EB%B0%A9%EB%B2%95%EC%97%90-%EB%8C%80%ED%95%9C-%EC%A0%95%EB%A6%AC/).
- Application-level SQL is rewritten by the Rewrite Engine to the required SQL for physical database nodes.

```sql
-- Sharding key is order_id
SELECT order_id FROM t_order WHERE order_id=1;

-- SQL sent to actual database nodes as per sharding strategy and algorithm
SELECT order_id FROM t_order_1 WHERE order_id=1;
```

[SHARDING-JDBC Example](https://juejin.im/post/5d9fe175f265da5bba417a28)

## Example SQL

```sql
create schema ds0; 
create schema ds1;

create table ds0.t_order
(
	id bigint not null
		primary key,
	name varchar(255) null comment 'Name',
	type varchar(255) null comment 'type',
	gmt_create timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'Creation time'
);

create table ds1.t_order
(
	id bigint not null
		primary key,
	name varchar(255) null comment 'Name',
	type varchar(255) null comment 'type',
	gmt_create timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'Creation time'
);

-- https://dev.mysql.com/doc/employee/en/employees-installation.html

---

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
