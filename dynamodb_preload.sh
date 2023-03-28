#!/bin/bash
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'User' \
--item '{
  "userId": {"S": "baf28172-8aaa-415d-a62c-4217c33dcdf2"},
  "firstName": {"S": "Hamza"},
  "lastName": {"S": "Malik"}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'User' \
--item '{
  "userId": {"S": "0084d4dc-3395-4ed1-afea-6e1d57a71930"},
  "firstName": {"S": "Eduardo"},
  "lastName": {"S": "Angulo"}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'User' \
--item '{
  "userId": {"S": "98d0900e-0888-44a7-b45e-72005055f216"},
  "firstName": {"S": "Nick"},
  "lastName": {"S": "Laustrup"}
}'
