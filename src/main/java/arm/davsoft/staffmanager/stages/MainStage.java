package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.controllers.AppSpecController;
import arm.davsoft.staffmanager.utils.ResourceManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Created by david on 8/1/16.
 */
public class MainStage extends CustomStage {
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windowBounds = screen.getVisualBounds();

    public MainStage() throws Exception {
        try {
            buildStage(StringUtils.strConcat(Arrays.asList(ResourceManager.getAppTitle(), ResourceManager.getMessage("title.window.addLog")) , " - "), ResourceManager.getAppLogo());
            Parent parent = fxmlLoader.load();
            getScene().setRoot(parent);
            fixMinDimensionsTo(((VBox) parent).getPrefWidth(), ((VBox) parent).getPrefHeight());
            controller = fxmlLoader.getController();
            controller.setCurrentStage(this);
            controller.prepareForm();
        } catch (Exception ex) {
            Logger.getLogger(getClass()).error("Error occurred during stage initialization.", ex);
        }
//        super();
//        initStyle(StageStyle.UNDECORATED);
//        initStage(ResourceManager.getParam("APPLICATION.NAME"), ResourceManager.getAppLogo());
//        Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/mainScreen.fxml"));
//        ((BorderPane) getScene().getRoot()).getChildren().addAll(/*new ApplicationTitleBar(this), */parent);
//        VBox.setVgrow(parent, Priority.ALWAYS);
//        fixMinDimensionsTo(700, 500);
//        setX(windowBounds.getMinX());
//        setY(windowBounds.getMinY());
//        setWidth(windowBounds.getWidth());
//        setHeight(windowBounds.getHeight());
//        ((AppSpecController) fxmlLoader.getController()).setCurrentStage(this);
    }
}
