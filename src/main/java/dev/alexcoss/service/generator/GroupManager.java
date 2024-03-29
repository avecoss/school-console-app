package dev.alexcoss.service.generator;

import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GroupManager {
    private final GroupService groupService;
    private final GroupsGenerator groupsGenerator;

    public void generateAndSaveGroupsToDatabase() {
        List<GroupDTO> groups = groupsGenerator.generateGroupList();
        groupService.addGroups(groups);
    }
}
