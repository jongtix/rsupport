#RSupport 과제
###RSupport 기술 과제 제출을 위한 Repository입니다.

##사용된 기술
```
Spring Boot 2.3.6.RELEASE
JDK 1.8
Gradle 6.7
H2
JUnit 5
Mustache 2.3.6.RELEASE
Spring REST Docs 2.0.5.RELEASE 등
```

##실행 방법
```
1. github에서 소스 clone
    - git clone https://github.com/jongtix/rsupport
    
2. Project build
    - 프로젝트가 clone 된 위치에서 ./gradlew build
    
3. Application 실행
    - 프로젝트 홈/build/libs/위치로 이동
    - java -jar Rsupport-1.0-SNAPSHOT.jar
    
4. 브라우저를 통해 확인
    - http://localhost:8080/
```

##REST API 확인
```
Application 실행 후 브라우저에서 http://localhost:8080/docs/api-docs.html를 통해 REST API 확인 가능
cf) IDE를 활용하여 Application을 실행할 경우 REST API용 html이 포함되어 있지 않아 오류가 발생할 수 있으니
    반드시 jar 파일로 Application을 실행한 후 확인 부탁드립니다.
```