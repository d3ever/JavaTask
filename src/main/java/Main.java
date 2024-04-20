import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final String REGEX = "^M*(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    public static void main (String[] args) {
        System.out.println("Введите выражение [2 + 2] или два римских числа от I до X:[V + V] + Enter ");
        String userInput = scanner.nextLine();
        String[] words = userInput.split(" ");
        if(words.length != 3) throw new IllegalArgumentException("Вы ввели неправильный формат ввода.");

        System.out.println(calc(userInput));
    }

    public static String calc(String in) {
        String[] args = in.split(" ");
        String first = args[0];
        String second = args[2];
        char operator = getOperator(args[1]);
        if(operator == '?') throw new IllegalArgumentException("Вы ввели неправильный оператор, доступные операторы: [/, *, -, +]");
        if(isRomanNumbers(first) && isRomanNumbers(second)) {
            int result = getResult(romanToInteger(first), romanToInteger(second), operator);
            if(result > 10) throw new NumberFormatException("Результат не может быть больше 10.");
            if(result < 1) throw new NumberFormatException("Результат отрицательный, попробуйте повторно выполнить рассчет.");
            return getRomanNumber(result);
        }

        return String.valueOf(getResult(Integer.parseInt(first), Integer.parseInt(second), operator));
    }

    public static int getResult(int x, int y, char oper) {
        if((x < 1 || x > 10) && (y < 1 || y > 10)) throw new NumberFormatException("Вы не можете использовать числа больше 10 и меньше 1");
        return switch (oper) {
            case '-' -> x - y;
            case '+' -> x + y;
            case '/' -> x / y;
            case '*' -> x * y;
            default -> -1;
        };
    }

    public static String getRomanNumber(int number) {
        return String.join("", Collections.nCopies(number, "I"))
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC")
                .replace("CCCCC", "D")
                .replace("CCCC", "CD")
                .replace("DD", "M")
                .replace("DCD", "CM");
    }

    public static char getOperator(String key) {
        char o = key.charAt(0);
        return switch (o) {
            case '+', '-', '/', '*' -> o;
            default -> '?';
        };
    }

    public static boolean isRomanNumbers(String string) {
        return string.matches(REGEX);
    }
    public static int romanToInteger(String roman) {
        Map<Character,Integer> numbersMap = new HashMap<>();
        numbersMap.put('I', 1);
        numbersMap.put('V', 5);
        numbersMap.put('X', 10);
        numbersMap.put('L', 50);
        numbersMap.put('C', 100);
        numbersMap.put('D', 500);
        numbersMap.put('M', 1000);

        final int[] result = {0};
        IntStream.range(0, roman.length()).forEach(i -> {
            char c = roman.charAt(i);
            char next = i < roman.length() - 1 ? roman.charAt(i + 1) : ' ';

            if (numbersMap.get(c) < numbersMap.getOrDefault(next, 0)) result[0] -= numbersMap.get(c);
            else result[0] += numbersMap.get(c);
        });

        return result[0];
    }
}
