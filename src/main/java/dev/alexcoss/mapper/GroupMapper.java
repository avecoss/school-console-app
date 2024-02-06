package dev.alexcoss.mapper;

import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.model.Group;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDTO mapToDTO(Group group);

    Group mapToEntity(GroupDTO groupDTO);

    List<GroupDTO> mapToDTOList(List<Group> groupList);

    List<Group> mapToEntityList(List<GroupDTO> groupDTOList);
}
