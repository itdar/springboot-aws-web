#!/bin/bash

# 프로젝트 디렉토리 주소와 프로젝트 이름
# 쉘에서는 타입 없이 선언해서 저장함
# 쉘에서는 $변수명 으로 변수를 사용함
REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=springboot-aws-web

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"

git pull

echo "> 프로젝트 Build 시작"

./gradlew build

echo "> step1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

# -f 옵션은 프로세스 이름으로 찾는 것
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> 새 애플리케이션 배포"

# 여러 jar 파일이 생길 것이기 때문에, tail -n 으로 가장 나중의 jar 파일(최신)을 변수에 저장한다.
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR_NAME: $JAR_NAME"

# nohup을 통해서 터미널 닫아도 실행 유지되도록
# 엠퍼샌드를 통해서 터미널 잡아놓지 않도록
# 스프링 설정파일 위치를 지정해서, 기본 옵션 application.properties 와 OAuth 설정인 application-oauth.properties 를 지정함
# classpath 가 붙으면 jar 안에 있는 resources 디렉토리 기준으로 경로가 생성됨
# application-oauth.properties 는 절대경로를 사용함. 외부에 파일이 있기 때문
nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties \
        $REPOSITORY/$JAR_NAME 2>&1 &


