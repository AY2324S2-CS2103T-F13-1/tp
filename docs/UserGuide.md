---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAPro User Guide

TAPro is a **Contact Book application** that is made for Computer Science Tutors for managing their students contact and attendance details.

TAPro is optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). 

If you can type fast, TAPro can get your contact management and attendance taking tasks done **faster** than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

## <i class="fa-solid fa-forward"></i> Quick start

1. Ensure you have Java 11 or above installed in your computer.

<box type="tip" seamless>

<i class="fa-solid fa-download"></i>
**Installing Java:**

If you do not have Java 11 or above installed, you can download the latest available version of Java from [here](https://www.oracle.com/sg/java/technologies/downloads/).
The latest version is compatible with older versions.

</box>

2. Download the latest version of `TAPro.jar` from [here](https://github.com/AY2324S2-CS2103T-F13-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TAPro.

1. Open a command terminal, `cd` into the folder you downloaded the jar file in, and run the `java -jar TAPro.jar` command to launch the application.

<box type="info" seamless>

A GUI similar to the below image should appear in a few seconds. Note how the app contains some sample data.

<img alt="Main Window UI on first launch" src="images/excalidrawUG/inital_launch_final.png" class="rounded-image" />

</box>
<div style="page-break-after: always;"></div>

5. Type the command in the command box and press <span class="badge bg-light text-dark">Enter</span> to execute it. e.g. typing **`help`** and pressing <span class="badge bg-light text-dark">Enter</span> will open the help window.

<box type="info" seamless>

**Here are some example commands you can try:**

* `list` : Lists all contacts.

* `addstu nn/e0952224 n/John Doe p/98765432 e/johnd@example.com m/Computer Science` : Adds a student named `John Doe` to the contact book.

* `delstu nn/NUSNET_ID` : Deletes the student with the specified `NUSNET_ID` from the contact book.

* `clear` : Deletes all students, and their contact and attendance information.

* `exit` : Exits the application.

</box>

6. Refer to the sections under [Features](#features) below for details of each command.

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

## <i class="fa-solid fa-font-awesome"></i> Features

<box type="info" seamless>

**About the command format:**<br>

<box type="success" seamless>

<span class="semi-bold">Words in `UPPER_CASE` are the parameters to be supplied by the user.</span>

<span class="semi-bold">#g#Example:##</span> In `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.
</box>


<box type="success" seamless>

<span class="semi-bold">Items in square brackets are optional.</span>

<span class="semi-bold">#g#Example:##</span> `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
</box>

<box type="success" seamless>

<span class="semi-bold">Items with `…`​ after them can be used multiple times including zero times.</span>

<span class="semi-bold">#g#Example:##</span> `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.
</box>


<box type="success" seamless>

<span class="semi-bold">Parameters can be in any order.</span>

<span class="semi-bold">#g#Example:##</span> If the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
</box>

<box type="success" seamless>

<span class="semi-bold">Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.</span>

<span class="semi-bold">#g#Example:##</span> If the command specifies `help 123`, it will be interpreted as `help`.
</box>

</box>

<box type="warning" seamless>

**If you are using a PDF version of this document:**

Be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-circle-question"></i> Viewing help : `help`

Entering the `help` command opens a help window that shows a message explaining how to access the help page, 
as well as quick reference of each command with simple examples on how to use them.

Format: `help`

<box type="info" seamless>

<i class="fa-regular fa-window-restore"></i>
**A new window is opened:**

A new window is opened, so that it does not clutter up your main window. A GUI similar to the below image should appear.

<img src="images/help_window_ui.png" alt="Help Window UI" class="rounded-image" width="800px"/>

</box>

<box type="tip" seamless>

<i class="fa-solid fa-maximize"></i>
**Resizing the help window:**

The help window is resizable, so you can **easily reposition and resize** it to fit anywhere on your screen, exactly where you want it to be.
</box>

<box type="tip" seamless>

<i class="fa-solid fa-window-restore"></i>
**Switching quickly between the help and main window:**

* On Windows and most Linux distributions, you can use the keyboard shortcut: <span class="badge bg-light text-dark">Alt</span> +  <span class="badge bg-light text-dark">Tab</span>, to switch between windows quickly. 

* On macOS, you can use the keyboard shortcut: <span class="badge bg-light text-dark">Cmd⌘</span> + <span class="badge bg-light text-dark">Tab</span>, to switch between windows quickly.

</box>

<box type="info" seamless>

**More about help's quick reference:**

The quick reference is meant for **fast and reliable** lookup of commands and their usages, without needing an internet connection and opening a browser to get an overview of the commands.

* It is not meant to contain detailed information about each command. To access detailed information about each command, you can refer to our user guide.
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-chalkboard"></i> Name/Rename CS course : `setcrs`

Names the course in question. 

Format: `setcrs COURSE_NAME`

Duplicate course are not allowed.
Courses are case-insensitive.
Course code should follow the format "XX1234Y", Y is optional.

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-user-plus"></i> Adding a student: `addstu`
Adds a student to the contact book.

Format: `addstu n/NAME nn/NUSNET [p/PHONE] [e/EMAIL] [m/MAJOR] [t/TAG]…​`

* Add a student with the given details.
* The name and nusnet id must be provided. And nusnet id must be unique.
* All the remaining fields are optional. If not provided, a placeholder value will be used.

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `addstu n/John Doe nn/e1234567 p/98765432 e/johnd@example.com m/Computer Science`
* `addstu n/Betsy Crowe nn/e01234567 t/friend e/betsycrowe@example.com m/Mathematics p/1234567 t/club`
* `addstu n/Betsy Crowe nn/e01234567`

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-list-ul"></i> Listing all students : `list`

Shows a list of all persons in the contact book.

Format: `list`

If there are additional arguments behind `list` we will simply ignore them.

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-user-pen"></i> Editing a student : `edit`

Edits an existing person in the contact book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [m/MAJOR] [nn/NUSNET_ID] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email contact of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

<box type="info" seamless>
This command differs from most other commands that use the `NUSNET_ID` to identify a student. This command uses the index number shown in the displayed person list to identify the student to be edited.
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-magnifying-glass"></i> Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]…​`

<box type="info" seamless>

**Parameters of `find` command:**

- `KEYWORD` is the first keyword to search for. This is a compulsory parameter.
- `[MORE_KEYWORDS]…​` are additional, optional keywords to search for. You can have any number of additional keywords.

</box>

<box type="info" seamless>

**About the `find` command:**

<box type="success" seamless>

<span class="semi-bold">Only the name is searched.</span>

<span class="semi-bold">#g#Example:##</span> `E01234567` will not match a student with NUSNet ID `E01234567`.
</box>

<box type="success" seamless>

<span class="semi-bold">The name search is case-insensitive.</span>

<span class="semi-bold">#g#Example:##</span> `hans` will match `Hans`
</box>


<box type="success" seamless>

<span class="semi-bold">The order of the keywords does not matter.</span>

<span class="semi-bold">#g#Example:##</span> `Hans Bo` will match `Bo Hans`
</box>

<box type="success" seamless>

<span class="semi-bold">Only full words will be matched.</span>

<span class="semi-bold">#g#Example:##</span> `Han` will not match `Hans`
</box>

<box type="success" seamless>

<span class="semi-bold">Students' name matching at least one keyword will be returned (i.e. `OR` search).</span>

<span class="semi-bold">#g#Example:##</span> `Hans Bo` will return `Hans Gruber`, `Bo Yang`
</box>
</box>

<box type="success" seamless>

**Examples of using the find command:**

<box type="success" seamless>

<span class="semi-bold">#g#Example:##</span> `find John` returns `john` and `John Doe`
</box>


<box type="success" seamless>

<span class="semi-bold">#g#Example:##</span> `find alex david` returns `Alex Yeoh`, `David Li`, as seen in the image below.

<img src="images/findAlexDavidResult.png" alt="result for 'find alex david" width="500px" class="rounded-image"/>
</box>
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-square-check"></i> Marking a student's attendance for a given week by their NUSNet: `mark`

Marks a student's attendance for a particular week.

Format: `mark nn/NUSNET_ID wk/WEEK_NUMBER`

Example:
* `mark nn/e1234567 wk/3`

<markdown class="d-print-none">---</markdown>
<br>

### <i class="fa-solid fa-square-xmark"></i> Unmarking a student's attendance for a given week by their NUSNet: `unmark`

Unmarks a student's attendance for a particular week.

Format: `unmark nn/NUSNET_ID wk/WEEK_NUMBER`

Example:
* `unmark nn/e1234567 wk/3`

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-trash"></i> Deleting a student: `delstu`

Deletes the specified student from the contact book.

Format: `delstu nn/NUSNET_ID`

* Deletes the student with the specified NUSNet ID from the contact book. 

Examples:
* `delstu nn/E0957499` deletes the student with the NUSNet ID of `E0957499` in the contact book.

Pro Tip:
* If you cannot remember your student's NUSNet ID, you could use `find Betsy` or `list` followed by `delstu nn/<Betsy's NUSNET_ID>` to find and delete the student.

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-broom"></i> Clearing all entries : `clear`

Clears all entries from the contact book.

Format: `clear`

<markdown class="d-print-none">---</markdown>
<br>

### <i class="fa-solid fa-right-from-bracket"></i> Exiting the program : `exit`

Exits the program.

Format: `exit`

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-wand-magic-sparkles"></i> Autocomplete

Autocompletes a word or a set of words, based on the current input.

<box type="warning" seamless>

**Autocomplete doesn't work on week number:**

This is because week number is short,
and it is much faster just typing out the number.

</box>

<box type="success" seamless>

<span class="semi-bold">#g#Example:##</span>

We have three students with NUSNet IDs `e0123456`, `e1234567` and `e2345678`. 
If we type `mark nn/` into the command input box and press <span class="badge bg-light text-dark">Tab</span>,
we can see that the text in the command input box autocompletes to become `mark nn/e01234567`.

Pressing <span class="badge bg-light text-dark">Tab</span> again, causes the text to update to `mark nn/e1234567`,
followed by `mark nn/e2345678`.
</box>

<box type="info" seamless>

**Using autocomplete to scroll through all possible autocomplete suggestions:**

Autocomplete will scroll through all possible options, based on the existing data in your contact list.

When reaching the end of the possible options list, pressing
<span class="badge bg-light text-dark">Tab</span> will wrap 
the possible options back to the start of that list again.
</box>

<box type="info" seamless>

**Sorted autocomplete suggestions:**

The autocomplete suggestions will be listed in alphabetical order, meaning if you have two possible options
`abc` and `abd`, autocomplete will give `abc` before `abd`.
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-clock-rotate-left"></i> Retrieving command history

TAPro saves successful commands input, so you can retrieve them later, using the 
<span class="badge bg-light text-dark">Up</span> and
<span class="badge bg-light text-dark">Down</span> keys.

<box type="success" seamless>

<span class="semi-bold">#g#Example:##</span>

We have entered `mark nn/e0123456` as the previous command, and it was successful. Now the command input box is empty.

Pressing <span class="badge bg-light text-dark">Up</span> will update the text in the command input box to
the previous command `mark nn/e0123456`.
</box>

<box type="warning" seamless>

**Only successful commands will be retrieved:**

If you enter an invalid command, it will not appear when attempting to retrieve it.
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

### <i class="fa-solid fa-floppy-disk"></i> Saving the data

TAPro's data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<markdown class="d-print-none">---</markdown>
<br>

### <i class="fa-solid fa-pen-to-square"></i> Editing the data file

TAPro's data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**

If your changes to the data file makes its format invalid, TAPro will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>

Furthermore, certain edits can cause the TAPro to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

## <i class="fa-solid fa-comments"></i> FAQ

<box type="info" icon=":fa-solid-question:" seamless>

<i class="fa-regular fa-circle-question"></i>
**Q:** How do I transfer my data to another computer?

<i class="fa-regular fa-comment-dots"></i>
**A:** Install the application in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TAPro home folder.
</box>



<markdown class="d-print-none">---</markdown>
<br>

## <i class="fa-solid fa-triangle-exclamation"></i> Known issues


<box type="warning" theme="danger" seamless>

**1. When using multiple screens**

If you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
</box>

<markdown class="d-print-none">---</markdown>
<div style="page-break-after: always;"></div>
<br>

## <i class="fa-solid fa-table-list"></i> Command summary

| Action             | Format, Examples                                                                                                                                                                       |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Student**    | `addstu n/NAME p/PHONE_NUMBER e/EMAIL nn/NUSNET_ID m/MAJOR [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com nn/e1234567 m/Computer Science t/friend t/colleague` |
| **Clear**          | `clear`                                                                                                                                                                                |
| **Delete Student** | `delstu nn/NUSNET_ID`<br> e.g., `delstu nn/e0957499`                                                                                                                                   |
| **Edit**           | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [m/MAJOR] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                              |
| **Mark**           | `mark nn/NUSNET_ID wk/WEEK_NUMBER`<br> e.g., `mark nn/e1234567 wk/3`                                                                                                                   |
| **Unmark**         | `unmark nn/NUSNET_ID wk/WEEK_NUMBER`<br> e.g., `unmark nn/e1234567 wk/3`                                                                                                               |
| **Find**           | `find KEYWORD [MORE_KEYWORDS]…​`<br> e.g., `find James Jake`                                                                                                                           |
| **Set Course**     | `setcrs COURSE_NAME`                                                                                                                                                                   |
| **List**           | `list`                                                                                                                                                                                 |
| **Help**           | `help`                                                                                                                                                                                 |
| **Exit**           | `exit`                                                                                                                                                                                 |
