# ȸ�ǽ� �����ϱ�

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
mvn clean package

- redis ���� IP, PORT(local)  
IP : 127.0.0.1  
PORT : 63700

- ����URL  
http:/localhost:9090

- ���� �ذ� ����  
1. �θ޸� DB�� �� �����ұ�? : �ٷ� ���డ���� �κ���� ���𽺸� �������  
2. spring-data-redis ������? : crudRepository ������� DB �۾� �� �߻�ȭ ����  
3. redis�� smember ��ɾ�� �� Ű�� �ؽ� ���� �˼� �ִ�.
3. 30�� ������ ������ ���� : VIEW�ܿ��� �������� 30�� ������ �����ϰԲ� ����  
4. �Ϲ� ȸ��, ���� ȸ�� ���� : ������ ������ ȸ�� Ÿ������ ������ �и�  