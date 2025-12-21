package dev.spring.API.service;

import dev.spring.API.Dto.GroupRequest;
import dev.spring.API.model.Group;
import dev.spring.API.repository.GroupRepository;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.client.ExpectedCount;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class) //Enables Mockito annotations
public class GroupServiceTest {

    @Mock //Creates a mock object automatically without calling mock(Class.class) manually.
    private GroupRepository groupRepository;

    @InjectMocks //Creates an instance of the class under test and automatically injects @Mock objects into it.
    private GroupService groupService;

    Group group1;
    Group group2;
    Group group3;

    @BeforeEach
    void init() {
        group1 = new Group();
        group1.setId(1L);
        group1.setName("Group A");
        group1.setDescription("Alpha");
        group1.setCity("Timisoara");
        group1.setOrganizer("Sergiu");
        group1.setCreatedDate(LocalDate.of(2025, 1, 1));

        group2 = new Group();
        group2.setId(2L);
        group2.setName("Group B");
        group2.setDescription("Beta");
        group2.setCity("Cluj");
        group2.setOrganizer("Alex");
        group2.setCreatedDate(LocalDate.of(2025, 1, 1));

        group3 = new Group();
        group3.setId(3L);
        group3.setName("Group C");
        group3.setDescription("Gamma");
        group3.setCity("Bucuresti");
        group3.setOrganizer("Maria");
        group3.setCreatedDate(LocalDate.of(2025, 1, 1));
    }

    @Test
    void testGetAllGroups() {
        when(groupRepository.findAll()).thenReturn(List.of(group1, group2, group3));

        List<Group> result = groupService.getAllGroups();

        assertEquals(3, result.size());
        assertEquals("Group A", result.getFirst().getName());
        verify(groupRepository).findAll();
    }

    @Test
    void testGetGroupById() {
        Long testId = 1L;
        when(groupRepository.findById(testId)).thenReturn(Optional.of(group1));

        Optional<Group> result = groupService.getById(group1.getId());
        assertEquals(group1, result.get());
        assertEquals(group1.getName(), result.get().getName());
        assertEquals(group1.getDescription(), result.get().getDescription());
        verify(groupRepository).findById(testId);
    }

    @Test
    void testNotGetGroupById() {
        Long testId = 4L;

        when(groupRepository.findById(testId)).thenReturn(Optional.empty());

        Optional<Group> result = groupService.getById(testId);
        assertEquals(Optional.empty(), result);
        verify(groupRepository).findById(testId);

    }

    @Test
    void testDeleteGroupById() {
        Long testId = 2L;
        when(groupRepository.findById(testId)).thenReturn(Optional.of(group2));
        groupService.deleteById(group2.getId());
        verify(groupRepository).delete(group2);
        verify(groupRepository).findById(testId);
    }


//@Test
//    void testSaveGroup(){
//    GroupRequest GroupRequest = new GroupRequest(
//            "Test Group",
//            "Description Group",
//            "City Group",
//            "Organizer Group"
//    );
//
//    when(groupRepository.save(new Group())).thenAnswer(invocation -> {
//        Group g = invocation.getArgument(0, Group.class);
//        g.setId(100L); // simulate DB ID assignment
//        return g;
//    });
//
//    Long id = groupService.createGroup(GroupRequest);
//    assertEquals(100L, id);
//    //verify(groupRepository).save(group);
//    verify(groupRepository).findByName("Test Group");
//
//}


}


