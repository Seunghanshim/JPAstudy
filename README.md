# JPA study
JPA study


# 요구사항 분석
 - 회원은 상품을 주문할 수 있다.
 
 - 주문 시 여러 종류의 상품을 선택할 수 있다.
 
 - 상품의 종류는 음반, 도서, 영화가 있고 이후 더 확장될 수 있다.
 
 - 모든 데이터는 등록일과 수정일이 포함된다.
 
# 기능 목록
  ## 회원 기능
   - 회원등록
   - 회원조회
  ## 상품 기능
   - 상품등록
   - 상품수정
   - 상품조회
  ## 주문 기능
   - 상품주문
   - 주문내역조회
   - 주문취소
   
   
# 도메인 모델 분석
  - 회원과 주문의 관계: 회원은 여러 번 주문할 수 있다. (1:N)
  - 주문과 상품의 관계: 주문할 때, 여러 상품을 선택할 수 있다. 
  반대로 같은 상품도 여러번 주문될 수 있다. 
  주문상품이라는 모델을 만들어서 N:N 관계를 1:N, N:1 관계로 풀어냈다.
  - 배송, 카테고리를 추가하였다.
  - 상품의 종류를 추가하였다.
  
      ![모델](https://user-images.githubusercontent.com/68942616/89101861-f1528780-d43e-11ea-8c2d-909afb4c4c65.png)
  
  
# 테이블 설계
  - 상품 테이블의 상속 전략은 Single table로 구현하였다.
  
   ![테이블](https://user-images.githubusercontent.com/68942616/89101865-f4e60e80-d43e-11ea-897c-76746472ce07.PNG)
      
      
# 객체 구조
  - 참조를 사용하도록 변경하였다.
  - 카테고리와 상품의 관계를 Category_Item이라는 Class을 만들어서 1:N, N:1 관계로 풀어냈다.
  
   ![엔티티](https://user-images.githubusercontent.com/68942616/89191596-20532f80-d5de-11ea-893c-5a0e8105476f.PNG)


# 연관관계 매핑 포인트
  - 객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.
  - 객체의 양방향 관계는 양방향 관계가 아니라 서로 다른 단방향 관계 2개이다.
  - 양방향 매핑 시, 테이블에서 외래 키가 있는 곳을 주인으로 정한다. (주인이 아닌쪽은 읽기만 가능)
  - 단방향 매핑만으로도 이미 연관관계 매핑은 완료된 것이다. (양방향은 필요할 때 추가)
  - 양방향 매핑 시, 순수 객체 상태를 고려하여 항상 양쪽에 값을 설정한다. (연관관계 편의 메소드 생성)
      
      
# N:M 관계는 1:N, N:1로
  - 테이블의 N:M 관계는 중간테이블을 이용해서 1:N, N:1관계로 풀어낸다.
  - 실전에서 N:M 관계를 지양해야하는 이유는 중간 테이블이 단순하지 않기 때문이다.
  - 따라서 실전에서는 @ManyToMany를 사용하지 않는다.
  

# 상속관계 매핑 포인트
  - 관계형 데이터베이스는 상속 관계가 없다.
  - 상속관계 매핑(@Inheritance)에는 3가지 전략이 있다. (JOINED, SINGLE_TABLE, TABLE_PER_CLASS)
  - TABLE_PER_CLASS는 사용을 지양한다. (조회시 성능 저하, 쿼리 문제 때문)
  - 상속관계에서 data type column을 위해 @DiscriminatorColumn을 사용한다. (부모 클래스)
  - DB에서 DTYPE에 저장될 명칭을 위해 @DiscriminatorValue를 사용한다. (자식 클래스)
  
# @MappedSuperclass
  - 상속관계 매핑이 아니고, 엔티티가 아니기 때문에 테이블과 매핑이 되지도 않는다.
  - 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모아서 관리할 수 있게 도와준다.
  - 조회가 불가능하고 직접 생석해서 사용할 일이 없으므로 추상 클래스로 사용한다.
  - @Entity 클래스는 @Entity나 @MappedSuperclass로 지정한 클래스만 상속이 가능하다.
  
# 즉시 로딩과 지연 로딩 포인트
  - 가급적 지연 로딩(LAZY)만 사용한다.
  - 즉시 로딩(EAGER)을 적용하면 예상하지 못한 SQL이 발생하고, JPQL에서 N+1 문제를 일으킨다.
  - @ManyToOne, @OneToOne은 default가 즉시 로딩이므로 지연 로딩으로 설정하여 사용한다.

# 영속석 전이: CASCADE
  - 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없다.
  - 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공할 뿐이다.
  
# 고아 객체
  - 고아 객체 제거(orphanReval = true): 부모 Entity와 연관관계가 끊어진 자식 Entity를 자동으로 삭제한다.
  - 참조하는 곳이 하나일 때만 사용해야한다. (주의)
  
# 엔티티 타입의 특징
  - 식별자가 존재하고 생명 주기를 관리할 수 있다.
  - 공유 가능하다
  
# 값 타입의 특징
  - 식별자가 존재하지 않고 생명 주기가 엔티티에 의존한다.
  - 공유하지 않는 것이 안전하다. (복사해서 사용한다)
  - 불변 객체로 만드는 것이 안전하다. (ex) setter를 private 혹은 만들지 않는다)
