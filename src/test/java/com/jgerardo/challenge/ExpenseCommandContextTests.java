package com.jgerardo.challenge;

import com.jgerardo.challenge.expense.application.internal.commandServices.ExpenseCommandServiceImpl;
import com.jgerardo.challenge.expense.domain.model.aggregates.Expense;
import com.jgerardo.challenge.expense.domain.model.commands.CreateExpenseCommand;
import com.jgerardo.challenge.expense.domain.model.commands.UpdateExpenseCommand;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseCommandContextTests {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseCommandServiceImpl expenseCommandService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createExpenseTest(){
        // Arrange
        CreateExpenseCommand command = new CreateExpenseCommand(
                "Prestamo 1",
                "Prestamo a un amigo",
                LocalDate.now(),
                100.0
        );

        Expense createdExpense = new Expense(command);

        when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(createdExpense);

        // Act
        var result = expenseCommandService.handle(command);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent(),"El resultado no debería estar vacío");
        Assertions.assertEquals(createdExpense.getTitle(), result.get().getTitle(),"El título no coincide");

        verify(expenseRepository,times(1)).save(any(Expense.class));
    }

    @Test
    public void updateExpenseTest(){
        // Arrange
        UpdateExpenseCommand command = new UpdateExpenseCommand(
                1L,
                "Pago a Jose",
                "Junta",
                LocalDate.of(2025,3,17),
                100.0
        );

        Expense previousExpense = new Expense(new CreateExpenseCommand(
                "Pago a un amigo",
                "-",
                LocalDate.of(2025,3,1),
                100.0
        ));

        Expense updatedExpense = new Expense();
        updatedExpense.setTitle(command.title());
        updatedExpense.setReason(command.reason());
        updatedExpense.setDate(command.date());
        updatedExpense.setAmount(command.amount());

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(previousExpense));
        when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(updatedExpense);

        // Act
        var result = expenseCommandService.handle(command);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent(),"El resultado no debe estar vacío");
        Assertions.assertEquals(previousExpense.getTitle(),result.get().getTitle(),"El resultado no se actualizó");

        verify(expenseRepository,times(1)).save(Mockito.any(Expense.class));

    }

}
