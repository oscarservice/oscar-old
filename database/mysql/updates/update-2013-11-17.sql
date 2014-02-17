ALTER TABLE `formAudiologyReport` ADD COLUMN `rr_plan_counselled_tinnit` TINYINT(1) UNSIGNED AFTER `connecting_lines`;
ALTER TABLE `formAudiologyReport` ADD COLUMN `test_done_text` VARCHAR(50) AFTER `rr_plan_counselled_tinnit`;

ALTER TABLE `formAudiologyReport` MODIFY COLUMN `speech_rec_1_list1` VARCHAR(10) DEFAULT NULL,
 MODIFY COLUMN `speech_rec_1_list2` VARCHAR(10) DEFAULT NULL,
 MODIFY COLUMN `speech_rec_2_list1` VARCHAR(10) DEFAULT NULL,
 MODIFY COLUMN `speech_rec_2_list2` VARCHAR(10) DEFAULT NULL;

ALTER TABLE `formAudiologyReport` ADD COLUMN `ph_otosc_rt_clear` TINYINT(1) UNSIGNED AFTER `test_done_text`,
 ADD COLUMN `ph_otosc_rt_non_occ_wax` TINYINT(1) UNSIGNED AFTER `ph_otosc_rt_clear`,
 ADD COLUMN `ph_otosc_rt_occ_wax` TINYINT(1) UNSIGNED AFTER `ph_otosc_rt_non_occ_wax`,
 ADD COLUMN `ph_otosc_lt_clear` TINYINT(1) UNSIGNED AFTER `ph_otosc_rt_occ_wax`,
 ADD COLUMN `ph_otosc_lt_non_occ_wax` TINYINT(1) UNSIGNED AFTER `ph_otosc_lt_clear`,
 ADD COLUMN `ph_otosc_lt_occ_wax` TINYINT(1) UNSIGNED AFTER `ph_otosc_lt_non_occ_wax`;
