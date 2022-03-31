package Process;

import Model.Article;
import Model.Countries;
import Model.FeatureType;
import Model.FeaturesVector;
import Repos.ArticlesRepo;

import java.util.*;
import java.util.stream.Stream;

public class ClassifyArticles {

    ArticlesRepo trainingArticles;
    ArticlesRepo testingArticles;

    private Map<Integer, Double> distances;

    public ClassifyArticles(ArticlesRepo trainingArticles, ArticlesRepo testingArticles, int metricsIndex, int k) {
        this.trainingArticles = trainingArticles;
        this.testingArticles = testingArticles;

        this.distances = new HashMap<>();

        classify(metricsIndex, k);
    }

    private void classify(int metrics, int k) {

        for (Article aTesting: testingArticles.getArticles()
             ) {

            for (Article aTraining: trainingArticles.getArticles()
                 ) {

                double[] distances = new double[FeatureType.values().length];

                FeaturesVector testingVector = aTesting.getFeaturesVector();
                FeaturesVector trainingVector = aTraining.getFeaturesVector();

                int i = 0;
                for (FeatureType type: FeatureType.values()
                     ) {

                    switch (type){

                        case day, mth, imp, met -> {

                            distances[i] = (int)testingVector.getFeature(type).getValue() - (int)trainingVector.getFeature(type).getValue();
                        }
                        case cit, top, exc, per, cur, com, con, ene -> {

                            distances[i] = calculateNGram(
                                    (String) testingVector.getFeature(type).getValue(), (String) trainingVector.getFeature(type).getValue());
                        }
                    }

                    i++;
                }

                double d = calculateDistance(metrics, distances);

                this.distances.put(trainingVector.hashCode(), d);


            }

            Stream<Map.Entry<Integer, Double>> sorted = this.distances.entrySet().stream().sorted(
                    (Map.Entry<Integer, Double> x1, Map.Entry<Integer, Double> x2) -> {
                if (x1.getValue() > x2.getValue())
                    return -1;
                if (x1.getValue() < x2.getValue())
                    return 1;
                else
                    return 0;
            });


            List<Map.Entry<Integer, Double>> kVectors = sorted.toList().subList(0, k);

            List<Article> kArticles = new ArrayList<>();

            for (var v: kVectors
                 ) {

                kArticles.add(trainingArticles.getArticleByVectorHashCode(v.getKey()));
            }

            Integer[] numberOfCountries = new Integer[Countries.values().length];

            int i = 0;
            for (Countries c: Countries.values()
                 ) {

                numberOfCountries[i] = kArticles.stream().filter(x -> x.getActualCountry() == c).toList().size();
                i++;
            }

            Integer max = Arrays.stream(numberOfCountries).max(Integer::compare).orElse(0);

            if (Arrays.stream(numberOfCountries).filter(x -> x == max).toArray().length == 1){

                aTesting.getFeaturesVector().setPredicatedCountry(Countries.values()[Arrays.stream(numberOfCountries).toList().indexOf(max)]);
            }
            else
            {
                List<Countries> c = new ArrayList<>();

                for (int j = 0; j<Countries.values().length; j++)
                {
                    if (numberOfCountries[j] == max)
                        c.add(Countries.values()[j]);
                }

                Double[] averages = new Double[c.size()];
                Arrays.fill(averages, 0.0);


                for (var a: kArticles
                     ) {

                    if (c.contains(a.getActualCountry())){

                        int index = c.indexOf(a.getActualCountry());

                        averages[index] += numberOfCountries[index];
                    }

                }


                for (Countries country: c
                     ) {

                    int numberOfSamples = kArticles.stream().filter(x -> x.getActualCountry() == country).toList().size();
                    int index = c.indexOf(country);

                    averages[index] /= (double)numberOfSamples;
                }

                Double minAverage = Arrays.stream(averages).min(Double::compare).orElse(0.0);

                if (Arrays.stream(averages).filter(x -> x == minAverage).toList().size() == 1)
                {
                    aTesting.getFeaturesVector().setPredicatedCountry(c.get(Arrays.stream(averages).toList().indexOf(minAverage)));
                }
                else
                {
                    Random r = new Random();
                    aTesting.getFeaturesVector().setPredicatedCountry(c.get(r.nextInt(0, c.size())));
                }

            }
        }
    }

    private double calculateDistance(int metrics, double[] distances){

        switch (metrics){
            case 1 ->{

                double sum = 0;

                for (double d: distances
                     ) {

                    sum += Math.pow(d, 2);
                }

                return Math.sqrt(sum);
            }
            case 2 -> {

                double sum = 0;

                for (double d: distances
                ) {
                    sum += Math.abs(d);
                }

                return sum;

            }
            case 3 ->{

                for (int i = 0; i<distances.length; i++) {
                    distances[i] = Math.abs(distances[i]);
                }

                return Arrays.stream(distances).max().orElse(0);
            }
            default -> {
                return 0;
            }
        }
    }

    private double calculateNGram (String t1, String t2){

        if (t1.isEmpty() || t2.isEmpty())
            return 0;

        double d = 0;

        String s1 = (t1.length() == t2.length()) ? t1 : ((t1.length() > t2.length()) ? t1 : t2);

        String s2 = (s1.equals(t1)) ? t2 : t1;

        for (int i=0; i<s1.length() - 3; i++){

            String tri = s1.substring(i, i+3);

            if (s2.contains(tri))
            {
                d++;
            }

        }

        return d / (s1.length() - 2);
    }
}
