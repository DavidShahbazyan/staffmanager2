package arm.davsoft.staffmanager.controllers.personalData;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.domain.AppSpecImage;
import arm.davsoft.staffmanager.domain.Classifier;
import arm.davsoft.staffmanager.domain.PersonalData;
import arm.davsoft.staffmanager.domain.UploadFile;
import arm.davsoft.staffmanager.helpers.ClassifierCache;
import arm.davsoft.staffmanager.service.PersonalDataService;
import arm.davsoft.staffmanager.utils.Dialogs;
import arm.davsoft.staffmanager.utils.IDGenerator;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by david on 7/23/16.
 */
public class PersonalDataEditController extends PersonalDataController {
    PersonalData tempPersonalData;
    Consumer<PersonalData> saveChangesConsumer;
    Consumer<PersonalData> discardChangesConsumer;

    @FXML private Button btn_browseAvatar, btn_cancel, btn_ok, btn_addAttachment, btn_removeAttachment, btn_addParticipation;
    @FXML private ComboBox<Classifier> combo_gender, combo_participation;
    @FXML private DatePicker date_birthDate;
    @FXML private ToolBar attachmentsToolBar;
    @FXML private TextField txtField_firstName, txtField_secondName, txtField_middleName, txtField_passport, txtField_email, txtField_phone, txtField_birthPlace, txtField_nationality, txtField_regAddress, txtField_resAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.LOGGER.info("PersonalDataEditController class instantiated successfully.");
    }

    public void prepareForm(PersonalData personalData) {
        super.prepareForm(personalData);
        tempPersonalData = personalData.clone();
        initEditabilityBindings();
        combo_gender.getItems().addAll(ClassifierCache.getInstance().getGenders());
        combo_participation.getItems().addAll(ClassifierCache.getInstance().getParticipations());
    }

    private void initEditabilityBindings() {
        btn_removeAttachment.disableProperty().bind(Bindings.isEmpty(attachmentsListView.getSelectionModel().getSelectedItems()));
    }

    public void setDiscardChangesConsumer(Consumer<PersonalData> discardChangesConsumer) {
        this.discardChangesConsumer = discardChangesConsumer;
    }

    public void setSaveChangesConsumer(Consumer<PersonalData> saveChangesConsumer) {
        this.saveChangesConsumer = saveChangesConsumer;
    }

    @Override
    protected void initValueBindings() {
        super.initValueBindings();
        combo_gender.valueProperty().bindBidirectional(personalData.genderProperty());
        date_birthDate.valueProperty().bindBidirectional(personalData.birthDateProperty());
        txtField_firstName.textProperty().bindBidirectional(personalData.firstNameProperty());
        txtField_secondName.textProperty().bindBidirectional(personalData.secondNameProperty());
        txtField_middleName.textProperty().bindBidirectional(personalData.middleNameProperty());
        txtField_passport.textProperty().bindBidirectional(personalData.passportProperty());
        txtField_email.textProperty().bindBidirectional(personalData.emailProperty());
        txtField_phone.textProperty().bindBidirectional(personalData.phoneProperty());
        txtField_birthPlace.textProperty().bindBidirectional(personalData.birthPlaceProperty());
        txtField_nationality.textProperty().bindBidirectional(personalData.nationalityProperty());
        txtField_regAddress.textProperty().bindBidirectional(personalData.regAddressProperty());
        txtField_resAddress.textProperty().bindBidirectional(personalData.resAddressProperty());
    }

    @FXML
    private void browseAvatar(ActionEvent event) {
        File file = Dialogs.openFileChooser(rootContainer.getScene().getWindow(), "Select an image.", new FileChooser.ExtensionFilter("Images", "png", "jpg", "jpeg", "bmp"), null);
        if (file != null) {
            personalData.setAvatar(new AppSpecImage(Utils.fileToByteArray(file)));
        }
    }

    @FXML
    private void saveAction(ActionEvent event) throws SQLException {
        if (personalData.isValid()) {
            if (personalData.getId() > 0) {
                System.out.println("Creates new record in DB.");
                PersonalDataService.savePersonalData(personalData);
            } else if (personalData.getId() < 0) {
                System.out.println("Saves current changes in DB.");
                PersonalDataService.savePersonalData(personalData);
            } else {
                System.out.println("The stage is not supported!");
            }
            if (saveChangesConsumer != null) {
                saveChangesConsumer.accept(personalData);
            }
        } else {
            Dialogs.showWarningPopup("The form is not valid!", "All fields in the form are required!");
        }
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        personalData = tempPersonalData;
        if (discardChangesConsumer != null) {
            discardChangesConsumer.accept(personalData);
        }
    }

    @FXML
    private void addAttachment(ActionEvent event) {
        File file = Dialogs.openFileChooser(rootContainer.getScene().getWindow(), "Select an attachment.", new FileChooser.ExtensionFilter("Images", "png", "jpg", "jpeg", "bmp"), null);
        if (file != null) {
            personalData.getAttachments().add(new UploadFile(IDGenerator.getNextTempId(), file));
        }
    }

    @FXML
    private void removeAttachment(ActionEvent event) {
        UploadFile selectedItem = attachmentsListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            personalData.getAttachments().remove(selectedItem);
        }
    }

    @FXML
    private void addParticipation(ActionEvent event) {
        Classifier selectedItem = combo_participation.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !participationTagBar.getTags().contains(selectedItem)) {
            participationTagBar.getTags().add(selectedItem);
            combo_participation.getSelectionModel().clearSelection();
        }
    }

}
