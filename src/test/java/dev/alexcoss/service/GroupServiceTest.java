package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GroupService.class})
class GroupServiceTest {
    @MockBean
    private GroupDao groupDao;

    @Autowired
    private GroupService groupService;

    @Test
    public void shouldGetAllGroups() {
        List<Group> expectedGroup = getSampleGroupList();
        when(groupDao.getAllItems()).thenReturn(expectedGroup);

        List<Group> actualGroups = groupService.getGroups();

        assertEquals(expectedGroup, actualGroups);
        verify(groupDao).getAllItems();
    }

    @Test
    public void shouldGetAllGroupsWithStudents() {
        Map<Group, Integer> expectedGroupsWithStudents = getSampleGroupsWithStudents();
        when(groupDao.getAllGroupsWithStudents()).thenReturn(expectedGroupsWithStudents);

        Map<Group, Integer> actualGroupsWithStudents = groupService.getAllGroupsWithStudents();

        assertEquals(expectedGroupsWithStudents, actualGroupsWithStudents);
        verify(groupDao).getAllGroupsWithStudents();
    }

    @Test
    public void shouldAddGroups() {
        List<Group> groupList = getSampleGroupList();
        groupService.addGroups(groupList);

        verify(groupDao, times(1)).addAllItems(groupList);
    }

    @Test
    public void shouldNotAddGroupsWhenListIsNull() {
        groupService.addGroups(null);

        verify(groupDao, never()).addAllItems(anyList());
    }

    @Test
    public void shouldNotAddGroupsWhenListIsEmpty() {
        groupService.addGroups(Collections.emptyList());

        verify(groupDao, never()).addAllItems(anyList());
    }

    @Test
    public void shouldNotAddGroupsWhenListContainsNull() {
        List<Group> groupListWithNull = Arrays.asList(new Group(), null);
        groupService.addGroups(groupListWithNull);

        verify(groupDao, never()).addAllItems(groupListWithNull);
    }

    private Group getGroup(int id, String name) {
        Group group = new Group();
        group.setId(id);
        group.setName(name);

        return group;
    }

    private List<Group> getSampleGroupList() {
        return Arrays.asList(getGroup(1, "Group1"), getGroup(2, "Group2"));
    }

    private Map<Group, Integer> getSampleGroupsWithStudents() {
        Map<Group, Integer> groupsWithStudents = new HashMap<>();
        groupsWithStudents.put(getGroup(1, "Group1"), 10);
        groupsWithStudents.put(getGroup(2, "Group2"), 15);

        return groupsWithStudents;
    }
}