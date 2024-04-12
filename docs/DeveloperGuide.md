---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---
{% import "_markbind/_macros.nj" as macros %}

# TAPro Developer Guide

<!-- * Table of Contents -->
<page-nav-print>Table of Contents</page-nav-print>

{{ newPage }}

## **Acknowledgements**

* Trie implementation is reused from [eugenp's tutorials](https://github.com/eugenp/tutorials) with minor modifications.

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

{{ newPage }}

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

{{ newPage }}

## **Design**

This section describes the design of various components of TAPro.

<br>

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of TAPro.

Given below is a quick overview of main components and how they interact with each other.

<br>

#### Main components of the architecture

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of TAPro's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of TAPro.
* [**`Logic`**](#logic-component): The command and autocomplete executor.
* [**`Model`**](#model-component): Holds the data of TAPro in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<br>

#### How the architecture components interact with each other

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user 
issues the command `delstu nn/E1234567`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

<box type="success" light>

**#g#Example:##**

The `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. 

Other components interact with a given component through its interface rather than the concrete class, as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

<box type="tip" light>

<span class="semi-bold">Reason:</span> To prevent outside component's being coupled to the implementation of a component.
</box>
</box>

The sections below give more details of each component.

{{ newPage }}

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

**The `UI` component,**

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

{{ newPage }}

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="800"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delstu nn/E1234567")` API 
call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delstu e1234567` Command" />

<box type="info" light>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

**How command execution works in `Logic` component:**

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

<br>

**How autocomplete execution works in `Logic` component:**

1. When `Logic` is called upon to autocomplete an input string, it is passed to an `AddressBookParser` object which in turn matches the input and return the corresponding autocomplete object (e.g. `AutoCompleteCommand`).
1. This results in a `AutoComplete` object (more precisely, an object of one of its subclasses e.g., `AutoCompleteCommand`) which is executed by the `LogicManager`.
1. The autocomplete object is solely responsible for generating the autocomplete suggestions based on the input string (e.g. the additional characters that can be appended to the input string).
1. The result of the autocompletion is simply a string that autocompletes the input string. Autocomplete classes uses [Trie](#trie) under the hood to efficiently generate the autocomplete suggestions.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/><br><br>

**How the parsing works:**
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
  * All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.
* When called upon to parse an autocomplete input, the `AddressBookParser` class checks whether the input contains arguments. If it does not contain arguments, it creates an `AutoCompleteCommand` object which autocompletes Commands. Otherwise, it checks for the last argument in the user input and creates the matching `AutoComplete` object if it exists (e.g. `arbitrary_command arg_a/arbitrary_arg` lead to the `AutoCompleteArgA` object, if it exists). Otherwise, a default `AutoComplete` object that always return an empty string is returned.

{{ newPage }}

### Model component
**API** : [`Model.java`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" /><br><br>

**The `Model` component,**
* stores the contact book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" light>

**An alternative model:**

An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

{{ newPage }}

### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S2-CS2103T-F13-1/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" /><br><br>

**The `Storage` component,**
* can save both contact book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

{{ newPage }}

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

<br>

#### Trie

The `Trie` class is a data structure that allows for efficient prefix matching of strings. It is used in the `AutoComplete` feature to suggest completions for user input.

We added the ability to search for the first word that matches a given prefix. This is useful for the autocomplete feature, where we want to suggest the first word that matches the prefix.

{{ newPage }}

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

<markdown class="d-print-none">---</markdown>
<br>

### Autocomplete Feature

**TODO**

{{ newPage }}

### Command History Retrieval

**TODO**

{{ newPage }}

### \[Proposed\] Undo/Redo Feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current contact book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous contact book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone contact book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

**Step 1.** The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial contact book state, and the `currentStatePointer` pointing to that single contact book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" /><br><br>

**Step 2.** The user executes `delstu nn/E1234567` command to delete student with NUSNet ID as E1234567 from the contact book. 
The `delstu` 
command 
calls 
`Model#commitAddressBook()`, causing the modified state of the contact book after the `delstu nn/E1234567` command 
executes to 
be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted contact book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" /><br><br>

**Step 3.** The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified contact book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" light>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the contact book state will not be saved into the `addressBookStateList`.

</box><br><br>

**Step 4.** The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous contact book state, and restores the contact book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" /><br><br>

**Step 5.** The user wants to set the course name. He enters the command `setcrs CS2103T`, causing the course name to appear on the main window's title.


<box type="info" light>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial `AddressBook` state, then there are no previous `AddressBook` states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" light>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the contact book to that state.

<box type="info" light>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest contact book state, then there are no undone `AddressBook` states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box><br><br>

**Step 6.** The user then decides to execute the command `list`. Commands that do not modify the contact book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" /><br><br>

**Step 7.** The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all contact book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" /><br><br>

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire contact book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delstu`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

{{ newPage }}

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


{{ newPage }}

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

{{ newPage }}

## **Appendix: Requirements**

<br>

### Product scope

**Target user profile**:
* Teaching Assistant for a Computer Science module in NUS
* Tech savvy
* Prefer desktop apps over other types
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: All in one contact book managing student’s progress in the course, by means of participation, grades, and other course specific attributes of an NUS CS class. Can quickly find information, filter and sort with keyboard shortcuts.

{{ newPage }}

### User stories

<box type="definition" light>

**Priorities:** 
* <span class="semi-bold">#g#High:##</span> (must have) - {{ threeStars }}
* <span style="color:#FF7F00;" class="semi-bold">Medium</span> (nice to have) - {{ twoStars }}
* <span class="semi-bold">#r#Low:##</span> (unlikely to have) - {{ oneStar }}
</box>


[//]: # (whitespace is added to force the header row into one line)
{% set whitespace = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' %}

| Priority         | As a …              | I want to …                                                                 | So that I can…                                 |
|------------------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------|
| {{ threeStars }} | TA {{ whitespace }} | name/rename the CS course that I am tutoring this semester                  | keep track of the module I am teaching         |
| {{ threeStars }} | TA                  | add a student to the CS course that I am tutoring that semester to my class | keep track of him or her                       |
| {{ threeStars }} | TA                  | view all students from my class                                             | view details about all of them                 |
| {{ threeStars }} | TA                  | mark attendance for a student in my class                                   | keep track of who's present                    |
| {{ threeStars }} | TA                  | unmark attendance for a student in my class                                 | keep track of who is absent                    |
| {{ threeStars }} | TA                  | delete a student                                                            | remove a student if he or she leaves the class |
| {{ threeStars }} | TA                  | know all the commands of TAPro                                              | use it effectively                             |

**TODO: Add more user stories that are applicable**

*{More to be added}*

{{ newPage }}

### Use cases

<box type="info" light>

For all use cases below, the **System** is TAPro and the **Actor** is the user, unless specified otherwise.
</box>
<br>

<box no-icon type="success" light>

**Use case: Add a Student**

<span class="semi-bold">MSS</span>

1. User requests to add a student, providing the name and NUSNet ID as compulsory information, with the phone number being optional.

   Use case ends.

</box>

<box no-icon type="success" light>

**TODO: Use case: Edit a Student**

</box>

<box no-icon type="success" light>

**Use case: Delete a Student**

<span class="semi-bold">MSS</span>

1. User requests to delete a specific student based on NUSNet ID.

   Use case ends.

<span class="semi-bold">Extensions</span>

* 1a. No such student exists.

    * 1a1. TAPro shows an error message.

    * Use case ends.

</box>

<box no-icon type="success" light>

**Use case: Name or Rename CS Course**

<span class="semi-bold">MSS</span>

1. User requests to name or rename a CS course by specifying the course name and the new name if applicable.

   Use case ends.

</box>

<box no-icon type="success" light>

**Use case: View All Students**

<span class="semi-bold">MSS</span>

1. User requests to view a list of all students.

    Use case ends.

</box>

<box no-icon type="success" light>

**TODO: Use case: Find a Student by Name**

</box>

<box no-icon type="success" light>

**Use case: Mark Attendance**

<span class="semi-bold">MSS</span>

1. User requests to mark attendance for a student by providing the student's NUSNet ID.

   Use case ends.

</box>

<box no-icon type="success" light>

**Use case: Unmark Attendance**

<span class="semi-bold">MSS</span>

1. User requests to unmark attendance for a student by providing the student's NUSNet ID.

   Use case ends.

</box>

<box no-icon type="success" light>

**TODO: Use case: Clear All Data**

</box>

<box no-icon type="success" light>

**Use case: Know Available Commands in TAPro**

<span class="semi-bold">MSS</span>

1. User requests to view the list of available commands for TAPro.

   Use case ends.

</box>

<box no-icon type="success" light>

**Use case: Autocompletion of Command Inputs**

<span class="semi-bold">MSS</span>

1. User focuses on the command box.

2. User presses the autocompletion hotkey.

3. Autocompleted command is shown in the command box.
   Use case ends.

<span class="semi-bold">Extensions</span>

* 3a. No autocompletion is available for the current input.

    * 3a1. No action is taken.

    * Use case ends.

</box>

<box no-icon type="success" light>

**TODO: Use case: Retrieve a previous successful command input**

</box>

**TODO: Add more use cases**

*{More to be added}*

{{ newPage }}

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java 11 installed.
2.  System to load the main interface in under 1 second on standard educational institution hardware.
3.  Application to be accessible on devices commonly used by the educational institution, such as desktop computers, laptops, and tablet.
4.  System to ensure data integrity, with a goal of zero data loss over the academic year.
5.  Input validation to prevent errors due to incorrect data entry.
6.  Application to handle common errors gracefully, such as incorrect data entry, without crashing or losing data.

*{More to be added}*

{{ newPage }}

### Glossary

{{ macros.definitionBox('TAPro', 'The name of our product') }}
{{ macros.definitionBox('Mainstream OS', 'Windows, Linux, Unix, MacOS') }}
{{ macros.definitionBox('CS', 'Computer Science') }}
{{ macros.definitionBox('NUS', 'National University of Singapore') }}
{{ macros.definitionBox('TA', 'Teaching Assistant') }}
{{ macros.definitionBox('NUSNet ID', 'A unique identifier for each student in NUS') }}
{{ macros.definitionBox('API', 'Application Programming Interface') }}
{{ macros.definitionBox('CLI', 'Command Line Interface') }}
{{ macros.definitionBox('UI', 'User Interface') }}
{{ macros.definitionBox('GUI', 'Graphical User Interface') }}
{{ macros.definitionBox('ASCIIbetical Order', 'An ordering where numbers and most punctuations are before letters, and uppercase letters before lowercase letters.') }}

{{ newPage }}

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually. 

<box type="info" light>

**Prerequisites:** For all features to test below, TAPro is already downloaded, only single instance of TAPro is already opened, and Java 11 or above is installed, unless specified otherwise.
</box>

<box type="info" light>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<br>

### Launch and shutdown

1. **Initial launch**

<box type="info" light>

<span class="semi-bold">1. Prerequisites:</span> TAPro is not downloaded, and an Internet connection is present.
</box>

<box type="success" light>

2. Download the latest TAPro jar file from [here](https://github.com/AY2324S2-CS2103T-F13-1/tp/releases) and move it into an empty folder. 

3. Ensure that the jar file is still named as `TAPro.jar` after moving.

4. Open a command terminal, and `cd` into that folder.

5. Run `java -jar TAPro.jar`.

Expected: TAPro launches and shows the GUI with a set of sample student contacts. The window size may not be optimal.
</box>

2. **Saving window preferences**

<box type="info" light>

<span class="semi-bold">1. Prerequisites:</span> No prerequisites.
</box>

<box type="success" light>

2. Resize the window to an optimum size. Move the window to a different location. Close the window.

3. Re-launch the app by double-clicking the jar file.<br>

Expected: The most recent window size and location is retained.
</box>

3. _{ more test cases …​ }_

<br>

### Adding a student
If TAPro does not have any student contacts, the following commands can be used to add some 
students.

1. **Adding a student with NUSNet ID E0123456**

<box type="info" light>

<span class="semi-bold">1. Prerequisites:</span> No student with NUSNet ID E0123456 in TAPro.
</box>

<box type="success" light>

<span class="semi-bold">2. Test case: `addstu n/John Doe p/98765432 e/johndoe@example.com nn/E0123456 m/Computer Science, #02-25 t/friends t/owesMoney`</span>

Expected: Student with NUSNet ID `E0123456` is added into TAPro. Details of the added student is
shown in the status message.
</box>
      
1. **Adding a student with NUSNet ID E0123457**

<box type="info" light>

<span class="semi-bold">1. Prerequisites:</span> Prerequisites: No student with NUSNet ID E0123457 in TAPro.
</box>

<box type="success" light>

<span class="semi-bold">2. Test case: `addstu n/Mary Jane p/91234911 e/janemary@example.com nn/E0123457 m/Biology t/friends t/owesTutorial2`</span>

Expected: Student with NUSNet ID `E0123457` is added into TAPro. Details of the added student is
shown in the status message.
</box>

<br>

### Editing a student

**TODO**

<br>

### Deleting a student

1. **Deleting a student**

<box type="info" light>

<span class="semi-bold">1. Prerequisites:</span> TAPro contains at least one student with NUSNet ID E0123456, and no student with NUSNet ID E6543210.
</box>

<box type="success" light>

<span class="semi-bold">2. Test case: `delstu nn/E0123456`</span>

Expected: The student with NUSNet ID `E0123456` is deleted from TAPro. Details of the deleted student
shown in the status message.
</box>

<box type="wrong" light>

<span class="semi-bold">3. Other incorrect `delstu` commands to try:</span>
* `delstu`: Missing parameter and prefix.
* `delstu nn/E6543210`: No student with this NUSNet ID.
* `delstu E0123456`: Missing prefix for the NUSNet ID parameter.
</box>

2. _{ more test cases …​ }_

<br>

### Finding a student

**TODO**

<br>

### Marking a student's attendance

1. **Marking attendance for a student**

<box type="info" light>

<span class="semi-bold">1. Prerequisites:</span> TAPro contains one student with NUSNet ID E0123456, and no student with NUSNet ID E6543210.
</box>

<box type="success" light>

<span class="semi-bold">2. Test case: `mark nn/E0123456 wk/1`</span>

Expected: Student with NUSNet ID `E0123456` is marked as present for week 1 in TAPro, depicted on that student's card in the panel.
Details of the marked student is shown in the status message.
</box>

<box type="wrong" light>

<span class="semi-bold">3. Examples of incorrect `mark` commands to try:</span>
* `mark`: Missing NUSNet ID and week number parameters.
* `mark nn/E6543210 wk/1`: No student with this NUSNet ID.
* `mark wk/1`: Missing the NUSNet ID parameter.
* `mark E0123456 1`: Missing prefix for the NUSNet ID and week number parameters.
</box>

<br>

### Unmarking a student's attendance

**TODO**

<br>

### Setting the course name

1. **Setting a course name**

<box type="info" light>

<span class="semi-bold">1. Prerequisites:</span> No prerequisites.
</box>

<box type="success" light>

<span class="semi-bold">2. Test case: `setcrs CS2103`</span>

<box type="info" light>

Enter `setcrs` followed by a whitespace, followed by a course code in the format `XXYYYYZ`, where `X` and `Z` can be any letter
in upper or lower case, `YYYY` can be any 4-digit number and `Z` is optional.
</box>

Expected: TAPro's main window's title contains the course code `CS2103` provided.
</box>

<br>

### Autocompleting fields

**TODO**

<br>

### Retrieving previous successful commands

**TODO**

<br>

### Accessing help

**TODO**

<br>

### Clearing all data

**TODO**

<br>

### Saving data

1. **Dealing with missing/corrupted data files**

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_


{{ newPage }}

## **Appendix: Design Decisions**

<br>

1. **Why does `edit` command use `INDEX` as identifier instead of `NUSNET`?**
    1. For our users, using `edit nn/E0123456 nn/E1234567` is unintuitive to edit the NUSNet ID of a student.
    1. `INDEX` is visually easier to reference and requires less effort to type.
    1. The alternative solution we considered was to disallow editing of `NUSNET`, but this would be a limitation on
       the user's freedom, or would necessitate that the user deletes the student and re-enter all the details again.
    
   <br>
   <box type="info" light>

   **Note:** `delstu` command uses `NUSNET` as identifier because it requires more intentional effort and
   therefore ensures that the TA intends to perform this dangerous action.
   </box>
   <br>

1. **Why does autocomplete sort in [ASCIIbetical order](https://en.wikipedia.org/wiki/ASCII#Character_order) and not normal alphabetical order?**
   1. The only difference ASCIIbetical order has from normal alphabetical order, is that uppercase letters are ordered before lowercase letters.
   2. For most parameters this doesn't make a difference, due to case-insensitivity or validation checks that enforce a certain format.
   3. But for the tag parameter, `TAG`, it is case-sensitive, so `LATE` and `late` are two different tags.
   4. A user can use uppercase for emphasis and to give priority to that tag, in the autocompletion order, thus allowing the user to autocomplete a certain tag more easily, and without much hassle.

    <br>
    <box type="success" light>

    **#g#Example:##** If a user uses the following tags: `LATE`, `early`, `onTime`, to tag their students based on their latest submission, then `LATE` would appear before `early` and `onTime` in the autocompletion of a blank tag parameter.
     </box>

   5. The alternative solution we considered was to use normal alphabetical ordering, but this meant that there would not be priority given to uppercase tags, without explicitly adding a number before that tag, which is less intuitive.
   6. Another alternative solution we considered was to allow the user to set the priority of the values in the autocompletion, but this meant adding unnecessary verbosity to the command format, which may not be that intuitive for new users.
   <br><br>
     
1. **Why does autocompletion only give values that are already present instead of predicting the next result?**
   1. It is performance intensive to predict the next autocompletion based on all possible values.
   2. It will also use up significant storage space to store the possible autocompletions, which would not meet the application size requirements required for this course.
   3. Moreover, autocompletion of certain unused words may be redundant, as not all words would be even used.
   4. Hence, we decided to just perform autocompletion based on the available values that are stored in TAPro, as it increases the usefulness of this feature in this context.

   <br>

1. **Why does autocomplete not work when the command input box is empty?**
   1. The alternative solution we considered is when the command input box is empty, autocomplete scrolls through all possible commands. However, it has a few issues.
   2. It is much faster typing out the first few letters of the command you want to autocomplete, and then autocompleting, since regular users would remember the commands to use.
   3. If it was a new user, then the new user would refer to help and our user guide instead of using the autocomplete to see what options there are.
   4. Moreover, for the new user, the autocomplete feature may be confusing at first, so by omitting autocomplete on an empty input, it prevents accidental misunderstandings on what {{ macros.keyFormat('Tab') }} should do.
   5. Furthermore, we do not want new users to execute a command without understanding what it did first, as it may cause irreversible data loss (`clear` command). We wanted a new user to understand the command, by referring to help or our user guide on what a command did, in order to prevent a situation where data is accidentally lost.


<div style="page-break-after: always;"></div>

5. **Why are placeholder values valid input?**
    1. There is no simple way to edit a student's optional attribute back to the default without the using placeholder value (`X not provided`, where X is the attribute), without having to perform two commands: delete the student and add back the student if a value was incorrectly filled when it is not available to the user.
    2. With autocompletion, a user can easily fill in the placeholder value, so it is much easier to set an attribute back to the placeholder value.

    <br>
    <box type="success" light>

   **#g#Example:##** If a user doesn't have all phone numbers, then the parameter value `Phone number not provided` may be a possible value that they will use later on, and so the autocomplete helps make the process easier for the user to reset the value if necessary.
   </box>

    3. Hence, we allowed the placeholder value as valid input to serve this purpose.
    4. The alternative solution we considered was to leave the parameter value empty to set it back to the default, but there is an issue with it. Users may accidentally set the parameter value as empty, resulting in the accidental lost of an attribute's value.
    5. As there is no way to undo this change easily, we decided to use the placeholder value for this purpose, so that in order to reset an attribute, the user has to intentionally want to do so, by typing out the full placeholder value (`X not provided`, where X is the attribute).

   <br>
   <box type="success" light>

   **#g#Example:##** Assuming we used the alternative solution, if a user inputs `edit 1 p/` and accidentally presses enter, then the phone number of the student at index 1 in the user's contact list would be erased to `Phone number not provided`.

   Although this was the user's accidental mistake, it resulted in the user needing to do more work to restore back the change. Hence, we decided to use the placeholder value, so that the user will be less likely to accidentally reset an attribute by mistake.
   </box>

    6. Furthermore, autocomplete has placeholder values present only when they are necessary, which helps against accidentally removing an optional attribute of a student.

    <br>
    <box type="success" light>

   **#g#Example:##** Usually, if a user would store all phone numbers or no phone numbers at all, depending on their needs.

   If a user has all phone numbers present, then the parameter value `Phone number not provided` would not be available as an autocompletion, to prevent accidental lost of a student's phone number by mistake.
   </box>
   <br>

6. **Why does command history toggle only retrieve successful commands?**
   1. The alternative we considered was to store the history of all command inputs.
   2. However, unsuccessful command inputs are not that helpful to a user, and it only clutters up a user's command history, resulting in more time wasted searching for a successful command input.
   3. Hence, storing only successful command inputs in the command history enabled users to be more efficient at using TAPro.
   <br>



{{ newPage }}

## **Appendix: Effort**

**TODO**

{{ newPage }}

## **Appendix: Planned Enhancements**

<box type="info" light>

**Team size:** 5
</box>


**TODO: Add feature flaws here**
