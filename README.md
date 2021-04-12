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

Approve Credit Officer Task
```
curl --request PUT http://localhost:8000/tasks/creditofficers/approve/{taskId}
```

Reject Credit Officer Task
```
curl --request PUT http://localhost:8000/tasks/creditofficers/reject/{taskId}
```
