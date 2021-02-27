alter table review drop foreign key review_fk;

alter table review add constraint review_fk foreign key (order_code, product_code, customer_code) 
	references order_basket (order_code, product_code, customer_code) 
    on delete cascade on update cascade;
    
alter table `order` modify order_status VARCHAR(15);