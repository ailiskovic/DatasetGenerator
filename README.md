# Web Application for Generating Timestamp Datasets

## Requirements 

- Frontend: NodeJS and npm 
- Backend: python fast api 

## Building the project
The building of the project should be done first time.
```sh
chmod +x build.sh
chmod +x startBackend.sh
chmod +x startFrontend.sh
./build.sh
```

## Starting the application
Starting Backend and Frontend of the application
```sh
./startBackend.sh
./startFrontend.sh
```
## Limitations
 - Math element should have the highest id value while generating other datasets. E.g. 

1. ID element
2. Integer element
3. Math element

 - The column must be specified as "c + number_of_column"
E.g. 
 - c1 + 5 * c2
