--------------------------------------------
руководство пользователя по Apache ActiveMQ
--------------------------------------------
1. скачать и настроить Apache ActiveMQ
    1.1ссылка для скачивания https://activemq.apache.org/components/classic/
    настройка
    1.2 указать JAVA_HOME в activemq.bat (рекомендуется Oracle JRE 8)
    1.3 положить файл jetty-realm.properties в Ваш <activemq>/conf папку
    для того что бы получить права админа (логин: "k", пароль: "1" - вводить без кавычек)
    1.5. перейти в каталог <activemq>/bin
    1.6. запустить в консоли "activemq.bat сonsole" (вводить без кавычек)
    на проверки - в консоли не должно быть ошибок как рис mq-concole.png
    1.7 в WebBrowser открыть URL -
    http://localhost:8161/index.html - см mq-welcome.png
    и если открылось то это http://localhost:8161/admin/  -- см рис mq-admin.png
        запросит авторизацию вводите что на шаге 1.3 : логин: "k", паоль: "1" - вводить без кавычек
    1.4 настроить 3 очереди на странице http://localhost:8161/admin/queues.jsp
        (вводить без кавычек)
        "jmsFromMes"  -- для вводящий сообщения
        "jmsResponseFromDatanaToMes" -- для ответов успешных
        "jmsFromMesToError" -- для ошибок
    1.5 в очередь "jmsFromMes" отправьте сообщение по ссылке http://localhost:8161/admin/send.jsp
        при этом нужно указать "jmsFromMes" в поле Destination
        cм рис mq-destination.png
    и далее по диагностике где и что - смотреть в админке
    например тут http://localhost:8161/admin/queues.jsp
    см mq-queues.png
на этом все по Apache MQ
