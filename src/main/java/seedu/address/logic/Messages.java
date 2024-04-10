package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.SetCourseCommand;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 * <p>
 * Messages that are not command-specific are listed here.
 * <p>
 * Messages that are command-specific should be in their respective classes.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The student's index provided is invalid";
    public static final String MESSAGE_MISSING_NUSNET = "No such student with the NUSNet ID found in contacts!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d students listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This student already exists in the contact book";

    /** Error messages are shown when a command cannot be executed. */
    private static final String[] ERROR_MESSAGES = {
        MESSAGE_UNKNOWN_COMMAND,
        MESSAGE_INVALID_COMMAND_FORMAT,
        MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
        MESSAGE_MISSING_NUSNET,
        MESSAGE_DUPLICATE_FIELDS,
    };

    /** Neutral messages are shown when a command executes successfully, but no change occurs. */
    private static final String[] NEUTRAL_MESSAGES = {
        MESSAGE_PERSONS_LISTED_OVERVIEW,
        EditPersonCommand.MESSAGE_NOT_EDITED,
        ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
        HelpCommand.SHOWING_HELP_MESSAGE,
        ListPersonCommand.MESSAGE_SUCCESS,
        MarkAttendanceCommand.MESSAGE_MARK_EXISTING_ATTENDANCE_SUCCESS,
        UnmarkAttendanceCommand.MESSAGE_UNMARK_NONEXISITING_ATTENDANCE_SUCCESS
    };

    /** Success messages are shown when a command executes successfully and does a modification. */
    private static final String[] SUCCESS_MESSAGES = {
        AddPersonCommand.MESSAGE_SUCCESS,
        ClearCommand.MESSAGE_SUCCESS,
        DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS,
        EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS,
        MarkAttendanceCommand.MESSAGE_MARKED_ATTENDANCE_SUCCESS,
        SetCourseCommand.MESSAGE_SUCCESS,
        UnmarkAttendanceCommand.MESSAGE_UNMARKED_ATTENDANCE_SUCCESS,
    };

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        ArrayList<String> duplicateFieldsArray = new ArrayList<>(duplicateFields);
        duplicateFieldsArray.sort(String::compareToIgnoreCase);

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFieldsArray);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; NUSNET: ")
                .append(person.getNusNet())
                .append("; Major: ")
                .append(person.getMajor())
                .append("; Attendance: ");
        String combinedString = person.getAttendance().stream()
                .map(weekNumber -> weekNumber.toString() + ", ")
                .collect(Collectors.joining());
        if (!combinedString.isEmpty()) {
            combinedString = combinedString.substring(0, combinedString.length() - 2);
        }
        builder.append(combinedString);
        builder.append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns true if the {@code message} is a message representing the failure of a command.
     * Otherwise, false is returned.
     *
     * @param message The message to check.
     * @see StringUtil#stripMessageFormatSpecifiers(String)
     */
    public static boolean isErrorMessage(String message) {
        requireNonNull(message);
        return Arrays
                .stream(ERROR_MESSAGES)
                .map(StringUtil::stripMessageFormatSpecifiers)
                .anyMatch(message::contains);
    }

    /**
     * Returns true if the {@code message} is a message representing the success of a command.
     * Otherwise, false is returned.
     *
     * @param message The message to check.
     * @see StringUtil#stripMessageFormatSpecifiers(String)
     */
    public static boolean isSuccessMessage(String message) {
        requireNonNull(message);
        return Arrays
                .stream(SUCCESS_MESSAGES)
                .map(StringUtil::stripMessageFormatSpecifiers)
                .anyMatch(message::contains);
    }
}
