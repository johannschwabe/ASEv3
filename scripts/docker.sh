cd sales-api
./gradlew build
cd..
cd airbnb-api
./gradlew build
cd..
cd backend
./gradlew build
cd..
docker-compose build
docker-compose up