
# Server voting system

Server application with microservices architecture for the voting system of the SENA headquarters 

## Used By

This project is used by the following companies:

- SENA German-Colombian center


## Tech Stack - Server

**Server:** Java, Springboot

**Security:** Spring-security OAuth2

**BBDD:** H2, MySQL

**Tools:** Docker, Kubernetes, Helm, Postman

## Run Locally

To run the project locally, you must follow the next steps carefully:
- Clone the project
- Unzip it
- Go to the docker-compose directory dev environment
With the following commands:

```bash
  git clone https://github.com/edev404/server-voting-system.git
  unzip server-voting-system.zip
  cd server-voting-system/docker-compose/dev

```
Execute the docker-compose.yml with Docker:

```bash
  sudo docker compose up
```
If you want to run the project without Docker, must get into each project and execute them in this order:

configserver → eurekaserver → votingserver → gatewayserver

Using the following command or with help of your preffered IDE

```bash
  mvn spring-boot:run
```

## Deployment

This project deployment depends on environment variables that are secret. To use it,
get in contact with fabricatic via email fabricatic@gmail.com

## Support

For support, email fabricatic@gmail.com

## Authors
This project was materialized by ADSO apprentices associated with the development unit of the SENA German-Colombian center called: "FabricaTic".

- [@edev404](https://www.github.com/edev404)
- [@cristianPorrety](https://www.github.com/cristianPorrety)


