package Repos;

import Model.Article;

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
}
