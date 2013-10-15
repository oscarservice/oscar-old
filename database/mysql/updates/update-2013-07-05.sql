INSERT INTO `secRole` VALUES(NULL,'lab','lab');
INSERT INTO `provider` VALUES('9123456','lab','lab','lab','00',NULL,'M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'999998','2013-07-03',NULL);
INSERT INTO `security` VALUES(NULL,'lab','10065-66120453-36124-82-12510361118354-65-1275337','9123456','1234',0,NULL,1,1);
INSERT INTO `secUserRole` VALUES(NULL,'9123456','lab','R0000001',1);
INSERT INTO `secObjectName` VALUES('admin_lab_config', 'lab setup privilege', 0);
INSERT INTO `secObjPrivilege` VALUES('admin','admin_lab_config','x',0,'999998');
INSERT INTO `secObjPrivilege` VALUES('lab','admin_lab_config','x',0,'999998');
