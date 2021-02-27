use shop;
ALTER TABLE category CHANGE category_code category_code TINYINT(1);

CREATE TABLE `product` (
	`product_code`	INT	unsigned  NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`product_name`	VARCHAR(45)	NOT NULL,
	`product_manufacturer`	VARCHAR(45)	NOT NULL,
	`product_seller`	VARCHAR(45)	NOT NULL,
	`product_price`	MEDIUMINT NOT NULL,
	`product_stock`	MEDIUMINT	NOT NULL DEFAULT 0,
	`product_point`	MEDIUMINT	NOT NULL DEFAULT 0,
	`product_score`	DECIMAL(2, 1)	NOT NULL	DEFAULT 0,
	`category_code`	TINYINT(2)	NOT NULL
);

CREATE TABLE `category` (
	`category_id`	TINYINT(2)	UNSIGNED NOT NULL PRIMARY KEY,
	`category_name`	VARCHAR(45)	NOT NULL
);

CREATE TABLE `review` (
	`review_code`	INT	UNSIGNED NOT NULL auto_increment PRIMARY KEY,
	`review_date`	TIMESTAMP NOT NULL DEFAULT NOW(),
	`review_comment`	VARCHAR(500),
	`review_score`	TINYINT(1)	UNSIGNED NOT NULL,
	`order_code`	INT	UNSIGNED NOT NULL
);

CREATE TABLE `order` (
	`order_code`	INT	UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`total_order_price`	MEDIUMINT	UNSIGNED NOT NULL,
	`order_date`	TIMESTAMP	NOT NULL DEFAULT NOW(),
	`order_status`	VARCHAR(10)	NOT NULL DEFAULT '주문 완료'
);

CREATE TABLE `delivery` (
	`delivery_code`	INT	 UNSIGNED NOT NULL	AUTO_INCREMENT PRIMARY KEY,
	`departure_date`	TIMESTAMP	NULL,
	`arrival_date`	TIMESTAMP	NULL,
	`shipping_address`	VARCHAR(45)	NOT NULL,
	`delivery_status`	VARCHAR(10)	NOT NULL DEFAULT '배송 준비중',
	`requests`	VARCHAR(50)	NULL,
	`deliver_phone`	VARCHAR(14)	NOT NULL,
	`order_code`	INT	UNSIGNED NOT NULL
);

CREATE TABLE `payment` (
	`payment_code`	INT	UNSIGNED NOT NULL	AUTO_INCREMENT PRIMARY KEY,
	`payment_method`	VARCHAR(5)	NOT NULL,
	`used_point`	MEDIUMINT	UNSIGNED NOT NULL	DEFAULT 0,
	`saved_point`	MEDIUMINT	UNSIGNED NOT NULL	DEFAULT 0,
	`point_check`	TINYINT(1)	NOT NULL	DEFAULT false,
	`total_payment_price`	MEDIUMINT	UNSIGNED NOT NULL,
	`payment_status`	VARCHAR(10)	NOT NULL DEFAULT '결제중',
	`order_code`	INT	UNSIGNED NOT NULL
);

CREATE TABLE `customer` (
	`customer_code`	INT	UNSIGNED NOT NULL	AUTO_INCREMENT PRIMARY KEY,
	`customer_password`	varchar(15)	NOT NULL,
	`customer_email`	varchar(30)	NOT NULL,
	`customer_address`	varchar(45)	NOT NULL,
	`customer_phone`	varchar(20)	NOT NULL,
	`customer_name`	varchar(10)	NOT NULL,
	`customer_check`	TINYINT(1)	NOT NULL,
	`customer_id`	VARCHAR(20)	NOT NULL,
	`customer_point`	MEDIUMINT UNSIGNED NOT NULL DEFAULT 0
);

CREATE TABLE `basket` (
	`customer_code`	INT	UNSIGNED NOT NULL
);

CREATE TABLE `order-basket` (
	`customer_code`	INT	UNSIGNED NOT NULL,
	`order_code`	INT	UNSIGNED NOT NULL,
	`product_quantity`	INT	UNSIGNED NOT NULL	DEFAULT 1
);

CREATE TABLE `product-basket` (
	`customer_code`	INT	UNSIGNED NOT NULL,
	`product_code`	INT	unsigned NOT NULL,
	`product_quantity`	INT UNSIGNED NOT NULL DEFAULT 1
);

CREATE TABLE `image` (
	`image_uuid`	VARCHAR(100)	NOT NULL PRIMARY KEY,
	`image_url`	VARCHAR(200)	NOT NULL,
	`product_code`	INT UNSIGNED NOT NULL
);

ALTER TABLE `order-basket` ADD CONSTRAINT `PK_ORDER-BASKET` PRIMARY KEY (
	`customer_code`,
	`order_code`
);

ALTER TABLE `product-basket` ADD CONSTRAINT `PK_PRODUCT-BASKET` PRIMARY KEY (
	`customer_code`,
	`product_code`
);

ALTER TABLE `basket` ADD CONSTRAINT `PK_BASKET` PRIMARY KEY (
	`customer_code`
);

ALTER TABLE `basket` ADD CONSTRAINT `FK_customer_TO_basket_1` FOREIGN KEY (
	`customer_code`
)
REFERENCES `customer` (
	`customer_code`
);

ALTER TABLE `order-basket` ADD CONSTRAINT `FK_basket_TO_order-basket_1` FOREIGN KEY (
	`customer_code`
)
REFERENCES `basket` (
	`customer_code`
);

ALTER TABLE `order-basket` ADD CONSTRAINT `FK_order_TO_order-basket_1` FOREIGN KEY (
	`order_code`
)
REFERENCES `order` (
	`order_code`
);

ALTER TABLE `product-basket` ADD CONSTRAINT `FK_basket_TO_product-basket_1` FOREIGN KEY (
	`customer_code`
)
REFERENCES `basket` (
	`customer_code`
);

ALTER TABLE `product-basket` ADD CONSTRAINT `FK_product_TO_product-basket_1` FOREIGN KEY (
	`product_code`
)
REFERENCES `product` (
	`product_code`
);

ALTER TABLE image ADD CONSTRAINT `FK_image_TO_product_1` FOREIGN KEY (
	`product_code`
)
REFERENCES `product` (
	`product_code`
);

ALTER TABLE product ADD CONSTRAINT `FK_product_TO_category_1` FOREIGN KEY (
	category_code
)
REFERENCES category (
	category_code
);

ALTER TABLE review ADD CONSTRAINT `FK_review_TO_order-basket_1` FOREIGN KEY (
	order_code
)
REFERENCES `order-basket` (
	order_code
);

ALTER TABLE delivery ADD CONSTRAINT `FK_delivery_TO_order_1` FOREIGN KEY (
	order_code
)
REFERENCES `order` (
	order_code
);

ALTER TABLE payment ADD CONSTRAINT `FK_payment_TO_order_1` FOREIGN KEY (
	order_code
)
REFERENCES `order` (
	order_code
);