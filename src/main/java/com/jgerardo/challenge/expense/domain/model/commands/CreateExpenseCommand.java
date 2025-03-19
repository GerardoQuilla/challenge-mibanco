package com.jgerardo.challenge.expense.domain.model.commands;

import java.time.LocalDate;

public record CreateExpenseCommand(
        String title,
        String reason,
        LocalDate date,
        Double amount
) {
}
