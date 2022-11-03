#########################################################
# First stage: image to  B U I L D the application           #
#########################################################

# Make Image Layers (read only)

# Layer 0 
FROM adoptopenjdk/openjdk11-openj9:alpine-slim as builder

WORKDIR /builder

# COPY: Add files to docker image

# Copy Gradle file (Layer 1) 
# Second argument path is relative to location of docker file
COPY *.gradle /builder/

# Copy sources (Layer 2)
COPY src /builder/src

# Copy Gradle resources (Layer 3 and 4)
COPY gradle /builder/gradle
COPY gradlew /builder/gradlew

# Make Container Layer (read and write)
# Test project
RUN ./gradlew test

# Build project 
RUN ./gradlew assemble

#########################################################
# Second stage: image to  R U N  the application            #
#########################################################

# jre: the Java Runtime Environment
FROM jetty:9.4-jre11-slim as runner


# Pull the built files from the builder container
COPY --from=builder /builder/build/libs/*.war /var/lib/jetty/webapps/wahlzeit.war

# Expose port
EXPOSE 8080
