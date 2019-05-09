package com.github.andriilab.promasy.app.commands.institutes;

import com.github.andriilab.promasy.app.commands.AbstractCommandHandler;
import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.commons.cq.IDeleteCommand;
import com.github.andriilab.promasy.commons.persistence.IStorage;
import com.github.andriilab.promasy.domain.organization.entities.Institute;

public class InstitutesCommandHandler extends AbstractCommandHandler<Institute> {
    public InstitutesCommandHandler(IStorage storage, ILoginData loginData) {
        super(storage, loginData);
    }

    @Override
    public void Handle(IDeleteCommand<Institute> command) {
        Institute entity = command.getObject();
        entity.getDepartments().forEach(e -> e.setDeleted(user, date));

        super.Handle(command);
    }
}
