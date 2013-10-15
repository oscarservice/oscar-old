ALTER TABLE `formAudiologyReport` ADD COLUMN `info_consert_obtained` TINYINT UNSIGNED AFTER `l125v120`;

ALTER TABLE `formAudiologyReport` ADD COLUMN `speech_rec_1_srt1` VARCHAR(4) AFTER `info_consert_obtained`,
ADD COLUMN `speech_rec_1_srt2` VARCHAR(4) AFTER `speech_rec_1_srt1`;

ALTER TABLE `formAudiologyReport` ADD COLUMN `speech_rec_2_srt1` VARCHAR(4) AFTER `speech_rec_1_srt2`,
ADD COLUMN `speech_rec_2_srt2` VARCHAR(4) AFTER `speech_rec_2_srt1`;

ALTER TABLE `formAudiologyReport` ADD COLUMN `reflect_decay_dnt` TINYINT(1) UNSIGNED AFTER `speech_rec_2_srt2`;

ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_500_contra_left` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_500_ipsi_right` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_500_contra_right` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_500_ipsi_left` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_1000_contra_left` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_1000_ipsi_right` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_1000_contra_right` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_1000_ipsi_left` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_2000_contra_left` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_2000_ipsi_right` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_2000_contra_right` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
ALTER TABLE `formAudiologyReport` MODIFY COLUMN `stape_acou_2000_ipsi_left` VARCHAR(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ALTER TABLE `formAudiologyReport` ADD COLUMN `connecting_lines` VARCHAR(500) AFTER `reflect_decay_dnt`;
