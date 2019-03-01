# ȸ�ǽ� �����ϱ� ��

- ���� ȯ��  
Spring boot  
Java 1.8  
Gradle  
Embedded Redis

- ���� �׽�Ʈ  
redis�� ���� ������ �Է��ϱ�(setReserveRepository)  
�Ϲ�ȸ�� �����ϱ�(setNormalReserveTest)  
����ȸ�� �����ϱ�(setReapeatReserveTest)    
Ư������ ���൥���� ��������(getReserve)  


- ���� ���  
������Ʈ ��Ʈ > gradle build

- redis ���� IP, PORT(local)  
IP : 127.0.0.1  
PORT : 63700

- ����ϴ� redis �ڷ���  
SET : ������ ���� �������� HASH ���� ����ȴ�.  
HASH : �ռ� ������ HASH ���� �̿��Ͽ� �������� Entity(ReserveInfo)�� �����Ѵ�.  
�������� ���� �߿��ϹǷ� ���೯(reserveDate)�� index ó���Ѵ�.  

- ����URL  
http:/localhost:9090  
http:/localhost:9090/main


- ���� �ذ� ����  
1. �θ޸� DB�� �� �����ұ�? : ���� �Ǿ� �ְ�, ���� ���� �ÿ� �ٷ� ���డ���� �κ���� ���𽺸� �������
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