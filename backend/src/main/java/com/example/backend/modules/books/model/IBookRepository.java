package com.example.backend.modules.books.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select * from books", nativeQuery = true)
    List<Book> getAll();
    @Query(value = "select * from books order by autor asc", nativeQuery = true)
    List<Book> getAllOrderedByAutor();
    @Query(value = "select * from books order by release_date asc", nativeQuery = true)
    List<Book> getAllOrderedByReleaseDate();
    @Query(value = "SELECT b FROM Book b WHERE b.img IS NOT NULL")
    List<Book> getAllWithImage();
}
