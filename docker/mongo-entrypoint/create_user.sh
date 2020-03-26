#!/usr/bin/env bash

echo "Creating mongo users..."
mongo admin --host localhost -u admin -p admin111 --eval "db.createUser({user: 'dev', pwd: 'dev', roles: [{role: 'readWrite', db: 'bmdbase'}]}); "
echo "Mongo users created."