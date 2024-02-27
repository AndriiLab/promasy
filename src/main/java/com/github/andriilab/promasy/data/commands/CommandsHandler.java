package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.data.repositories.IRepository;
import com.github.andriilab.promasy.data.storage.Storage;
import com.github.andriilab.promasy.domain.IEntity;

@SuppressWarnings("unchecked")
public class CommandsHandler {
    private final Storage storage;

    public CommandsHandler(Storage storage) {
        this.storage = storage;
    }

    public <T extends IEntity> void handle(ICommand<T> command) {
        if (command instanceof CreateCommand) {
            handleCreate((CreateCommand<T>) command);
        } else if (command instanceof UpdateCommand) {
            handleUpdate((UpdateCommand<T>) command);
        } else if (command instanceof DeleteCommand) {
            handleDelete((DeleteCommand<T>) command);
        } else if (command instanceof RefreshCommand) {
            handleRefresh((RefreshCommand<T>) command);
        } else {
            throw new UnsupportedOperationException(command.getClass().getName());
        }
    }

    private <T extends IEntity> void handleCreate(CreateCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).create(command.getObject());
    }

    private <T extends IEntity> void handleUpdate(UpdateCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).update(command.getObject());
    }

    private <T extends IEntity> void handleDelete(DeleteCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).delete(command.getObject());
    }

    private <T extends IEntity> void handleRefresh(RefreshCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).refresh(command.getObject());
    }
}
