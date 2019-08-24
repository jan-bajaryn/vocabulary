package com.learnenglish.vocabulary.logic.preparation;


import com.learnenglish.vocabulary.logic.pack1.Element;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static com.learnenglish.vocabulary.logic.longterm.LongTermCheck.SEPARETOR;


public class ConcatFilesConNum {
    public static final int[] fileNumArr = {0, 10, 11, 8};
    public static final String newFile = "HPbuf3";
    public static final String groupName = "HP";

    public static void main(String[] args) {

        ArrayList<Element> elements = new ArrayList<>();
        int size = fileNumArr.length;
        for (int value : fileNumArr) {
            elements.addAll(readingFromFile(groupName + value + ".txt"));
        }
        writeFile(newFile + ".txt", elements);
    }

    public static ArrayList<Element> readingFromFile(String fileName) {

        ArrayList<Element> elements = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (!s.equals("")) {
                    String[] arr = s.split(SEPARETOR);
                    elements.add(new Element(arr[0], arr[1]));
                }
            }
            return elements;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("problems");
        }
    }

    public static void writeFile(String fileName, ArrayList<Element> elements) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (int i = 0; i < elements.size(); i++) {
                String text = elements.get(i).toString();
                if (i != elements.size() - 1) {
                    writer.write(text + "\n");
                } else {
                    writer.write(text);
                }
                writer.flush();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
