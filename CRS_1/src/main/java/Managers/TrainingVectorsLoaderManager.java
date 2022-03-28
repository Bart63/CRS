package Managers;

import Repos.TrainingVectorsRepo;

public class TrainingVectorsLoaderManager {

    TrainingVectorsRepo trainingVectorsRepo;

    public TrainingVectorsLoaderManager() {
        load();
    }

    private void load(){

    }

    public TrainingVectorsRepo getTrainingVectorsRepo() {
        return trainingVectorsRepo;
    }
}
