# eaads-phosphor-service
A rest-api for eaads-phosphor-collector

Tech-stack for this project includes:

    AWS Lambda function
    API-Gateway
    Spring Boot
    JPA/Hibernate

Setup:
1. After building, upload the jar to a lambda function.
2. To create the rest-api: On api-gateway, deploy or configure a lambda integrated proxy endpoint
that points to your lambda function.




