package Model;

public class Feature<T> {

    private T value;
    private FeatureType featureType;
    private Countries country;

    public Feature(T value, FeatureType featureType, Countries country) {
        this.value = value;
        this.featureType = featureType;
        this.country = country;
    }

    public T getValue() {
        return value;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public Countries getCountry() {
        return country;
    }
}
