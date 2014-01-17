
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
	KEY(`billing_on_item_id`),
	CONSTRAINT `ch1_id_fk` FOREIGN KEY(`ch1_id`) REFERENCES `billing_on_cheader1`(`id`),
	CONSTRAINT `billing_on_payment_id_fk` FOREIGN KEY(`billing_on_payment_id`) REFERENCES `billing_on_payment`(`id`),
	CONSTRAINT `billing_on_item_id_fk` FOREIGN KEY(`billing_on_item_id`) REFERENCES `billing_on_item`(`id`)
);

CREATE TABLE `billing_on_transaction` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `ch1_id` int(12) NOT NULL,
  `payment_id` int(12) NOT NULL,
  `billing_on_item_payment_id` int(12) NOT NULL,
  `demographic_no` int(10) NOT NULL DEFAULT '0',
  `update_provider_no` varchar(6) NOT NULL,
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `payment_date` date DEFAULT NULL,
  `ref_num` varchar(6) DEFAULT NULL,
  `province` char(2) DEFAULT 'ON',
  `man_review` char(1) DEFAULT NULL,
  `billing_date` date DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `pay_program` char(3) DEFAULT 'HCP',
  `facility_num` char(4) DEFAULT NULL,
  `clinic` varchar(30) DEFAULT NULL,
  `provider_no` varchar(6) DEFAULT NULL,
  `creator` varchar(30) DEFAULT NULL,
  `visittype` char(2) DEFAULT NULL,
  `admission_date` date DEFAULT NULL,
  `sli_code` varchar(10) DEFAULT NULL,
  `service_code` varchar(10) DEFAULT NULL,
  `service_code_num` char(2) DEFAULT '01',
  `service_code_invoiced` varchar(64) DEFAULT NULL,
  `service_code_paid` decimal(10,2) DEFAULT NULL,
  `service_code_refund` decimal(10,2) DEFAULT NULL,
  `service_code_discount` decimal(10,2) DEFAULT NULL,
  `dx_code` varchar(3) DEFAULT NULL,
  `billing_notes` varchar(255) DEFAULT NULL,
  `action_type` char(1) DEFAULT NULL,
  `payment_typeID` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ch1_id_fk` (`ch1_id`),
  KEY `payment_id_fk` (`payment_id`)
);

CREATE TABLE `billing_on_print` (
	`id` INT(12) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`doc_name` VARCHAR(255)
);

