public class Decompile_38 {
    private int value;

    public Decompile(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void greet() {
        System.out.println("Hello from Decompile!");
    }

    public static void main(String[] args) {
        Decompile obj = new Decompile(42);
        obj.greet();
        System.out.println("Value is: " + obj.getValue());
    }
}
/*javac Decompile.java
To compile: javac Decompile.java
To run: java Decompile
To  Decompile the .class file using CFR:
java -jar cfr.jar Decompile.class
*/