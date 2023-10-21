package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;
import lombok.Getter;

@Getter
public class RefreshCommand<T extends IEntity> implements ICommand<T> {
    private final T object;

    public RefreshCommand(T object) {
        this.object = object;
    }
}
