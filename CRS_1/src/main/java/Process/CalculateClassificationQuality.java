package Process;

import Model.Article;
import Model.Countries;

import java.util.List;

public class CalculateClassificationQuality {

    private List<Article> articles;
    private Double[] precision;
    private Double[] recall;
    private Double accuracy;
    private Double F1;


    public CalculateClassificationQuality(List<Article> articles) {
        this.articles = articles;

        Calculate();

        precision = new Double[Countries.values().length + 1];
        recall = new Double[Countries.values().length + 1];

    }

    public List<Article> getArticles() {
        return articles;
    }

    public Double[] getPrecision() {
        return precision;
    }

    public Double[] getRecall() {
        return recall;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public Double getF1() {
        return F1;
    }

    private void Calculate() {

    }
}
