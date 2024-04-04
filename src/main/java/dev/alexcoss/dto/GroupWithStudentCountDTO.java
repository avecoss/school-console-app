package dev.alexcoss.dto;

import dev.alexcoss.model.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupWithStudentCountDTO {
    private Group group;
    private int studentCount;
}
