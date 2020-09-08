/**
 *    Revision History
 * *********************************************************
 * 11/2019 Rewrote compareTo so that there is only 1 return statement AA 
 * 
 */
package doghotelgui;
/**
 * The Date Class represents a date as 3 int values for month, day, and year.
 * Year is validated only as > 4712 BCE.  Month is validated for values from 
 * 1..12.  Day is validated based on the month that was set.  
 * dayNumber is the number of elapsed days since January 1, 4712 BCE
 * Java provides several date classes that are more complicated than this one.
 * @author aapplin
 */
public class Date implements Comparable<Date>{

    private int month;
    private int day;
    private int year;
    private double dayNumber; // the elapsed number of days since 1/12/4712 BCE
    /**
     * Default Constructor
     * Sets the date to January 1, 1970 
     */
    public Date() {
        this(1, 1, 1970); // a default date - the start date for Unix systems
    }
    /**
     * Parameterized Constructor
     * Validates Year, Month, and Day.  Sets invalid values to 1/1/1970
     * @param month input integer for month.
     * @param day   input integer for day.
     * @param year  input integer for year
     */
    public Date(int month, int day, int year) {
        if (year >= -4712)
           this.year = year;
        else 
            this.year = 1970;
        if (isValidMonth(month)) // have to validate month before day
           this.month = month;
        else 
            this.month = 1;
        if(isValidDay(day))
            this.day = day;
        else
            this.day = 1;  
        calcDayNumber();
    }
    /**
     * Validates the month passed in.  If the month is not valid it is set 
     * to 1.
     * @param month 
     */
    public void setMonth(int month) {
        if (isValidMonth(month)) {
            this.month = month;
        } else {
            this.month = 1;
        }
    }
    /**
     * validates the day passed in.  
     * Preconditions: The month must already be validated for this to work.
     * @param day 
     */
    public void setDay(int day) {
        // thirty days hath September, April, June, and November
        if (isValidDay(day) ) 
            this.day = day;
        else
            this.day = 1;
    }

    public void setYear(int year) {
        if (year > -4712)
           this.year = year;
        else 
            this.year = 1970;
    }
    
    /**
     * Validates that the value for month is between 1 and 12 inclusive
     * @param month input integer value of month
     * @return true if the value is between 1 and 12 inclusive and false
     * otherwise.
     */
    public boolean isValidMonth(int month){
        return (month > 0 && month <= 12);        
    }
    /**
     * validates the day based on the month
     * Preconditions: month must be validated and set before this is called.
     * Thirty days hath September, April, June, and November.  All the rest
     * have 31 excepting February alone and it has 28 days clear and 29
     * on each leap year.
     * @param day input integer for day
     * @return true if the day is valid based on the month.
     */
    public boolean isValidDay(int day){
        if (this.month == 9 || this.month == 4 || this.month == 6
                    || this.month == 11) {
                if (day > 0 && day <= 30) { // 1..30
                    return true;
                }
        } else if (this.month == 1 || this.month == 3|| this.month == 5 ||
                   this.month == 7 || this.month == 10 || this.month == 12) {
                if (day > 0 && day <= 31) { // 1 .. 31 are valid
                    return true;
                }
        } else if (isLeapYear()){ // month == 2 && leap year
            if (day > 0 && day <= 29) // 1 .. 29 are valid
                return true;
        } else {
            if (day > 0 && day <= 28) //  1 .. 28 are valid
                return true;
        }       
        return false;
    }
    /**
     * given that year is valid, this method check to see if it is a
     * leap year.  used to validate a day in February.
     * @return true if the year is a leap year using standard algorithm.
     */
    public boolean isLeapYear() {
 	return ((year % 400 == 0) || 
                ((year % 4 == 0) && (year % 100 != 0)));	
    }
    /**
     * The day number is the number of days that have elapsed since noon on 
     * January 1, 4712 BCE. This makes it easier to compute differences 
     * between two dates or calculate an age.
     * A = Y/100
     * B = A/4
     * C = 2-A+B
     * E = 365.25x(Y+4716)
     * F = 30.6001x(M+1)
     * JD= C+D+E+F-1524.5
     */
    
    private void calcDayNumber(){
        double a, b, c, e, f;
        a = year / 100;
        b = a / 4;
        c = 2 - a + b;
        e = 365.25 * (year + 4716);
        f = 30.6001 * (month + 1);
        dayNumber = c + day + e + f - 1524.5;
    }
    /**
     * calculates the difference between two dates as an absolute value
     * of the two day numbers.
     * @param that  a second date.
     * @return the number of elapsed days.
     */
    public double difference(Date that){
        return Math.abs(that.dayNumber - this.dayNumber);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getDayNumber() {
        return dayNumber;
    }
    
    /**
     * creates a string for date in the form mm/dd/yyyy
     * @return a string in the form mm/dd/yyyy
     */
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(String.format("%02d/%02d/%d", month, day, year));
        
        return str.toString();
    }
    /**
     * compareTo (abstract method of the Comparable Interface) is implemented 
     * to impose a natural ordering on a group of objects.
     * compareTo is used by the Collections.sort routine to allow us to sort
     * the Dog belonging to some Java collection by birthdate.
     * @param that is the Date object we are comparing this one to
     * @return a negative integer, zero, or a positive integer if this object 
     * comes before, is equal to, or comes after the specified object.
     */
    @Override
    public int compareTo(Date that){
        // some constants for clarity
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;   
        int comparison;
        // shouldn't be any null objects, but if there are
        // put them at the end
        if (that == null)
            comparison = AFTER; 
        //this optimization is usually worthwhile, and can
        //always be added - if the addresses are the same... they are equal
        else if (this == that) 
            comparison = EQUAL;
        // now we will compare dates using the daynumber and return the result.
        // since these are primitive values a simple difference is all we need.
        else comparison = (int)(this.dayNumber - that.getDayNumber()); 
        return comparison;
    }
    public static void main(String[] args) { 
	Date obj1 = new Date(54,14,-4800);
        System.out.println(obj1);
	System.out.println(obj1.getYear() + " is a leap year : " + obj1.isLeapYear());
        obj1.setYear(1996);
	System.out.println("1996 is a leap year : " + obj1.isLeapYear());
        obj1.setYear(2012);
	System.out.println("2012 is a leap year : " + obj1.isLeapYear());
 
    }
}
