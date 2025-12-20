package dev.spring.API.controller;

import dev.spring.API.model.Group;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private List<Group> groups = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public GroupController() {
        groups.add(
                new Group(
                        idCounter.getAndIncrement(),
                        "Tech Innovators",
                        "A group for people passionate about cutting-edge software development.",
                        "San Francisco",
                        "Alice Johnson",
                        LocalDate.of(2023, 3, 15)
                )
        );

        groups.add(
                new Group(
                        idCounter.getAndIncrement(),
                        "Hiking Explorers",
                        "Outdoor lovers who explore mountains every weekend.",
                        "Denver",
                        "Mark Brown",
                        LocalDate.of(2022, 11, 2)
                )
        );

        groups.add(
                new Group(
                        idCounter.getAndIncrement(),
                        "Book Lovers Club",
                        "A group for reading and discussing literature.",
                        "New York",
                        "Sophia Miller",
                        LocalDate.of(2024, 1, 12)
                )
        );

        groups.add(
                new Group(
                        idCounter.getAndIncrement(),
                        "Cycling Community",
                        "Cyclists sharing routes and planning events.",
                        "Amsterdam",
                        "Lucas van Dijk",
                        LocalDate.of(2023, 8, 20)
                )
        );

        groups.add(
                new Group(
                        idCounter.getAndIncrement(),
                        "Photography Tribe",
                        "Photographers who meet to share tips and do photo walks.",
                        "Paris",
                        "Emma Laurent",
                        LocalDate.of(2022, 6, 5)
                )
        );


    }

    @GetMapping("/")
    List<Group> getAllGroups() {
        return groups;
    }

    @GetMapping("/{id}")
    Optional<Group> getGroupById(@PathVariable Long id) {
     return groups.stream()
             .filter(g -> g.id().equals(id))
             .findFirst();
    }

    @DeleteMapping("/{id}")
    void deleteGroupById(@PathVariable Long id) {
        Optional<Group> group = getGroupById(id);
        if (group.isPresent()) {
            groups.remove(group.get());
        }else{
            System.out.println("Group not found");
        }
    }
}
