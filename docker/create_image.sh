#!/bin/bash
cd ..
cd angular
docker run --rm --name angular-cli -v ${PWD}:/angular -w /angular node:8.15.1 /bin/bash -c "npm install -g @angular/cli; ng config -g cli.warnings.versionMismatch false; npm install; ng build --prod --baseHref=/new/"
cp -R dist/my-app/* ../src/main/resources/static/new
cd ..
docker run --rm -v "$PWD":/usr/src -w /usr/src maven:alpine mvn -DskipTests package
cd target
mv daw-0.0.1-SNAPSHOT.jar ../docker/daw-0.0.1-SNAPSHOT.jar
cd ../docker
docker build -t saal4/santatecla-listados-2 . #create image
docker login --username=saal4 --password=DAWGrupo10
docker push saal4/santatecla-listados-2:latest # publish image

