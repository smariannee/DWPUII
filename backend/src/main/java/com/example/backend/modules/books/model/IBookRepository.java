package com.example.backend.modules.books.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select * from books", nativeQuery = true)
    Page<Book> getAllPageable(Pageable pageable);

}
