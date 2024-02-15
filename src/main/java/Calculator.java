import java.util.Scanner;

public class Calculator {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ALLOWED = "/ - + *";

    public static void main(String[] args) {
        int a = getInt();
        int b = getInt();
        char oper = getOperator();
        int result = 0;
        try {
            result = getResult(a, b, oper);
        } catch (Exception exception) {
            System.out.println("Произошла ошибка, обратитесь к разработчику");
            System.exit(0);
        } finally {
            System.out.printf("Результат: %d", result);
        }
    }

    public static int getResult(int first, int second, char operator) {
        return switch (operator) {
            case '+' -> first + second;
            case '-' -> first - second;
            case '*' -> first * second;
            case '/' -> first / second;
            default -> getResult(first, second, getOperator());
        };
    }

    public static int getInt(){
        System.out.println("Введите число:");
        if(!(scanner.hasNext()) || !(scanner.hasNextInt())) {
            System.out.println("Введите целое число! [1, 2, 3...]");
            scanner.next();
            return getInt();
        }

        return scanner.nextInt();
    }

    public static char getOperator(){
        System.out.println("Введите операцию:");
        if(!scanner.hasNext()) {
            System.out.println("Вы не ввели оператор!");
            scanner.next();
            return getOperator();
        }
        char operation = scanner.next().charAt(0);
        if(!(ALLOWED.contains(String.valueOf(operation)))) {
            System.out.println("Вы ввели неправильный оператор");
            return getOperator();
        }

        return operation;
    }
}
