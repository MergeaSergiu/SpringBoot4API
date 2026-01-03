package dev.spring.API.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "cart")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(
            mappedBy = "cart",
            orphanRemoval = true
    )
    private List<CartItem> items = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate createdDate;

    @PrePersist
    public void onCreate(){
        this.createdDate = LocalDate.now();
    }

//    public void addItem(CartItem item){
//        this.items.add(item);
//        item.setCart(this);
//    }
//
//    public void removeItem(CartItem item){
//        this.items.remove(item);
//        item.setCart(null);
//    }

    public Cart(String username, List<CartItem> items, LocalDate createdDate) {
        this.username = username;
        this.items = items;
        this.createdDate = createdDate;
    }

    public Cart(){}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
