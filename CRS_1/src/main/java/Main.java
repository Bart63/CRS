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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {


        int k = 5;
        double p = 0.7;
        int m = 1;

        boolean[] f = new boolean[12];
        Arrays.fill(f, true);


        System.out.println("Parameters (k-p-m-features)\nk-number of neighbours, p-percentage of training set, m-metric");
        Scanner sc = new Scanner(System.in);
        String buf = sc.nextLine();

        if (!buf.isEmpty()){

            List<String> strings = Arrays.stream(buf.split("-")).toList();

            k = Integer.parseInt(strings.get(0));
            p = Double.parseDouble(strings.get(1));
            m = Integer.parseInt(strings.get(2));

            int i = 0;
            for (char s:strings.get(3).toCharArray()
                 ) {
                f[i] = (s == '1');
                i++;
            }
        }

        ArticlesLoaderManager parser = new ArticlesLoaderManager();
        ArticlesRepo learningArticlesRepo = new ArticlesRepo(parser.getArticles(p, true));
        ArticlesRepo testingArticlesRepo = new ArticlesRepo(parser.getArticles(p, false));

        KeywordsLoaderManager keywordsLoaderManager = new KeywordsLoaderManager(f);
        KeywordsRepo learningKeywordsRepo = keywordsLoaderManager.getKeywordsRepo();

        //System.out.println(learningKeywordsRepo.getKeywordsGroup(11).getKeywords());

        //System.out.println(learningArticlesRepo.getArticle(0).getDate());
        //System.out.println(learningArticlesRepo.getArticle(0).getDateline());

        GenerateVectors generateLearningVectors = new GenerateVectors(learningArticlesRepo, learningKeywordsRepo, f);
        GenerateVectors generateTestingVectors = new GenerateVectors(testingArticlesRepo, learningKeywordsRepo, f);

        ClassifyArticles classifyArticles = new ClassifyArticles(learningArticlesRepo, testingArticlesRepo, m, k, f);

        CalculateClassificationQuality quality = new CalculateClassificationQuality(testingArticlesRepo);

        /*
        for(int i = 0; i< testingArticlesRepo.getArticles().size(); i++)
        {

            System.out.println(testingArticlesRepo.getArticle(i).getFeaturesVector().toString());
            System.out.println("\n");

        }
         */

        System.out.print(quality);

        System.out.print("Training set: " + learningArticlesRepo);
        System.out.print("Testing set: " + testingArticlesRepo);
    }
}
