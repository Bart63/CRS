package Process;

import Model.Article;

import java.util.List;

public class CalculateClassificationQuality {

    private List<Article> articles;
    private Double precision;
    private Double recall;
    private Double accuracy;
    private Double F1;

    public Double getPrecision() {
        return precision;
    }

    public Double getRecall() {
        return recall;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public Double getF1() {
        return F1;
    }

    public CalculateClassificationQuality(List<Article> articles) {
        this.articles = articles;

        Calculate();
    }

    private void Calculate() {

    }
}
