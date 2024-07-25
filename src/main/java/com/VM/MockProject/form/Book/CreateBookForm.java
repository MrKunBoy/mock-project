package com.VM.MockProject.form.Book;

import com.VM.MockProject.validation.author.AuthorIDExists;
import com.VM.MockProject.validation.publisher.PublisherIDExists;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateBookForm {
    @NotBlank(message = "{Book.createBook.form.title.NotBlank}")
    private String title;

    @NotNull(message = "{Book.createBook.form.authorId.NotBlank}")
    @Positive(message = "Number must be a positive integer")
    @AuthorIDExists
    private Integer authorId;

    @NotEmpty(message = "{Book.createBook.form.genreIds.NotBlank}")
    private List<Integer> genreIds;

    @NotNull(message = "{Book.createBook.form.publicationYear.NotBlank}")
    @Positive(message = "Number must be a positive integer")
    private Integer publicationYear;

    @NotNull(message = "{Book.createBook.form.quantity.NotBlank}")
    @Positive(message = "Number must be a positive integer")
    private Integer quantity;

    @NotBlank(message = "{Book.createBook.form.description.NotBlank}")
    private String description;

    @NotBlank(message = "{Book.createBook.form.urlImage.NotBlank}")
    private String urlImage;

    @NotNull(message = "{Book.createBook.form.price.NotBlank}")
    @PositiveOrZero(message = "Number must be a positive integer")
    private BigDecimal price;

    @NotNull(message = "{Book.createBook.form.publisherId.NotBlank}")
    @Positive(message = "Number must be a positive integer")
    @PublisherIDExists
    private Integer publisherId;

    private LocalDateTime createAt = LocalDateTime.now();
}
