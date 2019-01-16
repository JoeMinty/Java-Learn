package impl;


public class Tiger implements Animal {

    @Override
    public void eat() {
        System.out.println("impl.Tiger.eat()");
    }

    @Override
    public String toString() {
        return "I'm a tiger";
    }
}