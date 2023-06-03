package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;

public class RefreshCommand<T extends IEntity> implements ICommand {
    private final T object;

    public RefreshCommand(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}
