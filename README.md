# ASE 21
Advanced Software Engineering course repository.

### Add data in IntelliJ

Airbnb dataset:

1. Run `docker-compose up`
    - Make sure the airbnbapi service is running.
    Restart it, if it crashed the first time.

2. Add the database to your IDE:
    - `localhost:8585`
    - driver: `MySQL`
    - user: `db_user`, password: `db_user_pass`

3. Add data to table:
    - Right-click on table `property`
    - Select `Import Data from File`
    - Select file in the `data` folder

4. Import configuration:
    - Quotation: `" " Escape: Duplicate`
    - Tick `First row is header`
    - Select `Import`
    - There should not occur any errors