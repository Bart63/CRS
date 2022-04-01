package Process;

import Model.FeatureType;
import Model.FeaturesVector;
import Repos.ArticlesRepo;
import Repos.KeywordsRepo;

import java.util.*;

public class GenerateVectors {

    private ArticlesRepo articlesRepo;
    private KeywordsRepo keywordsRepo;

    Map<String, Integer> monthsToNumbers;

    public GenerateVectors(ArticlesRepo articlesRepo, KeywordsRepo keywordsRepo, boolean[] enableFeatures) {

        monthsToNumbers = new HashMap<>();
        monthsToNumbers.put("JAN", 1);
        monthsToNumbers.put("FEB", 2);
        monthsToNumbers.put("MAR", 3);
        monthsToNumbers.put("APR", 4);
        monthsToNumbers.put("MAY", 5);
        monthsToNumbers.put("JUN", 6);
        monthsToNumbers.put("JUL", 7);
        monthsToNumbers.put("AUG", 8);
        monthsToNumbers.put("SEP", 9);
        monthsToNumbers.put("OCT", 10);
        monthsToNumbers.put("NOV", 11);
        monthsToNumbers.put("DEC", 12);

        this.articlesRepo = articlesRepo;
        this.keywordsRepo = keywordsRepo;

        generate(enableFeatures);
    }

    private void generate(boolean[] enableFeatures) {

        for (var g:keywordsRepo.getKeywordsGroups()
             ) {

            if (g.isEnabled()) {

                for (var a : articlesRepo.getArticles()
                ) {

                    switch (g.getKeywordsType()){

                        case day -> {

                            if (!enableFeatures[0]){
                                a.getFeaturesVector().getFeature(FeatureType.day).setValue(0);
                                continue;
                            }


                            if (!a.getDate().isEmpty()) {
                                String[] dateParts = a.getDate().split("-");

                                a.getFeaturesVector().getFeature(FeatureType.day).setValue(Integer.parseInt(dateParts[0]));
                            }
                            else
                            {
                                a.getFeaturesVector().getFeature(FeatureType.day).setValue(0);
                            }

                        }

                        case mth -> {

                            if (!enableFeatures[1]){
                                a.getFeaturesVector().getFeature(FeatureType.mth).setValue(0);
                                continue;
                            }

                            if (!a.getDate().isEmpty()) {
                                String[] dateParts = a.getDate().split("-");

                                a.getFeaturesVector().getFeature(FeatureType.mth).setValue(monthsToNumbers.get(dateParts[1]));


                            }
                            else
                            {
                                a.getFeaturesVector().getFeature(FeatureType.mth).setValue(0);
                            }
                        }
                        case cit -> {

                            if (!enableFeatures[2]){
                                a.getFeaturesVector().getFeature(FeatureType.cit).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));
                            c.add(a.getDateline());

                            a.getFeaturesVector().getFeature(FeatureType.cit).setValue("");

                            for (String s: c
                                 ) {

                                if (g.getKeywords().contains(s))
                                {
                                    a.getFeaturesVector().getFeature(FeatureType.cit).setValue(s);
                                    break;
                                }
                            }

                        }
                        case top -> {

                            if (!enableFeatures[3]){
                                a.getFeaturesVector().getFeature(FeatureType.top).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));
                            c.add(a.getTopics());

                            a.getFeaturesVector().getFeature(FeatureType.top).setValue("");

                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    a.getFeaturesVector().getFeature(FeatureType.top).setValue(s);
                                    break;
                                }
                            }
                        }
                        case exc -> {

                            if (!enableFeatures[4]){
                                a.getFeaturesVector().getFeature(FeatureType.exc).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));
                            c.add(a.getExchanges());

                            a.getFeaturesVector().getFeature(FeatureType.exc).setValue("");

                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    a.getFeaturesVector().getFeature(FeatureType.exc).setValue(s);
                                    break;
                                }
                            }
                        }
                        case per -> {

                            if (!enableFeatures[5]){
                                a.getFeaturesVector().getFeature(FeatureType.per).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));
                            c.add(a.getPeople());

                            a.getFeaturesVector().getFeature(FeatureType.per).setValue("");

                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    a.getFeaturesVector().getFeature(FeatureType.per).setValue(s);
                                    break;
                                }
                            }
                        }
                        case cur -> {

                            if (!enableFeatures[6]){
                                a.getFeaturesVector().getFeature(FeatureType.cur).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));

                            List<Integer> n = new ArrayList<>();

                            for (String s: g.getKeywords()
                            ) {
                                    n.add(Collections.frequency(c, s));

                            }

                            Integer max = Collections.max(n);

                            if (!max.equals(0))
                            {
                                a.getFeaturesVector().getFeature(FeatureType.cur).setValue(g.getKeywords().get(n.indexOf(max)));
                            }
                            else
                            {
                                a.getFeaturesVector().getFeature(FeatureType.cur).setValue("");
                            }

                        }
                        case com -> {

                            if (!enableFeatures[7]){
                                a.getFeaturesVector().getFeature(FeatureType.com).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));

                            a.getFeaturesVector().getFeature(FeatureType.com).setValue("");

                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    a.getFeaturesVector().getFeature(FeatureType.com).setValue(s);
                                    break;
                                }
                            }

                        }
                        case con -> {

                            if (!enableFeatures[8]){
                                a.getFeaturesVector().getFeature(FeatureType.con).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));

                            a.getFeaturesVector().getFeature(FeatureType.con).setValue("");

                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    a.getFeaturesVector().getFeature(FeatureType.con).setValue(s);
                                    break;
                                }
                            }
                        }
                        case ene -> {

                            if (!enableFeatures[9]){
                                a.getFeaturesVector().getFeature(FeatureType.ene).setValue("");
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));


                            a.getFeaturesVector().getFeature(FeatureType.ene).setValue("");

                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    a.getFeaturesVector().getFeature(FeatureType.ene).setValue(s);
                                    break;
                                }
                            }
                        }
                        case imp -> {

                            if (!enableFeatures[10]){
                                a.getFeaturesVector().getFeature(FeatureType.imp).setValue(0);
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));

                            int n = 0;
                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    n++;
                                }
                            }

                            a.getFeaturesVector().getFeature(FeatureType.imp).setValue(n);
                        }
                        case met -> {

                            if (!enableFeatures[11]){
                                a.getFeaturesVector().getFeature(FeatureType.met).setValue(0);
                                continue;
                            }

                            List<String> c = new ArrayList<>();
                            c.addAll(a.getTitle());
                            c.addAll((a.getBody()));

                            int n = 0;
                            for (String s: c
                            ) {

                                if (g.getKeywords().contains(s))
                                {
                                    n++;
                                }
                            }

                            a.getFeaturesVector().getFeature(FeatureType.met).setValue(n);
                        }
                    }
                }
            }

        }
    }
}
