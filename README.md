# 회의실 예약하기 앱

**- 구현 환경**  
Spring boot  
Java 1.8  
Gradle  
Embedded tomcat  
Embedded Redis  


**- 단위 테스트**  

1) 소스 최상단의 예약 날짜와 예약 시간, 회의실을 조정하여 테스트 가능하다.  
2) 기본적인 적재 및 샘플 조정, 모든 테스트 케이스에 기본 예약 데이터(변수명 : test~)  
3) 일반 예약 테스트(변수명 : normal~)  
4) 정기 예약 테스트(변수명 : repeat~) 

   - redis에 예약 데이터 입력하기(ConferenceApplicationTests.setDataByReservationRepositoryForTest)  
   - 일반회의 예약하기(ConferenceApplicationTests.setNormalReserveForTest)  
   - 정기회의 예약하기(ConferenceApplicationTests.setReapeatReserveForTest)    
   - 특정일의 예약데이터 가져오기(ConferenceApplicationTests.getReservationForTest)  

   - API 컨트롤러의 POST 테스트(ReserveApiContrllerTest.controllerTestForPost)
   - API 컨트롤러의 PUT 테스트(ReserveApiContrllerTest.controllerTestForPut)
 

**- 빌드 방법**  
1. gradle 설치
2. cmd창이나 터미널에서 프로젝트 루트 접근 > gradle build  


**- 실행 가능한 jar 파일 받기**  
https://github.com/sung9020/Conference_reserve/releases  


**- jar 파일 실행하기(서비스 구동)**  
java -jar -Dspring.profiles.active=local conference-0.0.1-SNAPSHOT.jar  


**- redis 접근 IP, PORT(local)**  
IP : 127.0.0.1  
PORT : 63700  


**- 사용하는 redis 자료형**  
SET : 저장한 예약정보 Entity의 HASH 값이 저장된다.  
HASH : 앞서 저장한 HASH 값을 이용하여 예약정보 Entity(ReserveInfo)를 저장한다.  
예약일이 가장 중요하므로 예약날(reserveDate)를 index 처리한다.  


**- 접속URL**  
http:/localhost:9090  
http:/localhost:9090/main  


**- 문제 해결 전략**  
1. 인메모리 DB를 뭘 선택할까? : 숙련되어 있고, 서버 구동 시에 바로 실행가능한 인베디드 레디스를 사용하자
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
8. 반복할 횟수가 reaptCount 이다. reaptCount = 2이면 총 3건 예약(선택한 날짜의 회의실 예약 포함).  


**- 테스트 모듈 만들시에 생각한 내용**  
1. 실제 비지니스 모델 구현 케이스(service나 repository 테스트)의 경우는 단위 테스트로 인해 작업 공수가 확 줄어드는 것은 보장되는 듯 하다.  
    - VIEW단 없이 바로 테스트 되고 또 내가 짠 코드가 작동이 잘되는게 눈에 바로 보이기 때문이다.  
2. 컨트롤러 단의 테스트 모듈을 만들 시에 정상적으로 API 통신이 되고 있는지 확인 가능하다.  
    - 정상 통신 정도가 효율적으로 보이고 json 데이터를 하나씩 보더라도 실제 VIEW에서의 처리가 관건이므로 테스트 모듈을 만드는 시간보다 VIEW에서 테스팅을 진행하는게 시간 단축이 되는 것 같다.  
    - 물론 일정 상 여유가 되는 상황에서 json 데이터를 검증하는 모듈을 한번 만들어놓고 쓰면 빌드/배포가 편해질 것 같다.  


**- 예약 하기 스크린샷**  
- 원하는 예약정보를 적은 후에 예약하기 버튼을 누른다. 

![setreserve](https://user-images.githubusercontent.com/38482334/53679666-cd9f8500-3d12-11e9-9287-731a642a2ae0.JPG)  


**- 예약 현황 스크린샷**  
- 조회 날짜를 입력한 후에 조회 버튼을 누르면 예약 현황이 표기된다. 
- 정기 회의의 경우 회의 예약 내역에 (정기회의) 표기가 붙는다.  
- 예약된 시간대는 같은 색깔로 표기가 된다. 

![getreserve](https://user-images.githubusercontent.com/38482334/53679665-cd9f8500-3d12-11e9-93eb-8cefc15406a2.JPG)  


