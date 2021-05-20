cd .\app-server\
.\gradlew clean
.\gradlew build
cd ..\gateway\
.\gradlew clean
.\gradlew build
cd ..
docker-compose up --build -d