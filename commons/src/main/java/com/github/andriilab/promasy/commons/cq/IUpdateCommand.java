package com.github.andriilab.promasy.commons.cq;

import com.github.andriilab.promasy.commons.persistence.IEntity;

public interface IUpdateCommand<T extends IEntity> extends ICommand {
    T getObject();
}