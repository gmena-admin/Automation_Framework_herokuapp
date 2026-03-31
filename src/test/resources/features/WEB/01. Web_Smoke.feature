@WEB @Edge
Feature: Web Test Cases for Smoke Suite

@Smoke
Scenario: Access Main Page
Given Access to the url "https://thinking-tester-contact-list.herokuapp.com"
And Assert that the user is in the page


@Smoke
Scenario: Access SignUp Page
Given Access to the url "https://thinking-tester-contact-list.herokuapp.com"
And Assert that the user is in the page
Given Access to "Sign up" to create a new contact