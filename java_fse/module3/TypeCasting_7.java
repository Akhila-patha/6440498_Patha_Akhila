public class TypeCasting_7{
    public static void main(String[] args) {
        double myDouble = 9.78;
        int myIntFromDouble = (int) myDouble;
        System.out.println("Double value: " + myDouble);
        System.out.println("After casting double to int: " + myIntFromDouble);
        int myInt = 42;
        double myDoubleFromInt = myInt;
        System.out.println("Int value: " + myInt);
        System.out.println("After casting int to double: " + myDoubleFromInt);
    }
}

