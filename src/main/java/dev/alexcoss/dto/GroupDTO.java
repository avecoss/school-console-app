package dev.alexcoss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private int id;
    private String name;

    public GroupDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
