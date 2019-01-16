package module;

import impl.Animal;

public class Zoo {

    private Animal animal;

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        if (animal == null) {
            return null;
        }
        return animal.toString();
    }

}