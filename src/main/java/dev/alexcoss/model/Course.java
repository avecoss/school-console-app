package dev.alexcoss.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Course {

    private int id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return String.format("\n%d %s Description: %s", id, name, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
