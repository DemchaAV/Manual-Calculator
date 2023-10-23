import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Calculator {
    private int num1;
    private int num2;
    private char type;

    public Calculator() {
        boolean running = true;
        Scanner sc = new Scanner(System.in);

        while (running) {
            System.out.println("Enter an arithmetic expression (or an empty string for output): ");
            String input = sc.nextLine();

            if (input == null || input.isEmpty()) {
                running = false;
            } else {
                int splitIndex = 0;
                String numeral = "0123456789";
                for (int i = 0; i < input.length(); i++) {
                    if (!numeral.contains(String.valueOf(input.charAt(i)))) {
                        splitIndex = i;
                        break;
                    }
                }

                try {
                    num1 = Integer.parseInt(input.substring(0, splitIndex).trim());
                    type = input.charAt(splitIndex);
                    num2 = Integer.parseInt(input.substring(splitIndex + 1).trim());

                    switch (type) {
                        case '*' -> {
                            Multiplication m = new Multiplication(num1, num2);
                        }
                        case '-' -> {
                            Subtraction n = new Subtraction(num1, num2);
                        }
                        case '+' -> {
                            Addition a = new Addition(num1, num2);
                        }
                        case '/' -> {
                            Division d = new Division(num1, num2);
                        }
                        default -> System.out.println(" Invalid operation");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Incorrect input. Try again.");
                }
            }
        }
    }


    public static void main(String[] args) {
        Calculator calculator = new Calculator();
    }
}

class Addition {
    public Addition(int... numbers) {
        Print.printAddition(numbers);
        System.out.println();

    }

    public static ArrayList<Integer> multiAddition(int... num) {
        // Список для хранения суммы чисел
        ArrayList<Integer> equals = new ArrayList<>();

        // Конвертируем числа в двумерный список (каждое число как список цифр)
        ArrayList<ArrayList<Integer>> numList = new ArrayList<>(Splitter.to2xList(num));

        int temp = 0; // Временная переменная для хранения текущей суммы
        int digit = 0; // Переменная для хранения разряда переноса
        int rest = 0; // Переменная для хранения остатка от деления на 10
        int sizeCheckLine = maxSize2x(numList) - 1; // Максимальная длина чисел в списке

        // Проходимся по каждому разряду справа налево
        for (int i = 0; i < numList.size() || (sizeCheckLine - i) >= 0; i++) {
            int checkLine = numList.size() - 1;
            int posCheck = i;

            // Суммируем цифры чисел на текущем разряде
            while (checkLine >= 0 || (sizeCheckLine - posCheck) < 0) {
                if ((numList.get(checkLine).size() - 1 - i) < 0 && checkLine < 0) {
                    // Если достигли начала числа, переходим к следующему
                    checkLine--;
                    continue;
                }

                if (numList.get(checkLine).size() - 1 - i >= 0) {
                    // Складываем цифры чисел на текущем разряде
                    temp = temp + digit + numList.get(checkLine).get(numList.get(checkLine).size() - 1 - i);
                    digit = 0;
                }
                checkLine--;
            }

            // Добавляем остаток от деления на 10 в сумму чисел и определяем разряд переноса
            equals.add(temp % 10);
            digit = temp / 10;
            temp = 0;
        }

        // Переворачиваем список, чтобы получить правильный результат
        Collections.reverse(equals);

        return equals;
    }

    public static ArrayList<Integer> multiplyAddition(ArrayList<ArrayList<Integer>> numList) {
        ArrayList<Integer> equals = new ArrayList<>();
        int temp = 0;
        int digit = 0;
        int rest = 0;
        final int sizeChekLine = numList.get(numList.size() - 1).size() - 1;
        final int sizeList = numList.size() - 1;
        for (int i = 0; sizeChekLine + sizeList - i >= 0; i++) {
            int checkLine = numList.size() - 1;
            int posCheck = i;
            while (checkLine >= 0 && (numList.get(checkLine).size() - 1 + sizeList - posCheck) >= 0) {
                if (numList.get(checkLine).size() - 1 + sizeList - posCheck > numList.get(checkLine).size() - 1 || checkLine < 0) {
                    checkLine--;
                    posCheck++;
                    continue;
                }
                if (numList.get(checkLine).size() - 1 >= 0 && sizeChekLine - posCheck < numList.get(checkLine).size()) {
                    int get = numList.get(checkLine).get(numList.get(checkLine).size() - 1 + sizeList - posCheck);
                    temp = temp + digit + get;
                    digit = 0;
                }
                checkLine--;
                posCheck++;

            }

            equals.add(temp % 10);
            digit = temp / 10;
            temp = 0;
        }
        if (digit > 0) {
            equals.add(digit);

        }

        Collections.reverse(equals);
        return equals;

    }

    public static int maxSize2x(ArrayList<ArrayList<Integer>> intList) {
        int maxsize = 0;
        for (int i = 0; i < intList.size(); i++) {
            if (intList.get(i).size() > maxsize) {
                maxsize = intList.get(i).size();
            }

        }

        return maxsize;

    }

    public static int maxSize(ArrayList<Integer> intList) {
        int maxsize = 0;
        for (int i = 0; i < intList.size(); i++) {
            if (Splitter.getLenght(intList.get(i)) > Splitter.getLenght(maxsize)) {
                maxsize = intList.get(i);
            }

        }

        return maxsize;

    }

}

class Subtraction {

    public Subtraction(int minuend, int subtrahend) {
        ArrayList<Integer> minuendList = new ArrayList<>(Splitter.toList(minuend));
        ArrayList<Integer> subtrahendList = new ArrayList<>(Splitter.toList(subtrahend));
        subtraction(minuendList, subtrahendList);
        Print.printSubstruction(minuend, subtrahend);
        System.out.println();
    }

    public static ArrayList<Integer> subtraction(ArrayList<Integer> minuendList, ArrayList<Integer> subtrahendList) {
        ArrayList<Integer> equals = new ArrayList<>();
        int iteralMinuen = minuendList.size() - 1;
        int iteralSubtrahend = subtrahendList.size() - 1;
        while (iteralSubtrahend > -1 || iteralMinuen > -1) {
            if (iteralSubtrahend < 0) {
                equals.add(minuendList.get(iteralMinuen));

            } else {
                if ((minuendList.get(iteralMinuen) - subtrahendList.get(iteralSubtrahend)) < 0 && iteralSubtrahend > -1 && iteralMinuen > -1) {
                    equals.add((minuendList.get(iteralMinuen)) + 10 - subtrahendList.get(iteralSubtrahend));
                    minuendList.set(iteralMinuen - 1, (minuendList.get(iteralMinuen - 1)) - 1);
                } else {
                    if (iteralMinuen > -1 && iteralSubtrahend < 0) {
                        equals.add(minuendList.get(iteralMinuen));
                    }
                    equals.add((minuendList.get(iteralMinuen)) - subtrahendList.get(iteralSubtrahend));

                }
            }
            iteralMinuen--;
            iteralSubtrahend--;

        }


        Collections.reverse(equals);

        return equals;


    }


}

class Division {
    static int dividend = 0;
    static int devisor = 0;
    static int dividentLength;
    static int devisorLength;


    public Division(int dividend, int devisor) {

        if (devisor == 0 || dividend == 0) {
            System.out.println("Exception to 0 cannot be divided");
        } else {
            System.out.println("Enter the output type of the system division \"GB\", или \"EU\"");
            Scanner sc = new Scanner(System.in);
            String type = sc.nextLine();
            Division.dividend = dividend;
            Division.devisor = devisor;
            dividentLength = Splitter.getLenght(dividend);
            devisorLength = Splitter.getLenght(devisor);
            if (type.equalsIgnoreCase("eu")) {
                Print.printDivisionEU(dividend, devisor);
            } else if (type.equalsIgnoreCase("gb")) {
                Print.printDivisionGB(dividend, devisor);

            }
        }

    }


    public static ArrayList<ArrayList<ArrayList<Integer>>> division(int dividend, int divisor) {
        // Создаем списки для хранения результатов
        ArrayList<Integer> quotient = new ArrayList<>(); // Список для хранения частного
        ArrayList<Integer> dividendList = Splitter.toList(dividend); // Преобразуем делимое в список цифр
        ArrayList<Integer> divisorList = Splitter.toList(divisor); // Преобразуем делитель в список цифр
        ArrayList<Integer> tempMinuendList = new ArrayList<>(); // Временный список для хранения уменьшаемого
        ArrayList<Integer> tempSubtrahendList = new ArrayList<>(); // Временный список для хранения вычитаемого
        ArrayList<Integer> tempEqualsList = new ArrayList<>(); // Временный список для хранения результата вычитания
        ArrayList<ArrayList<Integer>> solution = new ArrayList<>(); // Список для хранения промежуточных решений
        ArrayList<ArrayList<Integer>> equals = new ArrayList<>(); // Список для хранения частных на каждом шаге
        ArrayList<ArrayList<ArrayList<Integer>>> resultList = new ArrayList<>(); // Список для хранения итоговых результатов

        int pos = 0;
        while (pos < dividendList.size()) {
            tempMinuendList.add(dividendList.get(pos));
            int tempDividend = Splitter.listToInt(tempMinuendList);

            // Если текущий временный делимый больше или равен делителю, выполняем деление
            if (tempDividend >= divisor) {
                solution.add(new ArrayList<>(tempMinuendList));
                quotient.add(tempDividend / divisor); // Находим цифру частного и добавляем ее в список
                tempSubtrahendList = Splitter.to1xList(Multiplication.multiplication(divisor, quotient.get(quotient.size() - 1))); // Находим результат умножения делителя на цифру частного
                tempEqualsList = Splitter.trim(Subtraction.subtraction(tempMinuendList, tempSubtrahendList)); // Находим результат вычитания уменьшаемого и вычитаемого

                // Добавляем промежуточные результаты в список решения
                solution.add(new ArrayList<>(tempSubtrahendList));
                solution.add(new ArrayList<>(tempEqualsList));

                // Если результат вычитания равен нулю, очищаем временный список для уменьшаемого
                if (tempEqualsList.size() == 1 && tempEqualsList.get(0) == 0) {
                    tempMinuendList.clear();
                } else {
                    tempMinuendList = tempEqualsList; // Иначе обновляем временный список уменьшаемого
                }
            } else {
                if (pos == dividendList.size() - 1) {
                    solution.add(new ArrayList<>(tempMinuendList));

                }
                if (!quotient.isEmpty()) {
                    quotient = Addition.multiplyAddition(Multiplication.multiplication(quotient, 10)); // Умножаем частное на 10 для добавления новой цифры
                }
            }
            pos++;
        }
        ArrayList<ArrayList<Integer>> newEquals = new ArrayList<>();
        newEquals.add(quotient); // Добавляем итоговое частное в список частных на каждом шаге
        equals.add(quotient); // Добавляем итоговое частное в отдельный список частных
        ArrayList<ArrayList<Integer>> newSolution = new ArrayList<>();
        resultList.add(solution); // Добавляем промежуточные решения в итоговый список
        resultList.add(newEquals); // Добавляем список итоговых частных в итоговый список

        return resultList; // Возвращаем итоговый список
    }


}

class Multiplication {
    int num1 = 0;
    int num2 = 0;

    public Multiplication(int num1, int num2) {
        if (num1 == 0 || num2 == 0) {
            System.out.println("Cannot be multiplied by 0 ");
        } else {
            this.num1 = num1;
            this.num2 = num2;
            int offset = Splitter.getLenght(num1 * num2);
            Print.SpaceLegth((offset - Splitter.getLenght(num1)) + 1);
            System.out.println(num1);
            Print.SpaceLegth(1);
            System.out.println("x");
            Print.SpaceLegth((offset - Splitter.getLenght(num2)) + 1);
            Print.SpaceLegth((multiplication(num1, num2).size() - Splitter.getLenght(num2)));
            System.out.println(num2);
            Print.SpaceLegth(2);
            Print.LineLength(offset - 1);
            System.out.println();

            Print.printMultiplication(multiplication(num1, num2));
            System.out.println();
        }
    }

    public static ArrayList<ArrayList<Integer>> multiplication(int num1, int num2) {
        ArrayList<Integer> num1List = Splitter.toList(num1);
        ArrayList<Integer> num2List = Splitter.toList(num2);
        ArrayList<ArrayList<Integer>> equalsSum = multiplication(num1List, num2List);
        return equalsSum;

    }

    public static ArrayList<ArrayList<Integer>> multiplication(ArrayList<Integer> num1List, int num2) {
        ArrayList<Integer> num2List = Splitter.toList(num2);
        ArrayList<ArrayList<Integer>> equalsSum = multiplication(num1List, num2List);
        return equalsSum;

    }


    public static ArrayList<ArrayList<Integer>> multiplication(ArrayList<Integer> num1List, ArrayList<Integer> num2List) {
        ArrayList<Integer> equals = new ArrayList<>();
        ArrayList<ArrayList<Integer>> equalsSum = new ArrayList<>();
        ArrayList<Integer> equalsTempSum = new ArrayList<>();
        int iteralNum2 = num2List.size() - 1;
        int temp = 0;
        while (iteralNum2 >= 0) {
            int iteralNum1 = num1List.size() - 1;
            while (iteralNum1 >= 0) {
                if (iteralNum1 == 0) {
                    equalsTempSum.add((num2List.get(iteralNum2) * num1List.get(iteralNum1) + temp) % 10);
                    temp = (num2List.get(iteralNum2) * num1List.get(iteralNum1) + temp) / 10;
                    if (temp > 0) {
                        equalsTempSum.add(temp);
                        temp = 0;
                    }


                } else {
                    equalsTempSum.add((num2List.get(iteralNum2) * num1List.get(iteralNum1) + temp) % 10);
                    temp = (num2List.get(iteralNum2) * num1List.get(iteralNum1) + temp) / 10;
                }


                iteralNum1--;
            }
            Collections.reverse(equalsTempSum);
            equalsSum.add(new ArrayList<>(equalsTempSum));
            equalsTempSum.clear();
            iteralNum2--;
        }
        Collections.reverse(equals);


        return equalsSum;

    }

}

/**                             ****** Suport classes *****/


class Print {

    public static void Space(int num) {
        for (int i = 0; i < Splitter.getLenght(num); i++) {
            System.out.print(" ");


        }
    }

    public static void SpaceLegth(int numLegth) {
        for (int i = 0; i < numLegth; i++) {
            System.out.print(' ');


        }
    }

    public static void LineLength(int numLength) {
        for (int i = 0; i < numLength; i++) {
            System.out.print('─');


        }
    }

    public static void Line(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print("─");


        }
    }
    public static void LineGB(int numLegth) {
        for (int i = 0; i < numLegth; i++) {
            System.out.print("─");


        }
    }

    public static void List(ArrayList<Integer> intList) {
        for (int numb : intList) {
            System.out.print(numb);
        }
    }

    public static void ListForm(int posStart, ArrayList<Integer> intList) {
        for (int i = posStart; i < intList.size(); i++) {
            System.out.print(intList.get(i));
        }
    }
    /**
     * Выводит отформатированное вычитание.
     *
     * @param posStartToPrint Начальная позиция для выравнивания.
     * @param minuend         Уменьшаемое в вычитании.
     * @param subtrahend      Вычитаемое в вычитании.
     */
    public static void printSubstruction(int posStartToPrint, int minuend, int subtrahend) {
        int equals = minuend - subtrahend;
        SpaceLegth(posStartToPrint);
        System.out.println(" " + minuend);
        SpaceLegth(Splitter.getLenght(minuend) - Splitter.getLenght(subtrahend));
        System.out.println("-" + subtrahend);
        System.out.print(" ");
        Line(Splitter.getLenght(minuend));
        System.out.println();
        SpaceLegth((Splitter.getLenght(minuend) - Splitter.getLenght(equals)) + 1);
        System.out.println(equals);

    }

    public static void printAddition(int posStartToPrint, int num1, int num2) {
        int equals = num1 + num2;
        SpaceLegth(posStartToPrint);
        System.out.println(" " + num1);
        SpaceLegth(Splitter.getLenght(num1) - Splitter.getLenght(num2));
        System.out.println("+" + num2);
        System.out.print(" ");
        Line(Splitter.getLenght(num1));
        System.out.println();
        SpaceLegth((Splitter.getLenght(num1) - Splitter.getLenght(equals)));
        System.out.println(equals);

    }

    public static void printAddition(int... num) {
        ArrayList<Integer> intList = Splitter.to1xList(num);

        int equals = 0;
        for (int i = 0; i < num.length; i++) {
            equals += num[i];

        }
        int mSize = Splitter.getLenght(sumNum(num));
        for (int i = 0; i < intList.size(); i++) {
            if (i == 0) {
                SpaceLegth(mSize - (Splitter.getLenght(intList.get(i))) + 1);
                System.out.println(" " + intList.get(i));
                continue;
            }
            SpaceLegth(mSize - Splitter.getLenght(intList.get(i)) + 1);
            System.out.println("+" + intList.get(i));
            if (i == intList.size() - 1) {
                System.out.print("  ");
                LineLength(mSize);
                System.out.println();
                SpaceLegth(mSize - Splitter.getLenght(equals));
                System.out.println("  " + equals);
            }

        }

    }
    public static void Zero(int legth){
        for (int i = 0; i < legth; i++) {
            System.out.print('0');

        }
    }

    public static void printDivisionEU(int num1, int num2) {
        ArrayList<ArrayList<ArrayList<Integer>>> list = Division.division(num1, num2);
        ArrayList<ArrayList<Integer>> solution = list.get(0);
        ArrayList<ArrayList<Integer>> quotient = list.get(1);


        int offset = Splitter.getLenght(num1);
        Print.SpaceLegth(offset);
        System.out.print(num1);
        System.out.print('|');
        System.out.print(num2);
        System.out.println();
        int pos = 0;
        for (int i = 0; i < solution.size(); i++) {
            int line = 0;

            if (i != 0 && i == 1) {
                pos += solution.get(i).size();
                Print.SpaceLegth(offset - 1);
                System.out.print("-");
                Print.List(solution.get(i));
                Print.SpaceLegth((offset - solution.get(i).size()));
                System.out.print('├');
                Line(quotient.get(0).size());
                System.out.println();
                Print.SpaceLegth(offset);
                Print.LineLength(solution.get(i).size());
                SpaceLegth((offset - solution.get(i).size()));
                System.out.print("|");
                List(quotient.get(0));
                System.out.println();
                SpaceLegth(offset + pos - solution.get(i + 1).size());
                List(solution.get(i + 1));
                i++;
                continue;
            } else if (i != 0) {
                if (i + 2 < solution.size()) {
                    pos = pos + solution.get(i).size() - solution.get(i - 1).size();
                    Print.ListForm(solution.get(i - 1).size(), solution.get(i));
                    System.out.println();
                    Print.SpaceLegth(((offset + pos) - solution.get(i + 1).size()) - 1);
                    System.out.print("-");
                    Print.List(solution.get(i + 1));
                    System.out.println();
                    Print.SpaceLegth(((offset + pos) - solution.get(i).size()));
                    Print.LineLength(solution.get(i).size());
                    System.out.println();
                    if (solution.get(solution.size() - 1).isEmpty() && i + 2 == solution.size() - 1) {
                        Print.SpaceLegth(offset + pos - solution.get(i + 2).size() - 1);
                        System.out.println(0);
                    }
                    Print.SpaceLegth(offset + pos - solution.get(i + 2).size());
                    List(solution.get(i + 2));
                    i += 2;
                } else {
                    ListForm(solution.get(i - 1).size(), solution.get(i));
                }
            }
        }
        System.out.println();
        if (!solution.get(solution.size() - 1).isEmpty()) {
            System.out.print("\n The remainder is: ");
            Print.List(solution.get(solution.size() - 1));
            System.out.println();
        }
    }

    public static void printDivisionGB(int num1, int num2) {
        ArrayList<ArrayList<ArrayList<Integer>>> list = Division.division(num1, num2);
        ArrayList<ArrayList<Integer>> solution = list.get(0);
        ArrayList<ArrayList<Integer>> quotient = list.get(1);


        int offset = Splitter.getLenght(num1);
        Print.SpaceLegth(offset);
        Print.Zero(Splitter.getLenght(num1) - Splitter.getLenght(num1/num2));
        System.out.println(num1 / num2);
        Print.SpaceLegth(offset);
        Print.LineGB(Splitter.getLenght( num1));
        System.out.println();
        Print.SpaceLegth(offset - (Splitter.getLenght(num2)+1));
        System.out.print(num2);
        System.out.print('│');
        System.out.print(num1);
        System.out.println();
        int pos = 0;
        for (int i = 0; i < solution.size(); i++) {
            int line = 0;

            if (i != 0 && i == 1) {
                pos += solution.get(i).size();
                Print.SpaceLegth(offset - 1);
                System.out.print("-");
                Print.List(solution.get(i));
                System.out.println();
                Print.SpaceLegth(offset);
                Print.LineLength(solution.get(i).size());
                System.out.println();
                SpaceLegth(offset + pos - solution.get(i + 1).size());
                List(solution.get(i + 1));
                i++;
                continue;
            } else if (i != 0) {
                if (i + 2 < solution.size()) {
                    pos = pos + solution.get(i).size() - solution.get(i - 1).size();
                    Print.ListForm(solution.get(i - 1).size(), solution.get(i));
                    System.out.println();
                    Print.SpaceLegth(((offset + pos) - solution.get(i + 1).size()) - 1);
                    System.out.print("-");
                    Print.List(solution.get(i + 1));
                    System.out.println();
                    Print.SpaceLegth(((offset + pos) - solution.get(i).size()));
                    Print.LineLength(solution.get(i).size());
                    System.out.println();
                    if (solution.get(solution.size() - 1).isEmpty() && i + 2 == solution.size() - 1) {
                        Print.SpaceLegth(offset + pos - solution.get(i + 2).size() - 1);
                        System.out.println(0);
                    }
                    Print.SpaceLegth(offset + pos - solution.get(i + 2).size());
                    List(solution.get(i + 2));
                    i += 2;
                } else {
                    ListForm(solution.get(i - 1).size(), solution.get(i));
                }
            }
        }
        System.out.println();
        if (!solution.get(solution.size() - 1).isEmpty()) {
            System.out.print("\n The remainder is: ");
            Print.List(solution.get(solution.size() - 1));
            System.out.println();
        }
    }


    public static int sumNum(int[] num) {
        int sumNum = 0;
        for (int i = 0; i < num.length; i++) {
            sumNum += num[i];
        }
        return sumNum;
    }


    public static void printMultiplication(ArrayList<ArrayList<Integer>> numList) {
        ArrayList<Integer> equals = Addition.multiplyAddition(numList);
        for (int i = 0; i < numList.size(); i++) {
            SpaceLegth(equals.size() - numList.get(i).size() - i);
            if (i == 0) {
                System.out.print(' ');
            } else {
                System.out.print('+');
            }
            for (int j = 0; j < numList.get(i).size(); j++) {
                System.out.print(numList.get(i).get(j));

            }
            System.out.println();

        }
        SpaceLegth(1);
        LineLength(equals.size());
        System.out.println();
        SpaceLegth(1);
        for (int i = 0; i < equals.size(); i++) {
            System.out.print(equals.get(i));


        }

    }

    /**
     * Выводит отформатированное вычитание.
     *
     * @param num1        Уменьшаемое в вычитании.
     * @param num2      Вычитаемое в вычитании.
     */
    public static void printSubstruction(int num1, int num2) {
        int equals = num1 - num2;
        System.out.println(" " + num1);
        SpaceLegth(Splitter.getLenght(num1) - Splitter.getLenght(num2));
        System.out.println("-" + num2);
        System.out.print(" ");
        Line(Splitter.getLenght(num1));
        System.out.println();
        SpaceLegth((Splitter.getLenght(num1) - Splitter.getLenght(equals)) + 1);
        System.out.println(equals);

    }

}

