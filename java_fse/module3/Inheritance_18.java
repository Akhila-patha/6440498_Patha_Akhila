// Parent class representing a generic animal
class Animal {
    // Method to make a generic animal sound
    void makeSound() {
        System.out.println("Animal sound");
    }
}

// Child class inheriting from Animal
class Dog extends Animal {
    // Override the makeSound method to provide specific behavior for Dog
    @Override
    void makeSound() {
        System.out.println("Bark");
    }
}

// Main class to demonstrate inheritance
public class Inheritance {
    public static void main(String[] args) {
        // Create an instance of Animal
        Animal a = new Animal();
        // Create an instance of Dog
        Dog d = new Dog();

        // Call makeSound on Animal instance
        a.makeSound(); // Output: Animal sound
        // Call makeSound on Dog instance
        d.makeSound(); // Output: Bark
    }
}