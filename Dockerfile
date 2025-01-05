# Usando uma imagem base do JDK
FROM eclipse-temurin:17-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar da sua aplicação para dentro do container
COPY target/my-collection-api-0.0.1-SNAPSHOT.jar app.jar

# Define as variáveis de ambiente
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db-mycollection:5432/mycollection_db
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

# Expõe a porta da aplicação
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
