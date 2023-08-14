SELECT s.name, s.age, f.name
FROM students s LEFT JOIN faculty f on s.faculty_id = f.id;
SELECT s.name, s.age
FROM student s INNER JOIN avatar a on s.id = a.student_id;