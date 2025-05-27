public class OperatorPrecedence_8{
    public static void main(String[] args) {
        int result1 = 10 + 5 * 2;
        System.out.println("Expression: 10 + 5 * 2");
        System.out.println("Result: " + result1);
        System.out.println("Explanation: Multiplication (*) happens before addition (+). So, 5 * 2 = 10, then 10 + 10 = 20.\n");
        int result2 = (10 + 5) * 2;
        System.out.println("Expression: (10 + 5) * 2");
        System.out.println("Result: " + result2);
        System.out.println("Explanation: Parentheses have highest precedence, so 10 + 5 = 15, then 15 * 2 = 30.\n");
        int result3 = 20 / 4 + 3 * 2 - 1;
        System.out.println("Expression: 20 / 4 + 3 * 2 - 1");
        System.out.println("Result: " + result3);
        System.out.println("Explanation:");
        System.out.println("1) Division and multiplication have same precedence and are evaluated left to right:");
        System.out.println("   20 / 4 = 5");
        System.out.println("   3 * 2 = 6");
        System.out.println("2) Then addition and subtraction are evaluated left to right:");
        System.out.println("   5 + 6 = 11");
        System.out.println("   11 - 1 = 10\n");
    }
}

