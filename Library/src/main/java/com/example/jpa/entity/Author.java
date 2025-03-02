package com.example.jpa.entity;

import jakarta.persistence.*;

@Entity
public class Author {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "biography_id", referencedColumnName = "id")
    private Biography biography;

    public Author() {}

    public Author(String name, Biography biography) {
        this.name = name;
        this.biography = biography;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

}
