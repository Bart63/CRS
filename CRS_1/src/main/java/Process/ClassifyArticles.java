package Process;

import Model.Article;

import java.util.List;
import java.util.Vector;

public class ClassifyArticles {

    List<Article> articles;

    List<Vector> trainingVectors;


    public ClassifyArticles(List<Article> articles, List<Vector> trainingVectors) {
        this.articles = articles;
        this.trainingVectors = trainingVectors;

        Classify();
    }

    private void Classify() {

    }
}
