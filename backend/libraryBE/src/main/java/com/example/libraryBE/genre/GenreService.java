package com.example.libraryBE.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public void addGenre(Genre genre) {
        Optional<Genre> genreOptional = genreRepository.findGenreByName(genre.getName());
        if (genreOptional.isPresent()) {
            throw new IllegalStateException("genre with this name already exists");
        }
        genreRepository.save(genre);
    }

    public void deleteGenre(Long genreId) {
        boolean exists = genreRepository.existsById(genreId);
        if (!exists) {
            throw new IllegalStateException("genre does not exist");
        }
        genreRepository.deleteById(genreId);
    }

    @Transactional
    public void updateGenre(Long genreId, String name) {
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new IllegalStateException("genre does not exist"));

        if ( (name != null) && (name.length() > 0) ) {
            Optional<Genre> genreOptional = genreRepository.findGenreByName(name);
            if (genreOptional.isPresent()) {
                throw new IllegalStateException("genre with this name already exists");
            }
            genre.setName(name);
        }
    }
}
