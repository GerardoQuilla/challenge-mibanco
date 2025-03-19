package com.jgerardo.challenge.expense.application.internal.queryServices;

import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import com.jgerardo.challenge.expense.domain.model.queries.GetAllExpensesQuery;
import com.jgerardo.challenge.expense.domain.model.queries.GetExpenseById;
import com.jgerardo.challenge.expense.domain.model.queries.GetExpensesByMonth;
import com.jgerardo.challenge.expense.domain.services.ExpenseQueryService;
import com.jgerardo.challenge.expense.infrastructure.persistence.jpa.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseQueryServiceImpl implements ExpenseQueryService {

    private final ExpenseRepository expenseRepository;

    public ExpenseQueryServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Optional<Expense> handle(GetExpenseById query) {
        return expenseRepository.findById(query.id());
    }

    @Override
    public List<Expense> handle(GetAllExpensesQuery query) {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> handle(GetExpensesByMonth query) {
        return expenseRepository.findAllByMonth(query.month());
    }
}
