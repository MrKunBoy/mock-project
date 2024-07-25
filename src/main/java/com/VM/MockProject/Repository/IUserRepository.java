package com.VM.MockProject.Repository;

import com.VM.MockProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Query("FROM User WHERE id IN(:ids)")
    List<User> getListUserByListId(@Param("ids") List<Integer> ids);

//    @Query("SELECT COUNT(User.userId) FROM User WHERE userId IN(:ids)")
    @Query("SELECT COUNT(u.id) FROM User u WHERE u.id IN(:ids)")
    int getCountIdForDelete(@Param("ids") List<Integer> ids);
//    Page<User> getListUserByRoleId(Pageable pageable, Integer roleId);
}
