package com.VM.MockProject.Specification.Book;

import com.VM.MockProject.Entity.Book;
import com.VM.MockProject.form.Book.BookFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;


public class BookSpecification {
    @SuppressWarnings("deprecation")
    public static Specification<Book> buildWhere(String search, BookFilterForm filterForm) {

        // Khởi tạo where
        Specification<Book> where = null;

        CustomSpecification init = new CustomSpecification("init", "init");
        where = Specification.where(init);

        if (!StringUtils.isEmpty(search)) {
            search = search.trim();
            CustomSpecification title = new CustomSpecification("title", search);
            CustomSpecification author = new CustomSpecification("author", search);
            CustomSpecification publicationYear = new CustomSpecification("publicationYear", search);
            where = where.and((title).or(author).or(publicationYear));
        }

        // Filter
        if (filterForm == null) {
            return where;
        }
        // Filter theo role hoặc department
        // role
        if (filterForm.getGenre() != null) {
            CustomSpecification genre = new CustomSpecification("genre", filterForm.getGenre());
            where = where.and(genre);
        }

        return where;
    }

    public static Specification<Book> getBooksBySpecification(String title, String author, String genre, Integer publicationYear) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate predicate = builder.conjunction();

            if (title != null && !title.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("title"), "%" + title + "%"));
            }
            if (author != null && !author.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("author"), "%" + author + "%"));
            }
            if (genre != null && !genre.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("genre"), "%" + genre + "%"));
            }
            if (publicationYear != null) {
                predicate = builder.and(predicate, builder.equal(root.get("publicationYear"), publicationYear));
            }

            return predicate;
        };
    }

    @SuppressWarnings("serial")
    @RequiredArgsConstructor
    static class CustomSpecification implements Specification<Book> {

        @NonNull
        private String field;
        @NonNull
        private Object value;

        @Override
        public Predicate toPredicate(
                Root<Book> root,
                CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {

            if (field.equalsIgnoreCase("init")) {
                return criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
            }

            if (field.equalsIgnoreCase("title")) {
                return criteriaBuilder.like(root.get("title"), "%" + value.toString() + "%");
            }

            if (field.equalsIgnoreCase("author")) {
                return criteriaBuilder.like(root.get("author"), "%" + value.toString() + "%");
            }

            if (field.equalsIgnoreCase("publicationYear")) {
                return criteriaBuilder.like(root.get("publicationYear"), "%" + value.toString() + "%");
            }

            if (field.equalsIgnoreCase("genres")) {
                return criteriaBuilder.equal(root.get("genres").get("id"), value);
            }
            return null;
        }
    }
}
