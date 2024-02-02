package dev.alexcoss.service.generator;

import dev.alexcoss.model.Group;
import dev.alexcoss.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupManager {
    private final GroupService groupService;
    private final GroupsGenerator groupsGenerator;

    @Autowired
    public GroupManager(GroupService groupService, GroupsGenerator groupsGenerator) {
        this.groupService = groupService;
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
        groupService.addGroups(groups);
    }
}
