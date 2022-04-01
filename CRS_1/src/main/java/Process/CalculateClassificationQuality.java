package Process;

import Model.Article;
import Model.Countries;
import Model.FeaturesVector;
import Repos.ArticlesRepo;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateClassificationQuality {

    private ArticlesRepo articlesRepo;
    private Double[] precision;
    private Double[] recall;
    private Double accuracy;
    private Double F1;

    private enum ErrorsType{
        TP,
        TN,
        FP,
        FN
    };

    private Map<ErrorsType, Double> errorsTable;

    public CalculateClassificationQuality(ArticlesRepo articlesRepo) {
        this.articlesRepo = articlesRepo;

        precision = new Double[Countries.values().length + 1];
        recall = new Double[Countries.values().length + 1];

        Arrays.fill(precision, 0.0);
        Arrays.fill(recall, 0.0);

        errorsTable = new HashMap<>();

        calculate();
    }



    public Double[] getPrecision() {
        return precision;
    }

    public Double[] getRecall() {
        return recall;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public Double getF1() {
        return F1;
    }

    private void calculate() {

        accuracy = 0.0;
        int i = 0;

        for (Countries country: Countries.values()
             ) {

            for (ErrorsType errorType: ErrorsType.values()
                 ) {

                Double n = 0.0;

                for (FeaturesVector vector: articlesRepo.getVectors()
                     ) {

                    switch (errorType){

                        case TP -> {

                            if (vector.getCountry() == country && vector.getCountry() == vector.getPredicatedCountry())
                                n++;
                        }
                        case TN -> {

                            if (vector.getCountry() != country && vector.getPredicatedCountry() != country)
                                n++;

                        }

                        case FP -> {

                            if (vector.getCountry() != country && vector.getPredicatedCountry() == country)
                                n++;
                        }

                        case FN -> {

                            if (vector.getCountry() == country && vector.getPredicatedCountry() != country)
                                n++;
                        }
                    }
                }

                errorsTable.put(errorType, n);
            }

            double TPFP = errorsTable.get(ErrorsType.TP) + errorsTable.get(ErrorsType.FP);
            if (TPFP != 0)
                precision[i] = errorsTable.get(ErrorsType.TP) / (TPFP);
            else
                precision[i] = 0.0;


            double TPFN = errorsTable.get(ErrorsType.TP) + errorsTable.get(ErrorsType.FN);

            if (TPFN != 0.0)
                recall[i] = errorsTable.get(ErrorsType.TP) / (TPFN);
            else
                recall[i] = 0.0;

            accuracy += errorsTable.get(ErrorsType.TP);

            i++;

            errorsTable.clear();
        }

        accuracy /= articlesRepo.getVectors().size();

        double p = 0;

        int N = Countries.values().length;

        for (Double d:precision
             ) {
            p += d;
        }

        precision[precision.length-1] = p / N;

        p = 0;
        for (Double d:recall
        ) {
            p += d;
        }

        recall[recall.length-1] = p / N;

        double PR = precision[precision.length-1] + recall[recall.length-1];
        if (PR != 0.0)
            F1 = (2 * precision[precision.length-1] * recall[recall.length-1]) / (PR);
        else
            F1 = 0.0;
    }

    @Override
    public String toString() {

        String s = "";
        for(int i = 0; i<Countries.values().length; i++){

            s += "|| " + Countries.values()[i].name() + " ||\n";
            s += "Precision: " + precision[i] + "\n";
            s += "Recall: " + recall[i] + "\n";
            s += "-----------------------------------------" + "\n";
        }

        s += "Avg precision: " + precision[precision.length - 1] + "\n";
        s += "Avg recall: " + recall[recall.length - 1] + "\n";
        s += "Accuracy: " + accuracy + "\n";
        s += "F1 : " + F1 + "\n";


        return s;
    }
}
