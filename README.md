# eaads-phosphor-service
A rest-api for eaads-phosphor-collector

Tech-stack for this project includes:

    AWS Lambda function
    API-Gateway
    SSM
    Spring Boot
    JPA/Hibernate

Setup:
1. After building, upload the jar to a lambda function.
2. On api-gateway, deploy or configure a lambda integrated proxy endpoint
that points to your lambda function.

Example curl statements:

    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/jira/pointsinprogress'  -H 'content-type: application/json'
    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/jira/sprintburndown'  -H 'content-type: application/json'
    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/jira/sprints'  -H 'content-type: application/json'
    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/jira/targetvelocity'  -H 'content-type: application/json'
    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/github/eaadscommits'  -H 'content-type: application/json'
    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/jira/topresolvers'  -H 'content-type: application/json'
    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/jira/pointscompleted'  -H 'content-type: application/json'
    curl -X GET 'https://tfd9kkgpsk.execute-api.us-east-2.amazonaws.com/dev/jira/repocommits'  -H 'content-type: application/json'


