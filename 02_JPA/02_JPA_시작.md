# 2장 JPA 시작
## 기본적인 애노테이션
- @Entity: 이 클래스를 테이블과 매핑한다고 JPA에게 알려준다
- @Table: 엔티티 클래스에 매핑할 테이블 정보를 알려준다. 생략하면 클래스 이름을 테이블 이름으로 매핑
- @Id: 엔티티 클래스의 필드를 테이블의 Primary Key에 매핑한다
- @Column: 필드를 칼럼에 매핑한다.

## 엔티티 매니저
- 엔티티 매니저의 생성 과정: 1) persistence.xml에서 설정 정보 조회 2) EntityManagerFactory 생성 3) EntityManager 생성
- 엔티티 매니저 팩토리 생성: persistence.xml의 설정 정보를 읽어서 객체를 만들고 구현체에 따라서는 DB 커넥션 풀도 생성한다. 
- 엔티티 매니저 생성: 엔티티 매니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.(스레드간에 공유, 재사용 금지)
- 사용이 끝난 엔티티 매니저와 엔티티 매니저 팩토리는 종료해야함

## 트랜잭션 관리
- EntityManger.getTransaction()을 통해 트랜잭션을 받아와서 try문 안에서 데이터들 변경해야 하고, 예외 발생 시 트랜잭션을 롤백한다.
- 수정: member.setAge(20); 하면 자동으로 반영된다. 궁금한점(spring data jpa에서 습관적으로 수정하면 마지막에 save 눌러주는데 안해도 되나..?)
- JPQL: SQL을 추상화한 객체지향 쿼리 언어. 엔티티 객체를 대상으로 쿼리한다.
- `SELECT M.ID, M.NAME, M.AGE FROM MEMBER M`