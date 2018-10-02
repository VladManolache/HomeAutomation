# include java 9
FROM openjdk:9-jdk-slim

# copy application JAR (with libraries inside)
COPY target/*.jar /app/

# copy storage folder
COPY api /api

# expose Docker ports 
EXPOSE 8080

#TARGETED ENVIRONMENT default to production
#ARG ENVIRONMENT=dev

# run JAR image
CMD ["java", "-jar", "/app/HomeAutomation-API-1.0-SNAPSHOT-jar-with-dependencies.jar", ".", "8080"]