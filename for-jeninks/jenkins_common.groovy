import com.google.common.graph.GraphBuilder

/** пример Jenkins сборки PipeLine **/
/** сделано по JIRA задаче : https://jira.dds.lanit.ru/browse/NKR-465 **/
//наименование аккаунта на гит
env.constGitCredentialsId = 'kostya5'

// nexus-репозитарий под докеры
env.constDockerDomain = "172.29.40.56:8083"

// url для авторизации для nexus репозитарий
env.constDockerRegistry = "http://$env.constDockerDomain/"

//логин для авторизации на nexus репозиторий
env.constDockerRegistryLogin = "robot-developer"

// токен для доступа в телеграм канал для бота который пишет сообщения о факте начало и окончания сборки
env.constTelegramURL = "https://api.telegram.org/bot1180854473:AAG1BHnbcM4oRRZW2-DKbZMYD2WqkDtUesU/sendMessage?chat_id=-1001325011128&parse_mode=HTML"

//имя проекта jenkins
env.allJob = JOB_NAME

//url для ссылок по задачам из коммитов
env.constJiraURL = "https://jira.dds.lanit.ru/browse/"

// номер сборки в jenkins
env.constDatanaVersion = "-1"

@NonCPS
def loadProperties(Properties properties) {
    File propertiesFile = new File("$WORKSPACE/buildNumber.properties")
    propertiesFile.withInputStream {
        properties.load(it)
    }
}
/**
 * Собирает информацию о коммитах
 * (писал Даниил)
 * @param passedBuilds
 * @param build
 * @return
 */
def lastSuccessfulBuild(passedBuilds, build) {
    if ((build != null) && (build.result != 'SUCCESS')) {
        passedBuilds.add(build)
        lastSuccessfulBuild(passedBuilds, build.getPreviousBuild())
    }
}

/**
 * Отправка сообщения в телеграм через бот
 * @param msg текст сообщения для телеграм
 * @return
 */
def sendTelegram(String msg) {
    sh "/usr/bin/curl -X POST  \"${env.constTelegramURL}\" -d \"text=${msg}\""
}

/**
 * Собирает информацию о коммитах
 * (писал Даниил)
 */
@NonCPS
def getChangeLog(passedBuilds) {
    def log = ""
    for (int x = 0; x < passedBuilds.size(); x++) {
        def currentBuild = passedBuilds[x];
        def changeLogSets = currentBuild.rawBuild.changeSets
        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                def comment = entry.msg

                def commentСut = comment.replaceAll("${env.constJiraURL}", "")
                def commentСut2 = commentСut
                def urls = ""

                //вырезается имя задачи по регулярному выражению NKR--XXXX -- где XXX - номер задача в JIRA
                commentСut.eachMatch("NKR-[0-9]+") {
                    ch ->
                        urls += '<a href=\\"' + "\"${env.constJiraURL}${ch}\"" + '\\">' + "${ch}</a> "
                        commentСut2 = commentСut2.replaceAll("${ch}", "")
                }
                //для отладки
                echo "Comment: ${commentСut2}"

                //ссылка на задачу в JRIA (она может быть пустой если нет задачи в описании коммита)
                echo "Tasks: ${urls}"

                //склейка сообщения из несколько комментариев по каммитам
                log += "${j + 1}. by ${entry.author} on ${new Date(entry.timestamp)}\nComment: ${commentСut2} \nTask: ${urls}\n"


            }
            log += "\n"
        }
    }
    return log;
}
