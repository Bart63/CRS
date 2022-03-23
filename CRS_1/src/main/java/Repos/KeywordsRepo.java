package Repos;

import Model.FeatureType;
import Model.KeywordsGroup;

import java.util.List;

public class KeywordsRepo {

    private List<KeywordsGroup> keywordsGroups;

    public KeywordsRepo(List<KeywordsGroup> keywordsGroups) {
        this.keywordsGroups = keywordsGroups;
    }

    public KeywordsGroup getKeywordsGroup(int index){
        return (index > 0 && index < keywordsGroups.size()) ? keywordsGroups.get(index) : null;
    }

    public KeywordsGroup[] getKeywordsGroup(FeatureType featureType){
        return (KeywordsGroup[]) keywordsGroups.stream().filter(x -> x.getKeywordsType().equals(featureType)).toArray();
    }

}
