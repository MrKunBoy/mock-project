package com.VM.MockProject.Service.Interface;

import com.VM.MockProject.Entity.Genre;

import java.util.List;

public interface IGenreService {
    Genre addGenre(Genre genre);

    void deleteGenre(Integer genreId);

    List<Genre> getAllGenres();

    // Thêm phương thức lấy thông tin chi tiết
    Genre getGenreById(Integer GenreId);
}
