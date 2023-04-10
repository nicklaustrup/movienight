#!/bin/bash
# Populating User table
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
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'User' \
--item '{
  "userId": {"S": "9a6775b9-35c8-4bde-9165-1854a69c624c"},
  "firstName": {"S": "Nathan"},
  "lastName": {"S": "Holt"}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'User' \
--item '{
  "userId": {"S": "01677f59-d4a7-4fbc-9455-f2ef80c06839"},
  "firstName": {"S": "Peter"},
  "lastName": {"S": "Itskovich"}
}'
# Populating Movie table
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Movie' \
--item '{
  "movieId": {"S": "062deb9d-937b-44f4-a057-c8629cb461d9"},
  "title": {"S": "Demon Slayer the Movie: Mugen Train"},
  "description": {"S": "After his family was brutally murdered and his sister turned into a demon, Tanjiro Kamado''s journey as a demon slayer began. Tanjiro and his comrades embark on a new mission aboard the Mugen Train, on track to despair."}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Movie' \
--item '{
  "movieId": {"S": "537b5cbe-7c12-468b-84c4-c3d1aa88093c"},
  "title": {"S": "Interstellar"},
  "description": {"S": "A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival."}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Movie' \
--item '{
  "movieId": {"S": "0410299e-7099-4a62-bc2d-b2ad2885ae40"},
  "title": {"S": "John Wick"},
  "description": {"S": "An ex-hit-man comes out of retirement to track down the gangsters that killed his dog and took his car."}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Movie' \
--item '{
  "movieId": {"S": "145f47b4-0c8d-4667-83c7-f3509ac59d88"},
  "title": {"S": "John Wick: Chapter 2"},
  "description": {"S": "After returning to the criminal underworld to repay a debt, John Wick discovers that a large bounty has been put on his life."}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Movie' \
--item '{
  "movieId": {"S": "4d78a6d8-12f8-45ae-82eb-b111804172ad"},
  "title": {"S": "John Wick: Chapter 3 - Parabellum"},
  "description": {"S": "John Wick is on the run after killing a member of the international assassins'' guild, and with a $14 million price tag on his head, he is the target of hit men and women everywhere."}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Movie' \
--item '{
  "movieId": {"S": "815fba8f-4ea9-43ed-a737-0c67bded9dc5"},
  "title": {"S": "Everything Everywhere All at Once"},
  "description": {"S": "A middle-aged Chinese immigrant is swept up into an insane adventure in which she alone can save existence by exploring other universes and connecting with the lives she could have led."}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Movie' \
--item '{
  "movieId": {"S": "a6c8b1b4-b96f-44ff-82cc-80d7da3afe12"},
  "title": {"S": "Top Gun: Maverick"},
  "description": {"S": "After thirty years, Maverick is still pushing the envelope as a top naval aviator, but must confront ghosts of his past when he leads TOP GUN''s elite graduates on a mission that demands the ultimate sacrifice from those chosen to fly it."}
}'
# Populating Event table
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Event' \
--item '{
  "eventId": {"S": "781a51c9-582d-4ce2-931c-76ade1f0f6e2"},
  "eventTitle": {"S": "Science Fiction Night - Space & Time travel"},
  "movieId": {"S": "537b5cbe-7c12-468b-84c4-c3d1aa88093c"},
  "date": {"S": "2023-04-17T19:30"},
  "active": {"BOOL": true}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Event' \
--item '{
  "eventId": {"S": "9a4142f2-ade1-4a12-92a7-e18190f0140c"},
  "eventTitle": {"S": "Anime Shonen Night - Destinies Collide"},
  "movieId": {"S": "062deb9d-937b-44f4-a057-c8629cb461d9"},
  "date": {"S": "2023-04-15T20:30"},
  "active": {"BOOL": true}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Event' \
--item '{
  "eventId": {"S": "277f7b73-bdbe-4944-a13c-15e820b69977"},
  "eventTitle": {"S": "Hitman Classic - Last Action Hero"},
  "movieId": {"S": "0410299e-7099-4a62-bc2d-b2ad2885ae40"},
  "date": {"S": "2023-04-13T18:00"},
  "active": {"BOOL": true}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'Event' \
--item '{
  "eventId": {"S": "a47d8c79-86f2-491d-893e-5153dfd93abd"},
  "eventTitle": {"S": "Oscar Winner 2022 - Best Movie"},
  "movieId": {"S": "815fba8f-4ea9-43ed-a737-0c67bded9dc5"},
  "date": {"S": "2023-04-20T18:00"},
  "active": {"BOOL": true}
}'
# Populating RSVP table
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "9a6775b9-35c8-4bde-9165-1854a69c624c"},
  "eventId": {"S": "277f7b73-bdbe-4944-a13c-15e820b69977"},
  "isAttending": {"BOOL": true}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "9a6775b9-35c8-4bde-9165-1854a69c624c"},
  "eventId": {"S": "781a51c9-582d-4ce2-931c-76ade1f0f6e2"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "9a6775b9-35c8-4bde-9165-1854a69c624c"},
  "eventId": {"S": "9a4142f2-ade1-4a12-92a7-e18190f0140c"},
  "isAttending": {"BOOL": true}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "9a6775b9-35c8-4bde-9165-1854a69c624c"},
  "eventId": {"S": "a47d8c79-86f2-491d-893e-5153dfd93abd"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "baf28172-8aaa-415d-a62c-4217c33dcdf2"},
  "eventId": {"S": "277f7b73-bdbe-4944-a13c-15e820b69977"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "baf28172-8aaa-415d-a62c-4217c33dcdf2"},
  "eventId": {"S": "9a4142f2-ade1-4a12-92a7-e18190f0140c"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "baf28172-8aaa-415d-a62c-4217c33dcdf2"},
  "eventId": {"S": "a47d8c79-86f2-491d-893e-5153dfd93abd"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "0084d4dc-3395-4ed1-afea-6e1d57a71930"},
  "eventId": {"S": "277f7b73-bdbe-4944-a13c-15e820b69977"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "01677f59-d4a7-4fbc-9455-f2ef80c06839"},
  "eventId": {"S": "277f7b73-bdbe-4944-a13c-15e820b69977"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "01677f59-d4a7-4fbc-9455-f2ef80c06839"},
  "eventId": {"S": "781a51c9-582d-4ce2-931c-76ade1f0f6e2"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "01677f59-d4a7-4fbc-9455-f2ef80c06839"},
  "eventId": {"S": "9a4142f2-ade1-4a12-92a7-e18190f0140c"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "01677f59-d4a7-4fbc-9455-f2ef80c06839"},
  "eventId": {"S": "a47d8c79-86f2-491d-893e-5153dfd93abd"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "98d0900e-0888-44a7-b45e-72005055f216"},
  "eventId": {"S": "277f7b73-bdbe-4944-a13c-15e820b69977"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "98d0900e-0888-44a7-b45e-72005055f216"},
  "eventId": {"S": "781a51c9-582d-4ce2-931c-76ade1f0f6e2"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "98d0900e-0888-44a7-b45e-72005055f216"},
  "eventId": {"S": "9a4142f2-ade1-4a12-92a7-e18190f0140c"},
  "isAttending": {"BOOL": false}
}'
aws dynamodb put-item \
--endpoint-url http://localhost:8000 \
--table-name 'RSVP' \
--item '{
  "userId": {"S": "98d0900e-0888-44a7-b45e-72005055f216"},
  "eventId": {"S": "a47d8c79-86f2-491d-893e-5153dfd93abd"},
  "isAttending": {"BOOL": false}
}'



