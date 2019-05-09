package com.github.andriilab.promasy.app.commands.subdepartments;

import com.github.andriilab.promasy.app.commands.AbstractCommandHandler;
import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.commons.cq.IDeleteCommand;
import com.github.andriilab.promasy.commons.persistence.IStorage;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

public class SubdepartmentsCommandHandler extends AbstractCommandHandler<Subdepartment> {
    public SubdepartmentsCommandHandler(IStorage storage, ILoginData loginData) {
        super(storage, loginData);
    }

    @Override
    public void Handle(IDeleteCommand<Subdepartment> command) {
        Subdepartment entity = command.getObject();
        entity.getEmployees().forEach(e -> e.setDeleted(user, date));
        entity.getFinanceDepartments().forEach(e -> e.setDeleted(user, date));
        super.Handle(command);
    }
}
