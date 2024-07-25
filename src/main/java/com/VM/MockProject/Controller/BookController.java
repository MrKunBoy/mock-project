package com.VM.MockProject.Controller;

import com.VM.MockProject.Controller.Admin.UserControllerAdmin;
import com.VM.MockProject.Controller.User.UserController;
import com.VM.MockProject.DTO.BookDTO;
import com.VM.MockProject.DTO.UserDTO;
import com.VM.MockProject.Entity.Book;

import com.VM.MockProject.Entity.Genre;
import com.VM.MockProject.Entity.Role;
import com.VM.MockProject.Entity.User;
import com.VM.MockProject.Service.Interface.IBookService;
import com.VM.MockProject.Service.Interface.IGenreService;
import com.VM.MockProject.form.Book.BookFilterForm;
import com.VM.MockProject.form.Book.CreateBookForm;
import com.VM.MockProject.form.MessageResponse;
import com.VM.MockProject.form.User.Admin.CreatingUserForAdminForm;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="/api/v1/books")
@Validated
@CrossOrigin
public class BookController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private IGenreService genreService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<?> addbook(@Valid @RequestBody CreateBookForm form) {

        try {
            Book bookDB = bookService.addBook(form);
            // Convert
            BookDTO bookDto = new BookDTO();
            bookDto.setId(bookDB.getId());
            bookDto.setTitle(bookDB.getTitle());
            bookDto.setAuthor(bookDB.getAuthor().getName());
            bookDto.setDescription(bookDB.getDescription());
            bookDto.setPublicationYear(bookDB.getPublicationYear());
            bookDto.setPrice(bookDB.getPrice());
            bookDto.setQuantity(bookDB.getQuantity());
            bookDto.setUrlImage(bookDB.getUrlImage());
            bookDto.setPublisher(bookDB.getPublisher().getName());
            bookDto.setGenres(bookDB.getGenres().stream().map(genre -> {
                Genre genre1 = new Genre();
                genre1.setId(genre.getId());
                genre1.setName(genre.getName());
                return genre1;
            }).collect(Collectors.toList()));

            bookDto.setCreatedAt(bookDB.getCreatedAt());
            bookDto.add(linkTo(methodOn(BookController.class).getBookById(bookDB.getId())).withSelfRel());
            return ResponseEntity.ok(bookDto);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new MessageResponse("Error while adding book: " + e.getMessage()));
        }

    }

    @PutMapping("/{bookId}") // Đã thêm {bookId} ở đây
    public ResponseEntity<Book> updateBook(@PathVariable Integer bookId, @RequestBody Book BookDetails) {
        Optional<Book> updatedBook = bookService.updateBook(bookId, BookDetails);
        return updatedBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deletedBook(@PathVariable Integer bookId){
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<BookDTO> getAllBooks(Pageable pageable, @RequestParam(value = "search", required = false) String search, BookFilterForm filterForm){
        Page<Book> entityPages = bookService.getAllBooks(pageable, search, filterForm);
        // convert entities --> dtos
        List<BookDTO> bookListDto = new ArrayList<>();
        for (Book bookDB : entityPages) {
            BookDTO bookDto = new BookDTO();
            bookDto.setId(bookDB.getId());
            bookDto.setTitle(bookDB.getTitle());
            bookDto.setAuthor(bookDB.getAuthor().getName());
            bookDto.setPublicationYear(bookDB.getPublicationYear());
            bookDto.setQuantity(bookDB.getQuantity());
            bookDto.setDescription(bookDB.getDescription());
            bookDto.setPrice(bookDB.getPrice());
            bookDto.setPublisher(bookDB.getPublisher().getName());
            bookDto.setCreatedAt(bookDB.getCreatedAt());
            bookDto.setUpdatedAt(bookDB.getUpdatedAt());
            bookDto.setUrlImage(bookDB.getUrlImage());

            bookDto.setGenres(bookDB.getGenres().stream().map(genre->{
                Genre genre1 = new Genre();
                genre1.setId(genre.getId());
                genre1.setName(genre.getName());
                return genre1;
            }).collect(Collectors.toList()));
            bookDto.add(linkTo(methodOn(BookController.class).getAllBooks(pageable, search, filterForm)).withSelfRel());

            bookListDto.add(bookDto);
        }

        List<BookDTO> dtos = modelMapper.map(bookListDto, new TypeToken<List<BookDTO>>() {}.getType());

        Page<BookDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return dtoPages;

    }

    // Endpoint tìm kiếm sách theo các tiêu chí
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer publicationYear) {
        return ResponseEntity.ok(bookService.searchBooks(title, author, genre, publicationYear));
    }
    // Endpoint lấy thông tin chi tiết sách
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Integer bookId) {

        Book bookDB = bookService.getBookById(bookId);
        // Convert
        BookDTO bookDto = new BookDTO();
        bookDto.setId(bookDB.getId());
        bookDto.setTitle(bookDB.getTitle());
        bookDto.setAuthor(bookDB.getAuthor().getName());
        bookDto.setDescription(bookDB.getDescription());
        bookDto.setPublicationYear(bookDB.getPublicationYear());
        bookDto.setPrice(bookDB.getPrice());
        bookDto.setQuantity(bookDB.getQuantity());
        bookDto.setUrlImage(bookDB.getUrlImage());
        bookDto.setPublisher(bookDB.getPublisher().getName());
        bookDto.setGenres(bookDB.getGenres().stream().map(genre -> {
            Genre genre1 = new Genre();
            genre1.setId(genre.getId());
            genre1.setName(genre.getName());
            return genre1;
        }).collect(Collectors.toList()));

        bookDto.setCreatedAt(bookDB.getCreatedAt());
        bookDto.setUpdatedAt(bookDB.getUpdatedAt());
        bookDto.add(linkTo(methodOn(BookController.class).getBookById(bookId)).withSelfRel());
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping("/{authorId}/add")
    public Book addBookToAuthor(@PathVariable Integer authorId, @RequestBody Book book) {
        return bookService.addBookToAuthor(authorId, book);
    }

    @GetMapping("/author/{authorId}")
    public List<Book> getBooksByAuthorId(@PathVariable Integer authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    @PostMapping("/{bookId}/genres/{genreId}")
    public Book addGenreToBook(@PathVariable Integer bookId, @PathVariable Integer genreId) {
        return bookService.addGenreToBook(bookId, genreId);
    }
}
