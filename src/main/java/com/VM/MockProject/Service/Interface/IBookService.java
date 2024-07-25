package com.VM.MockProject.Service.Interface;

import com.VM.MockProject.Entity.Book;
import com.VM.MockProject.form.Book.BookFilterForm;
import com.VM.MockProject.form.Book.CreateBookForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Book addBook(CreateBookForm book);

    Optional<Book> updateBook(Integer bookId, Book bookDetails);

    void deleteBook(Integer bookId);

    Page<Book> getAllBooks(Pageable pageable, String search, BookFilterForm filterForm);

    List<Book> searchBooks(String title, String author, String genre, Integer publicationYear);

    // Thêm phương thức lấy thông tin chi tiết sách
    Book getBookById(Integer bookId);

    Book addBookToAuthor(Integer authorId, Book book);

    List<Book> getBooksByAuthorId(Integer authorId);

    Book addGenreToBook(Integer bookId, Integer genreId);
}

