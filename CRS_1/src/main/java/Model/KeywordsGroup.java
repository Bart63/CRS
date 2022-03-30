package Model;

import java.util.List;

public class KeywordsGroup {

    private List<String> keywords;
    private FeatureType keywordsType;
    private boolean isEnabled;

    public KeywordsGroup(boolean isEnabled, FeatureType keywordsType, List<String> keywords) {
        this.keywords = keywords;
        this.keywordsType = keywordsType;
        this.isEnabled = isEnabled;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public FeatureType getKeywordsType() {
        return keywordsType;
    }
}
