package arm.davsoft.staffmanager.utils;

import javafx.fxml.FXMLLoader;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by aram.elchyan on 7/29/2015.
 */
//Wrapper Class
public class SpringFXMLLoader {
	private static final GenericXmlApplicationContext context = new GenericXmlApplicationContext("spring-config.xml");

	static {
//		context.load("spring-config.xml");
//		context.refresh();
	}

	private FXMLLoader loader;

	public static GenericXmlApplicationContext getContext() {
		return context;
	}

	public SpringFXMLLoader() {
		loader = new FXMLLoader();
		loader.setControllerFactory(clazz -> context.getBean(clazz));
		setResources(loader);
	}

	public SpringFXMLLoader(URL location, String controllerBeanName) {
		loader = new FXMLLoader(location);
		loader.setController(context.getBean(controllerBeanName));
		setResources(loader);
	}

	public SpringFXMLLoader(URL location) {
		loader = new FXMLLoader(location);
		loader.setControllerFactory(clazz -> context.getBean(clazz));
		setResources(loader);
	}

	private void setResources(FXMLLoader loader) {
		loader.setResources(getBundle());
	}

	public static ResourceBundle getBundle() {
		return ResourceBundle.getBundle("bundles.messages");
	}

	public <T> T load() throws IOException {
		return loader.load();
	}

	public void setLocation(URL location) {
		loader.setLocation(location);
	}

	public <T> T getController() {
		return loader.getController();
	}
}
