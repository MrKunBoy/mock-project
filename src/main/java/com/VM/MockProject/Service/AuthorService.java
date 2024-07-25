package com.VM.MockProject.Service;

import com.VM.MockProject.Entity.Author;
import com.VM.MockProject.Repository.IAuthorRepository;
import com.VM.MockProject.Service.Interface.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private IAuthorRepository authorRepository;
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public boolean isAuthorExistsByID(Integer id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Integer authorId) {
        authorRepository.deleteById(authorId);
    }



}
