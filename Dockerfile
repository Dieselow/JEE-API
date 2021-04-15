FROM maven:3.8.1-openjdk-11
COPY . /app
WORKDIR /app
RUN mvn clean
RUN mvn compile
RUN mvn package
CMD java -jar target/*.jar