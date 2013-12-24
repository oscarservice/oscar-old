DROP table billing_on_transaction;
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
  `paymentType` char(1) DEFAULT 'P',
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
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=latin1



CREATE TABLE `billing_on_item_payment` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `ch1_id` int(12) NOT NULL,
  `billing_on_payment_id` int(12) NOT NULL,
  `billing_on_item_id` int(12) DEFAULT NULL,
  `payment_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `paid` decimal(10,2) DEFAULT NULL,
  `refund` decimal(10,2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ch1_id` (`ch1_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1


DROP table billing_on_payment;
CREATE TABLE `billing_on_payment` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `ch1_id` int(12) NOT NULL,
  `creator` varchar(30) DEFAULT NULL,
  `total_payment` decimal(10,2) NOT NULL,
  `paymentTypeId` int(12) DEFAULT '1',
  `paymentDate` date NOT NULL,
  `total_refund` decimal(10,2) DEFAULT '0.00',
  `total_discount` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `ch1_id` (`ch1_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1
