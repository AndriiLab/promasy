package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;

public class UpdateCommand<T extends IEntity> implements ICommand<T> {
    private T object;

    public UpdateCommand(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}
