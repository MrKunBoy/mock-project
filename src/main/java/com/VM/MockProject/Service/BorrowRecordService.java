package com.VM.MockProject.Service;

import com.VM.MockProject.Entity.Book;
import com.VM.MockProject.Entity.BorrowRecords;
import com.VM.MockProject.Entity.User;
import com.VM.MockProject.Repository.IBorrowRecordsRepository;
import com.VM.MockProject.Service.Interface.IBorrowRecordService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowRecordService implements IBorrowRecordService {
    @Autowired
    private IBorrowRecordsRepository borrowRecordRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Transactional
    public BorrowRecords borrowBook(Integer userId, Integer bookId) {
        User user = userService.getUserByID(userId);
        Book book = bookService.findBookById(bookId);

        if (user != null && book != null && book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            BorrowRecords borrowRecord = new BorrowRecords();
            borrowRecord.setUser(user);
            borrowRecord.setBook(book);
            borrowRecord.setBorrowDate(LocalDate.from(LocalDateTime.now()));
            borrowRecord.setCreatedAt(LocalDateTime.now());
            borrowRecord.setUpdatedAt(LocalDateTime.now());
            return borrowRecordRepository.save(borrowRecord);
        }
        return null;
    }

    public List<BorrowRecords> findBorrowRecordsByUser(Integer userId) {
        User user = userService.getUserByID(userId);
        if (user != null) {
            return borrowRecordRepository.findByUser(user);
        }
        return null;
    }

    public BorrowRecords returnBook(Integer borrowId) {
        BorrowRecords borrowRecord = borrowRecordRepository.findById(borrowId).orElse(null);
        if (borrowRecord != null && borrowRecord.getReturnDate() == null) {
            borrowRecord.setReturnDate(LocalDate.from(LocalDateTime.now()));
            borrowRecord.setUpdatedAt(LocalDateTime.now());
            Book book = borrowRecord.getBook();
            book.setQuantity(book.getQuantity() + 1);
            return borrowRecordRepository.save(borrowRecord);
        }
        return null;
    }
}
