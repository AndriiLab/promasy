package com.github.andriilab.promasy.app.view.bids.status;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

public class EmptyStatusDialogListener extends AbstractEmptyListener implements StatusDialogListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(ICommand<T> model) {
        logEmptyListener(StatusDialogListener.class);
    }
}
