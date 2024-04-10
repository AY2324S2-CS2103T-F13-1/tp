package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.util.CommandMessageUsageUtil.generateMessageUsage;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the contact book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = generateMessageUsage(
            COMMAND_WORD,
            "Deletes all students from the contact book.",
            COMMAND_WORD
    );
    public static final String MESSAGE_SUCCESS = "Contact book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isModification() {
        return true;
    }
}
