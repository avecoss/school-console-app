package dev.alexcoss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private final Integer defaultInteger = -1;

    private int id;
    private String firstName;
    private String lastName;
    private Integer groupId = defaultInteger;


    public StudentDTO(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
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
