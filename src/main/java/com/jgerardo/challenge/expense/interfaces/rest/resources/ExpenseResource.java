package com.jgerardo.challenge.expense.interfaces.rest.resources;

import java.time.LocalDate;

public record ExpenseResource(
        Long id,
        String title,
        String reason,
        LocalDate date,
        Double amount
) {
}
