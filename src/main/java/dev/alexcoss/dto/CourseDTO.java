package dev.alexcoss.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private int id;
    private String name;
    private String description = "default description";

    @Override
    public String toString() {
        return String.format("\n%s Description: %s", name, description);
    }
}
