import Managers.KeywordsLoaderManager;
import Model.Article;
import Managers.ArticlesLoaderManager;
import Model.Countries;
import Model.FeatureType;
import Repos.ArticlesRepo;
import Repos.KeywordsRepo;
import Process.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        /*
        ArticlesLoaderManager parser = new ArticlesLoaderManager();
        ArticlesRepo articlesRepo = new ArticlesRepo(parser.getArticles());
        System.out.println(articlesRepo.getArticles().size());
        for (Article article: articlesRepo.getArticles()) {
            System.out.println(article.getBody());
           System.out.println();
       }

         */

        int k = Integer.parseInt(args[0]);
        double p = Double.parseDouble(args[1]);
        int m = Integer.parseInt(args[2]);

        boolean[] f = new boolean[12];

        for (int i = 0; i < 12; i++){
            f[i] = (Integer.parseInt(args[i + 3])) == 1;
        }

        ArticlesLoaderManager parser = new ArticlesLoaderManager();
        ArticlesRepo learningArticlesRepo = new ArticlesRepo(parser.getArticles(p, true));
        ArticlesRepo testingArticlesRepo = new ArticlesRepo(parser.getArticles(p, false));

        KeywordsLoaderManager keywordsLoaderManager = new KeywordsLoaderManager(f);
        KeywordsRepo learningKeywordsRepo = keywordsLoaderManager.getKeywordsRepo();

        //System.out.println(learningKeywordsRepo.getKeywordsGroup(11).getKeywords());

        //System.out.println(learningArticlesRepo.getArticle(0).getDate());
        //System.out.println(learningArticlesRepo.getArticle(0).getDateline());

        GenerateVectors generateLearningVectors = new GenerateVectors(learningArticlesRepo, learningKeywordsRepo);
        GenerateVectors generateTestingVectors = new GenerateVectors(testingArticlesRepo, learningKeywordsRepo);

        ClassifyArticles classifyArticles = new ClassifyArticles(learningArticlesRepo, testingArticlesRepo, m, k);

        for(int i = 0; i< testingArticlesRepo.getArticles().size(); i++)
        {
            if (testingArticlesRepo.getArticle(i).getFeaturesVector().getPredicatedCountry() != Countries.usa) {
                System.out.println(testingArticlesRepo.getArticle(i).getFeaturesVector().toString());
                System.out.println("\n");
            }
        }

    }
}
