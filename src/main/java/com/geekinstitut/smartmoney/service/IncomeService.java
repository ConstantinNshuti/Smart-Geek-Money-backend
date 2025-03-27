package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.dto.TransactionRequestDTO;
import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.entity.Category;
import com.geekinstitut.smartmoney.entity.Income;
import com.geekinstitut.smartmoney.enums.CategoryType;
import com.geekinstitut.smartmoney.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryService categoryService;

    public List<TransactionResponseDTO> getAllIncomes() {
        return incomeRepository.findAll().stream().map(Income::toResponse).toList();
    }

    public TransactionResponseDTO getIncomeById(UUID id) {
        return incomeRepository.findById(id).map(Income::toResponse).orElseThrow(
                () -> new RuntimeException("Income not found")
        );
    }

    public TransactionResponseDTO createIncome(TransactionRequestDTO requestDTO) {
        Category category = categoryService.getCategoryById(requestDTO.getCategoryId());

        if (category.getType() != CategoryType.INCOME) {
            throw new RuntimeException("Category must be of type INCOME");
        }

        Income income = new Income();
        income.setCategory(category);
        income.setAmount(requestDTO.getAmount());
        income.setDate(requestDTO.getDate());
        income.setNote(requestDTO.getNote());

        return incomeRepository.save(income).toResponse();
    }

    public TransactionResponseDTO updateIncome(UUID id, TransactionRequestDTO requestDTO) {
        return incomeRepository.findById(id)
                .map(income -> {
                    Category category = categoryService.getCategoryById(requestDTO.getCategoryId());

                    if (category.getType() != CategoryType.INCOME) {
                        throw new RuntimeException("Category must be of type INCOME");
                    }

                    income.setCategory(category);
                    income.setAmount(requestDTO.getAmount());
                    income.setDate(requestDTO.getDate());
                    income.setNote(requestDTO.getNote());

                    return incomeRepository.save(income).toResponse();
                })
                .orElseThrow(() -> new RuntimeException("Income not found"));
    }

    public void deleteIncome(UUID id) {
        incomeRepository.deleteById(id);
    }
}