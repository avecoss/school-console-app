package dev.alexcoss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private int id;
    private String name;
    private String description = "default description";

    public CourseDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("\n%s Description: %s", name, description);
    }
}
