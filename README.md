Starting kafka(foreground):
>docker-compose -f docker-compose.yml up

Starting kafka(background):
>docker-compose -f docker-compose.yml up -d

stoping kafka
>docker-compose down

getting into the container
>docker exec -it kafka /bin/sh

find kafka in the container
>cd /opt
>ls

Goto /opt/kafka_2.12-2.4.1/bin


Create a topic
>kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic syslog

list topics
>kafka-topics.sh --list --zookeeper zookeeper:2181 


listen to a topic
>kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic syslog --from-beginning