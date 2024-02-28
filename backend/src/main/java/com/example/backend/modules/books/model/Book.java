package com.example.backend.modules.books.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String autor;
    private Long releaseDate;
    @Column(columnDefinition = "TEXT")
    private String img;

    public void save (BookDto dto){
        this.name = dto.getName();
        this.autor = dto.getAutor();
        this.releaseDate = dto.getReleaseDate();
        this.img = dto.getImg();
    }
}
