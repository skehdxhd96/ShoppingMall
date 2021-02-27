ALTER TABLE customer DROP customer_password;
ALTER TABLE customer ADD signup_date TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE customer ADD social VARCHAR(20) NOT NULL;

DROP TABLE review;
DROP TABLE order_basket;
DROP TABLE product_basket;
DROP TABLE basket;

CREATE TABLE basket (
	`product_code`	INT	UNSIGNED NOT NULL,
	`customer_code`	INT	UNSIGNED NOT NULL,
	`product_quantity`	INT	UNSIGNED NOT NULL	DEFAULT 1
);
ALTER TABLE basket 
ADD CONSTRAINT product_code_pk
FOREIGN KEY (product_code)
REFERENCES product(product_code);

ALTER TABLE basket 
ADD CONSTRAINT customer_code_fk
FOREIGN KEY (customer_code)
REFERENCES customer(customer_code);

ALTER TABLE basket
ADD CONSTRAINT basket_pk
PRIMARY KEY (product_code, customer_code);

CREATE TABLE order_basket (
	`order_code`	INT	UNSIGNED NOT NULL,
    `product_code`	INT	UNSIGNED NOT NULL,
	`customer_code`	INT	UNSIGNED NOT NULL,
	`product_quantity`	INT	UNSIGNED NOT NULL	DEFAULT 1
);

ALTER TABLE order_basket 
ADD CONSTRAINT order_basket_fk1
FOREIGN KEY (order_code)
REFERENCES `order`(order_code);

ALTER TABLE order_basket 
ADD CONSTRAINT order_basket_fk2
FOREIGN KEY (product_code)
REFERENCES `basket`(product_code);

ALTER TABLE order_basket 
ADD CONSTRAINT order_basket_fk3
FOREIGN KEY (customer_code)
REFERENCES `basket`(customer_code);

ALTER TABLE order_basket
ADD CONSTRAINT order_basket_pk
PRIMARY KEY (order_code, product_code, customer_code);

CREATE TABLE `review` (
	`review_code`	INT	UNSIGNED NOT NULL auto_increment PRIMARY KEY,
	`review_date`	TIMESTAMP NOT NULL DEFAULT NOW(),
	`review_comment`	VARCHAR(500),
	`review_score`	TINYINT(1)	UNSIGNED NOT NULL,
	`order_code`	INT	UNSIGNED NOT NULL, 
	`product_code`	INT	UNSIGNED NOT NULL,
	`customer_code`	INT	UNSIGNED NOT NULL
);

ALTER TABLE review 
ADD CONSTRAINT review_fk
FOREIGN KEY (order_code, product_code, customer_code)
REFERENCES `order_basket`(order_code, product_code, customer_code);