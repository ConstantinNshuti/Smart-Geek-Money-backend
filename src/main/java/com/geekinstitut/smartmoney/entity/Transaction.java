package com.geekinstitut.smartmoney.entity;

import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.enums.CategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Double amount;

    private String note;

    @Column(nullable = false)
    private LocalDate date;

    /**
     * Wandelt eine Transaktion in ein Response DTO um.
     */

    public TransactionResponseDTO toResponse(){
        return new TransactionResponseDTO(
                this.category.getId(),
                this.category.getName(),
                this.amount,
                this.note,
                this.date
        );
    }

}