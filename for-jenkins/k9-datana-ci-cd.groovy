import org.codehaus.groovy.runtime.GStringImpl
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
//@Library('JenkinsDatanaCommon@1.0')
library 'JenkinsDatanaCommon'
import ru.datana.groovy.jenkins.JenkinsDatanaCommon

/** пример Jenkins сборки PipeLine **/
/** сделано по JIRA задаче : https://jira.dds.lanit.ru/browse/NKR-465 **/

def datanaCommons = new JenkinsDatanaCommon(this)
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

//имя проекта jenkins
def final constAllJob = JOB_NAME

// полное имя докер образа с учетом репозитария
env.constImageDocker = "$datanaCommons.constDockerDomain/$env.constDockerName/$env.constDockerTag:$env.constDockerImageVersion"


/**
 * Тело Pipeline
 */
try {
    node {
        stage('step-1: Init') {
            //очистка от старой сборки
            cleanWs()

            //чтение гитхаба
            checkout([$class: 'GitSCM', branches: [[name: env.constGitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: datanaCommons.constGitCredentialsId, url: env.constGitUrl]]])

            echo "[#0 file] $WORKSPACE/buildNumber.properties"

            Properties properties = new Properties()
            datanaCommons.loadProperties(properties)

            // номер сборки в jenkins
            datanaCommons.varDatanaVersion = properties.fixMajorMinor + "." + properties.buildNumber


            //путь на мавен и яву для запуска в SHELL-Linux
            env.PATH = "$pipelineParamMavenHome/bin:$pipelineParamJavaHome/bin:$PATH"
            echo "[PARAM] PATH=$PATH"
            passedBuilds = []
            datanaCommons.lastSuccessfulBuild(passedBuilds, currentBuild);

            def changeLog = datanaCommons.getChangeLog(passedBuilds)
            if (changeLog.trim() == '') {
                changeLog = 'нет изменений'
            }

            //отправка о начале сборки в телеграм
            datanaCommons.sendTelegram("Начинаю сборку:  ${env.constAllJob}. Version ${datanaCommons.varDatanaVersion}. build ${BUILD_NUMBER}\nВ этой серии вы увидите: \n ${changeLog}");


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
            datanaCommons.removeDocker()
        }

        stage('step-4: Docker build') {
            datanaCommons.dockerBuild(env.constImageDocker)
        }

        stage('step-5: Docker create') {
            datanaCommons.dockerCreate(env.constImageDocker, env.constExtPort, env.constInnerPort)
        }

        stage('step-6: Docker push') {
            datanaCommons.dockerPush(env.constImageDocker)
        }

        stage('step-7: Telegram step') {
            //отправка сообщения в телеграм об успешной сборке
            datanaCommons.sendTelegram("Сборка завершена ${env.constAllJob}. Version ${datanaCommons.varDatanaVersion}. build ${env.BUILD_NUMBER}")
        }
    }


} catch (e) {

    // перехват ошибки для отправки в телеграм о аварии при сборке
    currentBuild.result = "FAILED"
    node {
        datanaCommons.sendTelegram("Сборка сломалась ${env.constAllJob}.Version ${datanaCommons.varDatanaVersion}. build ${env.BUILD_NUMBER}")
    }
    throw e
}

