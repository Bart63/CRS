package Process;

import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ProcessWords {
    // From https://gist.github.com/larsyencken/1440509
    private final String stopWordsPath = "stopwords.txt";

    public List<String> removeStopWordsFromText (List<String> toClean) throws FileNotFoundException, URISyntaxException {
        List<String> words = new LinkedList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(stopWordsPath);

        File file = new File(resource.toURI());

        Scanner s = new Scanner(file);
        while (s.hasNext()) {
            words.add(s.next());
        }
        s.close();

        List<String> textWithoutStopWords = new ArrayList<>();
        for (String b : toClean) {
            if (!words.contains(b.toLowerCase())) {
                textWithoutStopWords.add(b);
            }
        }
        return textWithoutStopWords;
    }

    public List<String> stemWords (List<String> toStem) {
        List<String> stemmed = new ArrayList<>();
        EnglishStemmer stemmer = new EnglishStemmer ();
        for (String b : toStem) {
            stemmer.setCurrent(b);
            stemmer.stem();
            stemmed.add(stemmer.getCurrent());
        }
        return stemmed;
    }
}
