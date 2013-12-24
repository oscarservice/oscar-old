alter table `billing_on_payment` add column `total_refund` decimal(10,2);
alter table `billing_on_payment` add column `total_discount` decimal(10,2);
alter table `billing_on_payment` change column `payment` `total_payment` decimal(10,2) NOT NULL;

alter table `billing_on_item` add column `paid` decimal(10,2) NOT NULL;
alter table `billing_on_item` add column `refund` decimal(10,2) NOT NULL;
alter table `billing_on_item` add column `discount` decimal(10,2) NOT NULL;
alter table `billing_on_item` add column `payment_typeID` int(2) NOT NULL;

ALTER TABLE billing_on_transaction CHANGE COLUMN billing_on_item_id  billing_on_item_payment_id INT(12) NOT NULL;

ALTER TABLE billing_on_transaction DROP COLUMN paymentType;