package com.learnenglish.vocabulary.logic.preparation;


import com.learnenglish.vocabulary.logic.pack1.Element;
import com.learnenglish.vocabulary.logic.pack1.LowTermCheck;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Format {

    public static final String GROUP_NAME = "HP";
    public static final int MIN_VAL = 0;
    public static final int MAX_VAL = 128;
    public static final int INTERVAL = 20;


    public static void main(String[] args) {
        int maxVal = MAX_VAL;
        int minVal = MIN_VAL;
        int interval = INTERVAL;
        String groupName = GROUP_NAME;

        ArrayList<Element> elements = ConcatFilesFromTo.listAllElem(maxVal, minVal, groupName);
        System.out.println("Elements size = " + elements.size());
        formatFilesWrite(elements, interval, minVal, groupName);

    }

    public static void formatFilesWrite(ArrayList<Element> elements, int interval, int minValFile, String groupName) {
        if (interval < 1)
            throw new IllegalArgumentException();

        int countFiles1 = elements.size() / interval + minValFile;
//        int countElemLast = elements.size() % interval;

        int counter = 0;
        for (int i = minValFile; i < countFiles1; i++) {
            writeFile(groupName + i, elements, counter, counter = (counter + interval - 1));
            counter++;
        }
        writeFile(groupName + countFiles1, elements, counter, elements.size() - 1);
    }

    public static void writeFile(String fileName, ArrayList<Element> elements, int beginElem, int lastElem) {
        try (FileWriter writer = new FileWriter(LowTermCheck.PREFIX_FOLDER + File.separator + fileName + ".txt", false)) {
            for (int i = beginElem; i <= lastElem; i++) {
                String text = elements.get(i).toString();
                if (i != lastElem) {
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
