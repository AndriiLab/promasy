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
        switch (command) {
            case CreateCommand<T> createCommand -> handleCreate(createCommand);
            case UpdateCommand<T> updateCommand -> handleUpdate(updateCommand);
            case DeleteCommand<T> deleteCommand -> handleDelete(deleteCommand);
            case RefreshCommand<T> refreshCommand -> handleRefresh(refreshCommand);
            case null, default -> throw new UnsupportedOperationException(command.getClass().getName());
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
