package Model;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

    public void setFeatureType(FeatureType featureType) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("\n")
                .append("value", value)
                .append("featureType", featureType)
                .toString();
    }
}
