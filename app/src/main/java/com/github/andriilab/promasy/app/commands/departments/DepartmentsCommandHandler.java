package com.github.andriilab.promasy.app.commands.departments;

import com.github.andriilab.promasy.app.commands.AbstractCommandHandler;
import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.commons.cq.IDeleteCommand;
import com.github.andriilab.promasy.commons.persistence.IStorage;
import com.github.andriilab.promasy.domain.organization.entities.Department;

public class DepartmentsCommandHandler extends AbstractCommandHandler<Department> {
    public DepartmentsCommandHandler(IStorage storage, ILoginData loginData) {
        super(storage, loginData);
    }

    @Override
    public void Handle(IDeleteCommand<Department> command) {
        Department entity = command.getObject();
        entity.getSubdepartments().forEach(e -> e.setDeleted(user, date));
        super.Handle(command);
    }
}
