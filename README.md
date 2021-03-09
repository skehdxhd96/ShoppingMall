# [ Shopping Mall Project ]

## 1. Descrption
> 판매자/사용자로 나누어 쇼핑몰 프로젝트를 구현한다.  
>> - 판매자
>>> 1. 상품CRUD(자신이 판매한 상품에 한해)  
>>> 2. 댓글 관리(자신이 판매한 상품에 한해)
>> - 구매자
>>> 1. 장바구니 CRUD
>>> 2. 댓글 작성,수정,삭제(자신이 구매한 상품에 한해)
>>> 3. 결제, 주문 기능
>> - 공통
>>> 1. 로그인(네이버)
>>> 2. 회원 정보 수정 / 탈퇴
>>> 3. 포인트 적립 / 재고품절 기능
>>> 4. 평점(댓글을 작성한 구매자 평점의 평균)

## 2. Tech Stack
> Spring4  
MySQL 8.0  
Google Cloud Database  
Bootstrap4  
Javascript/Jquery/HTML/CSS  
Maven  
Eclipse

## 3. Database
![DB](https://user-images.githubusercontent.com/55571682/110319338-17ce4600-8052-11eb-8f7e-daa6fac8921b.PNG)

## 4. View   
> ## *4-1. 기본화면*    
>>- Main화면   
<img src = "https://user-images.githubusercontent.com/55571682/110480599-022c4f80-812a-11eb-9bfa-0222ee0d6c86.PNG" width="400px">

>>- 로그인(구매자, 판매자)  
<img src = "https://user-images.githubusercontent.com/55571682/110480603-03f61300-812a-11eb-8b78-986c9422ec51.PNG" width="400px" height = "100px">
<img src = "https://user-images.githubusercontent.com/55571682/110480606-05274000-812a-11eb-9514-db7763b6c19e.PNG" width="400px" height = "100px">

> ## *4-2. 회원정보화면(마이페이지)*
>>- 주문  
<img src = "https://user-images.githubusercontent.com/55571682/110483106-be871500-812c-11eb-8d77-a18436ba496d.PNG" width="300px" height = "300px">

>>- 장바구니  
<img src = "https://user-images.githubusercontent.com/55571682/110482244-cd20fc80-812b-11eb-885b-be9543325e72.PNG" width="300px" height = "300px">

>>- 회원정보수정(탈퇴)  
<img src = "https://user-images.githubusercontent.com/55571682/110482121-a6fb5c80-812b-11eb-92cd-687d3be7f3f2.PNG" width="300px" height = "300px">

> ## *4-3. 로그인(네이버 소셜 연동), 회원가입*
>> - 로그인  
<img src = "https://user-images.githubusercontent.com/55571682/110483485-16258080-812d-11eb-89f7-e333b264e243.PNG" width="150px" height = "150px">

>> - 회원가입(구매자, 판매자)  
<img src = "https://user-images.githubusercontent.com/55571682/110483741-5d137600-812d-11eb-88b7-2231bf8cbeda.PNG" width="150px" height = "300px"> 
<img src = "https://user-images.githubusercontent.com/55571682/110483729-5ab11c00-812d-11eb-9461-0dc7c981256b.PNG" width="150px" height = "300px">

> ## *4-4. 상품*
>> - 상품리스트  
<img src = "https://user-images.githubusercontent.com/55571682/110484455-035f7b80-812e-11eb-98a3-a266ff2daf02.PNG" width="400px" height = "300px">

>> - 상품세부조회  
<img src = "https://user-images.githubusercontent.com/55571682/110484806-5c2f1400-812e-11eb-915c-02eadff83d0c.PNG" width="300px" height = "300px">

>> - 상품업로드  
<img src = "https://user-images.githubusercontent.com/55571682/110484462-03f81200-812e-11eb-80bb-7c56e4d69343.PNG" width="400px" height = "300px">

>> - 댓글  
<img src = "https://user-images.githubusercontent.com/55571682/110484361-ecb92480-812d-11eb-93ec-40d1f3f2a5d9.PNG" width="300px" height = "300px">

> ## *4-5. 결제*
>> - 결제  
<img src = "https://user-images.githubusercontent.com/55571682/110485252-c9db4000-812e-11eb-969f-5819de5f703d.PNG" width="215px" height = "300px">
<img src = "https://user-images.githubusercontent.com/55571682/110485247-c8aa1300-812e-11eb-9e3c-085b8b4dcd92.PNG" width="215px" height = "300px">

>> - 주문배송(도로명주소api)  
<img src = "https://user-images.githubusercontent.com/55571682/110485265-cd6ec700-812e-11eb-9617-84f4aa3d809c.PNG" width="300px" height = "300px">
