#!/bin/bash

# bash ./local/script/setup-aws-container.bash ./local/env/local.env

# ====================== read variables file
if [ -n "$1"  ]; then
        echo "reading $1 into enviromment variables"
        set -a
        source $1
        set +a
fi

# ======================= CREATING PROFILE FOR CONTAINER
    echo "setting fake credentials"
    aws configure set aws_access_key_id "any"
    aws configure set aws_secret_access_key "any"
    aws configure set region $AWS_CONTAINER_DEFAULT_REGION

# ======================= CREATING QUEUE
    echo "creating default queues"
    aws --endpoint-url=http://localhost:$AWS_CONTAINER_EDGE_PORT sqs create-queue --queue-name $AWS_CONTAINER_NAME_QUEUE_1
    aws --endpoint-url=http://localhost:$AWS_CONTAINER_EDGE_PORT sqs create-queue --queue-name $AWS_CONTAINER_NAME_QUEUE_2
    # CHECKING QUEUES: aws --endpoint-url=http://localhost:4566 sqs list-queues