
* Docker,
    Jag har använt docker då jag arbetat med uppgiften och nedan tar jag med de kommandon som behövs om man
    vill köra kafka i en docker container. 
 
    För att använda Docker så finns en docker-compose fil under projektrooten
    Starting kafka(foreground):
    >docker-compose -f docker-compose.yml up

    Starta kafka dockercontainer (background):
    >docker-compose -f docker-compose.yml up -d

    stoppa kafka dockercontainer
    >docker-compose down

    För att köra kafka-skript i containern behöver man gå in i containern
    >docker exec -it kafka /bin/sh

* Kafka,
    Denna del antar jag är lika vare sig man kör kafka docker container eller en lokal installation av Kafka på datorn
    Gå till kafka foldern (i mitt fall var det)
    >cd /opt/kafka_2.12-2.4.1/bin

    Skapa en topic genom att köra från /opt/kafka_2.12-2.4.1/bin (eller motsvarande där skriptet ligger)
    >kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic syslog

    Om man vill se att det gick bra att skapa topicen kan man lista befintliga topics med: 
    >kafka-topics.sh --list --zookeeper zookeeper:2181 

    För att se att det publiceras meddelanden i topicen kan man köra följande skript:
    >kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic syslog --from-beginning

* Bygga,
    För att testa koden behöver man bygga den, jag har använt intellij då jag byggt och för det mesta kört koden där 
    ifrån. Jag har lagt till i pom filen så att man bygger en fet jar som kan köras.

* Köra,
    Man kan antingen exekvera main filen från intellij för att köra programmet, alternativt köra jar filen
    från konsolen:
    >java -jar kafkalabb-1.0-SNAPSHOT-jar-with-dependencies.jar

    Man får hålla koll i utskrifterna i terminalen efter json resultatet
