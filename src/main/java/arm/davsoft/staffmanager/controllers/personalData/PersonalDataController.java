package arm.davsoft.staffmanager.controllers.personalData;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.components.TagBar;
import arm.davsoft.staffmanager.components.UploadFileLabelListCell;
import arm.davsoft.staffmanager.domain.Classifier;
import arm.davsoft.staffmanager.domain.PersonalData;
import arm.davsoft.staffmanager.domain.UploadFile;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by david on 7/23/16.
 */
public class PersonalDataController implements Initializable {
    PersonalData personalData;

    @FXML protected VBox rootContainer;
    @FXML protected ImageView img_avatar;
    @FXML protected ListView<UploadFile> attachmentsListView;
    @FXML protected TagBar<Classifier> participationTagBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.LOGGER.info("Login controller instantiated successfully.");
    }

    public void prepareForm(PersonalData personalData) {
        this.personalData = personalData;

        initValueBindings();
        initAttachmentListView();

        this.img_avatar.setPreserveRatio(false);
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    protected void initValueBindings() {
        img_avatar.imageProperty().bind(personalData.avatarProperty());
        participationTagBar.tagsProperty().bindBidirectional(personalData.participationsProperty());
    }

    protected void initAttachmentListView() {
        attachmentsListView.setCellFactory(param -> new UploadFileLabelListCell());
        attachmentsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Utils.showAttachment(attachmentsListView.getSelectionModel().getSelectedItem());
            }
        });
        attachmentsListView.setItems(personalData.getAttachments());
    }
}
