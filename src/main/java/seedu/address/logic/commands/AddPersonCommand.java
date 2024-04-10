package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.util.CommandMessageUsageUtil.generateMessageUsage;
import static seedu.address.logic.commands.util.ParameterSyntax.PARAMETER_EMAIL;
import static seedu.address.logic.commands.util.ParameterSyntax.PARAMETER_MAJOR;
import static seedu.address.logic.commands.util.ParameterSyntax.PARAMETER_NAME;
import static seedu.address.logic.commands.util.ParameterSyntax.PARAMETER_NUSNET;
import static seedu.address.logic.commands.util.ParameterSyntax.PARAMETER_PHONE;
import static seedu.address.logic.commands.util.ParameterSyntax.PARAMETER_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the contact book.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "addstu";

    public static final String MESSAGE_USAGE = generateMessageUsage(
            COMMAND_WORD,
            "Adds a student into the contact book. ",
            PARAMETER_NAME,
            PARAMETER_NUSNET,
            PARAMETER_PHONE.asOptional(true),
            PARAMETER_EMAIL.asOptional(true),
            PARAMETER_MAJOR.asOptional(true),
            PARAMETER_TAG.asMultiple(2)
    );

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_NUSNET = "Another student with the same NUSNet ID "
            + "already exists in the contact book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            // This should not fail because the person is already in the model
            Person existing = model.getPersonByNusNet(toAdd.getNusNet()).orElseThrow();

            if (existing.getName().equals(toAdd.getName())) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } else {
                throw new CommandException(MESSAGE_DUPLICATE_NUSNET);
            }
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean isModification() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonCommand)) {
            return false;
        }

        AddPersonCommand otherAddCommand = (AddPersonCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
