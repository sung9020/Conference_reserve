# ȸ�ǽ� �����ϱ� ��

**- ���� ȯ��**  
Spring boot  
Java 1.8  
Gradle  
Embedded Redis

**- ���� �׽�Ʈ**  

1) �ҽ� �ֻ���� ���� ��¥�� ���� �ð�, ȸ�ǽ��� �����Ͽ� �׽�Ʈ �����ϴ�.  
2) �⺻���� ���� �� ���� ����, ��� �׽�Ʈ ���̽��� �⺻ ���� ������(������ : test~)  
3) �Ϲ� ���� �׽�Ʈ(������ : normal~)  
4) ���� ���� �׽�Ʈ(������ : repeat~) 

   - redis�� ���� ������ �Է��ϱ�(ConferenceApplicationTests.setDataByReservationRepositoryForTest)  
   - �Ϲ�ȸ�� �����ϱ�(ConferenceApplicationTests.setNormalReserveForTest)  
   - ����ȸ�� �����ϱ�(ConferenceApplicationTests.setReapeatReserveForTest)    
   - Ư������ ���൥���� ��������(ConferenceApplicationTests.getReservationForTest)  

   - API ��Ʈ�ѷ��� POST �׽�Ʈ(ReserveApiContrllerTest.controllerTestForPost)
   - API ��Ʈ�ѷ��� PUT �׽�Ʈ(ReserveApiContrllerTest.controllerTestForPut)

 

**- ���� ���**  
������Ʈ ��Ʈ > gradle build


**- redis ���� IP, PORT(local)**  
IP : 127.0.0.1  
PORT : 63700


**- ����ϴ� redis �ڷ���**  
SET : ������ �������� Entity�� HASH ���� ����ȴ�.  
HASH : �ռ� ������ HASH ���� �̿��Ͽ� �������� Entity(ReserveInfo)�� �����Ѵ�.  
�������� ���� �߿��ϹǷ� ���೯(reserveDate)�� index ó���Ѵ�.  


**- ����URL**  
http:/localhost:9090  
http:/localhost:9090/main


**- ���� �ذ� ����**  
1. �θ޸� DB�� �� �����ұ�? : ���õǾ� �ְ�, ���� ���� �ÿ� �ٷ� ���డ���� �κ���� ���𽺸� �������
2. spring-data-redis ������? : crudRepository ������� DB �۾� �� �߻�ȭ ����  
3. redis�� smember ��ɾ�� entity�� ��ȸ�� �� �ִ� �ؽ� ���� �� �� �ִ�.  
   - smembers conference > ��ü entity hash Ű���  
   - smembers conference:reserveDate:{������} > ������ ���� eneity hash Ű���  
   - hgetall conference:{hash Ű} > �ؽ� Ÿ������ ����� ���� ������ ��ȸ  
3. 30�� ������ ������ ���� : VIEW�ܿ��� �������� 30�� ������ �����ϰԲ� ����  
4. �Ϲ� ȸ��, ���� ȸ�� ���� : ������ ������ ȸ�� Ÿ������ ������ �и�  
5. ���� �� �� �ִ� �ð��� ������ ���� : 06~22�� ���̿��� ȸ�Ǹ� ������ �� �ֵ��� ����  
6. �������� �ð� ���� ã�ƿ���, �࿡���� ȸ�ǽ� �̸��� ��Ī�Ͽ� ȸ�� ���� �ð� ����
   - �����ϴ� �ð��� ���۽ð��� ���ð� ������ �ִ� ���� ��� ���� ���� ������ ��ĥ
7. �������� �����ڵ�, ��� �޼����� enum���� ����ȭ �� �ִ��� �ϰ� ����  
8. �ݺ��� Ƚ���� reaptCount �̴�. reaptCount = 2�̸� �� 3�� ����. 0��°�� �⺻ �����̴�.  

**- �׽�Ʈ ��� ����ÿ� ������ ����**
1. ���� �����Ͻ� ��(service�� repository �׽�Ʈ)�� ���� �׽�Ʈ �ϴ� ������ Ȯ �پ��� ���� ����Ǵ� �� �ϴ�.
    - VIEW�� ���� �ٷ� �׽�Ʈ �ǰ� �� ���� § �۵��� �ߵǴ°� ���� �ٷ� ���̱� �����̴�.
2. ��Ʈ�ѷ� ���� �׽�Ʈ ����� ���� �ÿ� ���������� API ����� �ǰ� �ִ��� Ȯ�� �����ϴ�.
    - ���� ��� ������ ȿ�������� ���̰� json �����͸� �ϳ��� ������ ���� VIEW������ ó���� �����̹Ƿ� �׽�Ʈ ����� ����� �ð����� VIEW���� �׽����� �����ϴ°� �ð� ������ �Ǵ� �� ����.  
    - ���� �� ������ �Ǵ� ��Ȳ���� json �����͸� �����ϴ� ����� �ѹ� �������� ���� ����/������ ������ �� ����.