package com.jgerardo.challenge.expense.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateExpenseResource(
        String title,
        String reason,
        LocalDate date,
        Double amount
) {
}
