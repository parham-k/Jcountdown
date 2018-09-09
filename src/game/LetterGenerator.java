package game;

import java.util.HashMap;
import java.util.Map;

public class LetterGenerator {

    private Map<Character, Integer> consonantWeights, vowelWeights;
    private int consonantWeightSum, vowelWeightSum;

    public LetterGenerator() {
        vowelWeights = new HashMap<>();
        vowelWeights.put('E', 21912);
        vowelWeights.put('A', 14810);
        vowelWeights.put('O', 14003);
        vowelWeights.put('I', 13318);
        vowelWeights.put('U', 5246);
        vowelWeightSum = 0;
        for (Integer w : vowelWeights.values())
            vowelWeightSum += w;
        consonantWeights = new HashMap<>();
        consonantWeights.put('T', 16587);
        consonantWeights.put('N', 12666);
        consonantWeights.put('S', 11450);
        consonantWeights.put('R', 10977);
        consonantWeights.put('H', 10795);
        consonantWeights.put('D', 7874);
        consonantWeights.put('L', 7253);
        consonantWeights.put('C', 4943);
        consonantWeights.put('M', 4761);
        consonantWeights.put('F', 4200);
        consonantWeights.put('Y', 3853);
        consonantWeights.put('W', 3819);
        consonantWeights.put('G', 3693);
        consonantWeights.put('P', 3316);
        consonantWeights.put('B', 2715);
        consonantWeights.put('V', 2019);
        consonantWeights.put('K', 1257);
        consonantWeights.put('X', 315);
        consonantWeights.put('Q', 205);
        consonantWeights.put('J', 188);
        consonantWeights.put('Z', 128);
        consonantWeightSum = 0;
        for (Integer w : consonantWeights.values())
            consonantWeightSum += w;
    }

    public char getConsonant() {
        int d = (int) (Math.random() * consonantWeightSum);
        for (Map.Entry<Character, Integer> entry : consonantWeights.entrySet()) {
            d -= entry.getValue();
            if (d < 0)
                return entry.getKey();
        }
        return 'T';
    }

    public char getVowel() {
        int d = (int) (Math.random() * vowelWeightSum);
        for (Map.Entry<Character, Integer> entry : vowelWeights.entrySet()) {
            d -= entry.getValue();
            if (d < 0)
                return entry.getKey();
        }
        return 'E';
    }

}
