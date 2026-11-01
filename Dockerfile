FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM tomcat:11.0-jdk21
RUN rm -rf /usr/local/tomcat/webapps/* \
 && sed -i 's/<Connector port="8080"/<Connector port="${http.port}"/' /usr/local/tomcat/conf/server.xml
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["sh", "-c", "export CATALINA_OPTS=\"-Dhttp.port=${PORT:-8080}\" && exec catalina.sh run"]