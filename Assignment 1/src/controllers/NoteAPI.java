package controllers;
import models.Item;
import models.Note;
import utils.CategoryUtility;
import utils.Utilities;
import java.util.ArrayList;


public class NoteAPI {

    private ArrayList<Note> notes;

    public NoteAPI() {
        notes = new ArrayList<Note>();
    }

    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < notes.size());
    }

    public boolean add(Note note) {
        return notes.add(note);
    }

    public boolean updateNote(int indexToUpdate, String noteTitle, int notePriority, String noteCategory) {
        Note foundNote = null;
        if (isValidIndex(indexToUpdate)) {
            foundNote = notes.get(indexToUpdate);
        }

        if (foundNote != null) {
            foundNote.setNoteTitle(noteTitle);
            foundNote.setNotePriority(notePriority);
            foundNote.setNoteCategory(noteCategory);
            return true;
        }
        return false;
    }

    public Note deleteNote(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return notes.remove(indexToDelete);
        }
        return null;
    }

    public String archiveNotesWithAllItemsComplete() {
        String newlyArchived = "";
        if (notes.isEmpty() || numberOfActiveNotes() == 0) {
            return "No active notes stored";
        } else {
            for (Note note : notes) {

                if (note.checkNoteCompletionStatus()) {
                    note.setNoteArchived(true);
                    newlyArchived += displayString(note);
                }
            }
        }
        return newlyArchived;
    }

    public int numberOfNotes() {
        return notes.size();
    }

    public int numberOfArchivedNotes() {
        int numArchived = 0;
        for (Note note : notes) {
            if (note.isNoteArchived()) {
                numArchived = numArchived + 1;
            }
        }

        return numArchived;
    }

    public int numberOfActiveNotes() {
        int numActive = 0;
        for (Note note : notes) {
            if (!note.isNoteArchived()) {
                numActive = numActive + 1;
            }
        }
        return numActive;
    }

    public int numberOfNotesByCategory(String category) {
        int numNoteInCategory = 0;
        if (CategoryUtility.isValidCategory(category)) {

            for (Note note : notes) {
                if (note.getNoteCategory().equalsIgnoreCase(category)) {
                    numNoteInCategory = numNoteInCategory + 1;

                }
            }

        }
        return numNoteInCategory;
    }

    public int numberOfNotesByPriority(int priority) {
        int numNoteInPriority = 0;
        for (Note note : notes) {
            if (note.getNotePriority() == priority) {
                numNoteInPriority += 1;

            }
        }

        return numNoteInPriority;
    }

    public int numberOfItems() {
        int numOfItems = 0;
        for (Note note : notes) {
            numOfItems = numOfItems + note.numberOfItems();
        }
        return numOfItems;
    }

    public int numberOfCompleteItems() {
        int numOfItems = 0;
        for (Note note : notes) {

            numOfItems = numOfItems + note.numberOfCompleteItems();
        }
        return numOfItems;
    }

    public int numberOfTodoItems() {
        int numOfItems = 0;
        for (Note note : notes) {

            numOfItems = numOfItems + note.numberOfTodoItems();
        }
        return numOfItems;
    }

    public String listAllNotes() {
        if (notes.isEmpty() == true) {
            return "No Notes Stored";

        } else {
            String listOfNotes = "";

            for (int i = 0; i < notes.size(); i++) {
                Note note = notes.get(i);
                listOfNotes = listOfNotes + displayString(i);
            }

            return listOfNotes;
        }
    }

    public String displayString(int index) {
        Note note = notes.get(index);
        return index + ": Note Title: " + note.getNoteTitle() + ". Note Priority: " + note.getNotePriority() + ". Note Archived: " + Utilities.booleanToYN(note.isNoteArchived()) + "\n" + note.listItems() + "\n";
    }

    public String displayString(Note note) {
        int index = notes.indexOf(note);
        return index + ": Note Title: " + note.getNoteTitle() + ". Note Priority: " + note.getNotePriority() + ". Note Archived: " + Utilities.booleanToYN(note.isNoteArchived()) + "\n" + note.listItems() + "\n";
    }

    public String listActiveNotes() {
        if (numberOfActiveNotes() == 0) {
            return "No active notes stored";
        } else {
            String listOfActNotes = "";
            for (Note note : notes) {
                if (!note.isNoteArchived()) {

                    {
                        listOfActNotes = listOfActNotes + displayString(note);
                    }
                }

            }

            return listOfActNotes;

        }
    }

    public String listArchivedNotes() {
        if (numberOfArchivedNotes() == 0) {
            return "\t \t no archived notes";
        } else {
            String listOfActNotes = "";

            for (int i = 0; i < notes.size(); i++) {
                Note note = notes.get(i);
                if (note.isNoteArchived()) {
                    return listOfActNotes = listOfActNotes + i + ": Note Title: " + note.getNoteTitle() + ". Note Priority: " + note.getNotePriority() + ". Note Archived: " + Utilities.booleanToYN(note.isNoteArchived()) + "\n" + note.listItems() + "\n";
                }
            }


            return listOfActNotes;
        }
    }

    public String listNotesBySelectedCategory(String category) {
        if (numberOfNotes() == 0) {
            return "No notes stored";
        } else if (numberOfNotesByCategory(category) == 0) {
            return "No notes with " + category;
        } else {
            String listOfNoteByCat = "";
            for (Note note : notes) {
                if (note.getNoteCategory().equalsIgnoreCase(category))
                {

                    {
                        listOfNoteByCat = listOfNoteByCat + displayString(note);
                    }
                }

            }
            return numberOfNotesByCategory(category) + " notes with category " + category + "\n" + listOfNoteByCat;
        }

    }

    public String listNotesBySelectedPriority(int priority) {
        if (numberOfNotes() == 0) {
            return "No notes stored";
        } else if (numberOfNotesByPriority(priority) == 0) {
            return "No notes of that " + priority;
        } else {
            String listOfNoteByPriority = "";
            for (int i = 0; i < notes.size(); i++) {

                if (findNote(i).getNotePriority() == priority)
                {

                    {
                        listOfNoteByPriority = listOfNoteByPriority + displayString(i);
                    }
                }

            }
            return numberOfNotesByPriority(priority) + " notes with priority " + priority + "\n" + listOfNoteByPriority;
        }

    }

    public String listTodoItems() {
        if (numberOfNotes() == 0) {
            return "No notes stored";
        } else {
            String toDoItemString = "";
            for (Note note : notes) {

                for (int i = 0; i < note.numberOfItems(); i++) {
                    if (!note.findItem(i).isItemCompleted()) {
                        Item item = note.findItem(i);
                        toDoItemString = toDoItemString + note.getNoteTitle() + ":\t" + item.getItemDescription() + ". " + Utilities.StatusOfCompilation(item.isItemCompleted()) + "\n";

                    }

                }
            }
            return toDoItemString;
        }
    }

    public String listItemStatusByCategory(String category) {
        String toDoString = "";
        String completeString = "";
        int numToDo = 0;
        int numComp = 0;
        if (numberOfNotes() == 0) {
            return "No notes stored";
        } else {
            for (Note note : notes) {
                if (note.getNoteCategory().equalsIgnoreCase(category)) {
                    for (int i = 0; i < note.numberOfItems(); i++) {
                        if (!note.findItem(i).isItemCompleted()) {
                            numToDo += 1;
                            toDoString += note.findItem(i).getItemDescription() + " (Note: " + note.getNoteTitle() + " )" + "\n";

                        } else if (note.findItem(i).isItemCompleted()) {
                            numComp += 1;
                            completeString += note.findItem(i).getItemDescription() + " (Note: " + note.getNoteTitle() + " )" + "\n";
                        }

                    }
                }
            }
        }
        return "Number Completed: " + numComp + "\n" + completeString + "\n" + "Number TODO: " + numToDo + "\n" + toDoString;
    }

    public Note findNote(int index) {
        if (isValidIndex(index)) {
            return notes.get(index);

        }
        return null;
    }

    public String searchNotesByTitle(String searchString) {
        String foundNote = "";
        if (notes.isEmpty()) {
            return "No notes stored";
        } else {
            for (int i = 0; i < notes.size(); i++) {

                if (findNote(i).getNoteTitle().toLowerCase().contains(searchString.toLowerCase())) {
                    foundNote = foundNote + displayString(i);
                }
            }
        }

        if (foundNote.equals("")) {
            return "No notes found for" + ": " + searchString;
        }
        return foundNote;
    }

    public String searchItemByDescription(String searchString) {
        String itemMatching = "";
        if (numberOfNotes() == 0) {
            return "No notes stored";
        } else {

            for (Note note : notes) {

                for (int i = 0; i < note.numberOfItems(); i++) {
                    if ((note.findItem(i).getItemDescription().toLowerCase()).contains(searchString.toLowerCase())) {
                        Item item = note.findItem(i);
                        int indexNote = notes.indexOf(note);
                        itemMatching = itemMatching + indexNote + ": " + note.getNoteTitle() + " " + i + ": " + item.getItemDescription() + ". " + Utilities.StatusOfCompilation(item.isItemCompleted()) + "\n";
                    }

                }

            }

            if (itemMatching.equals("")) {
                return "No items found for: " + searchString;
            }
        }
        return itemMatching;
    }
}
