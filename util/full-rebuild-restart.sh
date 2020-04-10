cd ..
chmod 755 gradlew
./gradlew clean bootJar || exit
cd ./docker || exit
eval $(docker-machine env default)
docker-compose -f bmd.yml build
docker-compose -f bmd.yml up