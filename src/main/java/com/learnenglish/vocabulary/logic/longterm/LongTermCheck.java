package com.learnenglish.vocabulary.logic.longterm;


import com.learnenglish.vocabulary.logic.pack1.Element;
import com.learnenglish.vocabulary.logic.pack1.Tip;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.learnenglish.vocabulary.logic.pack1.LowTermCheck.PREFIX_FOLDER;

@Service
@Getter
@Setter
public class LongTermCheck {
    public static final String DEFAULT_FILE_NAME = "words";
    public static final String SEPARETOR = "--";
    public static final int MIN_NUMBER_OF_VARIANTS = 1;
    public static final int COUNT_OF_VARIANTS = 4;
    public static final int DEFAULT_COUNT_OF_TRYING_PATCH = 1;

    @Autowired
    private Tip tip;

    private Element[] allWords;
    private int sizeAllWords;

    private ArrayList<Element> elements;

    private Random random = new Random();

    private int position;

    private Element currentElem;

    ArrayList<Element> displayList = new ArrayList<>(COUNT_OF_VARIANTS);


    public static ArrayList<Element> readingFromFile(String fileName, int countOfTryings) {
        ArrayList<Element> elements = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (!s.equals("")) {
                    String[] arr = s.split(SEPARETOR);
                    elements.add(new Element(arr[0], arr[1], countOfTryings));

                }
            }
            return elements;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("problems");
        }
    }

    public boolean chooseBase(String fileName, int countOfTrying) {
        try {
            this.elements = readingFromFile(System.getProperty("user.dir") + File.separator +
                    PREFIX_FOLDER + File.separator + fileName, countOfTrying);
            allWords = tip.getAllElements();
            sizeAllWords = allWords.length;
            deleteParenthesisTips(elements);

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

    //make answerArr
    public void displayForm(int countOfVariants) {

        displayList.clear();

        for (int i = 1; i < COUNT_OF_VARIANTS; i++) {
            Element bufElem = allWords[random.nextInt(sizeAllWords)];
            if (!currentElem.equals(bufElem))
                displayList.add(bufElem);
            else
                i--;
        }
        displayList.add(currentElem);

        System.out.println("Before scatter:");
        displayListConsole(displayList);


        scatterRandomlyList(displayList);
    }

    public boolean checkAnswer(int answer) {
        if (answer >= MIN_NUMBER_OF_VARIANTS && answer <= COUNT_OF_VARIANTS) {
            Element answerElem = displayList.get(answer - 1);
            if (answerElem.equals(currentElem)) {
                currentElem.setCountOfTrying(currentElem.getCountOfTrying() - 1);
                if (currentElem.getCountOfTrying() == 0) {
                    elements.remove(position);
                }
                return true;
            } else
                return false;

        } else
            return false;
    }


    public static void writeFile(String fileName, ArrayList<Element> elements) {
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

    public void scatterRandomlyList(ArrayList<Element> arr) {
        ArrayList<Element> bufArr = new ArrayList<>(arr);
        arr.clear();
        for (int i = MIN_NUMBER_OF_VARIANTS; i < COUNT_OF_VARIANTS + MIN_NUMBER_OF_VARIANTS; i++) {
            int randPos = random.nextInt(bufArr.size());
            Element randElem = bufArr.get(randPos);
            System.out.println(i + ". " + randElem.toString());
            arr.add(randElem);
            bufArr.remove(randPos);
        }
        System.out.println("Current element = " + currentElem.toString());
    }

    public void deleteParenthesisTips(ArrayList<Element> arr) {
        for (Element elem : arr) {
            int index;
            String transl = elem.getTranslation();
            if ((index = transl.indexOf("(")) > -1) {
                elem.setTranslation(transl.substring(0, index));
            }
        }

    }

    public void displayListConsole(List<Element> elements1) {
        elements1.forEach(System.out::println);
        System.out.println("Current elemtn = " + currentElem.toString());
    }
}