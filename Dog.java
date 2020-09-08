/**
 * Course: CSCI 160
 * Class: Dog 
 * Uses: Date class
 * Extends: nothing
 * Implements: nothing
 * Presumption: All dogs have these attributes 
 */
package doghotelgui;

/**
 * A class to represent a Dog competing in an Agility Contest.
 * @author aapplin
 */
public abstract class Dog implements Comparable<Dog>{
    
    protected final Date birthDate;
    protected final String breed;
    protected final double weight;        
    /**
     * Parameterized Constructor for the Dog class.
     * @param birthDate the dog's date of birth (Date)
     * @param breed the dog's breed
     * @param weight the dog's weight
     */
    public Dog(Date birthDate, String breed, double weight){
        this.birthDate = birthDate;
        this.breed = breed;
        this.weight = weight;         
    }
    public abstract Date getBirthDate();
    public abstract String getBreed();
    public abstract double getWeight();    
    @Override
    public abstract int compareTo(Dog that);   
}
