package com.github.andriilab.promasy.app.commands.financedepartments;

import com.github.andriilab.promasy.app.commands.AbstractCommandHandler;
import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.commons.cq.IDeleteCommand;
import com.github.andriilab.promasy.commons.persistence.IStorage;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

public class FinanceDepartmentCommandHandler extends AbstractCommandHandler<FinanceDepartment> {
    public FinanceDepartmentCommandHandler(IStorage storage, ILoginData loginData) {
        super(storage, loginData);
    }

    @Override
    public void Handle(IDeleteCommand<FinanceDepartment> command) {
        FinanceDepartment entity = command.getObject();
        entity.getBids().forEach(e -> e.setDeleted(user, date));
        super.Handle(command);
    }
}
