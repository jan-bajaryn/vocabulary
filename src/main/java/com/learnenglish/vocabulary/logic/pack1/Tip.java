package com.learnenglish.vocabulary.logic.pack1;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnenglish.vocabulary.logic.pack1.LowTermCheck.SEPARATOR;
import static com.learnenglish.vocabulary.logic.pack1.LowTermCheck.readingFromFile;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Tip {
    public static final String userDir = System.getProperty("user.dir");

    public Tip() {
    }

    public List<String> getListFiles() {
        try (Stream<Path> walk = Files.walk(Paths.get(userDir))) {

            return walk.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(f -> f.endsWith(".txt"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<String>();
        }
    }

    public String findTipInOther(String word) {
        List<String> files = getListFiles();
        for (String str :
                files) {
            Element element = findWordInDoc(word, str);
            if (element != null) {
                return "Your answer may be connecting with: <br/>" + element.getEnglishWord() +
                        " - " + element.getTranslation();
            }
        }
        return "";
    }

    public Element findWordInDoc(String word, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (s.startsWith(word)) {
                    String[] arr = s.split(SEPARATOR);
                    if (arr.length == 2)
                        if (arr[0].equals(word))
                            return new Element(arr[0], arr[1]);
                }
            }
            return null;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("problems");
        }
    }

    public Element[] getAllElements() {
        List<String> files = getListFiles();
        HashSet<Element> elements = new HashSet<>();
        for (String file : files) {
            try {
                elements.addAll(readingFromFile(file, Element.DEFAULT_COUNT_OF_TRYINGS));
            } catch (Exception ignored) {
            }
        }
        return elements.toArray(new Element[elements.size()]);
    }
}
