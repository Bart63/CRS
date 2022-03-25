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

    public Feature(FeatureType featureType) {
        this.featureType = featureType;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public Countries getCountry() {
        return country;
    }
}
