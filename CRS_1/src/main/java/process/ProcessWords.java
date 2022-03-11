package process;

import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ProcessWords {
    // From https://gist.github.com/larsyencken/1440509
    private final String stopWordsPath = "C:\\Users\\barte\\Documents\\INF_STOS\\SEM6_Proj\\KSR\\laby\\zad1\\CRS\\CRS_1\\src\\main\\resources\\stopwords.txt";

    public List<String> removeStopWordsFromText (List<String> toClean) throws FileNotFoundException {
        List<String> words = new LinkedList<>();
        File file = new File(stopWordsPath);
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
