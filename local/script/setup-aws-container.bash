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
    echo "==== SETTING FAKE CREDENTIALS"
    aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
    aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
    aws configure set region $AWS_CONTAINER_DEFAULT_REGION

# ======================= CREATING QUEUE
    echo "==== CREATING DEFAULT QUEUES"
    aws --endpoint-url=http://localhost:$AWS_CONTAINER_EDGE_PORT sqs create-queue --queue-name $AWS_SQS_NAME_QUEUE_1
    aws --endpoint-url=http://localhost:$AWS_CONTAINER_EDGE_PORT sqs create-queue --queue-name $AWS_SQS_NAME_QUEUE_2
    # CHECKING QUEUES: aws --endpoint-url=http://localhost:4566 sqs list-queues


# ======================= CREATING BUCKETS
    echo "==== CREATING DEFAULT BUCKETS"
    aws --endpoint-url=http://localhost:$AWS_CONTAINER_EDGE_PORT s3api create-bucket --bucket $AWS_S3_NAME_BUCKET_1