package Model;

import java.util.Arrays;

public class FeaturesVector {

    private Feature[] features;
    private Countries country;

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }

    public FeaturesVector() {

        features = new Feature[FeatureType.values().length];

        int i = 0;
        for (FeatureType feature: FeatureType.values()) {

            if (feature.equals(FeatureType.day) || feature.equals(FeatureType.mth) || feature.equals(FeatureType.imp)
            || feature.equals(FeatureType.met)){
                features[i] = new Feature<Integer>(feature);
            }
            else {
                features[i] = new Feature<String>(feature);
            }
            i++;
        }

    }

    public Feature[] getFeatures() {
        return features;
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
