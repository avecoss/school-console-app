package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupService extends AbstractService {

    private GroupDao groupRepository;
    private GroupMapper groupMapper;

    @Autowired
    public GroupService(GroupDao groupRepository, GroupMapper groupMapper) {
        super(GroupService.class.getName());
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public List<GroupDTO> getGroups() {
        return groupMapper.mapToDTOList(groupRepository.getAllItems());
    }

    public Map<GroupDTO, Integer> getAllGroupsWithStudents() {
        return groupRepository.getAllGroupsWithStudents()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                entry -> groupMapper.mapToDTO(entry.getKey()),
                Map.Entry::getValue
            ));
    }

    public void addGroups(List<GroupDTO> groupList) {
        if (isValidGroupList(groupList)) {
            groupRepository.addAllItems(groupMapper.mapToEntityList(groupList));
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
