[![CircleCI](https://circleci.com/gh/xykkong/planet-api.svg?style=shield)](https://circleci.com/gh/xykkong/planet-api)   [![codecov](https://codecov.io/gh/xykkong/planet-api/branch/master/graph/badge.svg)](https://codecov.io/gh/xykkong/planet-api)

# planet-api
A simple REST API built with Spring Boot.


Building planet-api
-----------

Just execute the following commands:

sudo ./gradlew build docker

sudo docker run -p 8080:8080 -t com.example.starwars/planet-api

After container initialization, the API will be available on localhost:8080.

If you are using a Web Browser, you will be redirected to a HTML page.
If not, you can access the documentation using curl -i localhost:8080/v2/api-docs
