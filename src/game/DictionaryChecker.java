package game;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DictionaryChecker {

    // URL format: http://words.bighugelabs.com/api/{version}/{api key}/{word}/{format}
    private static final String API_KEY = "2ffdce48d0d131efeb139b10b246af1f";

    public static boolean check(String word) throws IOException {
        String urlString = String.format("http://words.bighugelabs.com/api/%s/%s/%s/", "2", API_KEY, word);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setDoOutput(true);
        int responseCode = connection.getResponseCode();
        return responseCode == 200;
    }

    public static boolean hasValidLetters(String word, char[] letters) {
        for (int i = 0; i < word.length(); i++) {
            boolean contains = false;
            for (char c : letters)
                if (word.charAt(i) == c)
                    contains = true;
            if (!contains)
                return false;
        }
        return true;
    }


}
