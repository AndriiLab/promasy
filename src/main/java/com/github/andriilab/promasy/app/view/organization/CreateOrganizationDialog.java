package com.github.andriilab.promasy.app.view.organization;

import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.commands.UpdateCommand;
import com.github.andriilab.promasy.data.repositories.ServerRepository;
import com.github.andriilab.promasy.domain.organization.entities.Address;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.enums.CityTypes;
import com.github.andriilab.promasy.domain.organization.enums.StreetTypes;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.PJComboBox;
import com.github.andriilab.promasy.app.components.panes.ErrorOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Dialog for creation and edit of {@link Institute}
 */
class CreateOrganizationDialog extends JDialog {
    private final JTextField instituteNameField;
    private final JTextField mailField;
    private final JTextField phoneField;
    private final JTextField faxField;
    private final JTextField edrpouField;
    private final JTextField countryField;
    private final JTextField regionField;
    private final PJComboBox<CityTypes> cityTypeBox;
    private final JTextField cityField;
    private final PJComboBox<StreetTypes> streetTypeBox;
    private final JTextField streetField;
    private final JTextField buildingNumberField;
    private final JTextField corpusNumberField;
    private final JTextField postalCodeField;
    private final JButton createButton;
    private final JButton cancelButton;
    private final MainFrame parent;
    private Institute model;
    private CreateOrganizationDialogListener listener;

    public CreateOrganizationDialog(MainFrame parent) {
        super(parent, Labels.getProperty("addOrganization"), true);
        this.parent = parent;
        setSize(495, 330);
        setResizable(false);
        setLocationRelativeTo(parent);

        listener = new EmptyCreateOrganizationDialogListener();
        instituteNameField = new JTextField(37);
        mailField = new JTextField(10);
        phoneField = new JTextField(10);
        faxField = new JTextField(10);
        edrpouField = new JTextField(10);
        countryField = new JTextField(10);
        regionField = new JTextField(10);
        cityField = new JTextField(10);
        streetField = new JTextField(30);
        buildingNumberField = new JTextField(10);
        corpusNumberField = new JTextField(10);
        postalCodeField = new JTextField(10);

        Dimension boxSize = new Dimension(120, 20);

        cityTypeBox = new PJComboBox<>(CityTypes.values());
        cityTypeBox.setPreferredSize(boxSize);
        ((JLabel) cityTypeBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);

        streetTypeBox = new PJComboBox<>(StreetTypes.values());
        streetTypeBox.setPreferredSize(boxSize);
        ((JLabel) streetTypeBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);

        createButton = new JButton(Labels.getProperty("create"));
        cancelButton = new JButton(Labels.getProperty("cancel"));

        createLayout();

        createButton.addActionListener(e -> onCreateAction());
        cancelButton.addActionListener(e -> onCancel());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void clear() {
        String empty = "";
        instituteNameField.setText(empty);
        mailField.setText(empty);
        phoneField.setText(empty);
        faxField.setText(empty);
        edrpouField.setText(empty);
        countryField.setText(empty);
        regionField.setText(empty);
        cityField.setText(empty);
        streetField.setText(empty);
        buildingNumberField.setText(empty);
        corpusNumberField.setText(empty);
        postalCodeField.setText(empty);
        cityTypeBox.setSelectedIndex(0);
        streetTypeBox.setSelectedIndex(0);
    }

    void setModel(Institute model) {
        this.model = model;

        instituteNameField.setText(model.getInstName());
        mailField.setText(model.getEMail());
        phoneField.setText(model.getPhoneNumber());
        faxField.setText(model.getFaxNumber());
        edrpouField.setText(String.valueOf(model.getEdrpou()));
        if (model.getAddress() != null) {
            countryField.setText(model.getAddress().getCountry());
            regionField.setText(model.getAddress().getRegion());
            cityField.setText(model.getAddress().getCity());
            streetField.setText(model.getAddress().getStreet());
            buildingNumberField.setText(model.getAddress().getBuildingNumber());
            corpusNumberField.setText(model.getAddress().getCorpusNumber());
            postalCodeField.setText(model.getAddress().getPostalCode());
            cityTypeBox.setSelectedItem(model.getAddress().getCityType());
            streetTypeBox.setSelectedItem(model.getAddress().getStreetType());
        }
        setVisible(true);
    }

    private boolean checkFields() {
        String institute = instituteNameField.getText();
        if (institute.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("name"));
            return false;
        }

        String email = mailField.getText();
        if (email.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("email"));
            return false;
        }

