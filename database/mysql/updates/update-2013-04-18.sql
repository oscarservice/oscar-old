alter table appointment_status modify status char(2) binary not null;
alter table appointment modify status char(2) binary not null;

alter table site add siteLogoId int(11);

update consultationRequests set specId=null where specId=0;
