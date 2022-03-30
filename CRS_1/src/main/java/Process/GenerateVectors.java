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

    public GenerateVectors(ArticlesRepo articlesRepo, KeywordsRepo keywordsRepo) {

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

        generate();
    }

    private void generate() {

        for (var g:keywordsRepo.getKeywordsGroups()
             ) {

            if (g.isEnabled()) {

                for (var a : articlesRepo.getArticles()
                ) {

                    switch (g.getKeywordsType()){

                        case day -> {

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
