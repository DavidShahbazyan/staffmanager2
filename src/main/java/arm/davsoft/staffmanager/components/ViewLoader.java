package arm.davsoft.staffmanager.components;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by David on 5/22/2017.
 */
public class ViewLoader extends StackPane {
    private Node currentView;
    private Node nextView;

    public void loadView(Node view) {
        currentView = view;
    }
}
