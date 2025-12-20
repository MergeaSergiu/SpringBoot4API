package dev.spring.API.controller;

import dev.spring.API.Dto.GroupRequest;
import dev.spring.API.model.Group;
import dev.spring.API.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Group>> getGroupById(@PathVariable Long id) {
       return ResponseEntity.ok(groupService.getById(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteGroupById(@PathVariable Long id) {
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Long> createGroup(@RequestBody GroupRequest groupRequest) {
        Long id = groupService.createGroup(groupRequest);
        return ResponseEntity.ok(id);
    }
}
