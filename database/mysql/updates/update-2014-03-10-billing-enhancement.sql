ALTER TABLE `billing_on_transaction` ADD COLUMN `service_code_credit` DECIMAL(10, 2);
ALTER TABLE `billing_on_payment` ADD COLUMN `total_credit` DECIMAL(10, 2);
ALTER TABLE `billing_on_item_payment` ADD COLUMN `credit` DECIMAL(10, 2);