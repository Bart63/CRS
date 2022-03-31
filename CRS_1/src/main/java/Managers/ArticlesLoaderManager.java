package Managers;

// https://jsoup.org/
import Model.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import Process.ProcessWords;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ArticlesLoaderManager {
    private static final String ARTICLES_PATH = "reuters";
    private static final String CHARSET_UTF_8 = "UTF-8";
    private List<Document> reutersDocs;
    private List<Article> articles;

    public ArticlesLoaderManager() throws IOException, URISyntaxException {
        loadFiles();
        loadArticles();
        processArticles();
    }

    public void loadFiles() throws URISyntaxException, IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(ARTICLES_PATH);

        File directory = new File(resource.toURI());

        File[] documents = directory.listFiles();
        reutersDocs = new LinkedList<>();


        for (File doc : documents) {
            reutersDocs.add(Jsoup.parse(doc, CHARSET_UTF_8, ""));
        }

        //reutersDocs.add(Jsoup.parse(documents[0], CHARSET_UTF_8, ""));

    }

    public void loadArticles() {
        articles = new LinkedList<>();
        for (Document doc : reutersDocs) {
            for (Element el : doc.select("reuters")) {
                String date = el.select("date").text();
                String topics = el.select("topics").select("d").text().toLowerCase(Locale.ROOT);
                String places = el.select("places").select("d").text().toLowerCase(Locale.ROOT);
                String people = el.select("people").select("d").text().toLowerCase(Locale.ROOT);
                String orgs = el.select("orgs").select("d").text().toLowerCase(Locale.ROOT);
                String exchanges = el.select("exchanges").select("d").text().toLowerCase(Locale.ROOT);
                String title = el.select("title").text().toLowerCase(Locale.ROOT);
                String text = el.select("text").text().toLowerCase(Locale.ROOT);
                String dateline = el.select("dateline").text();
                String body = el.select("body").text();

                // Skip not completed articles and articles with multiple places
                boolean isIncomplete = text.contains("blah");
                boolean isMultiValue = places.contains(" ");
                boolean isFromRequiredPlace = places.matches("west-germany|usa|france|uk|canada|japan");

                if(isIncomplete || isMultiValue || !isFromRequiredPlace) {
                    continue;
                }

                Article a = new Article(title, text, date, dateline, places, topics, people, orgs, exchanges);
                articles.add(a);
            }
        }
    }

    public List<Article> getArticles(double p, boolean testing) {

        if (testing) {
            return articles.subList(0, (int)(articles.size() * p));
        }
        else {
            return articles.subList((int)(articles.size() * p), articles.size());
        }
    }

    // stemization and remove stopwords
    public void processArticles () throws FileNotFoundException, URISyntaxException {
        ProcessWords pw = new ProcessWords();

        for (Article a : articles) {
            List<String> removedStopWordsTitle = pw.removeStopWordsFromText(a.getTitle());
            List<String> stemmedTitle = pw.stemWords(removedStopWordsTitle);
            a.setTitle(stemmedTitle);

            List<String> removedStopWordsBody = pw.removeStopWordsFromText(a.getBody());
            List<String> stemmedBody = pw.stemWords(removedStopWordsBody);
            a.setBody(stemmedBody);

        }

    }
}