package com.github.andriilab.promasy.app.commands.finances;

import com.github.andriilab.promasy.app.commands.AbstractCommandHandler;
import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.commons.cq.IDeleteCommand;
import com.github.andriilab.promasy.commons.persistence.IStorage;
import com.github.andriilab.promasy.domain.finance.entities.Finance;

public class FinancesCommandHandler extends AbstractCommandHandler<Finance> {
    public FinancesCommandHandler(IStorage storage, ILoginData loginData) {
        super(storage, loginData);
    }

    @Override
    public void Handle(IDeleteCommand<Finance> command) {
        Finance entity = command.getObject();
        entity.getFinanceDepartmentModels().forEach(e -> e.setDeleted(user, date));
        super.Handle(command);
    }
}
