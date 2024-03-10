package dev.alexcoss.service;

import dev.alexcoss.dao.JPAGroupDao;
import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.model.Group;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService{

    private final JPAGroupDao groupRepository;
    private final ModelMapper modelMapper;

    public List<GroupDTO> getGroups() {
        log.info("Getting all groups from the database");
        List<Group> groups = groupRepository.findAllItems();

        List<GroupDTO> groupDTOList = groups.stream()
            .map(group -> modelMapper.map(group, GroupDTO.class))
            .toList();

        log.info("Retrieved {} groups from the database", groupDTOList.size());
        return groupDTOList;
    }

    public Map<GroupDTO, Integer> getAllGroupsWithStudents() {
        Map<GroupDTO, Integer> groupsWithStudents = groupRepository.findAllGroupsWithStudents()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                entry -> modelMapper.map(entry.getKey(), GroupDTO.class),
                Map.Entry::getValue
            ));

        log.info("Retrieved all groups with students from the database");
        return groupsWithStudents;
    }

    public void addGroups(List<GroupDTO> groupList) {
        if (isValidGroupList(groupList)) {
            List<Group> groups = groupList.stream()
                .map(groupDTO -> modelMapper.map(groupDTO, Group.class))
                .toList();

            groupRepository.saveAllItems(groups);
            log.info("Added {} groups to the database", groups.size());
        }
    }

    private boolean isValidGroupList(List<GroupDTO> groupList) {
        if (groupList == null || groupList.isEmpty()) {
            log.error("Group list is null or empty");
            return false;
        }

        for (GroupDTO group : groupList) {
            if (group == null) {
                log.error("Invalid group in the list");
                return false;
            }
        }
        return true;
    }
}
