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
