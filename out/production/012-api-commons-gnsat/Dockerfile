#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ADD target/api-auth-1.0.0.jar app.jar
#ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap  -XX:MaxRAMFraction=1 -XshowSettings:vm "
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

# Start with a base image containing Java runtime
#FROM openjdk:8-jdk-alpine
FROM alpine/java:21-jdk

# Add Maintainer Info
LABEL maintainer="martin84zarate@gmail.com"

ENV TZ=America/Asuncion
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 9002 available to the world outside this container
EXPOSE 8081

# The application's jar file
ARG JAR_FILE=cortesia-1.0.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} cortesia-1.0.0.jar

# Run the jar file
ENTRYPOINT ["java","-Dspring.profiles.active=development", "-Djava.security.egd=file:/dev/./urandom","-jar","/cortesia-1.0.0.jar"]