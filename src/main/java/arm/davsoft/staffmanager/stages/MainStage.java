package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.controllers.AppSpecController;
import arm.davsoft.staffmanager.utils.ResourceManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

/**
 * Created by david on 8/1/16.
 */
public class MainStage extends CustomStage {
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windowBounds = screen.getVisualBounds();

    public MainStage() throws Exception {
        super();
        initStyle(StageStyle.UNDECORATED);
        initStage(ResourceManager.getParam("APPLICATION.NAME"), ResourceManager.getAppLogo());
        Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/mainScreen.fxml"));
        ((VBox) getScene().getRoot()).getChildren().addAll(/*new ApplicationTitleBar(this), */parent);
        VBox.setVgrow(parent, Priority.ALWAYS);
        fixMinDimensionsTo(700, 500);
        setX(windowBounds.getMinX());
        setY(windowBounds.getMinY());
        setWidth(windowBounds.getWidth());
        setHeight(windowBounds.getHeight());
        ((AppSpecController) fxmlLoader.getController()).setCurrentStage(this);
    }
}
