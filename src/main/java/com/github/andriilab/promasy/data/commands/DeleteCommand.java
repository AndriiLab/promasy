package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;

public class DeleteCommand<T extends IEntity> implements ICommand<T> {
    private final T object;

    public DeleteCommand(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}