# 회의실 예약하기 앱

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
프로젝트 루트 > gradle build

- redis 접근 IP, PORT(local)  
IP : 127.0.0.1  
PORT : 63700

- 사용하는 redis 자료형  
SET : 저장한 예약 데이터의 HASH 값이 저장된다.  
HASH : 앞서 저장한 HASH 값을 이용하여 예약정보 Entity(ReserveInfo)를 저장한다.  
예약일이 가장 중요하므로 예약날(reserveDate)를 index 처리한다.  

- 접속URL  
http:/localhost:9090  
http:/localhost:9090/main


- 문제 해결 전략  
1. 인메모리 DB를 뭘 선택할까? : 숙련 되어 있고, 서버 구동 시에 바로 실행가능한 인베디드 레디스를 사용하자
2. spring-data-redis 장점은? : crudRepository 사용으로 DB 작업 시 추상화 가능  
3. redis의 smember 명령어로 entity를 조회할 수 있는 해쉬 값을 알 수 있다.  
   - smembers conference > 전체 entity hash 키목록  
   - smembers conference:reserveDate:{예약일} > 예약일 기준 eneity hash 키목록  
   - hgetall conference:{hash 키} > 해쉬 타입으로 저장된 예약 데이터 조회  
3. 30분 단위로 예약을 강제 : VIEW단에서 유저에게 30분 단위로 선택하게끔 유도  
4. 일반 회의, 정기 회의 구분 : 유저가 선택한 회의 타입으로 로직을 분리  
5. 예약 할 수 있는 시간을 강제로 지정 : 06~22시 사이에만 회의를 예약할 수 있도록 적용  
6. 열에서는 시간 값을 찾아오고, 행에서는 회의실 이름을 매칭하여 회의 예약 시간 뷰잉
   - 예약하는 시간의 시작시간과 끝시간 사이의 있는 셀을 모두 같은 랜덤 색으로 색칠
7. 고정정인 에러코드, 결과 메세지를 enum으로 세분화 및 최대한 일괄 정리