import Model.Article;
import Managers.ArticlesLoaderManager;
import Repos.ArticlesRepo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ArticlesLoaderManager parser = new ArticlesLoaderManager();
        ArticlesRepo articlesRepo = new ArticlesRepo(parser.getArticles());
        System.out.println(articlesRepo.getArticles().size());
        for (Article article: articlesRepo.getArticles()) {
            System.out.println(article.getBody());
           System.out.println();
       }
    }
}
