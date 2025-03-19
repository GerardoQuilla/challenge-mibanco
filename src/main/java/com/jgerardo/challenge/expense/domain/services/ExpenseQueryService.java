package com.jgerardo.challenge.expense.domain.services;

import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import com.jgerardo.challenge.expense.domain.model.queries.GetAllExpensesQuery;
import com.jgerardo.challenge.expense.domain.model.queries.GetExpenseById;
import com.jgerardo.challenge.expense.domain.model.queries.GetExpensesByMonth;

import java.util.List;
import java.util.Optional;

public interface ExpenseQueryService {
    Optional<Expense> handle(GetExpenseById query);
    List<Expense> handle(GetAllExpensesQuery query);
    List<Expense> handle(GetExpensesByMonth query);
}
