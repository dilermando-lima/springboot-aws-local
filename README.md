
# Spring boot and aws local with localstack
This project is a simple way to test service of AWS locally with [localstack image](https://hub.docker.com/r/localstack/localstack)

  * [Services AWS](#services-aws)
  * [Requeriments](#requeriments)
  * [Start container AWS LOCAL](#start-container-aws-local)
  * [Setup container AWS LOCAL](#setup-container-aws-local)
  * [Start application](#start-application)
  * [Swagger endpoint](#swagger-endpoint)

## Services AWS
Services implemented:
  - SQS
  - S3 ( not yet )
  - dynamo ( not yet )


## Requeriments
It's required install:
  - docker
  - java11+
  - gradle 
  - aws cli

> all scripts bash has been created on ubuntu SO. You can see them in `./local/sript/*`


## Start container AWS LOCAL
All script are placed in `./local/sript/*` and all of them use enviromment variabbles from `./local/env/local.env` as application uses too.

```bash

# start container aws from localstack/localstack image
bash ./local/script/start-local-aws.bash up ./local/env/local.env

```
## Setup container AWS LOCAL
After run container up let's set up all queues, database and storages

```bash

# set all quees into container already run up
bash ./local/script/setup-aws-container.bash ./local/env/local.env

```

## Start application
If you wanna start app with no IDE try:

```bash

# build project with gradle
gradle build

# run gradle bootRun add all env from `./local/env/local.env`
bash ./local/script/start-app-gradle.bash ./local/env/local.env

```

You want to use VSCODE all launch is already added in `.vscode/launch.json`. So let's try F5 and enjoy

## Swagger endpoint
After start aplication up access swagger link:
  - http://localhost:8080/swagger-ui.html



