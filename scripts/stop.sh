#!/usr/bin/env bash

## 기존 엔진엑스에 연결되어 있지 않지만, 실행 중이던 스프링부트 종료

ABSPATH=$(readlink -f $0)
# 현재 stop.sh 가 속해있는 경로를 찾는다.
# 하나 더 아래 코드와 같이 profile.sh 경로를 찾기 위해 사용된다.
ABSDIR=$(dirname $ABSPATH)
# 자바로 보면 일종의 import 구문, 해당 코드로 그 내부의 function 사용 가능해짐
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi