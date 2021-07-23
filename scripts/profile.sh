#!/usr/bin/env bash

## 다른 4개 스크립트 파일에서 공용으로 사용할 'profile'과 포트 체크 로직

## 쉬고 있는 profile 찾는 함수
# real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬는 중
function find_idle_profile()
{
    # 현재 엔진엑스가 바라보고 있는 스프링부트가 정상적으로 수행 중인지 확인
    # 응답 값을 HttpStatus로 받는다.
    # 정상이면 200, 오류면 400~ 503 사이 발생, 400 이상은 예외로 보고 real2를 현재 profile로 사용함
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      # 엔진엑스와 연결되지 않은 profile을 IDLE_PROFILE에 넣는다.
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    # bash 스크립트는 값을 반환하는 기능이 없음
    # 그래서 마지막에 echo 결과 출력 후, 클라이언트에서 그 값을 잡아서 사용함, 중간에 echo 사용하면 안됨
    echo "${IDLE_PROFILE}"
}

## 쉬고 있는 profile의 port 찾는 함수
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}