# Build Sales API
(
  cd sales-api || exit
  ./gradlew build
)

# Build Airbnb API
(
  cd airbnb-api || exit
  ./gradlew build
)

# Build Backend
(
  cd backend || exit
  ./gradlew build
)

# Build Docker images and run services
docker compose build
docker compose up
