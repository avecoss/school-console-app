package dev.alexcoss.service;

import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.dto.GroupWithStudentCountDTO;
import dev.alexcoss.model.Group;
import dev.alexcoss.repository.GroupRepository;
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
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    @Test
    public void shouldGetAllGroups() {
        List<Group> groupEntityList = getSampleGroupEntityList();
        when(groupRepository.findAll()).thenReturn(groupEntityList);

        List<GroupDTO> expectedGroup = getSampleGroupDtoList();
        List<GroupDTO> actualGroups = groupService.getGroups();

        assertEquals(expectedGroup.size(), actualGroups.size());
        assertEquals(expectedGroup.get(0).getName(), actualGroups.get(0).getName());
        verify(groupRepository).findAll();
    }

    @Test
    public void shouldGetAllGroupsWithStudents() {
        List<GroupWithStudentCountDTO> groupsWithStudents = getSampleGroupsWithStudents();
        when(groupRepository.findAllGroupsWithStudents()).thenReturn(groupsWithStudents);

        Map<GroupDTO, Integer> expectedGroupsDtoWithStudents = getSampleGroupsDtoWithStudents();
        Map<GroupDTO, Integer> actualGroupsWithStudents = groupService.getAllGroupsWithStudents();

        assertEquals(expectedGroupsDtoWithStudents.size(), actualGroupsWithStudents.size());
        verify(groupRepository).findAllGroupsWithStudents();
    }

    @Test
    public void shouldAddGroups() {
        List<GroupDTO> groupDtoList = getSampleGroupDtoList();
        groupService.addGroups(groupDtoList);

        verify(groupRepository, times(1)).saveAllAndFlush(getSampleGroupEntityList());
    }

    @Test
    public void shouldNotAddGroupsWhenListIsNull() {
        groupService.addGroups(null);

        verify(groupRepository, never()).saveAllAndFlush(anyList());
    }

    @Test
    public void shouldNotAddGroupsWhenListIsEmpty() {
        groupService.addGroups(Collections.emptyList());

        verify(groupRepository, never()).saveAllAndFlush(anyList());
    }

    @Test
    public void shouldNotAddGroupsWhenListContainsNull() {
        List<GroupDTO> groupListWithNull = Arrays.asList(new GroupDTO(), null);
        groupService.addGroups(groupListWithNull);

        verify(groupRepository, never()).saveAllAndFlush(anyList());
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

    private List<GroupWithStudentCountDTO> getSampleGroupsWithStudents() {
        List<GroupWithStudentCountDTO> groupsWithStudents = new ArrayList<>();
        groupsWithStudents.add(GroupWithStudentCountDTO.builder().group(getGroup(1, "Group1")).studentCount(10).build());
        groupsWithStudents.add(GroupWithStudentCountDTO.builder().group(getGroup(2, "Group2")).studentCount(15).build());

        return groupsWithStudents;
    }

    private Map<GroupDTO, Integer> getSampleGroupsDtoWithStudents() {
        Map<GroupDTO, Integer> groupsDtoWithStudents = new HashMap<>();
        groupsDtoWithStudents.put(new GroupDTO(1, "Group1"), 10);
        groupsDtoWithStudents.put(new GroupDTO(2, "Group2"), 15);

        return groupsDtoWithStudents;
    }
}