package com.learnenglish.vocabulary.logic.preparation;


import com.learnenglish.vocabulary.logic.pack1.Element;
import com.learnenglish.vocabulary.logic.pack1.LowTermCheck;

import java.io.*;
import java.util.ArrayList;

import static com.learnenglish.vocabulary.logic.longterm.LongTermCheck.SEPARETOR;


public class ConcatFilesFromToConsoleVariant {
    public static final int MAX_VAL = 79;
    public static final int MIN_VAL = /*MAX_VAL - 9 < 0 ? 0 : MAX_VAL - 9*/MAX_VAL - 9;
    public static final String NEW_FILE = "HPbuf1";
    public static final String GROUP_NAME = "HP";

    public static void main(String[] args) {
        run(MAX_VAL, MIN_VAL, GROUP_NAME);

    }

    public static void run(int maxVal, int minVal, String groupName) {
        ArrayList<Element> elements = listAllElem(maxVal, minVal, groupName);

//        elements.sort(new Comparator<Element>() {
//            @Override
//            public int compare(Element o1, Element o2) {
//                return o1.getEnglishWord().compareTo(o2.getEnglishWord());
//            }
//        });

        writeFile(LowTermCheck.PREFIX_FOLDER + File.separator + NEW_FILE + ".txt", elements);
    }

    public static ArrayList<Element> listAllElem(int maxVal, int minVal, String groupName) {
        ArrayList<Element> elements = new ArrayList<>();
        for (int i = minVal; i <= maxVal; i++) {
            elements.addAll(readingFromFile(LowTermCheck.PREFIX_FOLDER + File.separator + groupName + i + ".txt"));
        }
        return elements;
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
