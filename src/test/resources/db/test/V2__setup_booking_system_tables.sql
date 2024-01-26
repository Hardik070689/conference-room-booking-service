CREATE TABLE rooms (
  id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
  room_name varchar(255),
  room_capacity SMALLINT,
  created_on timestamp NOT NULL,
  updated_on timestamp NOT NULL,
  booking_time_factor SMALLINT,
  minimum_booking_time SMALLINT,
  booking_enabled bit
);
CREATE TABLE rooms_maintenance_schedules (
  id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
  fk_room_id NUMERIC,
  time_start TIME,
  time_end TIME,
  created_on timestamp NOT NULL,
  updated_on timestamp NOT NULL
);
CREATE TABLE rooms_booking_history (
  id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
  fk_room_id NUMERIC,
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  created_on timestamp NOT NULL,
  updated_on timestamp NOT NULL
);


