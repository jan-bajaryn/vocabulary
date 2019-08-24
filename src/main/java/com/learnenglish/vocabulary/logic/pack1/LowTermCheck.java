package com.learnenglish.vocabulary.logic.pack1;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Component
@Getter
@Setter
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LowTermCheck {

    public static final String SEPARATOR = "--";
    public static final String PREFIX_FOLDER = "ulist_vocabularies";

    @Autowired
    private Tip tip;

    private ArrayList<Element> elements;

    private Random random = new Random();

    private Element currentElem;

    private int position;


    public static ArrayList<Element> readingFromFile(String fileName, int countOfTryings) {
        ArrayList<Element> elements = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (!s.equals("")) {
                    String[] arr = s.split(SEPARATOR);
                    elements.add(new Element(arr[0], arr[1], countOfTryings));
                }
            }
            return elements;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("problems");
        }
    }

    public static void writeFile(String fileName, ArrayList<Element> elements) {

        File f = new File(System.getProperty("user.dir") + File.separator + fileName);

        if (!f.exists())
            f.getParentFile().mkdirs();


        try (FileWriter writer = new FileWriter(fileName + ".txt", false)) {
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

    public boolean chooseBase(String fileName, int countOfTrying) {
        try {
            this.elements = readingFromFile(System.getProperty("user.dir") + File.separator +
                    PREFIX_FOLDER + File.separator + fileName, countOfTrying);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Element chooseRandomPositionElem() {
        if (elements == null || elements.size() <= 0)
            return null;
        else
            return elements.get(position = random.nextInt(elements.size()));
    }

    public boolean chooseElement() {
        Element bufElem = chooseRandomPositionElem();
//        log.info("Random position element chosen = {}", bufElem);
        if (bufElem == null)
            return false;
        else {
            currentElem = bufElem;
            return true;
        }
    }




    public boolean isAnswerTrue(String answer) {
        if (currentElem.getEnglishWord().equals(answer)) {
            currentElem.setCountOfTrying(currentElem.getCountOfTrying() - 1);

            if (currentElem.getCountOfTrying() == 0) {
                log.info("Removed element = {}", currentElem);
                elements.remove(position);
            }

            return true;
        } else {
//            log.info("Not proper translating chosen = {}", answer);
            return false;
        }
    }

    public String answerTip(String tryWord) {
        return tip.findTipInOther(tryWord);
    }

}