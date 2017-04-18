package gui.components;

import model.models.AbstractModel;
import model.models.Model;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * {@link JComboBox} with additional functions
 */
public class PJComboBox<T> extends JComboBox<T> {

    public static final Dimension COMBOBOX_DIMENSION = new Dimension(225, 20);

    public PJComboBox() {
        super();
    }

    public PJComboBox(T[] values) {
        super(values);
    }

    public PJComboBox(DefaultComboBoxModel defaultComboBoxModel) {
        super(defaultComboBoxModel);
    }

    /**
     * Function sets PJComboBox with data with special parameters.
     * First it removes all objects from comboBox and adds emptyModel (if selected)
     * Next it adds only models where model.isActive()
     *
     * @param db           parametrized list, which implements {@link Model} interface
     * @param emptyModel   model with default data which implements {@link Model} interface
     * @param isFirstEmpty add first emptyModel to comboBox
     */

    public void setBoxData(List<T> db, T emptyModel, boolean isFirstEmpty) {
        this.removeAllItems();
        if (isFirstEmpty) {
            this.addItem(emptyModel);
        }
        if (!isFirstEmpty && (db == null || db.isEmpty())) {
            this.addItem(emptyModel);
        } else if (db != null && !db.isEmpty()) {
            for (T model : db) {
                if (model instanceof Model) {
                    if (((AbstractModel) model).isActive()) {
                        this.addItem(model);
                    }
                } else {
                    this.addItem(model);
                }
            }
        }
        this.repaint();
    }

    public <T extends Model> void setSelectedModel(T model) {
        if (model.getModelId() != 0L) {
            setSelectedObject(model);
        }
    }

    public void setSelectedObject(Object object) {
        for (int i = 0; i <= this.getItemCount(); i++) {
            if (this.getItemAt(i).equals(object)) {
                this.setSelectedIndex(i);
                break;
            }
        }
    }
}
