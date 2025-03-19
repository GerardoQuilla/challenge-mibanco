package com.jgerardo.challenge.expense.domain.services;

import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import com.jgerardo.challenge.expense.domain.model.commands.CreateExpenseCommand;
import com.jgerardo.challenge.expense.domain.model.commands.DeleteExpenseCommand;
import com.jgerardo.challenge.expense.domain.model.commands.UpdateExpenseCommand;

import java.util.Optional;

public interface ExpenseCommandService {
    Optional<Expense> handle(CreateExpenseCommand command);
    Optional<Expense> handle(UpdateExpenseCommand command);
    void handle(DeleteExpenseCommand command);
}
