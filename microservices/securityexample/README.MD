
## 2 ways to manage users with keycloak API: on behalf of some user, or create a dedicated client for this.

### On behalf of the user
1) create a confidential client (I suppose you already got one)
2) create a user and assign an appropriate role/s to it: e.g. view-users from the realm-management group
3) get the token (I'm using curl and jq):

KCHOST=https://yourkeycloak.com
REALM=realm
CLIENT_ID=confidential-client
CLIENT_SECRET=xxxxxxx-yyyyyy-zzzzzzzz
UNAME=user
PASSWORD=passwd

ACCESS_TOKEN=`curl -X POST
-d "client_id=$CLIENT_ID" -d "client_secret=$CLIENT_SECRET" 
-d "username=$UNAME" -d "password=$PASSWORD" 
-d "grant_type=password" 
"$KCHOST/auth/realms/$REALM/protocol/openid-connect/token"`
4) finally, call the Admin REST API users endpoint:

curl -X GET -H "Authorization: Bearer $ACCESS_TOKEN" $KCHOST/auth/admin/realms/$REALM/users | jq

### On behalf of the client (Service account)
1) create a **confidential** client and make sure to toggle the Service Accounts Enabled setting to On
2) go over to the Service account roles tab and select the appropriate role for this client, e.g. realm-admin from the realm-management group
3) get the access token

KCHOST=https://yourkeycloak.com
REALM=realm
CLIENT_ID=protector-of-the-realm
CLIENT_SECRET=xxxxxxx-yyyyyyyy-zzzzzzzzz

ACCESS_TOKEN=`curl -X POST
-d "client_id=$CLIENT_ID" -d "client_secret=$CLIENT_SECRET"
-d "grant_type=client_credentials"
"$KCHOST/auth/realms/$REALM/protocol/openid-connect/token"`
4) call the REST API endpoint:

`curl -X GET -H "Authorization: Bearer $ACCESS_TOKEN" $KCHOST/auth/admin/realms/$REALM/users`

[source](https://stackoverflow.com/questions/49572291/keycloak-user-validation-and-getting-token#)

[Keycloak API](https://documenter.getpostman.com/view/7294517/SzmfZHnd#7a1260e9-0a7b-4d08-a356-8ea5f34d347d)

[Fucking Broken Official Keycloak API](https://www.keycloak.org/docs-api/12.0/rest-api/index.html)