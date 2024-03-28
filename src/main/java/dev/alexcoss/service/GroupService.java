package dev.alexcoss.service;

import dev.alexcoss.dao.JPAGroupDao;
import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.model.Group;
import dev.alexcoss.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService{

    private final JPAGroupDao jpaGroupDao;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public List<GroupDTO> getGroups() {
        List<Group> groups = jpaGroupDao.findAllItems();

        return groups.stream()
            .map(group -> modelMapper.map(group, GroupDTO.class))
            .toList();
    }

    public Map<GroupDTO, Integer> getAllGroupsWithStudents() {
        return jpaGroupDao.findAllGroupsWithStudents()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                entry -> modelMapper.map(entry.getKey(), GroupDTO.class),
                Map.Entry::getValue
            ));
    }

    @Transactional
    public void addGroups(List<GroupDTO> groupList) {
        if (isValidGroupList(groupList)) {
            List<Group> groups = groupList.stream()
                .map(groupDTO -> modelMapper.map(groupDTO, Group.class))
                .toList();

            groupRepository.saveAllAndFlush(groups);
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
