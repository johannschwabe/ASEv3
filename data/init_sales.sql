USE app_db;
LOAD DATA INFILE '/data/coords_nyc_api.csv' INTO TABLE coordinates
FIELDS TERMINATED BY ','
LINES TERMINATED BY '/n'
IGNORE 1 ROWS;
