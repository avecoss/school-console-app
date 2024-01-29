package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupManager {
    private final GroupDao groupDao;
    private final GroupsGenerator groupsGenerator;

    @Autowired
    public GroupManager(GroupDao groupDao, GroupsGenerator groupsGenerator) {
        this.groupDao = groupDao;
        this.groupsGenerator = groupsGenerator;
    }

    public void generateAndSaveGroupsToDatabase() {
        List<Group> groups = generateGroups();
        saveGroupsToDatabase(groups);
    }

    private List<Group> generateGroups() {
        return groupsGenerator.generateGroupList();
    }

    private void saveGroupsToDatabase(List<Group> groups) {
        groupDao.addAllItems(groups);
    }
}
