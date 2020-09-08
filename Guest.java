/**
 *    Revision History
 * *********************************************************
 * 11/2019 Rewrote compareTo so that there is only 1 return statement AA 
 *
 */
package doghotelgui;

/**
 * Guest class models a guest at the dog hotel
 * @author Anne
 */
public class Guest extends Pet {

    /**
     * The date this guest checked into the kennel
     */
    private Date checkInDate;
    /**
     * the kennel number that the guest was put in
     */
    private int roomNumber;
    /**
     * The kind of food, based on age, that the guest will be fed. if the guests
     * age is between 6 months and 18 months they are fed puppy food. If they
     * are 19 months through 7 years they are fed adult food. If they are 7
     * years or older they are fed food for mature dogs.
     */
    private String typeOfFood;
    /**
     * Amount of food (in ounces) is based on weight.
     */
    private double amountOfFood;

    /**
     * Default constructor
     */
    public Guest() {
        super(new Date(0, 0, 0), "None", 0.0, "None", "None");
    }

    /**
     * Constructor for use during the check in procedure when the room number
     * and check in date are not yet known
     *
     * @param dob dog's date of birth
     * @param breed dog's breed
     * @param weight dog's weight
     * @param name petDog's name
     * @param owner petDog's owner
     */
    public Guest(Date dob, String breed, double weight,
            String name, String owner) {
        super(dob, breed, weight, name, owner);
    }

    /**
     * Constructor for use during start of day routines for adding guests from a
     * file with all information available. Calls: determineAmountOfFood() and
     * determineTypeOfFood()
     *
     * @param dob Date object dog's date of birth
     * @param breed String object dog's breed
     * @param weight dog's weight
     * @param name String object Pet's name
     * @param owner String object Pet's owner
     * @param room guest's room number
     * @param checkInDate Date object guest's check in date
     */
    public Guest(Date dob, String breed, double weight,
            String name, String owner, int room, Date checkInDate) {
        super(dob, breed, weight, name, owner);
        this.roomNumber = room;
        this.checkInDate = checkInDate;
        this.determineAmountOfFood();
        this.determineTypeOfFood();
    }

    /**
     * Used by: Hotel's checkInGuest() method to set a date for the guest's
     * check in. Calls: determineAmountOfFood() and determineTypeOfFood()
     *
     * @param checkInDate the current day's date
     */
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
        this.determineTypeOfFood();
        this.determineAmountOfFood();
    }

    /**
     * Used by Hotel's checkInGuest() method to set the room number for a guest
     * at check in.
     *
     * @param roomNumber an int between 0 and capacity-1
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Calculates the guest's age using birth date and check in date and then
     * determines the type of food based on that calculation. Needed by: Hotel's
     * createFeedingOrder()
     */
    private void determineTypeOfFood() {
        double age = checkInDate.difference(birthDate) / 364.5;

        if (age >= 7.0) {
            typeOfFood = "Mature";
        } else if (age >= 1.5) {
            typeOfFood = "Adult";
        } else {
            typeOfFood = "Puppy";
        }
    }

    /**
     * Calculates the amount of food based on the dog's weight. Needed by:
     * Hotel's createFeedingOrder()
     */
    private void determineAmountOfFood() {
        amountOfFood = weight * 0.25;
    }

    /**
     * Getter for roomNumber
     *
     * @return the value of the property roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Getter for checkInDate
     *
     * @return returns a Date, the value of the property checkInDate
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * Getter for type of food: puppy, adult, or mature. Used by: Hotel's
     * createFeedingOrder() method
     *
     * @return the String value of the property typeOfFood
     */
    public String getTypeOfFood() {
        return typeOfFood;
    }

    /**
     * Getter for the amount of food property. Used by Hotel's
     * createFeedingOrder() method
     *
     * @return the current value of amountOfFood property
     */
    public double getAmountOfFood() {
        return amountOfFood;
    }

    /**
     * toString allows an object to be directly printed by returning a String
     * that can be printed to the console or to a file. The String is formatted
     * for printing to the file. Used indirectly by: the backup method in the
     * hotel's driver.
     *
     * @return a formatted string that contains the values of all of the
     * attributes of the object.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        // find out what this OS uses as a line feed.
        String eol = System.getProperty("line.separator");
        str.append(super.toString()).append("\t");
        str.append(roomNumber).append("\t");
        str.append(checkInDate.getMonth()).append("\t");
        str.append(checkInDate.getDay()).append("\t");
        str.append(checkInDate.getYear());
        return str.toString();
    }

    /**
     * Guests are sorted by room number.
     *
     * @param that the guest we are comparing to
     * @return a negative number, positive number or 0 depending on the
     * comparison
     */
    @Override
    public int compareTo(Dog that) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        int comparison;
        if (that == null) {
            comparison = AFTER; // shouldn't be any null objects, but if there are
            // put them at the end
            //this optimization is usually worthwhile, and can
            //always be added = if the addresses are the same... they are equal
        } else if (this == that) {
            comparison = EQUAL;

        } else {  // sort by room number
            comparison = this.roomNumber - ((Guest) that).getRoomNumber();
        }
        return comparison;
    }
}
