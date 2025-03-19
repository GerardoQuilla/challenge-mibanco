package com.jgerardo.challenge.expense.domain.model.aggregates;

import com.jgerardo.challenge.expense.domain.model.commands.CreateExpenseCommand;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;


@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String reason;
    private LocalDate date;
    private Double amount;

    public Expense(CreateExpenseCommand command) {
        this.title=command.title();
        this.reason=command.reason();
        this.date=command.date();
        this.amount=command.amount();
    }

    public Expense() {
        this.title="";
        this.reason="";
        this.date=LocalDate.now();
        this.amount=0.0;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReason() {
        return reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
