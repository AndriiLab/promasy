package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;
import lombok.Getter;

@Getter
public class DeleteCommand<T extends IEntity> implements ICommand<T> {
    private final T object;

    public DeleteCommand(T object) {
        this.object = object;
    }
}