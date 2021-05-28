# Airbnb Property Rating Tool

This is the repository for our Advanced Software Engineering project at the University of Zurich.
The application was designed and implemented for the course in Spring 2021.

## Teaser Video

[![Teaser](https://img.youtube.com/vi/QCIdfIwzuw8/0.jpg)](https://www.youtube.com/watch?v=QCIdfIwzuw8)

Click to play!

## Running the application

You need to have the current version of Docker and MySQL installed on your machine. 
You can install MySQL on macOS using homebrew: ``brew install mysql``.

Make sure Docker is running.

1. Clone or download the code.


2. Add the ``.env.local`` file, containing the Google Maps API key to the sub-folder ``frontend``.


3. Open the folder (``ASEv3`` or ``ASEv3-main``) in your Terminal or Command Prompt.


4. Run ``scripts/build.sh`` (or for macOS: ``sh scripts/build.sh``) and wait until all services started.


5. Run
   
   ``mysql -h localhost -P 8585 --protocol TCP -u db_user -pdb_user_pass app_db < data/app_db-_localhost_8585_airbnbdb.sql``
   
   ``mysql -h localhost -P 8686 --protocol TCP -u db_user -pdb_user_pass app_db < data/app_db-_localhost_8686_salesdb.sql``
   
   to setup the databases.


6. Quit the application (ctrl + C).


7. Restart the application by running ``docker compose up``.


8. Open the Web-App in your browser (http://localhost:8181).

*It is possible, after starting the application (in step 4), that some services 
('airbnbapi', 'salesapi') crash. This is no problem as long as the two database-services
('airbnbdb', 'salesdb') are running. You have to restart the application (in step 6 and 7) anyways.*

## Wiki

To gain a deeper understanding for the project, check the [wiki](https://github.com/johannschwabe/ASEv3/wiki).

The Wiki contains the following chapters:

- [Project organization](https://github.com/johannschwabe/ASEv3/wiki/Project-Organization)
- [Documentation](https://github.com/johannschwabe/ASEv3/wiki/Documentation)
- [How to run it](https://github.com/johannschwabe/ASEv3/wiki/Setup)
- [Testing and continuous integration](https://github.com/johannschwabe/ASEv3/wiki/Testing-and-CI)
- [Features](https://github.com/johannschwabe/ASEv3/wiki/Features)
- [Implementation Details](https://github.com/johannschwabe/ASEv3/wiki/Implementation-Details)

Code Quality is monitored using [SonarCloud](https://sonarcloud.io/organizations/johannschwabe/projects?sort=name). 
(and additionally [here](https://github.com/johannschwabe/ASEv3/tree/main/frontend) for the Frontend)

## Project Structure

The project is structured into 4 main services:

- [Frontend](https://github.com/johannschwabe/ASEv3/tree/main/frontend) - The Web-Application.
- [Backend](https://github.com/johannschwabe/ASEv3/tree/main/backend) - The backend providing data to the frontend.
- [Sales API](https://github.com/johannschwabe/ASEv3/tree/main/sales-api) - The API providing access to the sales data.
- [Airbnb API](https://github.com/johannschwabe/ASEv3/tree/main/airbnb-api) - The API providing access to the airbnb data.

*Designed and Implemented by Thomas Mannhart, David Wyss, Johann Schwabe*
