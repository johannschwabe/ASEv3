CREATE TABLE coordinates (
    id_sale VARCHAR(255) NOT NULL,
    lat REAL,
    long REAL,
    returned_street_name VARCHAR(255),
    returned_zip_code VARCHAR(255),
    PRIMARY KEY (id_sale)
);
-- LOAD DATA INFILE '/data/coords_nyc_api.csv' INTO TABLE coordinates
-- FIELDS TERMINATED BY ','
-- LINES TERMINATED BY '/n'
-- IGNORE 1 ROWS;
