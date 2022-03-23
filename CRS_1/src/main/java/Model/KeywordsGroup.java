package Model;

import java.util.List;

public class KeywordsGroup {

    private List<String> keywords;
    private Countries country;
    private FeatureType keywordsType;

    public KeywordsGroup(List<String> keywords, Countries country, FeatureType keywordsType) {
        this.keywords = keywords;
        this.country = country;
        this.keywordsType = keywordsType;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Countries getCountry() {
        return country;
    }

    public FeatureType getKeywordsType() {
        return keywordsType;
    }
}
