package dev.alexcoss.mapper;

import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public GroupMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupDTO mapToDTO(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }

    public Group mapToEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    public List<GroupDTO> mapToDTOList(List<Group> groupList) {
        return groupList.stream()
            .map(group -> modelMapper.map(group, GroupDTO.class))
            .collect(Collectors.toList());
    }

    public List<Group> mapToEntityList(List<GroupDTO> groupDTOList) {
        return groupDTOList.stream()
            .map(groupDTO -> modelMapper.map(groupDTO, Group.class))
            .collect(Collectors.toList());
    }
}
