package com.github.andriilab.promasy.commons.cq;

import com.github.andriilab.promasy.commons.persistence.IEntity;

public interface IRefreshCommand<T extends IEntity> extends ICommand {
    T getObject();
}
