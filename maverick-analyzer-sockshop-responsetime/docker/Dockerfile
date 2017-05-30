FROM adalrsjr1/spot

RUN mkdir -p /home/app

COPY target/maverick-analyzer-sockshop-responsetime-0.0.1-SNAPSHOT.jar /home/app
COPY src/main/resources/log4j2.xml /home/app
VOLUME /home/app/src/main/resources
WORKDIR /home/app