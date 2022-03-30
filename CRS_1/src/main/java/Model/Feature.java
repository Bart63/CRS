package Model;

public class Feature<T> {

    private T value;
    private FeatureType featureType;


    public Feature(T value, FeatureType featureType) {
        this.value = value;
        this.featureType = featureType;

    }

    public Feature(FeatureType featureType) {
        this.featureType = featureType;
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


}
