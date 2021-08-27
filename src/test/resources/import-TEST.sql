DELETE FROM MVM_MOVEMENT;
DELETE FROM MVM_MOVEMENT_GROUP;

-- MovementGroup
INSERT INTO MVM_MOVEMENT_GROUP (c_pk,c_created,c_ol,c_pid,c_user,c_name,c_state) VALUES (1000,now(),0,'1000','tester','Batch 1','INACTIVE');

-- Movements
INSERT INTO MVM_MOVEMENT (c_pk,c_created,c_ol,c_pid,c_latest_due,c_mode,c_priority,c_start_earliest_date,c_target_location,c_target_location_group_name,c_transport_unit_bk,c_type,c_source_location,c_state,c_source_location_group_name,c_group_pk) VALUES (1000,now(),0,'1000',now(),'MANUAL','NORMAL',now(),'WA_01','WA','4711','INBOUND','HRL.10.20.2.0','INACTIVE','STOCK',1000);
INSERT INTO MVM_MOVEMENT (c_pk,c_created,c_ol,c_pid,c_latest_due,c_mode,c_priority,c_start_earliest_date,c_target_location,c_target_location_group_name,c_transport_unit_bk,c_type,c_source_location,c_state,c_source_location_group_name,c_group_pk) VALUES (1001,now(),0,'1001',now(),'AUTOMATIC','HIGH',now(),'WA_02','WA','4712','OUTBOUND','HRL.10.21.2.0','ACTIVE','STOCK',1000);
INSERT INTO MVM_MOVEMENT (c_pk,c_created,c_ol,c_pid,c_latest_due,c_mode,c_priority,c_start_earliest_date,c_target_location,c_target_location_group_name,c_transport_unit_bk,c_type,c_source_location,c_state,c_source_location_group_name,c_group_pk) VALUES (1002,now(),0,'1002',now(),'AUTOMATIC','HIGH',now(),'WA_02','WA','4713','OUTBOUND','HRL.10.22.2.0','DONE','STOCK',1000);
