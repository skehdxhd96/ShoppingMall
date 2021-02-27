use shop;

/*column rename*/
ALTER TABLE customer CHANGE customer_id social_id VARCHAR(20);
ALTER TABLE customer CHANGE social social_type VARCHAR(20);

/*Create table - social_detail */
CREATE TABLE social_detail (
	customer_code INT UNSIGNED, 
	accessToken VARCHAR(260), 
    refreshToken VARCHAR(260), 
    expiredDate TIMESTAMP
);

ALTER TABLE social_detail
ADD CONSTRAINT social_detail_pk
PRIMARY KEY (customer_code);

ALTER TABLE social_detail
ADD CONSTRAINT social_detail_fk
FOREIGN KEY (customer_code)
REFERENCES `customer`(customer_code);