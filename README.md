# ASE 21
Advanced Software Engineering course repository.

## Running the application

1. Clone or download the code.


2. Add the ``.env.local`` file, containing the Google Maps API key to the folder ``frontend``.


3. Open the folder in your Terminal or Command Prompt.


4. Run ``scripts/build.sh`` and wait until all services started.
    

5. Run ``mysql -h localhost -P 8585 -u db_user -pdb_user_pass app_db < data\app_db-_localhost_8585_airbnbdb.sql`` and
   ``mysql -h localhost -P 8686 -u db_user -pdb_user_pass app_db < data\app_db-_localhost_8686_salesdb.sql`` to setup 
   the databases.
    

6. Quit the application.
    

7. Restart the application by running ``docker compose up``.


8. Open the Web-App in your browser (http://localhost:8181).


*It is possible, after starting the application (in step 4), that some services 
('airbnbapi', 'salesapi') crash. This is no problem as long as the two database-services
('airbnbdb', 'salesdb') are running. You have to restart the application (in step 6 and 7) anyways.*

## Wiki

To gain a deeper understanding for the project, check the [wiki](https://github.com/johannschwabe/ASEv3/wiki)
To report bugs & help the project see the Trello Boards:
* [General Issues](https://trello.com/b/vKnwu8M5/general)
* [Backend](https://trello.com/b/wzDBzvwY/backend)
* [Frontend](https://trello.com/b/vw6tmCrR/frontend)

Code Quality is monitored using [SonarCloud](https://sonarcloud.io/organizations/johannschwabe/projects?sort=name). 
(and additionally [here](https://github.com/johannschwabe/ASEv3/tree/main/frontend) for the Frontend)

-- Designed and Implemented by Thomas Mannhart, David Wyss, Johann Schwabe ---
