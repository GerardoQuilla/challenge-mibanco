package com.jgerardo.challenge.expense.domain.model.commands;

import java.time.LocalDate;

public record UpdateExpenseCommand(
        Long id,
        String title,
        String reason,
        LocalDate date,
        Double amount
) {
}
