# 1장 JPA 소개

## 1.1 SQL을 직접 다룰 때 발생하는 문제점
### 1.1.1 SQL을 직접 다룰 때 발생하는 문제점
- SQL문 작성 -> JDBC를 이용하여 SQL을 실행 -> 조회 결과를 객체로 매핑
- 객체를 데이터베이스에 CRUD할 때마다 반복되는 DAO 작업을 해주어야 함

### 1.1.2 SQL에 의존적인 개발
- 엔티티와 데이터 접근 계층 간의 계층 분할이 어려움
- 엔티티를 신뢰할 수 없음
- SQL에 의존적인 개발을 하게됨

### 1.1.3 JPA와 문제 해결
- 저장: `jpa.persist(member);`
- 조회: `Member member = jpa.find(Member.class, memberId)`
- 수정: `member.setName("이름변경")` - 트랜잭션을 커밋할 때 적절한 UPDATE SQL이 날라감(영속성 컨텍스트)
- 연관된 객체 조회: `Team team = member.getTeam();`

## 1.2 패러다임의 불일치
객체지향: 추상화(Abstrcation), 캡슐화(Encapsulation), 정보은닉(Information Hiding), 상속(Inheritance), 다형성(Polymorphism)

RDB에서는 데이터 중심으로 구조화되어있고, 집합적인 사고를 요구하며 목적이 다르다

### 1.2.1 상속
JDBC를 사용한다면, 슈퍼 클래스의 테이블과 서브 클래스의 테이블을 따로 만들고 DTYPE으로 매핑하는 모든 과정을 개발자가 해야 함.
JPA를 사용하면, `jpa.persist(album)`의 코드만으로 ITEM, ALBUM 테이블 사이에서 ITEM_ID로 연관관계를 맺어주고 가져올때도 조인해서 가져옴

### 1.2.2 연관관계
객체에서는 참조가 있는 방향으로만 조회가 가능, 테이블은 FK 하나로 양방향 가능.
객체에서 관계를 저장할때는 ID를 저장하는 것보다 객체 참조를 사용하는 것이 좋음
그러나, 저장, 조회 과정에서 개발자가 해주어야 할 매핑 과정이 필요

### 1.2.3 객체 그래프 탐색 
객체 여러개가 서로서로 연관관계를 맺고 있을 경우, DAO의 코드를 열어 SQL을 보지 않는 이상 객체 그래프를 어디까지 탐색할 수 있을 지 알 수 없음.

JPA에서는 연관된 객체를 사용하는 시점에 적절한 SELECT SQL을 사용하는 '지연로딩'기능을 제공한다. - 프록시 객체를 사용

### 1.2.4 비교
동일성(==), 동등성(equals)의 차이. 같은 로우를 조회했지만 두개는 다른 별개의 인스턴스이므로 동일성 실패.

JPA는 "같은 트랜잭션일 때" 같은 객체가 조회되는 것을 보장.

`Member member1 = jpa.find(Member.class, memberId);`

`Member member2 = jpa.find(Member.class, memberId);`

`member1.setName("test");`

`member1.getName().equals(mebmer2.getName()) (True)`

## 1.3 JPA란 무엇인가?

자바 애플리케이션과 JDBC 사이에서 동작한다.(기술명세, 인터페이스임. 구현은 hibernate에서)

ORM은 SQL의 생성 뿐만 아니라, 객체지향 vs 관계형 데이터베이스 와 같은 패러다임의 불일치 문제도 해결해줌.

### 1.3.1 JPA 소개
JPA는 자바 ORM 기술에 대한 API 표준 명세이다.

구현기술로는 Hibernate, EclipsLink, DataNucleus가 있다.

### 1.3.2 왜 JPA를 사용해야 하는가?

- 생산성: 객체지향적으로 개발을 하면, SQL 작성, JDBC API를 사용하는 작업은 JPA가 해줌. 심지어 엔티티 모델링만 해놓으면 DDL 쿼리도 자동으로 작성해주고, 전략도 설정 가능(ddl-auto)

- 유지 보수: 엔티티에 필드 하나를 변경하면 관련된 모든 SQL, JDBC코드를 변경해야 함. 조그마한 서비스에서는 금방 끝나겠지만, 큰 기업 들의 경우에는 개발 하다 하나 를 고치려고 미리 작성해놓은 모든 SQL문들을 찾아다니면서 수정해야 하는 문제점이 생김

- 패러다임의 불일치 해결: 상속, 연관관계, 객체 그래프 탐색, 비교하기 같은 문제를 해결

- 성능: 영속성 컨텍스트를 제공하므로 같은 트랜잭션 안에서 쿼리도 적게 날릴 수 있음(최적화 가능)

- 데이터 접근 추상화와 벤더 독립성: 데이터베이스를 MySQL을 쓰든, Oracle DB를 쓰든, H2를 쓰든 동일한 추상화 계층을 제공하고 Dialect(방언) 을 이용하여 JPA가 각각 다른 쿼리를 생성.


## 번외: Spring Data JPA와 JPA는 무엇이 다른가?

- Spring Data JPA는 JPA를 또다시 편하게 쓰기위해 만들어놓은 모듈. JPA Hibernate만 사용한다면 EntityManger를 우리가 사용하면서 또다시 복잡한 과정(detach, remove)를 개발자가 해줘야 한다.

- 그러나 우리는 개발을 할때, CRUDRepository를 extend만 해놓으면 세부적인 과정을 신경쓰지 않고도 손쉽게 findMemberByNickname과 같이 메소드를 정의해서 사용할 수 있다. 
https://github.com/spring-projects/spring-data-jpa/blob/main/src/main/java/org/springframework/data/jpa/repository/support/SimpleJpaRepository.java 
이렇게 Spring-Projects에서 SimpleJpaRepository를 구현한 예시를 보면 내부적으로 EntityManager를 사용하는 것을 볼 수 있다.

아래와 같은 구조임(Reference: https://suhwan.dev/2019/02/24/jpa-vs-hibernate-vs-spring-data-jpa/)

![image](https://user-images.githubusercontent.com/46064193/128800353-cb20da8d-2063-47d9-98af-a1d544497a5d.png)