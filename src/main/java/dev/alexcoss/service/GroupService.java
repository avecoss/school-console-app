package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GroupService extends AbstractService {

    private GroupDao groupRepository;

    @Autowired
    public GroupService(GroupDao groupRepository) {
        super(GroupService.class.getName());
        this.groupRepository = groupRepository;
    }

    public List<Group> getGroups() {
        try {
            return groupRepository.getAllItems();
        } catch (DataAccessException e) {
            handleServiceException(e, "Error getting groups from database");
            return Collections.emptyList();
        }
    }

    public Map<Group, Integer> getAllGroupsWithStudents() {
        try {
            return groupRepository.getAllGroupsWithStudents();
        } catch (DataAccessException e) {
            handleServiceException(e, "Error getting all groups with students from database");
            return Collections.emptyMap();
        }
    }

    public void addGroups(List<Group> groupList) {
        if (isValidGroupList(groupList)) {
            try {
                groupRepository.addAllItems(groupList);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error adding groups to database");
            }
        }
    }

    private boolean isValidGroupList(List<Group> groupList) {
        if (groupList == null || groupList.isEmpty()) {
            handleServiceException("Group list is null or empty");
            return false;
        }

        for (Group group : groupList) {
            if (group == null) {
                handleServiceException("Invalid group in the list");
                return false;
            }
        }
        return true;
    }
}
