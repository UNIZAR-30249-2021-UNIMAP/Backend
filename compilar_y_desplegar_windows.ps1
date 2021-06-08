cd .\app-server\
.\gradlew build
cd ..\gateway\
.\gradlew build
cd ..
docker-compose up --build -d