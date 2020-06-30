FROM openjdk:13-alpine
LABEL version="3.0"
ENV TZ=Europe/Moscow
WORKDIR /app_mes
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY target/mes-gateway-@buildNumber@.jar .
COPY mesConfig/application.yaml .
COPY mesConfig/input-xsd-schema.xsd .
ENTRYPOINT ["java", "-Dapp.dir=/app_mes", "-Dfile.encoding=UTF8", "-jar", "mes-gateway-@buildNumber@.jar"]
