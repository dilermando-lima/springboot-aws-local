
# Spring boot and aws local with localstack

## Start container AWS LOCAL
All script are placed in `./local/sript/*` and all of them use enviromment variabbles from `./local/env/local.env` as application uses too.

``` bash
bash ./local/script/start-local-aws.bash up ./local/env/local.env
```
## Setup container AWS LOCAL
After run container up let's set up all queues, database and storages

```
bash ./local/script/setup-aws-container.bash ./local/env/local.env
```

## Swagger endpoint
After start aplication up access swagger link:
  - http://localhost:8080/swagger-ui




