package Managers;

import Model.FeatureType;
import Model.KeywordsGroup;
import Repos.KeywordsRepo;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KeywordsLoaderManager {

    private static final String FEATURES_PATH = "features";
    private KeywordsRepo keywordsRepo;
    private List<KeywordsGroup> keywordsGroups;
    private List<File> documents;

    public KeywordsLoaderManager(boolean[] features) throws URISyntaxException, IOException {

        keywordsGroups = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(FEATURES_PATH);

        File directory = new File(resource.toURI());

        documents = new ArrayList<File>(List.of(directory.listFiles()));

        for (int i = 0; i< FeatureType.values().length; i++){

            keywordsGroups.add(new KeywordsGroup(features[i], FeatureType.values()[i], loadKeywords(i)));
        }

        keywordsRepo = new KeywordsRepo(keywordsGroups);
    }

    private List<String> loadKeywords(int i) throws IOException {

        if (FeatureType.values()[i].equals(FeatureType.day) ||
                FeatureType.values()[i].equals(FeatureType.mth)){
            return new ArrayList<>();
        }
        else {

            String fileName = (i+1) + "_" + FeatureType.values()[i].name() + ".txt";

            File file = null;

            for (File f: documents
                 ) {
                if (f.getName().equals(fileName)) {
                    file = f;
                    break;
                }
            }

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            br.readLine();
            br.readLine();

            List<String> keywords = new ArrayList<>();

            while((line = br.readLine()) != null)
            {
                keywords.add(line);

            }
            fr.close();
            return keywords;
        }
    }

    public KeywordsRepo getKeywordsRepo() {
        return keywordsRepo;
    }
}
