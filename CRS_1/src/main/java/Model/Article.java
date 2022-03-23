package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Article {
    private List<String> title;
    private List<String> body;
    private String date;
    private String dateline;
    private String places;
    private String topics;
    private String people;
    private String orgs;
    private String exchanges;
    private FeaturesVector featuresVector;

    public Article(String title, String body, String date, String dateline, String places, String topics, String people, String orgs, String exchanges) {
        this.title = split(title);
        this.body = split(body);
        this.date = date;
        this.dateline = dateline;
        this.places = places;
        this.topics = topics;
        this.people = people;
        this.orgs = orgs;
        this.exchanges = exchanges;

        featuresVector = new FeaturesVector();
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getBody() {
        return body;
    }

    public void setBody(List<String> body) {
        this.body = body;
    }

    private String getDate() {
        return date;
    }

    private void setDate() {
        this.date = date;
    }

    private String getDateline() {
        return dateline;
    }

    private void setDateline() {
        this.dateline = dateline;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getOrgs() {
        return orgs;
    }

    public void setOrgs(String orgs) {
        this.orgs = orgs;
    }

    public String getExchanges() {
        return exchanges;
    }

    public void setExchanges(String exchanges) {
        this.exchanges = exchanges;
    }

    public List<String> split(String text) {
        List<String> wordList = new ArrayList<String>(Arrays.asList(text.replaceAll("[^a-zA-Z ']", "").split(" ")));
        wordList.removeAll(Arrays.asList("", null));
        return wordList;
    }
}
