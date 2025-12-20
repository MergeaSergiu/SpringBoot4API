package dev.spring.API.model;

import jakarta.persistence.*;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDate;

//public record Group(
//        Long id,
//        String name,
//        String description,
//        String city,
//        String organizer,
//        LocalDate createdDate
//) {
//}


@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    String description;
    String city;
    String organizer;
    LocalDate createdDate;

 public Group(String name, String description, String city, String organizer, LocalDate createdDate) {
     this.name = name;
     this.description = description;
     this.city = city;
     this.organizer = organizer;
     this.createdDate = createdDate;
 }


    public Group() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}
