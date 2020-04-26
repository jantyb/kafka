
För att använda Docker så finns en docker-compose fil under projektrooten
Starting kafka(foreground):
>docker-compose -f docker-compose.yml up

Starta kafka(background):
>docker-compose -f docker-compose.yml up -d

stoppa kafka
>docker-compose down

För att köra skript i containern behöver man in i containern
>docker exec -it kafka /bin/sh

Gå till kafka foldern t.ex.
>cd /opt/kafka_2.12-2.4.1/bin

Skapa en topic genom att köra
>kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic syslog

Visa befintliga topics
>kafka-topics.sh --list --zookeeper zookeeper:2181 

lyssna på en topic
>kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic syslog --from-beginning

För att testa koden behöver man köra 