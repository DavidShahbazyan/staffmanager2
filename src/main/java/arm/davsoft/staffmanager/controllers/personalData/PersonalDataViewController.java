package arm.davsoft.staffmanager.controllers.personalData;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.domain.PersonalData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by david on 7/23/16.
 */
public class PersonalDataViewController extends PersonalDataController {

    @FXML
    private Label label_firstName, label_secondName, label_middleName, label_gender,
            label_birthDate, label_birthPlace, label_nationality, label_regAddress,
            label_resAddress, label_phone, label_email, label_passport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.LOGGER.info("PersonalDataViewController class instantiated successfully.");
    }

    public void prepareForm(PersonalData personalData) {
        super.prepareForm(personalData);
    }

    @Override
    protected void initValueBindings() {
        super.initValueBindings();
        label_firstName.setText(personalData.getFirstName());
        label_secondName.setText(personalData.getSecondName());
        label_middleName.setText(personalData.getMiddleName());
        label_gender.setText(personalData.getGender().getName());
        label_birthDate.setText(personalData.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        label_birthPlace.setText(personalData.getBirthPlace());
        label_nationality.setText(personalData.getNationality());
        label_regAddress.setText(personalData.getRegAddress());
        label_resAddress.setText(personalData.getResAddress());
        label_phone.setText(personalData.getPhone());
        label_email.setText(personalData.getEmail());
        label_passport.setText(personalData.getPassport());
    }
}
