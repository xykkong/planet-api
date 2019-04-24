# planet-api
A simple REST API built using Spring Boot


Building planet-api
-----------

For easy run, you can run this project on docker. 
Just run the following commands:

sudo ./gradlew build docker

sudo docker run -p 8080:8080 -t br.rj.b2w.starwars/planet-api

After container initialization, the API will be availabel on localhost:8080.
If you are using a Web Browser you will be redireted to a HTML page.
If not, you can access the documentation on localhost:8080/v2/api-docs
