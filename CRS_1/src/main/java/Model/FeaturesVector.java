package Model;

import java.util.Arrays;

public class FeaturesVector {

    private Feature[] features;
    private Countries country;
    private Countries predicatedCountry;

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

    public Countries getPredicatedCountry() {
        return predicatedCountry;
    }

    public void setPredicatedCountry(Countries predicatedCountry) {
        this.predicatedCountry = predicatedCountry;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("features", features)
                .append("\n")
                .append("country", country)
                .append("predicatedCountry", predicatedCountry)
                .toString();
    }
}
