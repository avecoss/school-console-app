package dev.alexcoss.model;

import java.util.Objects;

public class Student {

    private final Integer defaultInteger = -1;

    private int id;
    private String firstName;
    private String lastName;
    private Integer groupId = defaultInteger;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = Objects.requireNonNullElse(groupId, defaultInteger);
    }

    public Integer getDefaultInteger() {
        return defaultInteger;
    }

    @Override
    public String toString() {
        return String.format("\n%d %s %s groupId:%d", id, firstName, lastName, groupId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(groupId, student.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, groupId);
    }
}
