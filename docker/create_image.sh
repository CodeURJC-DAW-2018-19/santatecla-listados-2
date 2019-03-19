#!/bin/bash
cd ..
docker run --rm -v "$PWD":/usr/src -w /usr/src maven:alpine mvn -DskipTests package #create jar
cd target
mv daw-0.0.1-SNAPSHOT.jar ../docker/daw-0.0.1-SNAPSHOT.jar
cd ../docker
docker build -t saal4/santatecla-listados-2 . #create image 
docker login --username=saal4 --password=DAWGrupo10 
docker push saal4/santatecla-listados-2:latest # publish image on docker hub

