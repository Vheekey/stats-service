FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY . .

RUN chmod +x gradlew

# Run Spock tests
RUN ./gradlew test --no-daemon -x configureChromeDriverBinary -x configureEdgeDriverBinary -x configureGeckoDriverBinary

# Build app JAR
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
