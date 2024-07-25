package com.VM.MockProject.Service;

import com.VM.MockProject.Entity.Genre;
import com.VM.MockProject.Repository.IGenreRepository;
import com.VM.MockProject.Service.Interface.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService implements IGenreService {
    @Autowired
    private IGenreRepository genreRepository;

    @Override
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Integer genreId) {
        genreRepository.deleteById(genreId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Genre not found"));
    }
}
