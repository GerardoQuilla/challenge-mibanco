package com.jgerardo.challenge.expense.domain.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Long id) {
        super("Expense with id " + id + " not found");
    }
}
