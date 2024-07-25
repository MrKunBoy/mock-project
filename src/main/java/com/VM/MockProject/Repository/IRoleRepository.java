package com.VM.MockProject.Repository;
import com.VM.MockProject.Entity.Role;
import com.VM.MockProject.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    // Các phương thức truy vấn tùy chỉnh
    Role findByName(RoleName name);
}
