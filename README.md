# Terra-Scan-Server

Terra-Scan-Server

# Step one: Set up DB

1. Opent main -> resources
2. Go to application.yml
3. Change datasource:
   url: to your connection string.
   ps: A username: & password: may be requerd.

# Step two: Set up IP

Set ip addres to the ip addres of your machine. So that the addroid may view the connection properly.

# Strep Three: Set up user

In com.terra.server.init
is a `@Component` that is used to inisualize the user in the db. You can create what ever you whant.
