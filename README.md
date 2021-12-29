
# Spring boot and aws local with localstack
This project is a simple way to test service of AWS locally with [localstack image](https://hub.docker.com/r/localstack/localstack)

  * [Services AWS](#services-aws)
  * [Requeriments](#requeriments)
  * [Start container AWS LOCAL](#start-container-aws-local)
  * [Setup container AWS LOCAL](#setup-container-aws-local)
  * [Start application](#start-application)
  * [Doc endpoint](#doc-endpoint)

## Services AWS
Services implemented:
  - SQS
  - S3
  - dynamo


## Requeriments
It's required install:
  - docker
  - java11+
  - gradle 
  - aws cli
  - postman

> all scripts bash has been created on ubuntu SO. You can see them in `./local/sript/*`


## Start container AWS LOCAL
All script are placed in `./local/sript/*` and all of them use enviromment variables from `./local/env/local.env` as application uses too.

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

If you wish to use VSCODE, all launch is already added in `.vscode/launch.json`. So let's try **F5** and enjoy it

## Doc endpoint
After start aplication up, all rest resources are placed in `./doc-api/springboot-aws-local.postman_collection.json` and can be imported into postman



