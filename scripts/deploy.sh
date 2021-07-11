#!/bin/bash

# 프로젝트 디렉토리 주소와 프로젝트 이름
# $변수명 으로 변수를 사용
REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=springboot-aws-web

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl springboot-aws-web | grep jar | awk '{print $1}')

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

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

# nohup을 통해서 터미널 닫아도 실행 유지되도록
# 엠퍼샌드를 통해서 터미널 잡아놓지 않도록
# classpath 가 붙으면 jar 안에 있는 resources 디렉토리 기준으로 경로가 생성됨
# application-oauth.properties 는 절대경로를 사용함. 외부에 파일이 있기 때문
nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
        -Dspring.profiles.active=real \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
# nohup 실행 시, Code deploy 는 무한대기 함
# 이 이슈 해결을 위해 nohup.out 파일을 표준 입출력용으로 별도로 사용함
# 이렇게 하지 않으면 nohup.out 파일이 생기지 않고 Code Deploy 로그에 표준 입출력이 출력됨
# nohup 이 끝나기 전까지 Code Deploy도 끝나지 않으니 꼭 이렇게 해야함
