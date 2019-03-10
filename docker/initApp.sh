#!/bin/sh
echo "Waiting for database dawgrupo10 connection on port 3306"
while ! nc -z -v -w20 DAWGrupo10 3306 
do
	sleep 3
done
echo "Connection succesfully"
java -jar /daw-0.0.1-SNAPSHOT.jar
