package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.utils.ResourceManager;
import arm.davsoft.staffmanager.utils.SpringFXMLLoader;
import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by david on 8/1/16.
 */
public class CustomStage extends Stage {
    protected final Stage parentStage;
    protected CustomStageController<CustomStage> controller;
    protected FXMLLoader fxmlLoader;
    protected Queue<Consumer> beforeStageCloseActions;

    public CustomStage() {
        this(null);
    }

    public CustomStage(Stage parentStage) {
        this.parentStage = parentStage;
        this.initOwner(parentStage);
        this.beforeStageCloseActions = new ArrayDeque<>();
    }

    @Override
    public void close() {
        beforeStageClose();
        if (parentStage == null) {
            Main.exit();
        } else {
            super.close();
            this.parentStage.requestFocus();
        }
    }

    @Override
    public void hide() {
        super.hide();
    }

    public abstract void initStage() throws Exception;

    public URL getViewLocation() {
        return null;
    }

    protected void buildStage(String title, Image icon) throws Exception {
        setTitle(title);
        getIcons().add(icon);
        setScene(new Scene(new VBox()));
        getScene().getStylesheets().add(Application.getUserAgentStylesheet());
        initFXMLLoader(getViewLocation());
    }

    private void initFXMLLoader(URL location) throws Exception {
        if (location != null) {
            fxmlLoader = new FXMLLoader(location);
            fxmlLoader.setControllerFactory(clazz -> SpringFXMLLoader.getContext().getBean(clazz));
        } else {
            fxmlLoader = new FXMLLoader();
        }
        fxmlLoader.setResources(ResourceManager.getMessageBundle());
    }

    public void fixAllDimensionsTo(double width, double height) {
        setResizable(false);
        fixMinDimensionsTo(width, height);
        fixMaxDimensionsTo(width, height);
    }

    public void fixMinDimensionsTo(double width, double height) {
        setMinWidth(width);
        setMinHeight(height);
    }

    public void fixMaxDimensionsTo(double width, double height) {
        setMaxWidth(width);
        setMaxHeight(height);
    }

    public final void showAndFocus() {
        show();
        requestFocus();
    }

    protected void beforeStageClose() {
        while (!this.beforeStageCloseActions.isEmpty()) {
            this.beforeStageCloseActions.poll().accept(null);
        }
    }

    public Stage getParentStage() {
        return parentStage;
    }

    public Queue<Consumer> getBeforeStageCloseActions() {
        return beforeStageCloseActions;
    }

    public void addBeforeStageCloseAction(Consumer consumer) {
        this.beforeStageCloseActions.offer(consumer);
    }

    public CustomStageController<CustomStage> getController() {
        return controller;
    }
}
