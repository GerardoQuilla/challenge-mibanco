package com.jgerardo.challenge.expense.interfaces.rest.transform;

import com.jgerardo.challenge.expense.domain.model.commands.CreateExpenseCommand;
import com.jgerardo.challenge.expense.interfaces.rest.resources.CreateExpenseResource;

public class CreateExpenseCommandFromResourceAssembler {
    public static CreateExpenseCommand fromResourceToCommand(CreateExpenseResource resource) {
        return new CreateExpenseCommand(
                resource.title(),
                resource.reason(),
                resource.date(),
                resource.amount()
        );
    }
}
