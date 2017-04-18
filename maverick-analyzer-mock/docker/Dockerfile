FROM adalrsjr1/spot

RUN makdir -p /home/app

COPY target/maverick-analyzer-mock-0.0.1-SNAPSHOT-jar-with-dependencies.jar /home/app
COPY src/libs/jhoafparser-1.1.1.jar /home/app
COPY src/main/resources/log4j2.xml /home/app
VOLUME /home/app/src/main/resources
WORKDIR /home/app