import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws IOException {

        String [] strings = input.split(" ");
        int a;
        int b;

        if (valuesAreArabic(input)){
            a = Integer.parseInt(strings[0]);
            b = Integer.parseInt(strings[2]);
        } else{
          a = romanToArabic(strings[0]);
          b = romanToArabic(strings[2]);
        }

        int res = 0;
        String sim = strings[1];

        switch (sim) {
            case "+" -> res = a + b;
            case "-" -> res = a - b;
            case "*" -> res = a * b;
            case "/" -> res = a / b;
            default -> throw new IOException();
        }

        if (valuesAreArabic(input)){return Integer.toString(res);}
        else if (!(valuesAreArabic(input)) && (res >= 1)){return arabicToRoman(res);}
        else throw new IOException();
    }


    enum romanTranslation{
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);

        private int value;

        romanTranslation(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        public static romanTranslation fromString(String symbol){
            for (romanTranslation num : romanTranslation.values()){
                if (num.name().equals(symbol)){
                    return num;
                }
            }
            return null;
        }
    }

    public static int romanToArabic(String symbol){
        int result = 0;
        int index = 0;
        while(index < symbol.length()){

            if (index + 1 < symbol.length()){
                String twoSymbols = symbol.substring(index, index + 2);
                if (romanTranslation.fromString(twoSymbols) != null){
                    result += romanTranslation.fromString(twoSymbols).getValue();
                    index += 2;
                    continue;
                }
            }

            String oneSymbol = symbol.substring(index, index+1);
            if (romanTranslation.fromString(oneSymbol) != null){
                result += romanTranslation.fromString(oneSymbol).getValue();
            }
            index++;
        }
        return result;
    }

    public static String arabicToRoman(int num){
        StringBuilder roman = new StringBuilder();
        for (int i = romanTranslation.values().length - 1; i >= 0; i--){
            romanTranslation number = romanTranslation.values()[i];
            while (num >= number.getValue()){
                roman.append(number.name());
                num -= number.getValue();
            }
        }
        return roman.toString();
    }

    public static boolean valuesAreArabic(String input) throws IOException {
        String [] strings = input.split(" ");
        boolean number_of_values = false;
        boolean is_arabic = false;
        boolean is_roman = false;

        if (strings.length == 3){number_of_values = true;}
        try {
            int a = Integer.parseInt(strings[0]);
            int b = Integer.parseInt(strings[2]);
            if (a <= 10 && b <= 10) {is_arabic = true;}
        } catch (Exception e) {}

        try {
            int c = romanToArabic(strings[0]);
            int d = romanToArabic(strings[2]);
            if (d <= 10 && c <= 10 && d >= 1 && c >= 1) {is_roman = true;}
        } catch (Exception e) {}

        if (is_arabic & number_of_values){return true;}
        if (is_roman & number_of_values){return false;}
        else{
            throw new IOException();
        }
    }
}


