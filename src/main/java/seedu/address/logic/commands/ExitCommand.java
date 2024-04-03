package seedu.address.logic.commands;

import seedu.address.model.Model;

import static seedu.address.logic.commands.util.CommandMessageUsageUtil.generateMessageUsage;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_USAGE = generateMessageUsage(
            COMMAND_WORD,
            "Closes the application.",
            COMMAND_WORD
    );

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Contact Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
