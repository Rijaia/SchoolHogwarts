-- liquibase formatted sql

-- changeset nzakharova:1
CREATE INDEX student_name ON Student (name);

-- changeset nzakharova:2
CREATE INDEX faculty_name_and_color ON Faculty (name,color);
