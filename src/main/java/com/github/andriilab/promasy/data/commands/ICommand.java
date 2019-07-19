package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.domain.IEntity;

public interface ICommand<T extends IEntity> {
    T getObject();
}
