# How to run project

Go to backend project (`cd backend/project`) and compile via maven
```sh
mvn clean install
```

In main directory (where `docker-compose.yml` exists) run
```sh
docker-compose up
```

After run all containers you should see something like that:
```sh
frontend    |  13% building modules 31/34 modules 3 active ...ype=template&index=0!/app/src/App.vue{ parser: "babylon" } is deprecated; we now treat it as { parser: "babel" }.
 95% emitting DONE  Compiled successfully in 2026ms6:18:27 PM
frontend    |
frontend    |  I  Your application is running here: http://0.0.0.0:8080
```

To upgrade your backend service you can use deploy profile in maven, without restarting docker.
```sh
mvn clean install -Pdeploy
```

To debug backend wildfly service you can create remote launcher to address localhost:8787

Postman API collection is available in main directory (`EatMeAll.postman_collection.json`)

### Finally open Postman and check GET request http://localhost:8080/api/v1/products
### Frontend application is available in http://localhost