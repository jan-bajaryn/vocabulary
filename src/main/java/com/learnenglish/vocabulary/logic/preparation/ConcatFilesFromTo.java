package com.learnenglish.vocabulary.logic.preparation;


import com.learnenglish.vocabulary.logic.pack1.Element;
import com.learnenglish.vocabulary.logic.pack1.LowTermCheck;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

import static com.learnenglish.vocabulary.logic.longterm.LongTermCheck.SEPARETOR;

@Slf4j
public class ConcatFilesFromTo {
    public static final int MAX_VAL = 89;
    public static final int MIN_VAL = MAX_VAL - 9;
    public static final String NEW_FILE = "HPbuf1";
    public static final String GROUP_NAME = "HP";

    public static void run(int maxVal, int minVal, String groupName) {
        ArrayList<Element> elements = listAllElem(maxVal, minVal, groupName);
        String toWriteStr = writeFile(elements);

        String fileName = LowTermCheck.PREFIX_FOLDER + File.separator + NEW_FILE + ".txt";

//        elements.sort((o1, o2) -> o1.getEnglishWord().compareTo(o2.getEnglishWord()));
//        elements.sort(Comparator.comparing(Element::getEnglishWord));

        try {
            writeStringToFile(toWriteStr, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public static String writeFile(ArrayList<Element> elements) {
        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            String text = elements.get(i).toString();
            if (i != elements.size() - 1) {
                retStr.append(text).append("\n");
            } else {
                retStr.append(text);
            }
        }
        return retStr.toString();
    }

    public static void writeStringToFile(String str, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(LowTermCheck.PREFIX_FOLDER + File.separator + NEW_FILE + ".txt"));
        writer.write(str);
        writer.close();
    }

//    public static void writeFile(String fileName, ArrayList<Element> elements) {
//        try (FileWriter writer = new FileWriter(fileName, false)) {
//            for (int i = 0; i < elements.size(); i++) {
//                String text = elements.get(i).toString();
//                if (i != elements.size() - 1) {
//                    writer.write(text + "\n");
//                } else {
//                    writer.write(text);
//                }
//                writer.flush();
//            }
//
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
}