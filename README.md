# eaads-phosphor-service
A rest-api for eaads-phosphor-collector

Tech-stack for this project includes:
aws Lambda function
api-gateway
spring boot
jpa/hibernate

Setup:
1. After building the jar, upload it to a lambda function.
2. To create the rest-api, on api-gateway, deploy or configure a lambda integrated proxy endpoint
that points to your lambda function.




