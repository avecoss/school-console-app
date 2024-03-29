package dev.alexcoss.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private int id;
    private String firstName;
    private String lastName;
    private GroupDTO group;

    @Override
    public String toString() {
        return String.format("\n%s %s Group: %s", firstName, lastName, group);
    }
}
