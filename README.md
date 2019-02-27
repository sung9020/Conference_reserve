# 회의실 예약하기

- 구현 환경  
Spring boot  
Java 1.8  
Gradle
Embedded Redis

- 단위 테스트  
redis에 예약 데이터 입력하기(setReserveRepository)  
일반회의 예약하기(setNormalReserveTest)  
정기회의 예약하기(setReapeatReserveTest)    
특정일의 예약데이터 가져오기(getReserve)  


- 빌드 방법  
mvn clean package

- redis 접근 IP, PORT(local)  
IP : 127.0.0.1  
PORT : 63700

- 접속URL  
http:/localhost:9090

- 문제 해결 전략  
1. 인메모리 DB를 뭘 선택할까? : 바로 실행가능한 인베디드 레디스를 사용하자  
2. spring-data-redis 장점은? : crudRepository 사용으로 DB 작업 시 추상화 가능  
3. redis의 smember 명령어로 각 키의 해쉬 값을 알수 있다.
3. 30분 단위로 예약을 강제 : VIEW단에서 유저에게 30분 단위로 선택하게끔 유도  
4. 일반 회의, 정기 회의 구분 : 유저가 선택한 회의 타입으로 로직을 분리  