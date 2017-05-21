package arm.davsoft.staffmanager.controllers;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.components.MemberListCell;
import arm.davsoft.staffmanager.components.ProcessIndicator;
import arm.davsoft.staffmanager.controllers.personalData.PersonalDataController;
import arm.davsoft.staffmanager.controllers.personalData.PersonalDataEditController;
import arm.davsoft.staffmanager.domain.PersonalData;
import arm.davsoft.staffmanager.service.PersonalDataService;
import arm.davsoft.staffmanager.stages.LoginStage;
import arm.davsoft.staffmanager.utils.Dialogs;
import arm.davsoft.staffmanager.utils.IDGenerator;
import arm.davsoft.staffmanager.utils.ResourceManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by david on 7/20/16.
 */
public class MainScreenController extends AppSpecController {
    private final ListProperty<PersonalData> membersList = new SimpleListProperty<>(observableArrayList());
    private PersonalData currentMember;



    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.LOGGER.info("Main controller instantiation started.");
        prepareForm();
        Main.LOGGER.info("Main controller instantiated successfully.");
    }

    @Override
    public void prepareForm() {
        super.prepareForm();
    }




}
