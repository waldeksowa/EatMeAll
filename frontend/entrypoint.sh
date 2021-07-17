#!/bin/sh

ENDPODINT_FILE=/app/src/EndpointAddresses.js
echo "Processing $ENDPODINT_FILE ...";
sed -i 's|localhost:8080|'${SERVER_URL}'|g' $ENDPODINT_FILE
head -1 $ENDPODINT_FILE