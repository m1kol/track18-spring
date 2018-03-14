package ru.track.cypher;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

/**
 * Вспомогательные методы шифрования/дешифрования
 */
public class CypherUtil {

    public static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Генерирует таблицу подстановки - то есть каждой букве алфавита ставится в соответствие другая буква
     * Не должно быть пересечений (a -> x, b -> x). Маппинг уникальный
     *
     * @return таблицу подстановки шифра
     */
    @NotNull
    public static Map<Character, Character> generateCypher() {
        Map<Character, Character> retMap = new HashMap<>();
        for (int i=0; i < SYMBOLS.length()-1; i++) {
            retMap.put(SYMBOLS.charAt(i), SYMBOLS.charAt(i+1));
        }
        retMap.put(SYMBOLS.charAt(SYMBOLS.length()-1), SYMBOLS.charAt(0));

        return retMap;
    }

}
