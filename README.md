# ASE 21
Advanced Software Engineering course repository.

### Import data in IntelliJ

Airbnb dataset:

1. Run `docker-compose up`
    - Make sure the airbnbapi service is running.
    Restart it, if it crashed the first time.

2. Add the database to your IDE:
    - `localhost:8585`
    - driver: `MySQL`
    - user: `db_user`, password: `db_user_pass`

3. Add data to table:
    - Right-click on table `property`.
    - Select `Import Data from File`.
    - Select the file `AB_NYC_2019.csv` in the `data` folder.

4. Import configuration:
    - Quotation should only be: `" " Escape: Duplicate`.
    Remove `' ' Escape: Duplicate`.
    - Tick `First row is header`.
    - Select `Import`.
    
Rolling Sales dataset:

1. Run `docker-compose up`
    - Make sure the salesapi service is running.
    Restart it, if it crashed the first time.
    
2. Add the database to your IDE:
    - `localhost:8686`
    - driver: `MySQL`
    - user: `db_user`, password: `db_user_pass`
    
3. Add coordinate data to table:
    - Right-click on table `coordinates`.
    - Select `Import Data from File`.
    - Select the file `coords_nyc_api.csv` in the `data` folder.
    
4. Coordinates import configuration:
    - Tick `First row is header`.
    - Select `Import`.
    
5. Add property data to table:
    - Right-click on table `property`.
    - Select `Import Data from File`.
    - Select the file `nyc-rolling-sales.csv` in the `data` folder.
    
6. Property import configuration:
    - Tick `First row is header`.
    - Click on the first attribute `id`, chang type to `bigint` and tick `Auto increment`
    - Select `Import`.
    - Click `Yes` if prompted to change schema.
   
7. Create index on `property` table
   - Right-click on table `property`.
   - Select `Modify Table...`.
   - Go to the `Indexes` tab.
   - Add a new Index `+`
   - Tick `Unique`
   - Add columns `number` and `borough`
   - CLick `Execute`