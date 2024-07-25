package com.VM.MockProject.Service;

import com.VM.MockProject.Entity.*;

import com.VM.MockProject.Repository.IAuthorRepository;
import com.VM.MockProject.Repository.IBookRepository;
import com.VM.MockProject.Repository.IGenreRepository;
import com.VM.MockProject.Repository.IPublisherRepository;
import com.VM.MockProject.Service.Interface.IBookService;
import com.VM.MockProject.Specification.Book.BookSpecification;
import com.VM.MockProject.form.Book.BookFilterForm;
import com.VM.MockProject.form.Book.CreateBookForm;
import com.VM.MockProject.form.User.Admin.CreatingUserForAdminForm;
import com.VM.MockProject.utils.UniqueRandomStringGenerator;
import com.VM.MockProject.utils.UsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookService implements IBookService {
    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IPublisherRepository publisherRepository;

    @Autowired
    private IGenreRepository genreRepository;

    @Override
    public Book addBook(CreateBookForm form) {
        Book book = new Book();

        book.setTitle(form.getTitle());
        book.setDescription(form.getDescription());
        book.setPublicationYear(form.getPublicationYear());
        book.setPrice(form.getPrice());
        book.setQuantity(form.getQuantity());
        book.setUrlImage(form.getUrlImage());
        book.setAuthor(authorRepository.findById(form.getAuthorId())
               .orElseThrow(() -> new IllegalArgumentException("Invalid author ID: " + form.getAuthorId())));
        book.setPublisher(publisherRepository.findById(form.getPublisherId())
               .orElseThrow(() -> new IllegalArgumentException("Invalid publisher ID: " + form.getPublisherId())));


        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : form.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + genreId));
            genres.add(genre);
        }
        book.setGenres(genres);
        book.setCreatedAt(form.getCreateAt());
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> updateBook(Integer bookId, Book bookDetails) {
        return bookRepository.findById(bookId).map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setDescription(bookDetails.getDescription());
            book.setPublicationYear(bookDetails.getPublicationYear());
            return bookRepository.save(book);
        });
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable, String search, BookFilterForm filterForm) {
        Specification<Book> where = BookSpecification.buildWhere(search, filterForm);
        return bookRepository.findAll(where, pageable);

    }

    @Override
    public List<Book> searchBooks(String title, String author, String genre, Integer publicationYear) {
        return bookRepository.findAll(BookSpecification.getBooksBySpecification(title, author, genre, publicationYear));
    }
    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).get();
    }

    // ******************************Hải****************************************************
    public Book findBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public Book addBookToAuthor(Integer authorId, Book book) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public List<Book> getBooksByAuthorId(Integer authorId) {
        return bookRepository.findAllByAuthorId(authorId);
    }

    public Book addGenreToBook(Integer bookId, Integer genreId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        book.getGenres().add(genre);
        return bookRepository.save(book);
    }
}
