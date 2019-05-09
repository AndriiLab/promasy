package com.github.andriilab.promasy.commons.cq;

import com.github.andriilab.promasy.commons.persistence.IEntity;

public interface ICommandHandler<T extends IEntity> {
    void Handle(ICreateCommand<T> command);

    void Handle(IUpdateCommand<T> command);

    void Handle(IDeleteCommand<T> command);

    void Handle(IRefreshCommand<T> command);
}
