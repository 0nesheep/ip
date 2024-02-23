package yippee;

import GUI.DialogBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import yippee.commands.Command;
import yippee.exceptions.YippeeException;

/**
 * Implements the Yippee chatbot that records and store tasks from the user.
 * It is able to add, delete, and mark tasks as complete/incomplete
 */
public class Yippee extends Application {
    private String storePath;
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image user = new Image(getClass().getResourceAsStream("/images/DaDuke.png"));
    private Image duke = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    /**
     * Instantiates the Yippee bot instance.
     * @param filePath Path where data is stored.
     */
    public Yippee(String filePath) {
        this.storePath = filePath;
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        try {
            this.taskList = storage.load();
        } catch (YippeeException e) {
            ui.printError(e);
            this.taskList = new TaskList();
        }
    }

    public Yippee() {

    }

    /**
     * Greets the user.
     */
    public void greet() {
        String name = "Yippee";
        System.out.println("    ____________________________________________________________");
        System.out.printf("      Hello! I'm %s\n", name);
        System.out.println("      What can I do for you?");
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Runs bot instance and processes any inputs from user.
     */
    public void run() {
        greet();
        boolean isExit = false;

        while (!isExit) {
            try {
                String commandString = this.ui.readCommand();
                ui.showLine();
                Command command = new Parser().parseCommand(commandString);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();
            } catch (YippeeException e) {
                ui.printError(e);
            } finally {
                if (!isExit) {
                    ui.showLine();
                }
            }
        }
        this.ui.endCommands();
    }
    
    @Override
    public void start(Stage stage) {
        AnchorPane mainLayout = setUpComponents(stage);
        setDimensions(stage, mainLayout);
        setUserInputDim();

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        setAnchor();

        setInputHandler();


    }

    private AnchorPane setUpComponents(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        return mainLayout;
    }

    private void setDimensions(Stage stage, AnchorPane mainLayout) {
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);
    }

    private void setUserInputDim() {
        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);
    }

    private void setAnchor() {
        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
    }

    private void setInputHandler() {
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText()));
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, new ImageView(user)),
                DialogBox.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    private String getResponse(String input) {
        return "Duke heard: " + input;
    }

    public static void main(String[] args) {
        new Yippee("yippee/data/storage.txt").run();
    }
}
