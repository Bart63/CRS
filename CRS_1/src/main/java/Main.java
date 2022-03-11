import article.Article;
import article.Parser;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        List<Article> articles = parser.getArticles();
        System.out.println(articles.size());
//        for (Article article: articles) {
//            System.out.println(article.getBody());
//            System.out.println();
//        }
    }
}
