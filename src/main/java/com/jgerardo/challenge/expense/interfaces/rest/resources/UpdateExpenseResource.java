package com.jgerardo.challenge.expense.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateExpenseResource(
        String title,
        String reason,
        LocalDate date,
        Double amount
) {
}
