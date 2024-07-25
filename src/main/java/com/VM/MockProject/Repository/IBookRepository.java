package com.VM.MockProject.Repository;

import com.VM.MockProject.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    // Các phương thức truy vấn tùy chỉnh
    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    List<Book> findAllByAuthorId(Integer authorId);
}
