# Instamoolah Loans

## Run
Start server
```
./mvnw spring-boot:run
```
## Test
Apply for a loan
```
curl --header "Content-Type: application/json" --request POST --data '{"riskScore":90,"emailVerified":true,"collectionStatus":"HARDSHIP"}' http://localhost:8000/loans
```
