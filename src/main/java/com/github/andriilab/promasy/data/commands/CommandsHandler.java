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

    public <T extends IEntity> void Handle(ICommand<T> command) {
        if (command instanceof CreateCommand) {
            HandleCreate((CreateCommand<T>) command);
        } else if (command instanceof UpdateCommand) {
            HandleUpdate((UpdateCommand<T>) command);
        } else if (command instanceof DeleteCommand) {
            HandleDelete((DeleteCommand<T>) command);
        } else if (command instanceof RefreshCommand) {
            HandleRefresh((RefreshCommand<T>) command);
        } else {
            throw new UnsupportedOperationException(command.getClass().getName());
        }
    }

    private <T extends IEntity> void HandleCreate(CreateCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).create(command.getObject());
    }

    private <T extends IEntity> void HandleUpdate(UpdateCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).update(command.getObject());
    }

    private <T extends IEntity> void HandleDelete(DeleteCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).delete(command.getObject());
    }

    private <T extends IEntity> void HandleRefresh(RefreshCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).refresh(command.getObject());
    }
}
