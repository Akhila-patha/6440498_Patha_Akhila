public class MyClass_37 {
    public void greet() {
        System.out.println("Hello Bytecode!");
    }

    public static void main(String[] args) {
        new MyClass().greet();
    }
}
/*To compile:javac MyClass.java
To run: java MyClass
To inspect bytecode:
javap -c MyClass
    * Output:
    * public class MyClass {
    *   public MyClass();
    *     Code:
    *        0: aload_0
    *        1: invokespecial #1                
    *        4: return
    *
    *   public void greet();
    *     Code:
    *        0: getstatic     #2                 
    *        3: ldc           #3               
    *        5: invokevirtual #4                 
    *        8: return
    *
    *   public static void main(java.lang.String[]);
    *     Code:
    *        0: new           #5                
    *        3: dup
    *        4: invokespecial #1                
    *        7: invokevirtual #6               
    *       10: return
    * }
    */