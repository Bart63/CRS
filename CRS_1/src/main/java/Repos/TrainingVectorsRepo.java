package Repos;

import Model.FeaturesVector;

import java.util.List;

public class TrainingVectorsRepo {

    private List<FeaturesVector> vectors;

    public TrainingVectorsRepo(List<FeaturesVector> vectors) {
        this.vectors = vectors;
    }

    public List<FeaturesVector> getVectors() {
        return vectors;
    }
}
