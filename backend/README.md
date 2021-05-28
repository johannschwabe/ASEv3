# Backend

The backend Microservice is the interface to the frontend, unifying the communication to all databases.
Additionally, it handles combining information from several databases.
This is where the business logic happens.

The API is implemented using GraphQL and is specified below.
The GraphQL schema can be viewed [here](https://github.com/johannschwabe/ASEv3/blob/main/backend/src/main/resources/schema.graphqls).

## API

### Query

The requests you can send to the Backend.

| Query                         | Returns       | Arguments             | Description                                                                           |
|-------------------------------|---------------|-----------------------|---------------------------------------------------------------------------------------|
| allAirbnbCoordinates          | [Coordinates] | -                     | Fetch all coordinates of the airbnb properties as Coordinates objects.                |
| allSalesCoordinates           | [Coordinates] | -                     | Fetch all coordinates of the sale properties as Coordinates objects.                  |
| saleById                      | Sale          | id: ID                | Fetch a sale property by its ID.                                                      |
| saleByCoordinatesId           | Sale          | id: ID                | Fetch a sale property by the ID of the Coordinates object.                            |
| airbnbById                    | Airbnb        | id: ID                | Fetch an airbnb property by its ID.                                                   |
| estimatedSalePriceById        | Int           | id: ID                | Calculates the sale price by averaging over all sale properties in the neighbourhood. |
| salesByNeighbourhood          | [Sale]        | neighbourhood: String | Fetch all sale properties in the given neighbourhood.                                 |
| airbnbsByNeighbourhood        | [Airbnb]      | neighbourhood: String | Fetch all airbnb properties in the given neighbourhood.                               |
| neighbourhoodRating           | Float         | neighbourhood: String | Calculates a score for a given neighbourhood.                                         |
| calculateBreakEven            | Float         | id: ID <br/> price: Int - 212$ <br/> nights: Int - 365 <br/> occupancyRate: Float - 80% <br/> maintenance: Float - 2.00$ <br/> mortgageRate: Float - 3% <br/> mortgageRatio: Float - 75% | Calculate the number of years it takes to break even after buying the sale property given by its ID. <br/> All arguments (except ID) have a default value. |
| calculatePropertyScore        | Float         | id: ID                | Calculate a overall score for the sale property given by its ID.                      |
| estimatedPriceByNeighbourhood | Int           | neighbourhood: String | Calculates the average price of the airbnb properties in the neighbourhood.           |
| allScores                     | [SaleScore]   | -                     | Fetch the overall scores of all sale properties that can be scored.                   |

### SaleScore

The object containing the ID and score of a sale property.
Also provides some additional information about the sale property.

| Field                       | Type   | Description                                                                         |
|-----------------------------|--------|-------------------------------------------------------------------------------------|
| id                          | ID     | The ID of the sale property.                                                        |
| score                       | Float  | The calculated score of the sale property.                                          |
| address                     | String | The address of the sale property.                                                   |
| price                       | Int    | The price of the sale property.                                                     |
| neighbourhood               | String | The name of the neighbourhood of the sale property.                                 |
| coordinatesId               | String | The unique coordinates ID composed of ``number`` and ``borough`` of the property.   |

### Coordinates

The object containing the ID and coordinates of a property.

| Field                       | Type   | Description                                                                         |
|-----------------------------|--------|-------------------------------------------------------------------------------------|
| id                          | ID     | The ID of the airbnb property or the coordinates ID of the sale property.           |
| latitude                    | Float  | The latitude of the coordinates.                                                    |
| longitude                   | Float  | The longitude of the coordinates.                                                   |

### Sale

The object describing a sales property.

| Field                       | Type        | Description                                                            |
|-----------------------------|-------------|------------------------------------------------------------------------|
| id                          | ID          | The unique ID.                                                         |
| number                      | Int         | Uniquely identifies a property if combined with ``borough``.           |
| borough                     | Int         | Uniquely identifies a property if combined with ``number``.            |
| neighbourhood               | String      | The name of the neighbourhood the property is in.                      |
| buildingClassCategory       | String      | The category of the building class.                                    |
| taxClassAtPresent           | String      | The tax class at time of data collection.                              |
| block                       | Int         | The city block number.                                                 |
| lot                         | Int         | The lot number.                                                        |
| easement                    | String      | The right of use for this property.                                    |
| buildingClassAtPresent      | String      | The building class at time of data collection.                         |
| address                     | String      | The address of the property.                                           |
| apartmentNumber             | String      | The apartment number.                                                  |
| zipCode                     | Int         | The ZIP code.                                                          |
| residentialUnits            | Int         | The number of residential units.                                       |
| commercialUnits             | Int         | The number of commercial units.                                        |
| totalUnits                  | Int         | The number of units in total.                                          |
| landSquareFeet              | Int         | Area of the land.                                                      |
| grossSquareFeet             | Int         | Area inside.                                                           |
| yearBuilt                   | Int         | The year of construction.                                              |
| taxClassAtTimeOfSale        | String      | The tax class when the property was sold.                              |
| buildingClassAtTimeOfSale   | String      | The building class when the property was sold.                         |
| salePrice                   | Int         | The sale price of the property.                                        |
| saleDate                    | String      | The date when the property was sold.                                   |
| coordinates                 | Coordinates | The coordinates information of the property if available.              |
| idSale                      | ID          | The coordinates ID composed of ``number`` and ``borough``.             |
| latitude                    | Float       | The latitude of the coordinates.                                       |
| longitude                   | Float       | The longitude of the coordinates.                                      |
| returnedStreetName          | String      | The street name returned from the Google API.                          |
| returnedZipCode             | Int         | The ZIP code returned from the Google API.                             |
| score                       | Float       | The calculated score of the sale property.                             |

### Airbnb

The object describing an airbnb property.

| Field                       | Type   | Description                                                                  |
|-----------------------------|--------|------------------------------------------------------------------------------|
| id                          | ID     | The unique ID.                                                               |
| name                        | String | The name of the airbnb listing.                                              |
| hostId                      | Int    | The ID of the airbnb host.                                                   |
| hostName                    | Int    | The name of the airbnb host.                                                 |
| neighbourhoodGroup          | String | The neighbourhood group the property is in.                                  |
| neighbourhood               | String | The neighbourhood the property is in.                                        |
| latitude                    | Float  | The latitude of the property coordinates.                                    |
| longitude                   | Float  | The longitude of the property coordinates.                                   |
| roomType                    | String | The type of room (``Private room``, ``Shared room`` or ``Entire home/apt``). |
| price                       | Int    | The price per night.                                                         |
| minimumNights               | Int    | The minimum nights in the airbnb listing.                                    |
| numberOfReviews             | Int    | The number of reviews on airbnb.                                             |
| lastReview                  | String | The date of the last review on airbnb.                                       |
| reviewsPerMonth             | Int    | The average number of reviews per month.                                     |
| calculatedHostListingsCount | Int    | The number of listings of the same host.                                     |
| availability365             | Int    | The number of available nights in a year.                                    |
