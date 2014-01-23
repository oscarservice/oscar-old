CREATE TABLE `billing_on_item_payment`(
	`id` INT(12) NOT NULL PRIMARY KEY,
	`ch1_id` INT(12) NOT NULL,
	`billing_on_payment_id` INT(12) NOT NULL,
	`billing_on_item_id` INT(12) NOT NULL,
	`payment_timestamp` TIMESTAMP,
	`paid` DECIMAL(10,2) NOT NULL,
	`refund` DECIMAL(10,2) NOT NULL,
	`discount` DECIMAL(10,2) NOT NULL,
	KEY(`ch1_id`),
	KEY(`billing_on_payment_id`),
	KEY(`billing_on_item_id`)
);

CREATE TABLE `billing_on_transaction` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `ch1_id` int(12) NOT NULL,
  `payment_id` int(12) NOT NULL,
  `billing_on_item_payment_id` int(12) NOT NULL,
  `demographic_no` int(10) NOT NULL,
  `update_provider_no` varchar(6) NOT NULL,
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `payment_date` date,
  `ref_num` varchar(6),
  `province` char(2),
  `man_review` char(1),
  `billing_date` date,
  `status` char(1),
  `pay_program` char(3),
  `facility_num` char(4),
  `clinic` varchar(30),
  `provider_no` varchar(6),
  `creator` varchar(30),
  `visittype` char(2),
  `admission_date` date,
  `sli_code` varchar(10),
  `service_code` varchar(10),
  `service_code_num` char(2),
  `service_code_invoiced` varchar(64),
  `service_code_paid` decimal(10,2),
  `service_code_refund` decimal(10,2),
  `service_code_discount` decimal(10,2),
  `dx_code` varchar(3),
  `billing_notes` varchar(255),
  `action_type` char(1),
  `payment_type_id` int(2),
  PRIMARY KEY (`id`),
  KEY `ch1_id` (`ch1_id`),
  KEY `payment_id` (`payment_id`),
  KEY `service_code` (`service_code`),
  KEY `dx_code` (`dx_code`),
  KEY `payment_type_id` (`payment_type_id`),
  KEY `demographic_no`(`demographic_no`),
  KEY `provider_no`(`provider_no`),
  KEY `creator`(`creator`),
  KEY `pay_program`(`pay_program`)
);

CREATE UNIQUE INDEX `payment_type` ON billing_payment_type(payment_type);
alter table billing_on_cheader1 modify total decimal(10,2);
alter table billing_on_cheader1 modify paid decimal(10,2);
alter table billing_on_payment modify paymentTypeId int(2);
alter table billing_on_payment add column creator varchar(30);
alter table billing_on_payment change payment total_payment decimal(10,2) not null;
alter table billing_on_payment add column total_discount decimal(10,2) not null;
alter table billing_on_payment add column total_refund decimal(10,2) not null;
