# Readme

## Datenbank

Image bauen:
```shell
docker build -t marsrover-db-image .
```

Container erstellen und starten:
```shell
docker run -d --name marsrover-db-container -p 5555:5432 marsrover-db-image
```

Container stoppen:
```shell
docker stop marsrover-db-container 
```