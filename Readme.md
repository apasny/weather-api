## Run service
From folder docker run
```bash
docker-compose -f docker-compose.yml up -d
```
## Init data in DB
USER
```json
{
  "email": "user@user.com",
  "password": "user"
}
```
EDITOR
```json
{
  "email": "editor@editor.com",
  "password": "editor"
}
```
## API

Login endpoint. Response contains token that must be used for POST as bearer token
```http
POST localhost:9005/api/v1/login
```
Get all weather records
```http
GET localhost:9005/api/v1/weather
```
Get all weather records with filtering and sorting
```http
GET localhost:9005/api/v1/weather?date=2022-10-21&city=London,madrid&sort=date
```
Get single weather records by id
```http
GET localhost:9005/api/v1/weather/{id}
```
Create weather records. Access has only EDITOR.
```http
POST localhost:9005/api/v1/weather

Request body example:
{
   "date": "1995-01-01",
   "lat": 36.1189,
   "lon": -86.6892,
   "city": "London",
   "state": "London",
   "temperatures": [17.3, 16.8]
}
```
