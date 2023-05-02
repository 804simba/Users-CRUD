# How to get your Google client id and secrets.

1. Go to console.cloud.google.com

2. Click Select Project and then click on new Project and write the name  of your project then click on save project
3. Click on APIs and Services, then click on Oauth Consent Screen
4. Add App name, Add App support email and then, at the bottom add developer's contact email.
5. Click Save and Continue

6. Add scopes you want like, email, profile and OpenId
7. Click Save and continue
8. Add test users email and Click Save and Continue

9. Next Click on "Credentials" on the APIs and Services tab
10. Next Click Add Credentials at the top of the displayed page and then click on Oauth 2.0 Client ID
11. Next select application type "Web Application"
12. Then add Authorized Redirect URIs e.g http://localhost:8081/login/oauth2/code/google
13. Click on Create! Now, you have access to your very own Client ID and Secret.