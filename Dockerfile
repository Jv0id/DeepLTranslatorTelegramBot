FROM openjdk:8-jdk
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY . /app

RUN mvn clean install -Dmaven.test.skip=true

# Put YOUR_BOT_NAME here
ENV BOT_NAME=<YOUR_BOT_NAME>
# Put YOUR_BOT_TOKEN here
ENV BOT_TOKEN=<YOUR_BOT_TOKEN>
ENV BOT_DB_URL=jdbc:postgresql://host.docker.internal:5432/deepl-telegram-bot
ENV BOT_DB_USERNAME=postgres
ENV BOT_DB_PASSWORD=786123
ENV ADMIN_ID=201728830

ENTRYPOINT ["java", "-Dbot.name=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dbot.adminId=${ADMIN_ID}", "-Dspring.datasource.url=${BOT_DB_URL}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-jar", "target/DeeplTranslatorTelegramBot-0.0.1.jar"]
