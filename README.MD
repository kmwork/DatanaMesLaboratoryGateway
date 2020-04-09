# Шлюз для загрузки MES-данных лаботатории хим состава стали
````
сделано по тех заданию: https://conf.dds.lanit.ru/pages/viewpage.action?pageId=43683996
или файл `Tech-Doc-MES-v13-20200407_105058.pdf` в папке `<this project>/doc-manual`
````
задача в JIRA: https://jira.dds.lanit.ru/browse/NIOKR-50

## сборка Maven 3 + OpenJDK 13
```
mvn clean compile package spring-boot:repackage 
```

## Как пользоваться для теста
##### Шлюз слушает JMS запросы 
    1) читает из папки app.dir (системное свойство Ява-приложении)
    2) и ждет рест запросы
    
    
### Как настроить к тестированию
1. разверните ActiveMQ и запустите
2. в appliction.yaml пропишите нужные jms очереди    
    datana:
      activemq:
        brokerUrl: tcp://localhost:61616
        requestQueue: jmsFromMes
        responseQueue: jmsResponseFromDatanaToMes
        responseQueueOnError: jmsFromMesToError
  и доступ к Postgresql:
  datana:
    spring:
      datasource:
        driver-class: org.postgresql.Driver
        jdbcUrl: "jdbc:postgresql://localhost:5432/datana"
        username: postgres
        password: 1
        schema: datalake
3. в run_mes_on_windows.bat пропишите путь к Java и к папке app.dir
4. киньте сообщение в очередь что указана по "requestQueue: jmsFromMes"
   примеры сообщений в папке for_send_by_activemq