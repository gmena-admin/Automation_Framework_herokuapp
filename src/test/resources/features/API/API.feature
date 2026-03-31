@API
Feature: API Test Cases

Scenario: Access endpoint
Given Having "https://api.restful-api.dev/objects" as the endpoint
When The request is sent by "GET"
Then The "StatusCode" should be "200"