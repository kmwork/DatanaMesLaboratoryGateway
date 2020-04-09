#!/bin/sh
##### MES Gateway (DATANA) #####
export JAVA_HOME=/home/lin/apps/jdk13
export PATH=$JAVA_HOME/bin:$PATH
java -Dapp.dir="/home/lin/apps/MesGateway" -Dfile.encoding=UTF8 -jar mes-gateway.jar