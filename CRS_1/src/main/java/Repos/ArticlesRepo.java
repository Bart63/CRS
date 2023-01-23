package Repos;

import Model.Article;
import Model.Countries;
import Model.FeaturesVector;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class ArticlesRepo {

    private List<Article> articles;

    public ArticlesRepo(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Article getArticle(int index){
        return (index < articles.size() && index >= 0) ? articles.get(index) : null;
    }

    public void addArticle(Article article){
        articles.add(article);
    }

    public Article getArticleByVectorHashCode (int featuresVectorHashCode) {

        for (Article a: articles
             ) {
            if (a.getFeaturesVector().hashCode() == featuresVectorHashCode)
                return a;
        }

        return null;
    }

    public List<FeaturesVector> getVectors(){

        List<FeaturesVector> vectors = new ArrayList<>();

        for (Article a:articles
        ) {

            vectors.add(a.getFeaturesVector());
        }

        return vectors;
    }

    @Override
    public String toString() {

        String s = "Number of articles by country:\n";

        for (Countries c: Countries.values()
             ) {

            Integer n = articles.stream().filter(x -> x.getFeaturesVector().getCountry() == c).toList().size();

            s += c.name() + ": " + n.toString() + "\n";
        }

        s += "Total: " + articles.stream().count() + "\n";

        return s;
    }
}
