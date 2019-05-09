package com.github.andriilab.promasy.app.commands.bids;

import com.github.andriilab.promasy.app.commands.AbstractCommandHandler;
import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.commons.cq.IDeleteCommand;
import com.github.andriilab.promasy.commons.persistence.IStorage;
import com.github.andriilab.promasy.domain.bid.entities.Bid;

public class BidsCommandHandler extends AbstractCommandHandler<Bid> {
    public BidsCommandHandler(IStorage storage, ILoginData loginData) {
        super(storage, loginData);
    }

    @Override
    public void Handle(IDeleteCommand<Bid> command) {
        Bid entity = command.getObject();

        entity.getStatuses().forEach(e -> e.setDeleted(user, date));

        super.Handle(command);
    }
}
