FROM openjdk:17-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR generado por Maven
COPY target/${JAR_FILE} ./upload-download-files-api.jar

# Expone el puerto que utiliza la aplicación
EXPOSE 8080

ENTRYPOINT ["java","-jar","/upload-download-files-api.jar"]
