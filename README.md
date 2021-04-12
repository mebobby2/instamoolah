# Instamoolah Loans

## Prerequisites
* PostgreSQL
* Kafka

## Run
Start server
```
./mvnw spring-boot:run
```
## Test
Get Credit Officer Tasks
```
curl http://localhost:8000/tasks/creditofficers
```

Complete Credit Officer Task
```
curl --request PUT http://localhost:8000/tasks/creditofficers/{taskId}
```
