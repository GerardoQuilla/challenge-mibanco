package com.jgerardo.challenge;

import com.jgerardo.challenge.expense.application.internal.queryServices.ExpenseQueryServiceImpl;
import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import com.jgerardo.challenge.expense.domain.model.commands.CreateExpenseCommand;
import com.jgerardo.challenge.expense.domain.model.queries.GetAllExpensesQuery;
import com.jgerardo.challenge.expense.domain.model.queries.GetExpensesByMonth;
import com.jgerardo.challenge.expense.infrastructure.persistence.jpa.repositories.ExpenseRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseQueryContextTests {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseQueryServiceImpl expenseQueryService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllExpensesTest(){
        // Arrange
        List<Expense> expenseList = Arrays.asList(
                new Expense(new CreateExpenseCommand(
                        "Pasaje a universidad","Pasaje del micro a la U", LocalDate.now(),5.0)
                ),
                new Expense(new CreateExpenseCommand(
                        "Almuerzo","Almuerzo con primos",LocalDate.now(),60.0)
                )
        );

        GetAllExpensesQuery query = new GetAllExpensesQuery();

        when(expenseRepository.findAll()).thenReturn(expenseList);

        // Act
        var result = expenseQueryService.handle(query);

        // Assert
        Assertions.assertEquals(2, result.size());

        verify(expenseRepository,times(1)).findAll();
    }

    @Test
    public void getExpensesByMonthTest(){
        // Act
        List<Expense> expenseList = Arrays.asList(
                new Expense(new CreateExpenseCommand(
                        "Pasaje al trabajo","Pasaje de la 31", LocalDate.of(2025,3,18),3.0)
                ),
                new Expense(new CreateExpenseCommand(
                        "Almuerzo","Almuerzo en familia",LocalDate.of(2025,2,18),60.0)
                ),
                new Expense(new CreateExpenseCommand(
                        "Apuesta con primo","Apuesto partido de Alianza", LocalDate.of(2025,3,10),30.0)
                )
        );

        int filterByMonthNumber = 3;
        GetExpensesByMonth query = new GetExpensesByMonth(filterByMonthNumber);

        when(expenseRepository.findAllByMonth(Mockito.anyInt())).thenAnswer(invocation -> {
            int monthNumber = invocation.getArgument(0);
            return expenseList.stream()
                    .filter(expense -> expense.getDate().getMonthValue()==monthNumber)
                    .collect(Collectors.toList());
        });


        // Act
        var result = expenseQueryService.handle(query);

        // Assert
        Assertions.assertEquals(2, result.size());

        verify(expenseRepository,times(1)).findAllByMonth(filterByMonthNumber);
    }

}
