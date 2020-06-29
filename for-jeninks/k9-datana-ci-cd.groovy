/** пример Jenkins сборки PipeLine **/
/** сделано по JIRA задаче : https://jira.dds.lanit.ru/browse/NKR-465 **/

//ветка git проекта
env.constGitBranch = 'DatanaMesLaboratoryGateway'

// gitlab рапозитарий датаны
env.constGitUrl = 'git@gitlab.dds.lanit.ru:mmk_niokr/tools.git'

//наименование аккаунта на гит
env.constGitCredentialsId = 'kostya5'

//путь где установлен мавен
env.constMVN_HOME = '/home/lin/apps/apache-maven-3.5.4'

//путь где ява
env.constJAVA_HOME = '/home/lin/apps/jdk13'

// nexus-репозитарий под докеры
env.constDockerDomain = "172.29.40.56:8083"

// url для авторизации для nexus репозитарий
env.constDockerRegistry = "http://$env.constDockerDomain/"

//внешний порт для докера
env.constExtPort = 9990
// рабочий порт явы-приложения внутри докера
env.constInnerPort = 61616

//имя докер образа
env.constDockerName = "k9_mes_gataway_demo"

//тег докера образа
env.constDockerTag = "mes_jms"

//версия приложения (докера)
env.constDockerImageVersion = "3"

// полное имя докер образа с учетом репозитария
env.constImageDocker = "$env.constDockerDomain/$env.constDockerName/$env.constDockerTag:$env.constDockerImageVersion"

//логин для авторизации на nexus репозиторий
env.constDockerRegistryLogin = "robot-developer"

// токен для доступа в телеграм канал для бота который пишет сообщения о факте начало и окончания сборки
env.constTelegramURL = "https://api.telegram.org/bot1180854473:AAG1BHnbcM4oRRZW2-DKbZMYD2WqkDtUesU/sendMessage?chat_id=-1001325011128&parse_mode=HTML"

//имя проека jenkins
env.allJob = JOB_NAME

// номер собрки в jenkins
env.Version = "0.0.${BUILD_NUMBER}"

//url для ссылок по задачам из коммитов
env.constJiraURL = "https://jira.dds.lanit.ru/browse/"

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
                echo "Comment: ${commentСut2}"
                echo "Tasks: ${urls}"

                log += "${j + 1}. by ${entry.author} on ${new Date(entry.timestamp)}\nComment: ${commentСut2} \nTask: ${urls}\n"


            }
            log += "\n"
        }
    }
    return log;
}

/**
 * Тело Pipeline
 */
try {
    node {
        stage('step-1: Init') {
            cleanWs()
            checkout([$class: 'GitSCM', branches: [[name: env.constGitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: env.constGitCredentialsId, url: env.constGitUrl]]])

            env.PATH = "$env.constMVN_HOME/bin:$env.constJAVA_HOME/bin:$PATH"
            passedBuilds = []
            lastSuccessfulBuild(passedBuilds, currentBuild);

            def changeLog = getChangeLog(passedBuilds)
            if (changeLog.trim() == '') {
                changeLog = 'нет изменений'
            }

            sendTelegram("Начинаю сборку:  ${allJob}. build ${BUILD_NUMBER}\nВ этой серии вы увидите: \n ${changeLog}");

            echo "[PARAM] PATH=$PATH"
            echo "-----------------------------------"
            echo sh(script: 'env|sort', returnStdout: true)
            echo "==================================="
        }

        stage('step-2: Build by maven') {
            sh "mvn clean compile package spring-boot:repackage"
        }
        stage('step-3: Docker remove') {
            //sh "docker container prune -f"
            try {
                sh '''#!/bin/bash -xe
                    echo "for name = ${constDockerTag}"
                    echo "[cmd] = docker ps | grep ${constDockerTag} | awk '{print $1}' | xargs docker stop"
                    docker ps | grep ${constDockerTag} | awk '{print $1}' | xargs docker stop
                '''
            } catch (e) {
                echo "[#1]stop docker with error : " + e
            }

            try {
                sh '''#!/bin/bash -xe
                    echo "[cmd] = docker images | grep ${constDockerTag} | awk '{print $3}' | xargs docker rmi -f"
                    docker images | grep ${constDockerTag} | awk '{print $3}' | xargs docker rmi -f
                '''
            } catch (e) {
                echo "[#2]remove docker with error : " + e
            }
        }


        stage('step-4: Docker build') {
            sh "docker build --tag=$env.constImageDocker ."
        }

        stage('step-5: Docker create') {
            sh "docker create \"$env.constImageDocker\""

            //kostya-temp
            sh "docker run --rm -d -p $env.constExtPort:$env.constInnerPort \"$env.constImageDocker\""
        }

        stage('step-6: Docker push') {
            sh "cat /home/lin/apps/datana-docker-secret/password-nexus-datana.txt | docker login --password-stdin --username=${env.constDockerRegistryLogin} ${env.constDockerRegistry}"
            sh "docker push $env.constImageDocker"
        }


        stage('step-7: Telegram step') {
            sendTelegram("Сборка завершена ${env.allJob}. build ${env.BUILD_NUMBER}")
        }
    }


} catch (e) {
    currentBuild.result = "FAILED"
    node {
        sendTelegram("Сборка сломалась ${env.allJob}. build ${env.BUILD_NUMBER}")
    }
    throw e
}

