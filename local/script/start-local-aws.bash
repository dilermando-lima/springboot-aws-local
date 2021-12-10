#!/bin/bash

# bash ./local/script/start-local-aws.bash up ./local/env/local.env
# bash ./local/script/start-local-aws.bash down


# ========= requires aws CLI installing
        # curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
        # && unzip awscliv2.zip
        # && sudo ./aws/install


# ====================== read variables file

if [ -n "$2"  ]; then
        echo "reading $2 into enviromment variables"
        set -a
        source $2
        set +a
fi

# ======================= handle args
    if [ "$1" != "up" ] && [ "$1" != "down" ]; then
        echo "argument not found. try 'file.bash up' or 'file.bash down'"
        exit -1;
    fi

# ======================= handle arg down to remove container
    if [ "$1" == "down" ] || [ "$1" == "DOWN"  ]; then
        docker rm -f aws_services_container
        echo "aws_services_container container removed sucessfully!"
        exit 0;
    fi


# ======================= handle arg up start process
if [ "$1" == "up" ] || [ "$1" == "UP"  ]; then
        
    # ======================= start container
    echo -e "\nrestarting container  aws_services_container..."
    docker rm -f aws_services_container
    docker run -d --name aws_services_container \
            -p $AWS_CONTAINER_EDGE_PORT:$AWS_CONTAINER_EDGE_PORT \
            -e SERVICES=$AWS_CONTAINER_SERVICES_LIST \
            -e EDGE_PORT=$AWS_CONTAINER_EDGE_PORT \
            -e DEFAULT_REGION=$AWS_CONTAINER_DEFAULT_REGION \
        localstack/localstack:0.13

    # ======================= waiting container
    URL_HEALTH_CONTAINER="http://localhost:$AWS_CONTAINER_EDGE_PORT/health"

    IS_SONAR_ON=NOT_200
    while [ "$IS_SONAR_ON" != "200" ]; do 
        IS_SONAR_ON=$(curl -LIsS -X GET $URL_HEALTH_CONTAINER -o /dev/null -w '%{http_code}\n'); 
        echo "waiting for aws_services_container..."
        sleep 5
    done;

    echo -e "\n\nThat's all ready!!! \n"

# ============= some helpfull commands


    # alias awsserv='aws --endpoint-url=http://localhost:4566'
    # aws configure set aws_access_key_id "any" # --profile local_aws
    # aws configure set aws_secret_access_key "any" # --profile local_aws
    # aws configure set region $AWS_CONTAINER_DEFAULT_REGION # --profile local_aws


    # === SQS
        
        # awsserv sqs list-queues
        # awsserv sqs create-queue --queue-name queue-1
        # awsserv sqs send-message --queue-url http://localhost:4566/000000000000/queue-1 --message-body 'any message'
        # awsserv sqs get-queue-attributes --attribute-names All --queue-url http://localhost:4566/000000000000/queue-1
        # awsserv sqs purge-queue --queue-url http://localhost:4566/000000000000/queue-1
        # awsserv sqs get-queue-attributes --attribute-names All --queue-url http://localhost:4566/000000000000/queue-1
 
    # ==== DYNAMO
        # awsserv dynamodb list-tables
        # awsserv dynamodb create-table --table-name MyTable --attribute-definitions AttributeName=id,AttributeType=S  --key-schema AttributeName=id,KeyType=HASH  --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
        # awsserv dynamodb describe-table --table-name MyTable
        # awsserv dynamodb delete-table --table-name MyTable
        # awsserv dynamodb put-item --table-name MyTable --item '{"id":{"S":"123"}}'
        # awsserv dynamodb put-item --table-name MyTable --item '{"id":{"S":"123"},"name":{"S":"name1"}}'
        # awsserv dynamodb put-item --table-name MyTable --item file://item.json

        # awsserv dynamodb get-item --table-name MyTable --key '{"id":{"S":"123"}}'
        # awsserv dynamodb scan --table-name MyTable --page-size 1  --max-items 1 # --select COUNT

    # ==== S3

        # awsserv s3api create-bucket --bucket mybucket
        # awsserv s3api put-bucket-acl --bucket mybucket --acl public-read
        
        # echo "content in file" > ./new_file.txt
        # awsserv s3 cp file-to-upload.txt  s3://mybucket/      # Uplod file http://mybucket2.s3.localhost.localstack.cloud:4566/new_file.txt
        # awsserv s3api create-bucket --bucket mybucket2        # Create bucket
        # awsserv s3 sync  s3://mybucket/  s3://mybucket2/      # Create bucket 
        # awsserv s3 ls s3://mybucket/                          # List bucket
        # awsserv s3 ls s3://mybucket/ --recursive --human-readable --summarize     # List summarized bucket
        # awsserv s3 cp s3://mybucket/file-to-upload.txt test-downloaded.txt        # Download file
        # awsserv s3 presign s3://mybucket/file-to-upload.txt --expires-in 600      # Create temp link

fi  
