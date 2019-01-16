package impl;

public class Lion implements Animal {

    @Override
    public void eat() {
        System.out.println("Lion.eat()");
    }

    @Override
    public String toString() {
        return "I'm a lion";
    }
}
