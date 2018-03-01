package ru.track;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNumeric;


/**
 * Задание 1: Реализовать два метода
 *
 * Формат файла: текстовый, на каждой его строке есть (или/или)
 * - целое число (int)
 * - текстовая строка
 * - пустая строка (пробелы)
 *
 * Числа складываем, строки соединяем через пробел, пустые строки пропускаем
 *
 *
 * Пример файла - words.txt в корне проекта
 *
 * ******************************************************************************************
 *  Пожалуйста, не меняйте сигнатуры методов! (название, аргументы, возвращаемое значение)
 *
 *  Можно дописывать новый код - вспомогательные методы, конструкторы, поля
 *
 * ******************************************************************************************
 *
 */
public class CountWords {

    String skipWord;

    public CountWords(String skipWord) {
        this.skipWord = skipWord;
    }

    /**
     * Метод на вход принимает объект File, изначально сумма = 0
     * Нужно пройти по всем строкам файла, и если в строке стоит целое число,
     * то надо добавить это число к сумме
     * @param file - файл с данными
     * @return - целое число - сумма всех чисел из файла
     */
    public long countNumbers(File file) throws Exception {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        long result = 0;
        int num;
        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            try {
                num = parseInt(line);
                result += num;
            } catch (NumberFormatException e){
                //
            }
        }

        return result;
    }


    /**
     * Метод на вход принимает объект File, изначально результат= ""
     * Нужно пройти по всем строкам файла, и если в строка не пустая и не число
     * то надо присоединить ее к результату через пробел
     * @param file - файл с данными
     * @return - результирующая строка
     */
    public String concatWords(File file) throws Exception {
        StringBuilder resultBuilder = new StringBuilder();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line, result;

        while ((line = bufferedReader.readLine()) != null)
        {
            if (isNumeric(line) | line.equals(skipWord))
            {
                continue;
            }
            resultBuilder.append(line);
            resultBuilder.append(" ");
        }
        result = resultBuilder.toString();

        return result;
    }

    public static void main (String args[])
    {
        CountWords obj = new CountWords("");
        File file = new File ("/home/mkolesov/track18-spring/L2-objects/words.txt");
        try {
            long number = obj.countNumbers(file);
            System.out.println("number = " + number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String line = obj.concatWords(file);
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

