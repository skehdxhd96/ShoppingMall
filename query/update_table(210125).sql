use shop;
/*테이블명 변경*/
RENAME TABLE user TO customer;

/*칼럼명 변경*/
ALTER TABLE customer CHANGE customer_check customer_type TINYINT(1);

/*칼럼 추가*/
ALTER TABLE customer ADD COLUMN company_name varchar(45) NULL;
ALTER TABLE customer ADD COLUMN company_phone varchar(20) NULL;