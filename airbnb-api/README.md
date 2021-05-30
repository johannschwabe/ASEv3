# Airbnb API

This service provides access to the airbnb database.

The API is implemented using GraphQL and is specified below.
The GraphQL schema can be viewed [here](https://github.com/johannschwabe/ASEv3/blob/main/airbnb-api/src/main/resources/schema.graphqls).

## API

### Query

The requests you can send to the Airbnb API.

| Query                     | Returns    | Arguments             | Description                                         |
|---------------------------|------------|-----------------------|-----------------------------------------------------|
| propertyById              | Property   | id: ID                | Fetch the property with the provided ID.            |
| allProperties             | [Property] | -                     | Fetch all properties.                               |
| propertiesByNeighbourhood | [Property] | neighbourhood: String | Fetch all properties in the provided neighbourhood. |

### Property

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
