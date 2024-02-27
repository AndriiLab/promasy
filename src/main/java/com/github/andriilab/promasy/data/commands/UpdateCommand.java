package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;
import lombok.Getter;

@Getter
public class UpdateCommand<T extends IEntity> implements ICommand<T> {
    private final T object;

    public UpdateCommand(T object) {
        this.object = object;
    }
}
