CREATE TABLE `zeiss_oru_result` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `pid` int(10) NOT NULL,
  `placer_order_number` varchar(16) NOT NULL,
  `instrument` text,
  `study_date` datetime,
  `content_date` datetime,
  `doc_title` text,
  `img_type` text,
  `laterality` varchar(10),
  `study_uid` text,
  `instance_uid` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `placer_order_number` (`placer_order_number`)
);