# Шлюз для загрузки MES-данных лаботатории хим состава стали
````
сделано по тех заданию: https://conf.dds.lanit.ru/pages/viewpage.action?pageId=43683996
или файл `Tech-Doc-MES-v13-20200407_105058.pdf` в папке `<this project>/doc-manual`
````
задача в JIRA: https://jira.dds.lanit.ru/browse/NIOKR-50

# генерация POJO по XML-Схемам
````
mvn clean jaxb2:xjc
и разкомментировать строку <goal>xjc</goal> в pom.xml так как она опасная, ломает сборку, поэтому выключена 
для этого нужно подложить новые xsd схемы в папку <путь к проекту >/src/main/xsd/*.xsd
````        

