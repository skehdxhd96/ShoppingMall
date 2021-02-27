alter table order_basket drop foreign key order_basket_fk3;
alter table order_basket drop foreign key FK_basket_TO_order_basket_1;
alter table order_basket drop foreign key order_basket_fk2;
alter table social_detail drop foreign key social_detail_fk;

alter table order_basket add constraint order_basket_fk3 foreign key (customer_code) references basket (customer_code) on delete cascade on update cascade;
alter table order_basket add constraint order_basket_fk2 foreign key (product_code) references basket (product_code) on delete cascade on update cascade;
alter table social_detail add constraint social_detail_fk foreign key (customer_code) references customer (customer_code) on delete cascade on update cascade;