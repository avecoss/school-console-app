package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.model.Group;

import java.util.List;

public class GroupManager {
    private final GroupDao groupDao;
    private final GroupsGenerator groupsGenerator;

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
