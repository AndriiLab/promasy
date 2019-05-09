package com.github.andriilab.promasy.app.commands;

import com.github.andriilab.promasy.app.interfaces.ILoginData;
import com.github.andriilab.promasy.commons.cq.*;
import com.github.andriilab.promasy.commons.persistence.IRepository;
import com.github.andriilab.promasy.commons.persistence.IStorage;
import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.persistence.repositories.ServerRepository;

@SuppressWarnings("unchecked")
public abstract class AbstractCommandHandler<T extends AbstractEntity> implements ICommandHandler<T> {
    private IStorage storage;
    private ILoginData loginData;

    public AbstractCommandHandler(IStorage storage, ILoginData loginData) {
        this.storage = storage;
        this.loginData = loginData;
    }

    @Override
    public void Handle(ICreateCommand<T> command) {
        T entity = command.getObject();
        entity.setCreatedEmployee(loginData.getCurrentUser());
        entity.setCreatedDate(ServerRepository.getServerTimestamp());
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).createOrUpdate(command.getObject());
    }

    @Override
    public void Handle(IDeleteCommand<T> command) {
        T entity = command.getObject();
        entity.setActive(false);
        entity.setModifiedEmployee(loginData.getCurrentUser());
        entity.setModifiedDate(ServerRepository.getServerTimestamp());
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).createOrUpdate(command.getObject());
    }

    @Override
    public void Handle(IUpdateCommand<T> command) {
        T entity = command.getObject();
        entity.setModifiedEmployee(loginData.getCurrentUser());
        entity.setModifiedDate(ServerRepository.getServerTimestamp());
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).createOrUpdate(command.getObject());
    }

    @Override
    public void Handle(IRefreshCommand<T> command) {
        ((IRepository<T>) storage.getByClass(command.getObject().getClass())).refresh(command.getObject());
    }
}
