# ReadingHood-Server

This is the source code for the server of the ReadingHood app.

### How to start the server

- Install MySQL (If you haven't already)
- Create a database with the name ```readinghooddb``` (If you haven't already)
- Start the MySQL (Execute ```mysqld``` from cmd)
- Start the ReadingHood server (Click run from the NetBeans project)

### How to test the server (localhost)

- Open a browser
- Type ``https://localhost:8443/`` and hit enter.
If you get the "Connection not safe" message, just choose to add exception for this site. This message appears because the certificates aren't signed from a CA (Certificate Authority) yet. After that, if the message "Welcome to ReadingHood Server" appears, then the server is on !!!
