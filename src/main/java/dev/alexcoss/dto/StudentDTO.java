package dev.alexcoss.dto;

import java.util.Objects;

public class StudentDTO {
    private final Integer defaultInteger = -1;

    private int id;
    private String firstName;
    private String lastName;
    private Integer groupId = defaultInteger;

    public StudentDTO() {
    }

    public StudentDTO(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StudentDTO(int id, String firstName, String lastName, Integer groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = Objects.requireNonNullElse(groupId, defaultInteger);
    }

    @Override
    public String toString() {
        return String.format("\n%s %s groupId:%d", firstName, lastName, groupId);
    }
}
