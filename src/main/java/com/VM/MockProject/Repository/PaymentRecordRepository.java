package com.VM.MockProject.Repository;

import com.VM.MockProject.Entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Integer> {
}
