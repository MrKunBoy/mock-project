package com.VM.MockProject.Repository;
import com.VM.MockProject.Entity.BorrowRecords;
import com.VM.MockProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBorrowRecordsRepository extends JpaRepository<BorrowRecords, Integer>, JpaSpecificationExecutor<BorrowRecords> {
    List<BorrowRecords> findByUser(User user);
}
