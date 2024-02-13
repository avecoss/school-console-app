package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupService extends AbstractService {

    private GroupDao groupRepository;
    private ModelMapper modelMapper;

    public GroupService(GroupDao groupRepository, ModelMapper modelMapper) {
        super(GroupService.class.getName());
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    public List<GroupDTO> getGroups() {
        List<Group> groups = groupRepository.getAllItems();
        return groups.stream()
            .map(group -> modelMapper.map(group, GroupDTO.class))
            .toList();
    }

    public Map<GroupDTO, Integer> getAllGroupsWithStudents() {
        return groupRepository.getAllGroupsWithStudents()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                entry -> modelMapper.map(entry.getKey(), GroupDTO.class),
                Map.Entry::getValue
            ));
    }

    public void addGroups(List<GroupDTO> groupList) {
        if (isValidGroupList(groupList)) {
            List<Group> groups = groupList.stream()
                .map(groupDTO -> modelMapper.map(groupDTO, Group.class))
                .toList();

            groupRepository.addAllItems(groups);
        }
    }

    private boolean isValidGroupList(List<GroupDTO> groupList) {
        if (groupList == null || groupList.isEmpty()) {
            handleServiceException("Group list is null or empty");
            return false;
        }

        for (GroupDTO group : groupList) {
            if (group == null) {
                handleServiceException("Invalid group in the list");
                return false;
            }
        }
        return true;
    }
}
