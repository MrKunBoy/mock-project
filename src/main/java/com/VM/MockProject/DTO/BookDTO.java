package com.VM.MockProject.DTO;

import com.VM.MockProject.Entity.Genre;
import com.VM.MockProject.Entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BookDTO extends RepresentationModel<BookDTO> {
    private Integer id;

    private String title;

    private String author;

    private Integer publicationYear;

    private Integer quantity;

    private String description;

    private List<Genre> genres;

    private String urlImage;

    private BigDecimal price;

    private String publisher;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
