package com.VM.MockProject.Service.Interface;

import com.VM.MockProject.Entity.BorrowRecords;

import java.util.List;

public interface IBorrowRecordService {

    BorrowRecords borrowBook(Integer userId, Integer bookId);
    List<BorrowRecords> findBorrowRecordsByUser(Integer userId);
    BorrowRecords returnBook(Integer borrowId);
}
