FROM amazoncorretto:21-alpine

EXPOSE 8080 8443

COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]