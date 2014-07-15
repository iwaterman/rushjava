package org.xman.javafx.jxc.frames;

import org.xman.javafx.jxc.controls.BreadcrumbBar;
import org.xman.javafx.jxc.controls.WindowButtons;
import org.xman.javafx.jxc.controls.WindowResizeButton;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import netscape.javascript.JSObject;

/**
 * Ensemble Main Application
 */
public class IndexFrame extends Application {
	static {
		// Enable using system proxy if set
		System.setProperty("java.net.useSystemProxies", "true");
	}
	public static final String DEFAULT_DOCS_URL = "http://download.oracle.com/javafx/2/api/";

	private static IndexFrame ensemble2;
	private static boolean isApplet = false;
	private Scene scene;
	private BorderPane root;
	private ToolBar toolBar;
	private SplitPane splitPane;
	private TreeView pageTree;
	private Pane pageArea;
	private String currentPagePath;
	private Node currentPageView;
	private BreadcrumbBar breadcrumbBar;
	private boolean changingPage = false;
	private double mouseDragOffsetX = 0;
	private double mouseDragOffsetY = 0;
	private WindowResizeButton windowResizeButton;
	public boolean fromForwardOrBackButton = false;
	private StackPane modalDimmer;
	private ToolBar pageToolBar;
	private JSObject browser;
	private String docsUrl;

	/**
	 * Get the singleton instance of Ensemble
	 * 
	 * @return The singleton instance
	 */
	public static IndexFrame getEnsemble2() {
		return ensemble2;
	}

