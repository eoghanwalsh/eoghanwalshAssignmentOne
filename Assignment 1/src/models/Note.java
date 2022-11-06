package models;

import utils.CategoryUtility;
import utils.Utilities;
import java.util.ArrayList;
import java.util.Objects;


public class Note {
    
    private String noteTitle ="No Title";
    private int notePriority = 1;
    private  String noteCategory ="";
    private boolean isNoteArchived= false;
    private ArrayList<Item> items;


    public Note(String noteTitle, int notePriority, String noteCategory)
    {
        this.noteTitle =  Utilities.truncateString(noteTitle, 20);
        if (Utilities.validRange(notePriority,1,5)) { this.notePriority =notePriority;}
        if (CategoryUtility.isValidCategory(noteCategory) == true) {this.noteCategory = noteCategory;}
        this.isNoteArchived= isNoteArchived;
        items = new ArrayList<Item>();

    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public int getNotePriority() {
        return notePriority;
    }

    public String getNoteCategory() {
        return noteCategory;
    }

    public boolean isNoteArchived() {
        return isNoteArchived;
    }


    public ArrayList<Item> getItems() {
        return items;
    }


    public void setNoteTitle(String noteTitle) {
        if ( Utilities.validateStringLength(noteTitle,20) == true ) {this.noteTitle=noteTitle;}

    }

    public void setNotePriority(int notePriority) {
        if (Utilities.validRange(notePriority,1,5)) {this.notePriority =notePriority;}

    }

    public void setNoteCategory(String noteCategory) {if (CategoryUtility.isValidCategory(noteCategory) == true) {this.noteCategory = noteCategory;}}

    public void setNoteArchived(boolean noteArchived) {
        isNoteArchived = noteArchived;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public boolean isValidIndex ( int index )
    {
        return (index >= 0) && (index < items.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return notePriority == note.notePriority
                && isNoteArchived
                == note.isNoteArchived
                && Objects.equals(noteTitle, note.noteTitle)
                && Objects.equals(noteCategory, note.noteCategory)
                && Objects.equals(items, note.items);
    }

    public boolean addItem (Item item)
    {
        return items.add(item);
    }

    public String listItems()
    {
        if (items.isEmpty()== true)
        {
            return"\t \tNo items added"+"\n";
        }
        else
        {
            String listOfItems= "";

            for (int i =0; i<items.size();i++)
            {
                Item item = findItem(i);
                listOfItems = listOfItems +"\t"+"\t"+ i +":  Item Description: "+item.getItemDescription() + ". "+Utilities.StatusOfCompilation(item.isItemCompleted())+ "\n";
            }

            return listOfItems;
        }

    }

    public boolean updateItem (int index, String itemDescription, boolean itemCompleted)
    {

        Item foundItem = null;
        if (isValidIndex(index))
        {
            foundItem = items.get(index);
        }

        if(foundItem != null)
        {
            foundItem.setItemDescription(itemDescription);
            foundItem.setItemCompleted(itemCompleted);
            return true;
        }
        return false;
    }

    public Item deleteItem ( int index )
    {
        if(isValidIndex(index)== true)
        {
            return items.remove(index);
        }
        return null;
    }

    public Item findItem(int index)
    {
        if(isValidIndex(index))
        {
            return items.get(index);

        }
        return null;

    }

    public int  numberOfItems()
    {
        return items.size();
    }

    public int numberOfCompleteItems()
    {
        int numOfCompletedItems =0;
        for (Item item : items) {
            boolean test  = item.isItemCompleted();
            if (test == true)
            {
                numOfCompletedItems = numOfCompletedItems +1 ;
            }

        }
        return numOfCompletedItems;
    }

    public int numberOfTodoItems()
    {
        int numOfCompletedItems =0;
        for (Item item : items) {
            boolean test  = item.isItemCompleted();
            if (test == false)
            {
                numOfCompletedItems = numOfCompletedItems +1 ;
            }

        }
        return numOfCompletedItems;
    }

    public boolean checkNoteCompletionStatus()
    {
        if (items.size() == 0)
        {
            return true;
        }

        for (Item item : items) {
            boolean test  = item.isItemCompleted();
            if (test == false)
            {
                return false;
            }

        }

        return true;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", notePriority=" + notePriority +
                ", noteCategory='" + noteCategory + '\'' +
                ", isNoteArchived=" + isNoteArchived +
                ", items=" + items +
                '}';
    }
}