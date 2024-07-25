package com.VM.MockProject.Service.Interface;

import com.VM.MockProject.Entity.Author;

import java.util.List;

public interface IAuthorService {
    Author addAuthor(Author author);

    void deleteAuthor(Integer AuthorId);

    List<Author> getAllAuthors();

    // Thêm phương thức lấy thông tin chi tiết
    Author getAuthorById(Integer AuthorId);

    public boolean isAuthorExistsByID(Integer id);
}
