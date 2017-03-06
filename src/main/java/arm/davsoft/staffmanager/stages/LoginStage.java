package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.components.ApplicationTitleBar;
import arm.davsoft.staffmanager.controllers.AppSpecController;
import arm.davsoft.staffmanager.utils.ResourceManager;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

import java.util.Arrays;

/**
 * Created by david on 8/1/16.
 */
public class LoginStage extends CustomStage {
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windowBounds = screen.getVisualBounds();

    public LoginStage() throws Exception {
        super();
        initStyle(StageStyle.UNDECORATED);
        initStage(Utils.concatObjects(Arrays.asList(ResourceManager.getParam("APPLICATION.NAME"), ResourceManager.getMessage("label.sign.in")), " - "), ResourceManager.getAppLogo());
        Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/loginScreen.fxml"));
        ApplicationTitleBar applicationTitleBar = new ApplicationTitleBar(this, true, false, true);
        AnchorPane anchorPane = new AnchorPane(parent, applicationTitleBar);
        AnchorPane.setLeftAnchor(applicationTitleBar, 0d);
        AnchorPane.setTopAnchor(applicationTitleBar, 0d);
        AnchorPane.setRightAnchor(applicationTitleBar, 0d);

        AnchorPane.setLeftAnchor(parent, 0d);
        AnchorPane.setTopAnchor(parent, 0d);
        AnchorPane.setRightAnchor(parent, 0d);
        AnchorPane.setBottomAnchor(parent, 0d);

        ((VBox) getScene().getRoot()).getChildren().addAll(anchorPane);
        VBox.setVgrow(anchorPane, Priority.ALWAYS);
        setX(windowBounds.getMinX());
        setY(windowBounds.getMinY());
        setWidth(windowBounds.getWidth());
        setHeight(windowBounds.getHeight());
//        fixAllDimensionsTo(264, 351);
        ((AppSpecController) fxmlLoader.getController()).setCurrentStage(this);
    }
}
