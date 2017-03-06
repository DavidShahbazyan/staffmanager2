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
    @FXML private BorderPane mainViewRoot;
    @FXML private ListView<PersonalData> membersListView;
    @FXML private TextField txt_search;
    @FXML private Button btn_add, btn_edit, btn_delete;

//    private FilteredList<PersonalData> membersListViewItems = new FilteredList<>(observableArrayList());


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

        initFormBindings();
        initMembersListViewSearch();
        initMembersListView();
        refreshMembersListView();
    }

    private void initFormBindings() {
        btn_edit.disableProperty().bind(Bindings.isNull(membersListView.getSelectionModel().selectedItemProperty()));
        btn_delete.disableProperty().bind(Bindings.isNull(membersListView.getSelectionModel().selectedItemProperty()));
    }

    private void initMembersListViewSearch() {
        txt_search.textProperty().addListener((observable, oldVal, newVal) -> filterMembersList(oldVal, newVal));
    }

    private void initMembersListView() {
        membersListView.setCellFactory(param -> new MemberListCell());
        membersListView.setItems(observableArrayList());
        membersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            PersonalData selectedPersonalData = membersListView.getSelectionModel().getSelectedItem();
            if (selectedPersonalData != null) {
                viewPersonalDetails();
            }
        });
    }

    private void viewPersonalDetails() {
        PersonalData selectedItem = membersListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                PersonalData data = PersonalDataService.loadPersonalDataById(selectedItem.getId());
                if (data != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setResources(ResourceBundle.getBundle("properties/messages"));
                    Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/dataViewScreen.fxml"));
                    ((PersonalDataController) fxmlLoader.getController()).prepareForm(data);
                    mainViewRoot.setCenter(parent);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                Main.LOGGER.error("Error occurred in viewPersonalDetails method: ", e);
            }
        }
    }

    private void filterMembersList(String oldVal, String newVal) {
        Objects.requireNonNull(newVal);
        ((FilteredList<PersonalData>) membersListView.getItems()).setPredicate(personalData -> personalData.getFullName().trim().toLowerCase().contains(newVal.trim().toLowerCase()));
    }

    @FXML
    private void openAppSettings(ActionEvent event) {
        Dialogs.showSettingsDialog(mainViewRoot.getScene().getWindow());
    }

    @FXML
    private void aboutApp(ActionEvent event) {
        Dialogs.showAboutAppDialog(mainViewRoot.getScene().getWindow());
    }

    @FXML
    private void logOut(ActionEvent event) throws Exception {
        new LoginStage().showAndFocus();
        currentStage.hide();
    }

    @FXML
    private void restartApp(ActionEvent event) throws Exception {
        ProcessIndicator graphic = new ProcessIndicator("images/icons/process/fs/step_1@2x.png", true);
        if (Dialogs.showConfirmDialog(graphic, ResourceManager.getMessage("title.dialog.restarting"), null, ResourceManager.getMessage("label.confirmation.restartTheApplication"))) {
            Main.restart();
        }
    }

    @FXML
    private void exitApp(ActionEvent event) {
        Main.exit();
    }

    @FXML
    private void createNewPersonalData(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("properties/messages"));
        Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/dataEditScreen.fxml"));
        ((PersonalDataEditController) fxmlLoader.getController()).prepareForm(new PersonalData(IDGenerator.getNextTempId()));
        ((PersonalDataEditController) fxmlLoader.getController()).setSaveChangesConsumer(personalData -> refreshMembersListView());
        ((PersonalDataEditController) fxmlLoader.getController()).setDiscardChangesConsumer(personalData -> refreshMembersListView());
        mainViewRoot.setCenter(parent);
    }

    @FXML
    private void editPersonalData(ActionEvent event) throws Exception {
        PersonalData selectedItem = membersListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                PersonalData data = PersonalDataService.loadPersonalDataById(selectedItem.getId());
                if (data != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setResources(ResourceBundle.getBundle("properties/messages"));
                    Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/dataEditScreen.fxml"));
                    ((PersonalDataEditController) fxmlLoader.getController()).prepareForm(data);
                    ((PersonalDataEditController) fxmlLoader.getController()).setSaveChangesConsumer(personalData -> {
                        refreshMembersListView();
                        viewPersonalDetails();
                    });
                    ((PersonalDataEditController) fxmlLoader.getController()).setDiscardChangesConsumer(personalData -> viewPersonalDetails());
                    mainViewRoot.setCenter(parent);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                Main.LOGGER.error("Error occurred in editPersonalData method: ", e);
            }
        } else {
            Dialogs.showWarningPopup("Attention!", "There is nothing selected in the table below. Please select a row first.");
        }
    }

    @FXML
    private void deletePersonalData(ActionEvent event) throws SQLException {
        PersonalData selectedMember = membersListView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            String content = String.format(ResourceManager.getMessage("label.confirmation.delete.item"), selectedMember.getFullName());
            if (Dialogs.showConfirmDialog(ResourceManager.getMessage("title.dialog.delete"), null, content)) {
                PersonalDataService.deletePersonalData(selectedMember);
                refreshMembersListView();
            }
        } else {
            Dialogs.showWarningPopup("Attention!", "There is nothing selected in the table below. Please select a row first.");
        }
    }

    @FXML
    private void refreshMembersListView() {
        int selectedIndex = membersListView.getSelectionModel().getSelectedIndex();
        try {
            membersListView.setItems(new FilteredList<>(observableArrayList(PersonalDataService.loadAllMembers())));
            filterMembersList("", "");
            if (membersListView.getItems().size() > 0) {
                if (selectedIndex < 0 || selectedIndex >= membersListView.getItems().size()) {
                    membersListView.getSelectionModel().selectFirst();
                } else {
                    membersListView.getSelectionModel().select(selectedIndex);
                }
            } else {
                membersListView.getSelectionModel().clearSelection();
            }
            membersListView.requestFocus();
        } catch (SQLException e) {
            Main.LOGGER.error("Error during refreshing members table: ", e);
        }
    }
}