	/**
	 * Start the application
	 * 
	 * @param stage
	 *            The main application stage
	 */
	@Override
	public void start(final Stage stage) {
		stage.sizeToScene();
	
		stage.centerOnScreen();
		
		ensemble2 = this;
		stage.setTitle("Ensemble");
		// set default docs location
		docsUrl = System.getProperty("docs.url") != null ? System.getProperty("docs.url") : DEFAULT_DOCS_URL;
		// create root stack pane that we use to be able to overlay proxy dialog
		StackPane layerPane = new StackPane();
		// check if applet
		try {
			browser = getHostServices().getWebContext();
			isApplet = browser != null;
		} catch (Exception e) {
			isApplet = false;
		}
		if (!isApplet) {
			// 在右下角创建一个改变窗口大小的按钮，后面两个参数为窗口的最小值
			windowResizeButton = new WindowResizeButton(stage, 1020, 700);
			// create root
			root = new BorderPane() {
				@Override
				protected void layoutChildren() {
					super.layoutChildren();
					windowResizeButton.autosize();
					windowResizeButton.setLayoutX(getWidth() - windowResizeButton.getLayoutBounds().getWidth());
					windowResizeButton.setLayoutY(getHeight() - windowResizeButton.getLayoutBounds().getHeight());
				}
			};
			root.getStyleClass().add("application");
		} else {
			root = new BorderPane();
			root.getStyleClass().add("applet");
		}
		root.setId("root");
		layerPane.setDepthTest(DepthTest.DISABLE);
		layerPane.getChildren().add(root);

		// create scene
		boolean is3dSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
		scene = new Scene(layerPane, 1020, 700, is3dSupported);

		if (is3dSupported) {
			// RT-13234
			scene.setCamera(new PerspectiveCamera());
		}
		scene.getStylesheets().add(IndexFrame.class.getResource("index.css").toExternalForm());
		// create modal dimmer, to dim screen when showing modal dialogs
		modalDimmer = new StackPane();
		modalDimmer.setId("ModalDimmer");
		modalDimmer.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				t.consume();
				hideModalMessage();
			}
		});
		modalDimmer.setVisible(false);
		layerPane.getChildren().add(modalDimmer);
		// create main toolbar
		toolBar = new ToolBar();
		toolBar.setId("mainToolBar");
		ImageView logo = new ImageView(new Image(IndexFrame.class.getResourceAsStream("images/logo.png")));
		HBox.setMargin(logo, new Insets(0, 0, 0, 5));
		toolBar.getItems().add(logo);
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		toolBar.getItems().add(spacer);

		Region spacer2 = new Region();
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		toolBar.getItems().add(spacer2);

		toolBar.setPrefHeight(66);
		toolBar.setMinHeight(66);
		toolBar.setMaxHeight(66);
		GridPane.setConstraints(toolBar, 0, 0);
		if (!isApplet) {
			// add close min max
			final WindowButtons windowButtons = new WindowButtons(stage);
			toolBar.getItems().add(windowButtons);
			// add window header double clicking
			toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.getClickCount() == 2) {
						windowButtons.toogleMaximized();
					}
				}
			});
			// add window dragging
			toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					mouseDragOffsetX = event.getSceneX();
					mouseDragOffsetY = event.getSceneY();
				}
			});
			toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (!windowButtons.isMaximized()) {
						stage.setX(event.getScreenX() - mouseDragOffsetX);
						stage.setY(event.getScreenY() - mouseDragOffsetY);
					}
				}
			});
		}
		// create page tree toolbar
		ToolBar pageTreeToolBar = new ToolBar() {
			@Override
			public void requestLayout() {
				super.requestLayout();
				// keep the height of pageToolBar in sync with pageTreeToolBar
				// so they always match
				if (pageToolBar != null && getHeight() != pageToolBar.prefHeight(-1)) {
					pageToolBar.setPrefHeight(getHeight());
				}
			}
		};
		pageTreeToolBar.setId("page-tree-toolbar");
		pageTreeToolBar.setMinHeight(29);
		pageTreeToolBar.setMaxWidth(Double.MAX_VALUE);
		ToggleGroup pageButtonGroup = new ToggleGroup();
		final ToggleButton allButton = new ToggleButton("All");
		allButton.setToggleGroup(pageButtonGroup);
		allButton.setSelected(true);
		pageTreeToolBar.getItems().addAll(allButton);

		pageTree = new TreeView();
		pageTree.setId("page-tree");

		BorderPane leftSplitPane = new BorderPane();
		leftSplitPane.setTop(pageTreeToolBar);
		leftSplitPane.setCenter(pageTree);
		// create page toolbar
		pageToolBar = new ToolBar();
		pageToolBar.setId("page-toolbar");
		pageToolBar.setMinHeight(29);
		pageToolBar.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);
		breadcrumbBar = new BreadcrumbBar();
		pageToolBar.getItems().add(breadcrumbBar);
		if (!isApplet) {
			Region spacer3 = new Region();
			HBox.setHgrow(spacer3, Priority.ALWAYS);
			Button settingsButton = new Button();
			settingsButton.setId("SettingsButton");
			settingsButton.setGraphic(new ImageView(new Image(IndexFrame.class
					.getResourceAsStream("images/settings.png"))));
			settingsButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					// showProxyDialog();
				}
			});
			settingsButton.setMaxHeight(Double.MAX_VALUE);
			pageToolBar.getItems().addAll(spacer3, settingsButton);
		}
		// create page area
		pageArea = new Pane() {
			@Override
			protected void layoutChildren() {
				for (Node child : pageArea.getChildren()) {
					child.resizeRelocate(0, 0, pageArea.getWidth(), pageArea.getHeight());
				}
			}
		};
		pageArea.setId("page-area");
		// create right split pane
		BorderPane rightSplitPane = new BorderPane();
		rightSplitPane.setTop(pageToolBar);
		rightSplitPane.setCenter(pageArea);
		// create split pane
		splitPane = new SplitPane();
		splitPane.setId("page-splitpane");
		splitPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		GridPane.setConstraints(splitPane, 0, 1);
		splitPane.getItems().addAll(leftSplitPane, rightSplitPane);
		splitPane.setDividerPosition(0, 0.25);

		this.root.setTop(toolBar);
		this.root.setCenter(splitPane);
		// add window resize button so its on top
		if (!isApplet) {
			windowResizeButton.setManaged(false);
			this.root.getChildren().add(windowResizeButton);
		}

		// show stage
		stage.setScene(scene);
		// stage.show();
	}

	/**
	 * Called from JavaScript in the browser when the page hash location changes
	 * 
	 * @param hashLoc
	 *            The new has location, e.g. #SAMPLES
	 */
	public void hashChanged(String hashLoc) {
		if (hashLoc != null) {
			// remove #
			if (hashLoc.length() == 0) {
				hashLoc = null;
			} else {
				hashLoc = hashLoc.substring(1);
			}
			// if new page then navigate to it
			final String path = hashLoc;
			Platform.runLater(new Runnable() {
				public void run() {
					// if (!path.equals(currentPagePath)) goToPage(path);
				}
			});
		}
	}

	/**
	 * Get the URL of the java doc root directory being used to get
	 * documentation from
	 * 
	 * @return Documentation directory URL
	 */
	public String getDocsUrl() {
		return docsUrl;
	}

	/**
	 * Set the URL of the java doc root directory being used to get
	 * documentation from
	 * 
	 * @param docsUrl
	 *            Documentation directory URL
	 */
	public void setDocsUrl(String docsUrl) {
		this.docsUrl = docsUrl;
	}

	/**
	 * Fetch the current hash location from the browser via JavaScript
	 * 
	 * @return Current browsers hash location
	 */
	private String getBrowserHashLocation() {
		String hashLoc = null;
		try {
			hashLoc = (String) browser.eval("window.location.hash");
		} catch (Exception e) {
			try {
				System.out.println("Warning failed to get browser location, retrying...");
				hashLoc = (String) browser.eval("window.location.hash");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		// remove #
		if (hashLoc != null) {
			if (hashLoc.length() == 0) {
				hashLoc = null;
			} else {
				hashLoc = hashLoc.substring(1);
			}
		}
		return hashLoc;
	}

	/**
	 * Show the given node as a floating dialog over the whole application, with
	 * the rest of the application dimmed out and blocked from mouse events.
	 * 
	 * @param message
	 */
	public void showModalMessage(Node message) {
		modalDimmer.getChildren().add(message);
		modalDimmer.setOpacity(0);
		modalDimmer.setVisible(true);
		modalDimmer.setCache(true);
		TimelineBuilder.create().keyFrames(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				modalDimmer.setCache(false);
			}
		}, new KeyValue(modalDimmer.opacityProperty(), 1, Interpolator.EASE_BOTH))).build().play();
	}

	/**
	 * Hide any modal message that is shown
	 */
	public void hideModalMessage() {
		modalDimmer.setCache(true);
		TimelineBuilder.create().keyFrames(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				modalDimmer.setCache(false);
				modalDimmer.setVisible(false);
				modalDimmer.getChildren().clear();
			}
		}, new KeyValue(modalDimmer.opacityProperty(), 0, Interpolator.EASE_BOTH))).build().play();
	}

	/**
	 * Check if current call stack was from back or forward button's action
	 * 
	 * @return True if current call was caused by action on back or forward
	 *         button
	 */
	public boolean isFromForwardOrBackButton() {
		return fromForwardOrBackButton;
	}

	/**
	 * Java Main Method for launching application when not using JavaFX
	 * Launcher, eg from IDE without JavaFX support
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
