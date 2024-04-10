package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.course.Course;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NusNet;
import seedu.address.model.weeknumber.WeekNumber;

class CommandTest {

    @Test
    void isModification() {
        NusNet genericNusNet = new NusNet("E1234567");
        WeekNumber genericWeekNumber = new WeekNumber("1");
        Course genericCourse = new Course("CS2103T");

        // EP: AddPersonCommand
        Command addCommand = new AddPersonCommand(ALICE);
        assertTrue(addCommand.isModification());

        // EP: ClearCommand
        Command clearCommand = new ClearCommand();
        assertTrue(clearCommand.isModification());

        // EP: DeletePersonCommand
        Command deleteCommand = new DeletePersonCommand(genericNusNet);
        assertTrue(deleteCommand.isModification());

        // EP: EditPersonCommand
        Command editCommand = new EditPersonCommand(
              Index.fromOneBased(1),
              new EditPersonCommand.EditPersonDescriptor()
        );
        assertTrue(editCommand.isModification());

        // EP: FindPersonCommand
        Command findCommand = new FindPersonCommand(
              new NameContainsKeywordsPredicate(new ArrayList<>())
        );
        assertFalse(findCommand.isModification());

        // EP: ListPersonCommand
        Command listCommand = new ListPersonCommand();
        assertFalse(listCommand.isModification());

        // EP: MarkAttendanceCommand
        Command markAttendanceCommand = new MarkAttendanceCommand(
              genericNusNet,
              genericWeekNumber
        );
        assertTrue(markAttendanceCommand.isModification());

        // EP: UnmarkAttendanceCommand
        Command unmarkAttendanceCommand = new UnmarkAttendanceCommand(
              genericNusNet,
              genericWeekNumber
        );
        assertTrue(unmarkAttendanceCommand.isModification());

        // EP: HelpCommand
        Command helpCommand = new HelpCommand();
        assertFalse(helpCommand.isModification());

        // EP: ExitCommand
        Command exitCommand = new ExitCommand();
        assertFalse(exitCommand.isModification());

        // EP: SetCourseCommand
        Command setCourseCommand = new SetCourseCommand(genericCourse);
        assertTrue(setCourseCommand.isModification());
    }
}
