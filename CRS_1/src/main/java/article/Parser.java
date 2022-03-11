package article;

// https://jsoup.org/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import process.ProcessWords;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private static final String ARTICLES_PATH = "C:\\Users\\barte\\Documents\\INF_STOS\\SEM6_Proj\\KSR\\laby\\zad1\\CRS\\CRS_1\\src\\main\\resources\\reuters";
    private static final String CHARSET_UTF_8 = "UTF-8";
    private List<Document> reutersDocs;
    private List<Article> articles;

    public Parser() throws IOException {
        loadFiles();
        loadArticles();
    }

    public void loadFiles() throws IOException {
        File directory = new File(ARTICLES_PATH);
        File[] documents = directory.listFiles();
        reutersDocs = new LinkedList<>();
        for (File doc : documents) {
            reutersDocs.add(Jsoup.parse(doc, CHARSET_UTF_8, ""));
        }
    }

    public void loadArticles() {
        articles = new LinkedList<>();
        for (Document doc : reutersDocs) {
            for (Element el : doc.select("reuters")) {
                String date = el.select("date").text();
                String topics = el.select("topics").select("d").text();
                String places = el.select("places").select("d").text();
                String people = el.select("people").select("d").text();
                String orgs = el.select("orgs").select("d").text();
                String exchanges = el.select("exchanges").select("d").text();
                String title = el.select("title").text();
                String text = el.select("text").text();
                String dateline = el.select("dateline").text();
                String body = el.select("body").text();

                // Skip not completed articles and articles with multiple places
                boolean isIncomplete = text.contains("blah");
                boolean isMultiValue = places.contains(" ");
                boolean isFromRequiredPlace = places.matches("west-germany|usa|france|uk|canada|japan");

                if(isIncomplete || isMultiValue || !isFromRequiredPlace) {
                    continue;
                }

                Article a = new Article(title, body, date, dateline, places, topics, people, orgs, exchanges);
                articles.add(a);
            }
        }
    }

    public List<Article> getArticles() {
        return articles;
    }

    // stemization and remove stopwords
    public List<Article> processArticles () throws FileNotFoundException {
        ProcessWords pw = new ProcessWords();
        List<Article> processedArticles = new ArrayList<Article>();
        for (Article a : getArticles()) {
            List<String> removedStopWordsTitle = pw.removeStopWordsFromText(a.getTitle());
            List<String> stemmedTitle = pw.stemWords(removedStopWordsTitle);
            a.setTitle(stemmedTitle);

            List<String> removedStopWordsBody = pw.removeStopWordsFromText(a.getBody());
            List<String> stemmedBody = pw.stemWords(removedStopWordsBody);
            a.setBody(stemmedBody);

            processedArticles.add(a);
        }
        return processedArticles;
    }
}