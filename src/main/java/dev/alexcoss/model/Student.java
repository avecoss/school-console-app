package dev.alexcoss.model;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private Integer groupId;

    public void setGroupId(Integer groupId) {
        this.groupId = (groupId != null && groupId != -1) ? groupId : null;
    }

    @Override
    public String toString() {
        return String.format("\n%d %s %s groupId:%d", id, firstName, lastName, groupId);
    }
}
