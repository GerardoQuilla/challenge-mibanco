package com.jgerardo.challenge.expense.interfaces.rest;

import com.jgerardo.challenge.expense.domain.model.commands.DeleteExpenseCommand;
import com.jgerardo.challenge.expense.domain.model.queries.GetAllExpensesQuery;
import com.jgerardo.challenge.expense.domain.model.queries.GetExpensesByMonth;
import com.jgerardo.challenge.expense.domain.services.ExpenseCommandService;
import com.jgerardo.challenge.expense.domain.services.ExpenseQueryService;
import com.jgerardo.challenge.expense.interfaces.rest.resources.CreateExpenseResource;
import com.jgerardo.challenge.expense.interfaces.rest.resources.ExpenseResource;
import com.jgerardo.challenge.expense.interfaces.rest.resources.UpdateExpenseResource;
import com.jgerardo.challenge.expense.interfaces.rest.transform.CreateExpenseCommandFromResourceAssembler;
import com.jgerardo.challenge.expense.interfaces.rest.transform.ExpenseResourceFromEntityAssembler;
import com.jgerardo.challenge.expense.interfaces.rest.transform.UpdateExpenseCommandFromResourceAssembler;
import com.jgerardo.challenge.shared.interfaces.rest.resources.MessageResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/expenses", produces = APPLICATION_JSON_VALUE)
public class ExpenseController {
    private final ExpenseCommandService expenseCommandService;
    private final ExpenseQueryService expenseQueryService;

    public ExpenseController(ExpenseCommandService expenseCommandService, ExpenseQueryService expenseQueryService) {
        this.expenseCommandService = expenseCommandService;
        this.expenseQueryService = expenseQueryService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResource> createExpense(@RequestBody CreateExpenseResource resource){
        var command = CreateExpenseCommandFromResourceAssembler.fromResourceToCommand(resource);
        var expense = expenseCommandService.handle(command);
        if (expense.isEmpty())return ResponseEntity.badRequest().build();
        var expenseResource = ExpenseResourceFromEntityAssembler.fromEntityToResource(expense.get());
        return new ResponseEntity<>(expenseResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResource> updateExpense(
            @PathVariable Long id,
            @RequestBody UpdateExpenseResource resource){

        var command = UpdateExpenseCommandFromResourceAssembler.fromResourceToCommand(resource,id);
        var expense = expenseCommandService.handle(command);
        if (expense.isEmpty())return ResponseEntity.badRequest().build();
        var expenseResource = ExpenseResourceFromEntityAssembler.fromEntityToResource(expense.get());
        return new ResponseEntity<>(expenseResource, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResource>> getAllExpenses(){
        var command = new GetAllExpensesQuery();
        var expenses = expenseQueryService.handle(command);
        var expensesResource = expenses.stream()
                .map(ExpenseResourceFromEntityAssembler::fromEntityToResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(expensesResource);
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<ExpenseResource>> getExpensesByMonth(@RequestParam Integer month){
        var command = new GetExpensesByMonth(month);
        var expenses = expenseQueryService.handle(command);
        var expensesResource = expenses.stream()
                .map(ExpenseResourceFromEntityAssembler::fromEntityToResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(expensesResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id){
        var command = new DeleteExpenseCommand(id);
        expenseCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResource("Expense was deleted successfully"));
    }
}