        String phone = phoneField.getText();
        if (phone.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("phone"));
            return false;
        }

        String fax = faxField.getText();

        String edrpouStr = edrpouField.getText();
        if (edrpouStr.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("edrpou"));
            return false;
        }

        int edrpou;
        try {
            edrpou = Integer.parseInt(edrpouStr);
        } catch (NumberFormatException ex) {
            ErrorOptionPane.wrongFormat(parent, Labels.getProperty("edrpou"), Labels.getProperty("wrongIntegerFormat"));
            return false;
        }

        String country = countryField.getText();
        if (country.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("country"));
            return false;
        }
        String region = regionField.getText();
        if (region.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("region"));
            return false;
        }

        String city = cityField.getText();
        if (city.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("cityTypes.city"));
            return false;
        }

        String street = streetField.getText();
        if (street.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("streetTypes.street"));
            return false;
        }

        String buildNumStr = buildingNumberField.getText();
        if (buildNumStr.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("buildingNum"));
            return false;
        }

        String corpusNumStr = corpusNumberField.getText();

        String postalCode = postalCodeField.getText();
        if (postalCode.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("postalCode"));
            return false;
        }
        CityTypes cityType = (CityTypes) cityTypeBox.getSelectedItem();
        StreetTypes streetType = (StreetTypes) streetTypeBox.getSelectedItem();

        if (model == null) {
            model = new Institute();
        }

        model.setInstName(institute);
        model.setEMail(email);
        model.setPhoneNumber(phone);
        model.setFaxNumber(fax);
        model.setEdrpou(edrpou);
        Address address;
        if (model.getAddress() == null) {
            address = new Address();
            address.setCreatedDate(ServerRepository.getServerTimestamp());
            address.setCreatedEmployee(LoginData.getInstance());
        } else {
            address = model.getAddress();
            address.setModifiedDate(ServerRepository.getServerTimestamp());
            address.setModifiedEmployee(LoginData.getInstance());
        }
        address.setCountry(country);
        address.setRegion(region);
        address.setCity(city);
        address.setCityType(cityType);
        address.setStreet(street);
        address.setStreetType(streetType);
        address.setBuildingNumber(buildNumStr);
        address.setCorpusNumber(corpusNumStr);
        address.setPostalCode(postalCode);
        model.setAddress(address);

        return true;
    }

    private void onCreateAction() {
        if (checkFields()) {
            ICommand<Institute> command = model.getModelId() == 0L ? new CreateCommand<>(model) : new UpdateCommand<>(model);
            listener.persistModelEventOccurred(command);
            clear();
            setVisible(false);
        }
    }

    private void onCancel() {
        clear();
        setVisible(false);
    }

    public void setListener(CreateOrganizationDialogListener listener) {
        this.listener = listener;
    }

    private void createLayout() {

        GridBagConstraints gc = new GridBagConstraints();
        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        ////First row/////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        namePanel.add(new JLabel(Labels.withColon("name")), gc);

        gc.gridx = 1;
        gc.gridwidth = 3;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        namePanel.add(instituteNameField, gc);

        JPanel addressPanel = new JPanel(new GridBagLayout());
        addressPanel.setBorder(BorderFactory.createTitledBorder(Labels.getProperty("address")));

        ////// Next row//////
        gc.gridy++;

        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addressPanel.add(streetTypeBox, gc);

        gc.gridx++;
        gc.gridwidth = 3;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addressPanel.add(streetField, gc);

        ////// Next row//////
        gc.gridy++;

        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        addressPanel.add(new JLabel(Labels.withColon("buildingNum")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addressPanel.add(buildingNumberField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        addressPanel.add(new JLabel(Labels.withColon("corpusNum")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addressPanel.add(corpusNumberField, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        addressPanel.add(cityTypeBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addressPanel.add(cityField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        addressPanel.add(new JLabel(Labels.withColon("region")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addressPanel.add(regionField, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        addressPanel.add(new JLabel(Labels.withColon("country")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addressPanel.add(countryField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        addressPanel.add(new JLabel(Labels.withColon("postalCode")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        addressPanel.add(postalCodeField, gc);

        JPanel contactsPanel = new JPanel(new GridBagLayout());
        contactsPanel.setBorder(BorderFactory.createTitledBorder(Labels.getProperty("contacts")));

        ////// Next row//////
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        contactsPanel.add(new JLabel(Labels.withColon("phone")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        contactsPanel.add(phoneField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        contactsPanel.add(new JLabel(Labels.withColon("fax")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        contactsPanel.add(faxField, gc);

        ////// Next row//////
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        contactsPanel.add(new JLabel(Labels.withColon("email")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        contactsPanel.add(mailField, gc);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(addressPanel);
        infoPanel.add(contactsPanel);

        JPanel idPanel = new JPanel(new GridBagLayout());
        idPanel.setBorder(BorderFactory.createTitledBorder(Labels.getProperty("registrationNumbers")));

        ////// Next row//////
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = noPadding;
        idPanel.add(new JLabel(Labels.withColon("edrpou")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        idPanel.add(edrpouField, gc);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(namePanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(idPanel, BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        buttonsPanel.add(createButton);
        buttonsPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

}
