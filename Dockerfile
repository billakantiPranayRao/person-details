FROM openjdk:8
ADD target/person-details-0.0.1-SNAPSHOT.jar person-details-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","person-details-0.0.1-SNAPSHOT.jar"]