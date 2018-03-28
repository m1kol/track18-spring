package ru.track.cypher;

import java.util.*;

import org.jetbrains.annotations.NotNull;

public class Decoder {

    // Расстояние между A-Z -> a-z
    public static final int SYMBOL_DIST = 32;

    private Map<Character, Character> cypher;

    /**
     * Конструктор строит гистограммы открытого домена и зашифрованного домена
     * Сортирует буквы в соответствие с их частотой и создает обратный шифр Map<Character, Character>
     *
     * @param domain - текст по кторому строим гистограмму языка
     */
    public Decoder(@NotNull String domain, @NotNull String encryptedDomain) {
        Map<Character, Integer> domainHist = createHist(domain);
        Map<Character, Integer> encryptedDomainHist = createHist(encryptedDomain);

        cypher = new LinkedHashMap<>();

        Iterator<Character> domainIter = domainHist.keySet().iterator();

        for (Character character : encryptedDomainHist.keySet()) {
            cypher.put(character, domainIter.next());
        }
    }

    public Map<Character, Character> getCypher() {
        return cypher;
    }

    /**
     * Применяет построенный шифр для расшифровки текста
     *
     * @param encoded зашифрованный текст
     * @return расшифровка
     */
    @NotNull
    public String decode(@NotNull String encoded) {
        StringBuilder decodedStr = new StringBuilder();
        for (int i = 0; i < encoded.length(); i++) {
            char ch = encoded.charAt(i);
            if (Character.isLetter(ch))
                decodedStr.append(cypher.get(Character.toLowerCase(ch)));
            else decodedStr.append(ch);
        }

        return decodedStr.toString();
    }

    /**
     * Считывает входной текст посимвольно, буквы сохраняет в мапу.
     * Большие буквы приводит к маленьким
     *
     *
     * @param text - входной текст
     * @return - мапа с частотой вхождения каждой буквы (Ключ - буква в нижнем регистре)
     * Мапа отсортирована по частоте. При итерировании на первой позиции наиболее частая буква
     */
    @NotNull
    Map<Character, Integer> createHist(@NotNull String text) {
        Map<Character, Integer> retMap = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            if (letter >= 'A' && letter <= 'Z' || letter >= 'a' && letter <= 'z') {
                if (letter < 'Z') {
                    letter += SYMBOL_DIST;
                }
                if (retMap.containsKey(letter)) {
                    int val = retMap.get(letter);
                    retMap.put(letter, val+1);
                }
                else retMap.put(letter, 1);
            }
        }

        List<Map.Entry<Character, Integer>> list = new ArrayList<>(retMap.entrySet());

        list.sort((o1, o2) -> o2.getValue() - o1.getValue());

        Map<Character, Integer> sortedRetMap = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> entry: list)
            sortedRetMap.put(entry.getKey(), entry.getValue());

        return sortedRetMap;
    }

}
