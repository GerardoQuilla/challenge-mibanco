package com.jgerardo.challenge.expense.application.internal.commandServices;

import com.jgerardo.challenge.expense.domain.exceptions.ExpenseNotFoundException;
import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import com.jgerardo.challenge.expense.domain.model.commands.CreateExpenseCommand;
import com.jgerardo.challenge.expense.domain.model.commands.DeleteExpenseCommand;
import com.jgerardo.challenge.expense.domain.model.commands.UpdateExpenseCommand;
import com.jgerardo.challenge.expense.domain.services.ExpenseCommandService;
import com.jgerardo.challenge.expense.infrastructure.persistence.jpa.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseCommandServiceImpl implements ExpenseCommandService {

    private final ExpenseRepository expenseRepository;

    public ExpenseCommandServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Optional<Expense> handle(CreateExpenseCommand command) {
        var expense = new Expense(command);
        expenseRepository.save(expense);
        return Optional.of(expense);
    }

    @Override
    public Optional<Expense> handle(UpdateExpenseCommand command) {
        var expense = expenseRepository.findById(command.id());
        if (expense.isEmpty())return Optional.empty();
        expense.get().setTitle(command.title());
        expense.get().setReason(command.reason());
        expense.get().setDate(command.date());
        expense.get().setAmount(command.amount());
        expenseRepository.save(expense.get());
        return expense;
    }

    @Override
    public void handle(DeleteExpenseCommand command) {
        var expense = expenseRepository.findById(command.id());
        if (expense.isEmpty())throw new ExpenseNotFoundException(command.id());
        expenseRepository.delete(expense.get());

    }
}
