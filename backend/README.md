# Backend Microservice
## Task
The backend Microservice is the interface to the frontend, unifying the communication to all databases. Additionally, it 
handles combining information from several databases.

## API
| Name                  | Return value        | Arguments     | Description       |
| ----------------------|:-------------:|:-------------|------------------:|
| allAirbnbCoordinates  |List of all coordinates| - | Get all coordinates of the airbnb properties as Coordinate objects.|
| allSalesCoordinates   |List of all coordinates| - | Get all coordinate objects from the sales API.|
| airbnbById            |The Airbnb object| The **ID** of the airbnb property| Get an airbnb property by its ID|
| saleById              |The sale property with all fields|The **ID** of the sale property|Get a sale from the API by its ID|
| saleByCoordinatesId   |The sale property with all fields|The **ID** of the Coordinate object|Get a sale from the API by the coordinates id|
| estimatedSalePriceById|The estimated sale price for the property (Returns null if there are no valid neighbors)| The **ID** of the property|Return the estimated sale price for a property of the sales DB|
| salesByNeighbourhood  |The List of all sale properties in the neighbourhood|The **neighbourhood** name|Get all sale properties in the given neighbourhood|
| airbnbsByNeighbourhood|The List of all airbnb properties in the neighbourhood|The **neighbourhood** name|Get all airbnb properties in the given neighbourhood|
| neighbourhoodRating   |A quality score for the neighbourhood|The **neighbourhood** to be evaluated|Calculates a score for a given neighbourhood|
| calculateBreakEven    |The expected number of years to break even|The **id** of the property to lease,  <br /> The **revenue** generated per night (price of the airbnb). Defaults to the average price in the neighbourhood if null,  <br />The **number of nights per year** this apartment is available for renting. Defaults to 365 (whole year) if null,  <br />The **rate** to which the apartment will bo booked. Defaults to 0.8 (80%) if null,  <br />The **monthly maintenance cost** per square feet. Defaults to 2.50$ if null,  <br />The **mortgage rate** for the sale price. Defaults to 0.03 (3%) if null,  <br /> The **ratio** of the sale price covered by a mortgage. Default to 0.75 (75%) if null|Calculate the number of years it takes to break even by leasing the given sale property on airbnb
| calculatePropertyScore|The score between 0 and 10|The **ID** of the scored sale property| Compute a score for a sale property|
| estimatedPriceByNeighbourhood|The average revenue|The name of the **neighbourhood**|Returns the average revenue of the given neighbourhood|
| score                 |The score between 0 and 10|The **ID** of the scored sale property| Compute a score for a sale property|