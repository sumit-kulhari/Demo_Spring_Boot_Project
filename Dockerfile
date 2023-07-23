# Use a base image that includes the JRE (Java Runtime Environment)
FROM openjdk:11

# Set environment variables for Maven
ENV MAVEN_VERSION 3.8.4
ENV MAVEN_HOME /usr/lib/maven

# Install wget to download Maven
RUN apt-get update && apt-get install -y wget

# Download and install Maven
RUN wget -q https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    && tar -xf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /usr/lib \
    && ln -s /usr/lib/apache-maven-${MAVEN_VERSION} /usr/lib/maven \
    && rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

# Set Maven bin directory in the system PATH
ENV PATH $PATH:$MAVEN_HOME/bin
