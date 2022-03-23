package Model;

import java.util.Arrays;

public class FeaturesVector {

    private Feature[] features;

    public FeaturesVector() {
        features = new Feature[10];
    }

    public Feature getFeature(int index) {
        return (index < features.length && index >= 0) ? features[index] : null;
    }

    public Feature getFeature(FeatureType featureType){
        return Arrays.stream(features).filter(x -> x.getFeatureType().equals(featureType)).findAny().orElse(null);
    }

    public void setFeature(int index, Feature feature) {
        features[index] = feature;
    }
}
