package com.example.backend.modules.books.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select * from books", nativeQuery = true)
    List<Book> getAll();
}
