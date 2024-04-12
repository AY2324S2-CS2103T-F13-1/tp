package seedu.address.ui;

import static seedu.address.ui.util.SyntaxHighlighter.ERROR_STYLE_CLASS;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.autocomplete.AutoComplete;
import seedu.address.logic.autocomplete.AutoCompleteResult;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final AutoCompleteExecutor autoCompleteExecutor;

    private String lastModifiedText = "";
    private KeyCode previousKeyCodePressed;
    private AutoCompleteResult autoCompleteResultCache;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, AutoCompleteExecutor autoCompleteExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.autoCompleteExecutor = autoCompleteExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.textProperty().addListener((observable, oldValue, newValue) ->
                updateLastModifiedText(newValue)
        );
        commandTextField.setOnKeyPressed(this::handleKeyPressEvent);
    }

    /**
     * Updates {@link #lastModifiedText},
     * only if it is not updated with the latest {@link #commandTextField},
     * and clears the autoCompleteResultCache.
     *
     * @param newText The text to set {@link #lastModifiedText} to.
     */
    private void updateLastModifiedText(String newText) {
        assert previousKeyCodePressed != null;

        if (!previousKeyCodePressed.equals(KeyCode.TAB)) {
            lastModifiedText = newText;
            autoCompleteResultCache = null;
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the Up and Down arrow key pressed event.
     */
    private void handleArrowKeyPressed(KeyEvent event) {
        String direction = event.getCode().toString();
        CommandHistory commandHistory = CommandHistory.getInstance();
        String previousCommand = commandHistory.getCommandHistory(direction);
        commandTextField.setText(previousCommand);

        // Move cursor to end
        commandTextField.end();
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private void handleKeyPressEvent(KeyEvent e) {
        previousKeyCodePressed = e.getCode();

        // handle certain key events that update text in a different order
        updateLastModifiedText(commandTextField.getText());

        switch (e.getCode()) {
        case TAB:
            handleTabKeyPressEvent(e);
            break;
        case UP:
            // Fallthrough
        case DOWN:
            handleArrowKeyPressed(e);
            break;
        default:
            break;
        }
    }

    private void handleTabKeyPressEvent(KeyEvent e) {
        // Prevent the default Tab behavior
        e.consume();

        if (autoCompleteResultCache == null) {
            autoCompleteResultCache = autoCompleteExecutor.getAutoComplete(lastModifiedText);
        }

        commandTextField.setText(lastModifiedText + autoCompleteResultCache.getNextResult());

        // Request focus on the commandTextField
        commandTextField.requestFocus();

        // Move the cursor to the end of the text
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface AutoCompleteExecutor {
        /**
         * Returns the autocomplete results based on the input.
         *
         * @see AutoComplete#getAutoComplete(String)
         */
        AutoCompleteResult getAutoComplete(String input);
    }
}
