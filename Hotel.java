/**
 *    Revision History
 * *********************************************************
 * 
 * 
 */
package doghotelgui;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Anne
 */
public class Hotel {
    private String hotelName;
    private double dailyRate;
    private int capacity;
    private ArrayList<Guest> guests;

    /**
     * Parameterized constructor to create a Hotel object
     * @param hotelName the name of the Hotel
     * @param dailyRate the cost of a room
     * @param capacity  the number of rooms in this physical facility
     */
    public Hotel(String hotelName, double dailyRate, int capacity) {
        this.hotelName = hotelName;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.guests = new ArrayList<Guest>();
    }

    /**
     * Used to add a guest to the ArrayList and then sort the list
     * by room number.
     * Used by: the start of day routines in the driver as well as 
     *          the checkInGuest() method in this class
     * @param guest a Guest object with values for all fields 
     * @return true if there is room for the guest, false if we 
     *         have hit the physical limit of the facility.
     */
    public boolean addGuest(Guest guest) {
        boolean added = false;
        if (guests.size() < capacity) {
            guests.add(guest);
            added = true;
            Collections.sort(guests);
        }
        return added;
    }
    /**
     * seems like a nice idea to see if there is room before we bother 
     * trying to check in a guest.
     * @return true if we have room and false otherwise.
     */
    public boolean hasVacancy(){
        return guests.size() < this.capacity;
    }
   /**
     * Returns the first "empty room" found within the capacity of
     * available rooms.
     * Since the guests are sorted by room number as they are added
     * this method can do a linear search that compares the current
     * index to the room number of that guest.  When they don't 
     * match, we have an empty room.  
     * Called by CheckIn only after we know we have room, so the 
     * -1 should never actually be returned.
     * 
     * @return the number of a room that is not in use, or a -1
     *         if the occupancy has hit our physical capacity.
     */
    private int findEmptyRoom() {
        int room = 0;
        while (room < guests.size()
                && room == guests.get(room).getRoomNumber()){
            room++;
        }
        if (room == capacity) 
            room = -1;
        return room;
    }

    /**
     * returns the index of the element in the ArrayList whose guest 
     * matches the owner's last name
     *
     * @param owner last name of the owner we are looking for
     * @return index of matching record or -1 if we get to the end
     *         of the list.
     */
    private int findGuest(String owner) {
        int index = 0;
        while (index < guests.size()
                && ! guests.get(index).getOwner().equalsIgnoreCase(owner) ) {
            index++;
        }
        if (index == guests.size()) {
            index = -1;
        }
        return index;
    }

    /**
     * This method checks for an empty room and if one is found it
     * sets the room number and the check in date in the guest
     * object parameter and calls addGuest() so that the guest is 
     * added to the ArrayList and then sorted.
     * @param guest A Guest object missing a check in date and room number
     * @param today the current date
     * @return the room number or a -1
     */
    public int checkInGuest(Guest guest, Date today) {        
        int room;
        room = findEmptyRoom();
        if (room != -1){
            guest.setCheckInDate(today);
            guest.setRoomNumber(room);
            addGuest(guest);
        }
  
        return room;
    }

    /**
     * If the owner is found, this calculates the bill using the 
     * check in date and today's date and produces an invoice.
     * @param owner the name of the guest's owner
     * @param today the current date
     * @return an invoice or that the owner wasn't found
     */
    public String checkOutGuest(String owner, Date today){
        int index = findGuest(owner);
        
        StringBuilder str = new StringBuilder();
        if (index != -1){
            Guest g = guests.remove(index);
            str.append(g.getName());
            str.append("\t\t\t");
            str.append(g.getRoomNumber());
            str.append("\n");
            int days =(int) g.getCheckInDate().difference(today);
            double charge = days * dailyRate;
            String chrg = String.format("Total due: $%5.2f", charge);
            str.append(chrg);
        } else {
            str.append("Guest not found.");
        }
       return str.toString(); 
    }

    /**
     * Creates a list of foods so that the guest can be fed.
     * @return an array with room, name, amount and type of food information
     * that can be printed and "filled" by the staff.
     */
    public String[] createFeedingOrder(){
        int len = guests.size();
        String [] order = new String[len+1];
        Guest current = null;
        StringBuilder str = new StringBuilder();
        order[0] = " Room   Name            Amount         Type";
        for (int i = 0; i < len; i++){
            str = new StringBuilder();
            current = guests.get(i);
            str.append(String.format("%5d\t", current.getRoomNumber()));
            str.append(String.format("%-15s", current.getName()));                        
            str.append(String.format("%5.2f", current.getAmountOfFood()));
            str.append(" ounces of ");
            str.append(String.format("%-10s", current.getTypeOfFood()));
            order[i+1] = str.toString();            
        }
        return order;
    }

    /**
     * Used for several things, determining vacancies as well as the 
     * end of a for loop in the driver.
     * @return the current size of the arraylist (count of the objects
     * in the arraylist)
     */
    public int getOccupancy(){
        return guests.size();
    }
    /**
     * Accessor for the property capacity
     * @return the capacity of the kennel
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Returns one guest.  This is used for the backup operation
     * @param index the index in the arraylist of Guests
     * @return a Guest or null
     */
    public Guest getGuestAt(int index){
        if (index < guests.size())
            return guests.get(index);
        else
            return null;
    }
}
