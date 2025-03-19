package com.jgerardo.challenge.expense.interfaces.rest.transform;

import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import com.jgerardo.challenge.expense.interfaces.rest.resources.ExpenseResource;

public class ExpenseResourceFromEntityAssembler {
    public static ExpenseResource fromEntityToResource(Expense entity) {
        return new ExpenseResource(
                entity.getId(),
                entity.getTitle(),
                entity.getReason(),
                entity.getDate(),
                entity.getAmount()
        );
    }
}
