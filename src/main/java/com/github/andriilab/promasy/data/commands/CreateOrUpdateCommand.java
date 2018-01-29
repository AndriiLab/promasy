package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;

public class CreateOrUpdateCommand<T extends IEntity> implements ICommand {
    private T object;

    public CreateOrUpdateCommand(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}