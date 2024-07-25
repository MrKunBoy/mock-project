package com.VM.MockProject.Repository;
import com.VM.MockProject.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface INotificationRepository  extends JpaRepository<Notification, Integer>, JpaSpecificationExecutor<Notification> {
    // Các phương thức truy vấn tùy chỉnh
}
