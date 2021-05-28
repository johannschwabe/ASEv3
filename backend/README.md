# Backend

The backend Microservice is the interface to the frontend, unifying the communication to all databases.
Additionally, it handles combining information from several databases.
This is where the business logic happens.

The API is implemented using GraphQL and is specified below.
The GraphQL schema can be viewed [here](https://github.com/johannschwabe/ASEv3/blob/main/backend/src/main/resources/schema.graphqls).

## API

### Query

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

| Field                       | Type   | Description                                                                         |
|-----------------------------|--------|-------------------------------------------------------------------------------------|
| id                          | ID     | The ID of the sale property.                                                        |
| score                       | Float  | The calculated score of the sale property.                                          |
| address                     | String | The address of the sale property.                                                   |
| price                       | Int    | The price of the sale property.                                                     |
| neighbourhood               | String | The name of the neighbourhood of the sale property.                                 |
| coordinatesId               | String | The unique coordinates ID composed of ``number`` and ``borough`` of the property.   |

### Coordinates

| Field                       | Type   | Description                                                                         |
|-----------------------------|--------|-------------------------------------------------------------------------------------|
| id                          | ID     | The ID of the airbnb property or the coordinates ID of the sale property.           |
| latitude                    | Float  | The latitude of the coordinates.                                                    |
| longitude                   | Float  | The longitude of the coordinates.                                                   |
