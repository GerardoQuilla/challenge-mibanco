package com.jgerardo.challenge.expense.infrastructure.persistence.jpa.repositories;

import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE FUNCTION('MONTH', e.date) = :month")
    List<Expense> findAllByMonth(int month);
}
