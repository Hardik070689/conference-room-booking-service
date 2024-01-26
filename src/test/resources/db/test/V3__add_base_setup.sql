INSERT INTO rooms(id,room_name , room_capacity , created_on , updated_on  )
values ( 1, 'Amaze' , 3  ,  GETDATE() , GETDATE());
INSERT INTO rooms(id,room_name , room_capacity , created_on , updated_on  )
values ( 2, 'Beauty' , 7  ,  GETDATE() , GETDATE());
INSERT INTO rooms(id,room_name , room_capacity , created_on , updated_on  )
values ( 3, 'Inspire' , 12  ,  GETDATE() , GETDATE());
INSERT INTO rooms(id,room_name , room_capacity , created_on , updated_on  )
values ( 4, 'Strive' , 20  ,  GETDATE() , GETDATE());

INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 1, 1 , PARSEDATETIME('09:00', 'HH:mm'),PARSEDATETIME('09:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 2, 1 , PARSEDATETIME('13:00', 'HH:mm'),PARSEDATETIME('13:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 3, 1 , PARSEDATETIME('17:00', 'HH:mm'),PARSEDATETIME('17:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 4, 2 , PARSEDATETIME('09:00', 'HH:mm'),PARSEDATETIME('09:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 5, 2 , PARSEDATETIME('13:00', 'HH:mm'),PARSEDATETIME('13:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 6, 2 , PARSEDATETIME('17:00', 'HH:mm'),PARSEDATETIME('17:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 7, 3 , PARSEDATETIME('09:00', 'HH:mm'),PARSEDATETIME('09:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 8, 3 , PARSEDATETIME('13:00', 'HH:mm'),PARSEDATETIME('13:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 9, 3 , PARSEDATETIME('17:00', 'HH:mm'),PARSEDATETIME('17:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 10, 4 , PARSEDATETIME('09:00', 'HH:mm'),PARSEDATETIME('09:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 11, 4 , PARSEDATETIME('13:00', 'HH:mm'),PARSEDATETIME('13:15', 'HH:mm')  ,  GETDATE() , GETDATE());
INSERT INTO rooms_maintenance_schedules(id,fk_room_id , time_start , time_end ,created_on, updated_on  )
values ( 12, 4 , PARSEDATETIME('17:00', 'HH:mm'),PARSEDATETIME('17:15', 'HH:mm')  ,  GETDATE() , GETDATE());
