# Spring boot / JPA / AWS / Mustache / Travis / TDD / CI,CD / MariaDB

## 스프링부트와 AWS로 혼자 구현하는 웹 서비스 참조

-------------------------------------------------

* Language: Java / Shell
* Framework: Spring boot / Hibernate / Mustache / JUnit  
* DB: MariaDB  
* Environment: Window / Amazon Linux  
* Tool: Travis / IntelliJ
* Git commit convention: 
  * https://gist.github.com/stephenparish/9941e89d80e2bc58a153
  * https://udacity.github.io/git-styleguide/
* Coding convention (Java):
  * https://naver.github.io/hackday-conventions-java/#modifier-order
      
-------------------------------------------------

![image](https://user-images.githubusercontent.com/35808172/117843678-fc81f000-b2b9-11eb-9d4c-8ee64ede8567.png)

# 전체 구조도 (2021.05.12)
1. Java, Spring boot를 이용하여 간단한 Web application을 만든다.
2. TDD로 만들며 Github에 commit / push 한다.
3. Travis 를 이용하여 Continuous Integration 구축한다.
4. AWS의 S3 서비스를 이용하여, 만든 Jar 파일을 저장한다.
5. AWS의 Code Deploy 서비스를 이용하여 배포, Continuous Deploy 구축한다.
6. AWS EC2 서비스를 이용하여, Jar의 Web application 프로그램을 구동한다.
7. 외부에서 접속하여 확인한다.
-------------------------------------------------