class Splitter {
    public static int getSplitDevisior(int number) {
        int splitDevisior = 1;
        if (number >= 0 || number == 0) {
            if (number != 0) {
                while (number >= splitDevisior * 10) {
                    splitDevisior *= 10;
                }

            } else {
                System.out.println("Число равно 0 ");
                return splitDevisior = 1;
            }
        } else {
            number *= -1;
            while (number >= splitDevisior * 10) {
                splitDevisior *= 10;
            }

        }
        return splitDevisior;
    }

    public static int getMyltiplyDevisior(int size) {
        int myltiplydevisior = 1;
        for (int i = 0; i < size - 1; i++) {
            myltiplydevisior *= 10;
        }
        return myltiplydevisior;
    }

    public static int getLenght(int number) {
        int lengthArray = 1;
        int numLength = 1;
        if (number >= 0 || number == 0) {
            if (number != 0) {
                while (number >= lengthArray * 10) {
                    lengthArray *= 10;
                    numLength++;
                }


            } else {
                return numLength = 1;
            }
        } else {
            number *= -1;
            while (number >= lengthArray * 10) {
                lengthArray *= 10;
                numLength++;
            }

        }
        return numLength;
    }

    public static ArrayList<Integer> toList(int number) {
        int splitDevisior = getSplitDevisior(number);
        int lenght = getLenght(number);
        ArrayList<Integer> intList = new ArrayList<>();
        int i = 0;
        while (i < lenght) {
            intList.add(number / splitDevisior);
            number %= splitDevisior;
            splitDevisior /= 10;
            i++;


        }
        return intList;
    }

