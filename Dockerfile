# Nesta primeira parte o Dockerfile apenas gera arquivos .jar para a pasta target da aplicacao
# E define diretorios

# Imagem base leve linux
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
# Copia o src e o pom para diretorio /app do container que sera executado
COPY src /app/src
COPY pom.xml /app
# Define o /app como o diretorio de trabalho
WORKDIR /app
# Executa o maven para gerar arquivos .jar (sem executar testes)
RUN mvn clean package

# Nesta segunda etapa a aplicacao e executada de fato
FROM maven:3.9.9-eclipse-temurin-17-alpine
# Cria um grupo spring e usuario spring sem privilegios
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
# Copia o .jar gerado na primeira etapa para a pasta /app do container
COPY --from=build /app/target/*.jar /app/app.jar
# Indica que sera executado na porta 8080
EXPOSE 8080
# Inicia a aplicacao
ENTRYPOINT ["java", "-jar", "/app/app.jar"]