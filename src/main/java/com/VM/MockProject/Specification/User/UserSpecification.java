package com.VM.MockProject.Specification.User;

import com.VM.MockProject.Entity.User;
import com.VM.MockProject.form.User.Admin.UserFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


public class UserSpecification {
    @SuppressWarnings("deprecation")
    public static Specification<User> buildWhere(String search, UserFilterForm filterForm) {

        // Khởi tạo where
        Specification<User> where = null;

        // where default (1 = 1)
//        CustomSpecification init = new CustomSpecification("init", "init");
        CustomSpecification init = new CustomSpecification("init", "init");
        where = Specification.where(init);

        // Search theo theo username & fullname & email
        if (!StringUtils.isEmpty(search)) {
            search = search.trim();
            CustomSpecification username = new CustomSpecification("username", search);
            CustomSpecification fullName = new CustomSpecification("fullName", search);
            CustomSpecification email = new CustomSpecification("email", search);
            where = where.and((username).or(fullName).or(email));
        }

        // Filter
        if (filterForm == null) {
            return where;
        }
        // Filter theo role hoặc department
        // role
        if (filterForm.getRole() != null) {
            CustomSpecification role = new CustomSpecification("role", filterForm.getRole());
            where = where.and(role);
        }

        return where;
    }

    @SuppressWarnings("serial")
    @RequiredArgsConstructor
    static class CustomSpecification implements Specification<User> {

        @NonNull
        private String field;
        @NonNull
        private Object value;

        @Override
        public Predicate toPredicate(
                Root<User> root,
                CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {

            if (field.equalsIgnoreCase("init")) {
                return criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
            }

            if (field.equalsIgnoreCase("username")) {
                return criteriaBuilder.like(root.get("username"), "%" + value.toString() + "%");
            }

            if (field.equalsIgnoreCase("email")) {
                return criteriaBuilder.like(root.get("email"), "%" + value.toString() + "%");
            }

            if (field.equalsIgnoreCase("fullName")) {
                return criteriaBuilder.like(root.get("fullName"), "%" + value.toString() + "%");
            }

            if (field.equalsIgnoreCase("roles")) {
                return criteriaBuilder.equal(root.get("roles").get("id"), value);
            }
            return null;
        }
    }
}