    public static ArrayList<ArrayList<Integer>> to2xList(int... number) {
        ArrayList<ArrayList<Integer>> intList = new ArrayList<>();
        for (int f = 0; f < number.length; f++) {


            int i = 0;
            int lenght = getLenght(number[f]);
            int splitDevisior = getSplitDevisior(number[f]);
            ArrayList<Integer> temp = new ArrayList<>();
            while (i < lenght) {
                temp.add(number[f] / splitDevisior);
                number[f] %= splitDevisior;
                splitDevisior /= 10;
                i++;


            }
            intList.add(new ArrayList<>(temp));
        }
        return intList;
    }

    public static ArrayList<Integer> to1xList(int... numbers) {
        ArrayList<Integer> list1x = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            list1x.add(numbers[i]);

        }
        return list1x;
    }

    public static ArrayList<Integer> to1xList(ArrayList<ArrayList<Integer>> numbers) {
        ArrayList<Integer> list1x = new ArrayList<>();
        for (int i = 0; i < numbers.get(0).size(); i++) {
            list1x.add(numbers.get(0).get(i));

        }
        return list1x;
    }

    public static int[] getIntToArray(int numb) {
        int splitDevisior = getLenght(numb);
        splitDevisior = getSplitDevisior(numb);
        System.out.println(splitDevisior);
        if (numb < 0) {
            numb *= -1;
        }
        int[] intArray = new int[getLenght(numb)];
        int i = 0;
        while (i < getLenght(numb)) {
            intArray[i] = numb / splitDevisior;
            numb %= splitDevisior;
            splitDevisior /= 10;
            i++;


        }
        return intArray;
    }

    public static int listToInt(ArrayList<Integer> number) {
        int numberInt = 0;
        int lenght = number.size();
        int splitDivisor = 1;
        for (int i = 0; i < lenght - 1; i++) {
            splitDivisor *= 10;
        }
        for (int i = 0; i < lenght; i++) {
            numberInt = numberInt + (number.get(i) * splitDivisor);
            splitDivisor /= 10;

        }

        return numberInt;
    }

    public static ArrayList<Integer> trim(ArrayList<Integer> intList) {
        for (int i = 0; i < intList.size() && intList.get(0) == 0; i++) {
            intList.remove(0);

        }
        if (intList.size() == 1 && intList.get(0) == 0) {
            intList.clear();
            return intList;
        } else {
            return intList;
        }

    }


}



