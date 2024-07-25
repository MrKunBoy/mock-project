package com.VM.MockProject.Controller;

import com.VM.MockProject.Entity.BorrowRecords;
import com.VM.MockProject.Service.Interface.IBorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrow-records")
public class BorrowRecordController {

    @Autowired
    private IBorrowRecordService borrowRecordService;

    @PostMapping("/borrow")
    public BorrowRecords borrowBook(@RequestParam Integer userId, @RequestParam Integer bookId) {
        return borrowRecordService.borrowBook(userId, bookId);
    }

    @PostMapping("/return")
    public BorrowRecords returnBook(@RequestParam Integer borrowId) {
        return borrowRecordService.returnBook(borrowId);
    }

    @GetMapping("/user/{userId}")
    public List<BorrowRecords> getBorrowRecordsByUser(@PathVariable Integer userId) {
        return borrowRecordService.findBorrowRecordsByUser(userId);
    }
}
