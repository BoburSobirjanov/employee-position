create extension if not exists "uuid-ossp";
       create table positions(
                                 id uuid default uuid_generate_v4() primary key ,
                                 name varchar(255) not null unique ,
                                 createdTime timestamp default CURRENT_TIMESTAMP,
                                 updatedTime timestamp default CURRENT_TIMESTAMP,
                                 isDeleted boolean default false,
                                 deletedBy uuid,
                                 deletedTime timestamp,
                                 createdBy uuid
       )