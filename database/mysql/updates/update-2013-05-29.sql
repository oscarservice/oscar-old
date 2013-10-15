CREATE TABLE  `site_role_mpg` (
  `site_id` int(10) unsigned NOT NULL,
  `access_role_id` int(10) unsigned default NULL,
  `crt_dt` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `admit_discharge_role_id` int(10) unsigned default NULL
);