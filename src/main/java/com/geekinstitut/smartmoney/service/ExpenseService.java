package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.dto.TransactionRequestDTO;
import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.entity.Category;
import com.geekinstitut.smartmoney.entity.Expense;
import com.geekinstitut.smartmoney.enums.CategoryType;
import com.geekinstitut.smartmoney.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;

    public List<TransactionResponseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(Expense::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO getExpenseById(UUID id) {
        return expenseRepository.findById(id).map(Expense::toResponse).orElseThrow(
                () -> new RuntimeException("Expense not found")
        );
    }

    public TransactionResponseDTO createExpense(TransactionRequestDTO requestDTO) {
        Category category = categoryService.getCategoryById(requestDTO.getCategoryId());

        if (category.getType() != CategoryType.EXPENSE) {
            throw new RuntimeException("Category must be of type EXPENSE");
        }

        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAmount(requestDTO.getAmount());
        expense.setDate(requestDTO.getDate());
        expense.setNote(requestDTO.getNote());

        return expenseRepository.save(expense).toResponse();
    }

    public TransactionResponseDTO updateExpense(UUID id, TransactionRequestDTO requestDTO) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    Category category = categoryService.getCategoryById(requestDTO.getCategoryId());

                    if (category.getType() != CategoryType.EXPENSE) {
                        throw new RuntimeException("Category must be of type EXPENSE");
                    }

                    expense.setCategory(category);
                    expense.setAmount(requestDTO.getAmount());
                    expense.setDate(requestDTO.getDate());
                    expense.setNote(requestDTO.getNote());

                    return expenseRepository.save(expense).toResponse();
                })
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public void deleteExpense(UUID id) {
        expenseRepository.deleteById(id);
    }
}