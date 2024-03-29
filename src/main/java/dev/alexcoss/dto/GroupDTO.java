package dev.alexcoss.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupDTO {
    private int id;
    private String name;

    @Override
    public String toString() {
        return String.format("%d Group name: %s", id, name);
    }
}
