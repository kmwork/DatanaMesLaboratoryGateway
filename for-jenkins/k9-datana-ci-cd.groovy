@Library('JenkinsDatanaCommon@1.0')
//@Grab(group='ru.datana.groovy.jenkins', module='JenkinsDatanaCommon', version='1.0')
import ru.datana.groovy.jenkins.JenkinsDatanaCommon

/** пример Jenkins сборки PipeLine **/
/** сделано по JIRA задаче : https://jira.dds.lanit.ru/browse/NKR-465 **/

datanaCommons = new JenkinsDatanaCommon()
//ветка git проекта
env.constGitBranch = 'DatanaMesLaboratoryGateway'

// gitlab репозитарий датаны
env.constGitUrl = 'git@gitlab.dds.lanit.ru:mmk_niokr/tools.git'

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

/**
 * Тело Pipeline
 */
try {
    node {
        stage('step-1: Init') {
            //очистка от старой сборки
            cleanWs()

            //чтение гитхаба
            checkout([$class: 'GitSCM', branches: [[name: datanaCommons.constGitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: datanaCommons.constGitCredentialsId, url: env.constGitUrl]]])

            echo "[#0 file] $WORKSPACE/buildNumber.properties"

            Properties properties = new Properties()
            loadProperties(properties)

            // номер сборки в jenkins
            datanaCommons.constDatanaVersion = properties.fixMajorMinor + "." + properties.buildNumber


            //путь на мавен и яву для запуска в SHELL-Linux
            env.PATH = "$datanaCommons.constMVN_HOME/bin:$datanaCommons.constJAVA_HOME/bin:$PATH"
            echo "[PARAM] PATH=$PATH"
            passedBuilds = []
            lastSuccessfulBuild(passedBuilds, currentBuild);

            def changeLog = getChangeLog(passedBuilds)
            if (changeLog.trim() == '') {
                changeLog = 'нет изменений'
            }

            //отправка о начале сборки в телеграм
            sendTelegram("Начинаю сборку:  ${datanaCommons.allJob}. Version ${datanaCommons.constDatanaVersion}. build ${BUILD_NUMBER}\nВ этой серии вы увидите: \n ${changeLog}");


            //для отладки
            echo "-----------------------------------"
            echo sh(script: 'env|sort', returnStdout: true)
            echo "==================================="
        }

        stage('step-2: Build by maven') {
            //сборка проекта
            sh "mvn clean compile package spring-boot:repackage"
        }
        stage('step-3: Docker remove') {
            try {
                //остановка докер контейнеров
                sh '''#!/bin/bash -xe
                    echo "for name = ${constDockerTag}"
                    echo "[cmd] = docker ps | grep ${constDockerTag} | awk '{print $1}' | xargs docker stop"
                    docker ps | grep ${constDockerTag} | awk '{print $1}' | xargs docker stop
                '''
            } catch (e) {
                //если ошибка то "глуши ошибку так как контейнеров может  не быть, если в первый запуск
                echo "[#1]stop docker with error : " + e
            }

            try {
                // удаление всех докер образов
                sh '''#!/bin/bash -xe
                    echo "[cmd] = docker images | grep ${constDockerTag} | awk '{print $3}' | xargs docker rmi -f"
                    docker images | grep ${constDockerTag} | awk '{print $3}' | xargs docker rmi -f
                '''
            } catch (e) {
                //возможна ошибка если докер нет (в первый запуск)
                echo "[#2]remove docker with error : " + e
            }
        }


        stage('step-4: Docker build') {
            //сборка докера и установка тега-метки на образ
            sh "docker build --tag=$env.constImageDocker --file ./target/Dockerfile ."
        }

        stage('step-5: Docker create') {
            //создание локально докер контейнера в докер машине
            sh "docker create \"$env.constImageDocker\""

            //запуск докера "в бой"
            sh "docker run --rm -d -p $env.constExtPort:$env.constInnerPort \"$env.constImageDocker\""
        }

        stage('step-6: Docker push') {
            //авторизация в nexus (пароль на nexus читается из файла на локальном ПК)
            sh "cat /home/lin/apps/datana-docker-secret/password-nexus-datana.txt | docker login --password-stdin --username=${env.constDockerRegistryLogin} ${env.constDockerRegistry}"

            //публикация докер контейнера в nexus
            sh "docker push $env.constImageDocker"
        }


        stage('step-7: Telegram step') {
            //отправка сообщения в телеграм об успешной сборке
            sendTelegram("Сборка завершена ${datanaCommons.allJob}. Version ${datanaCommons.constDatanaVersion}. build ${env.BUILD_NUMBER}")
        }
    }


} catch (e) {

    // перехват ошибки для отправки в телеграм о аварии при сборке
    currentBuild.result = "FAILED"
    node {
        sendTelegram("Сборка сломалась ${datanaCommons.allJob}.Version ${datanaCommons.constDatanaVersion}. build ${env.BUILD_NUMBER}")
    }
    throw e
}

