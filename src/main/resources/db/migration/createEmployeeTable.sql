create extension if not exists "uuid-ossp";
create table employees(
                          id uuid default uuid_generate_v4() primary key ,
                          createdTime timestamp default CURRENT_TIMESTAMP,
                          updatedTime timestamp default CURRENT_TIMESTAMP,
                          isDeleted boolean default false,
                          deletedBy uuid,
                          deletedTime timestamp,
                          createdBy uuid,
                          fullNameUz varchar(255) not null,
                          fullNameRu varchar(255) not null,
                          fullNameEng varchar(255) not null,
                          email varchar(255) not null unique ,
                          password varchar(255) not null,
                          age int not null ,
                          phoneNumber varchar(255) not null unique ,
                          position_id uuid,
                          role varchar(255),
                          foreign key (position_id) references positions(id)
)