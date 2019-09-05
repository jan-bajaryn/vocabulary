package com.learnenglish.vocabulary.logic.preparation;

import com.learnenglish.vocabulary.logic.pack1.Element;
import com.learnenglish.vocabulary.logic.pack1.LowTermCheck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindDuplicates {


    //the method write text with duplicated english words to the file with fileName
    public static void findDuplicatesInTextFiles(String fileName) throws IOException {
        Stream<Path> walk = Files.walk(Paths.get(System.getProperty("user.dir")));

        List<String> files = walk.filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(f -> f.endsWith(".txt"))
                .collect(Collectors.toList());

        List<String> globalStrings = new ArrayList<>();

        List<List<String>> filesWithStr = new ArrayList<>();

        for (String file :
                files) {
            filesWithStr.add(Files.lines(Paths.get(file)).collect(Collectors.toList()));
        }

        for (List<String> stringList :
                filesWithStr) {
            globalStrings.addAll(stringList);
        }

        List<Element> elements = globalStrings.stream()
                .map(s -> {
                    String[] strings = s.split(LowTermCheck.SEPARATOR);
                    if (strings.length == 2)
                        return new Element(strings[0], strings[1]);
                    else
                        return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

//не забыть исправить
        Map<String, Integer> countMap = new HashMap<>();

        for (Element item : elements) {
            if (countMap.containsKey(item.getEnglishWord()))
                countMap.put(item.getEnglishWord(), countMap.get(item.getEnglishWord()) + 1);
            else
                countMap.put(item.getEnglishWord(), 1);
        }

        StringBuilder retStr = new StringBuilder();

        // using for-each loop for iteration over Map.entrySet()
        for (Map.Entry<String, Integer> entry : countMap.entrySet()){
            if (entry.getValue()>1)
                retStr.append(entry.getKey()).append("\n");
        }
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());

        BufferedWriter writer = new BufferedWriter(new FileWriter(LowTermCheck.PREFIX_FOLDER + File.separator + fileName + ".txt"));
        writer.write(retStr.toString());
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        findDuplicatesInTextFiles("duplicates");
    }

}


//        files.forEach(s -> globalStrings.addAll(s));

//
//        files.stream()
//                .map(f -> {
//                    try {
//                        return Files.lines(Paths.get(f)).collect(Collectors.toList());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return null;
//                    }
//                })
//                .collect(Collectors.toList());

//                .map(f -> {
//                    try {
//                        return Files.lines(Paths.get(f)).collect(Collectors.toList());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return null;
//                    }
//
//                })
//                .reduce((fst, sec) -> {
//                    assert sec != null;
//                    fst.addAll(sec);
//
//                    return fst;
//                }).;
//
