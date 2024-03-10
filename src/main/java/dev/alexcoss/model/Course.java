package dev.alexcoss.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"description", "students"})
@ToString(exclude = "students")
@Builder
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "course_name")
    private String name;

    @Column(name = "course_description")
    private String description;

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "students_courses",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    public void addStudentToCourse(Student student) {
        students.add(student);
        student.getCourses().add(this);
    }

    public void removeStudentFromCourse(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }
}
