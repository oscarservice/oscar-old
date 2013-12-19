ALTER TABLE `formivftracksheet` ADD COLUMN `amh` VARCHAR(6) AFTER `tsh`;
ALTER TABLE `formivftracksheet` ADD COLUMN `age` VARCHAR(20) AFTER `dob`;
ALTER TABLE `formivftracksheet` ADD COLUMN `pgb` VARCHAR(20) AFTER `date18`;
ALTER TABLE `formivftracksheet` ADD COLUMN `mml` VARCHAR(20) AFTER `puregon18`;
ALTER TABLE `formivftracksheet` ADD COLUMN `osc` VARCHAR(20) AFTER `menopur18`;

ALTER TABLE `formivftracksheet` drop COLUMN puregon ;
ALTER TABLE `formivftracksheet` drop COLUMN gonal ;
ALTER TABLE `formivftracksheet` drop COLUMN menopur ;
ALTER TABLE `formivftracksheet` drop COLUMN mhcg ;
ALTER TABLE `formivftracksheet` drop COLUMN luver ;
ALTER TABLE `formivftracksheet` drop COLUMN orgalutran ;
ALTER TABLE `formivftracksheet` drop COLUMN suprefact ;