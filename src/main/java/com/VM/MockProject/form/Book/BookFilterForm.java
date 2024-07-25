package com.VM.MockProject.form.Book;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookFilterForm {
    private String title;
    private String author;
    private String genre;
    private Integer publicationYear;
}
