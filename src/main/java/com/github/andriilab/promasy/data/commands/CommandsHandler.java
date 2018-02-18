package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.data.repositories.IRepository;
import com.github.andriilab.promasy.data.storage.Storage;
import com.github.andriilab.promasy.domain.IEntity;

@SuppressWarnings("unchecked")
public class CommandsHandler {
    private Storage storage;

    public CommandsHandler(Storage storage) {
        this.storage = storage;
    }

    public <T extends IEntity> void Handle(CreateOrUpdateCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).createOrUpdate(command.getObject());
    }

    public <T extends IEntity> void Handle(RefreshCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).refresh(command.getObject());
    }
}
