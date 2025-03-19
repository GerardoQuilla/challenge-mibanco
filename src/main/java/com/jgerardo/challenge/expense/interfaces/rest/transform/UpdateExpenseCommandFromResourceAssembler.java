package com.jgerardo.challenge.expense.interfaces.rest.transform;

import com.jgerardo.challenge.expense.domain.model.commands.UpdateExpenseCommand;
import com.jgerardo.challenge.expense.interfaces.rest.resources.UpdateExpenseResource;

public class UpdateExpenseCommandFromResourceAssembler {
    public static UpdateExpenseCommand fromResourceToCommand(UpdateExpenseResource resource, Long id) {
        return new UpdateExpenseCommand(
                id,
                resource.title(),
                resource.reason(),
                resource.date(),
                resource.amount()
        );
    }
}
