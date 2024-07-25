package com.VM.MockProject.Controller;

import com.VM.MockProject.Entity.Author;
import com.VM.MockProject.Service.Interface.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1/authors")
@Validated
@CrossOrigin
public class AuthorController {
    @Autowired
    private IAuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getBookById(@PathVariable Integer authorId) {
        Author author = authorService.getAuthorById(authorId);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<?> deletedAuthor(@PathVariable Integer authorId){
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        Author save = authorService.addAuthor(author);
        return ResponseEntity.ok(save);
    }
}
