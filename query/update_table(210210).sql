alter table delivery modify delivery_status VARCHAR(15);

alter table order_basket drop foreign key order_basket_fk1;

alter table order_basket add constraint order_basket_fk1 foreign key (order_code) 
	references `order` (order_code) 
    on delete cascade on update cascade;
    
alter table delivery modify column deliver_phone VARCHAR(14);
alter table delivery modify column shipping_address VARCHAR(45);