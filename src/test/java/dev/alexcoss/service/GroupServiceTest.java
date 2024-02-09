package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.model.Group;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GroupService.class, ModelMapper.class})
class GroupServiceTest {
    @MockBean
    private GroupDao groupDao;

    @Autowired
    private GroupService groupService;

    @Test
    public void shouldGetAllGroups() {
        List<Group> groupEntityList = getSampleGroupEntityList();
        when(groupDao.getAllItems()).thenReturn(groupEntityList);

        List<GroupDTO> expectedGroup = getSampleGroupDtoList();
        List<GroupDTO> actualGroups = groupService.getGroups();

        assertEquals(expectedGroup.size(), actualGroups.size());
        assertEquals(expectedGroup.get(0).getName(), actualGroups.get(0).getName());
        verify(groupDao).getAllItems();
    }

    @Test
    public void shouldGetAllGroupsWithStudents() {
        Map<Group, Integer> groupsWithStudents = getSampleGroupsWithStudents();
        when(groupDao.getAllGroupsWithStudents()).thenReturn(groupsWithStudents);

        Map<GroupDTO, Integer> expectedGroupsDtoWithStudents = getSampleGroupsDtoWithStudents();
        Map<GroupDTO, Integer> actualGroupsWithStudents = groupService.getAllGroupsWithStudents();

        assertEquals(expectedGroupsDtoWithStudents.size(), actualGroupsWithStudents.size());
        verify(groupDao).getAllGroupsWithStudents();
    }

    @Test
    public void shouldAddGroups() {
        List<GroupDTO> groupDtoList = getSampleGroupDtoList();
        groupService.addGroups(groupDtoList);

        verify(groupDao, times(1)).addAllItems(getSampleGroupEntityList());
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
        List<GroupDTO> groupListWithNull = Arrays.asList(new GroupDTO(), null);
        groupService.addGroups(groupListWithNull);

        verify(groupDao, never()).addAllItems(anyList());
    }

    private Group getGroup(int id, String name) {
        Group group = new Group();
        group.setId(id);
        group.setName(name);

        return group;
    }

    private List<Group> getSampleGroupEntityList() {
        return Arrays.asList(getGroup(1, "Group1"), getGroup(2, "Group2"));
    }

    private List<GroupDTO> getSampleGroupDtoList() {
        return Arrays.asList(new GroupDTO(1, "Group1"), new GroupDTO(2, "Group2"));
    }

    private Map<Group, Integer> getSampleGroupsWithStudents() {
        Map<Group, Integer> groupsWithStudents = new HashMap<>();
        groupsWithStudents.put(getGroup(1, "Group1"), 10);
        groupsWithStudents.put(getGroup(2, "Group2"), 15);

        return groupsWithStudents;
    }

    private Map<GroupDTO, Integer> getSampleGroupsDtoWithStudents() {
        Map<GroupDTO, Integer> groupsDtoWithStudents = new HashMap<>();
        groupsDtoWithStudents.put(new GroupDTO(1, "Group1"), 10);
        groupsDtoWithStudents.put(new GroupDTO(2, "Group2"), 15);

        return groupsDtoWithStudents;
    }
}