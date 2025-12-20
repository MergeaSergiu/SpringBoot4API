package dev.spring.API.service;

import dev.spring.API.Dto.GroupRequest;
import dev.spring.API.model.Group;
import dev.spring.API.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.isPresent() ? group : null;
    }

    public void deleteById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        group.ifPresent(groupRepository::delete);
    }

    public Long createGroup(GroupRequest groupRequest) {
        Group group = new Group();
        group.setName(groupRequest.name());
        group.setDescription(groupRequest.description());
        group.setOrganizer(groupRequest.organizer());
        group.setCity(groupRequest.city());
        group.setCreatedDate(LocalDate.now());
        return groupRepository.save(group).getId();
    }
}
