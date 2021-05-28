# Sales API

This service provides access to the sales database.

The API is implemented using GraphQL and is specified below.
The GraphQL schema can be viewed [here](https://github.com/johannschwabe/ASEv3/blob/main/sales-api/src/main/resources/schema.graphqls).

## API

### Query

| Query                     | Returns       | Arguments             | Description                                               |
|---------------------------|---------------|-----------------------|-----------------------------------------------------------|
| allProperties             | [Property]    | -                     | Fetch all properties.                                     |
| propertyById              | Property      | id: ID                | Fetch the property with the provided ID.                  |
| propertyByCoordinatesId   | Property      | id: ID                | Fetch the property belonging to the given coordinates ID. | 
| allCoordinates            | [Coordinates] | -                     | Fetch all coordinates.                                    |
| propertiesByNeighbourhood | [Property]    | neighbourhood: String | Fetch all properties in the provided neighbourhood.       |

### Property

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


### Coordinates

| Field                       | Type   | Description                                                                         |
|-----------------------------|--------|-------------------------------------------------------------------------------------|
| idSale                      | ID     | The unique ID composed of ``number`` and ``borough`` of the corresponding property. |
| latitude                    | Float  | The latitude of the coordinates.                                                    |
| longitude                   | Float  | The longitude of the coordinates.                                                   |
| returnedStreetName          | String | The street name returned from the Google API.                                       |
| returnedZipCode             | Int    | The ZIP code returned from the Google API.                                          |
