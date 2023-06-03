package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;

public class CreateCommand<T extends IEntity> implements ICommand<T> {
    private final T object;

    public CreateCommand(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}