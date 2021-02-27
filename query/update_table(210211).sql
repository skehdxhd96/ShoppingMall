alter table delivery add recipient VARCHAR(10) NOT NULL;
alter table delivery modify column deliver_phone VARCHAR(14) NOT NULL;
alter table delivery modify column shipping_address VARCHAR(45) NOT NULL;

drop table review;
drop table order_basket;

alter table `order` modify customer_code int unsigned;
ALTER TABLE `order` ADD CONSTRAINT `order_fk` FOREIGN KEY (
	`customer_code`
)
REFERENCES `customer` (
	`customer_code`
) on delete cascade on update cascade;

create table order_detail (
	order_detail_code int unsigned auto_increment PRIMARY KEY, 
    order_code int unsigned, 
    product_code int unsigned, 
    product_quantity int unsigned);
alter table order_detail modify product_code int unsigned not null;
alter table order_detail modify product_quantity int unsigned not null default 1;
ALTER TABLE order_detail ADD CONSTRAINT order_detail_fk1 FOREIGN KEY (
	order_code
)
REFERENCES `order` (
	order_code
) on delete cascade on update cascade;

create table review (
	review_code int unsigned primary key, 
    order_detail_code int unsigned, 
    review_date timestamp not null default now(), 
    review_comment VARCHAR(500), 
    review_score smallint unsigned not null);
ALTER TABLE review ADD CONSTRAINT review_fk1 FOREIGN KEY (
	order_detail_code
)
REFERENCES order_detail (
	order_detail_code
) on delete cascade on update cascade;

ALTER TABLE delivery DROP foreign key FK_delivery_TO_order_1;
ALTER TABLE delivery ADD CONSTRAINT delivery_fk FOREIGN KEY (
	order_code
)
REFERENCES `order` (
	order_code
) on delete cascade on update cascade;