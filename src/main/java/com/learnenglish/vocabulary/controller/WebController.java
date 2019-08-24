package com.learnenglish.vocabulary.controller;

import com.learnenglish.vocabulary.logic.longterm.LongTermCheck;
import com.learnenglish.vocabulary.logic.pack1.AlphaNumComparator;
import com.learnenglish.vocabulary.logic.pack1.LowTermCheck;
import com.learnenglish.vocabulary.util.AttributeNames;
import com.learnenglish.vocabulary.util.MyMappings;
import com.learnenglish.vocabulary.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class WebController {

    @Autowired
    LongTermCheck longTermCheck;

    @Autowired
    LowTermCheck lowTermCheck;

    @GetMapping(MyMappings.HOME)
    public String home() {
        return ViewNames.HOME;
    }

    @GetMapping(MyMappings.FIRST)
    public String first() {
        return ViewNames.FIRST;
    }

    @PostMapping(MyMappings.SECOND)
    public String second(@RequestParam String variable, Model model) {
        model.addAttribute(AttributeNames.MESSAGE, variable);
        log.info("Redirected to Second page with parameter variable = {}", variable);
        return ViewNames.SECOND;
    }

    @GetMapping(MyMappings.LOW_TERM)
    public String lowterm(Model model) {

        List<String> listFiles = lowTermCheck.getTip().getListFiles();
        List<File> listFilesReal = new ArrayList<>();
        for (String listFile : listFiles) {
            File file = new File(listFile);
            listFilesReal.add(file);
        }

        listFilesReal.sort(new AlphaNumComparator());


        for (int i = 0; i < listFiles.size(); i++) {
            listFiles.set(i, listFilesReal.get(i).getName());
        }


        model.addAttribute(AttributeNames.FILE_NAMES, listFiles);
        model.addAttribute(AttributeNames.MESSAGE, "some textttt");
        return ViewNames.LOW_TERM;
    }

    @GetMapping(MyMappings.LOW_TERM_REALIZATION)
    public String lowTermRealization(@RequestParam String lowtfile, @RequestParam int countTr, Model model) {
        if (lowTermCheck.chooseBase(lowtfile, countTr) && lowTermCheck.chooseElement()) {
            model.addAttribute(AttributeNames.CURRENT_ELEMENT_LOW_TERM, lowTermCheck.getCurrentElem());
            model.addAttribute(AttributeNames.REMAIN_ELEMENTS, "Remaining: " + lowTermCheck.getElements().size());
            return ViewNames.LOW_TERM_REALIZATION;
        } else {
            model.addAttribute(AttributeNames.MESSAGE, "Choose enother file. Some problem with this.");
            return ViewNames.LOW_TERM;
        }
    }

    @PostMapping(MyMappings.LOW_TERM_REALIZATION)
    public String lowTermRealizationPost(@RequestParam String answer, Model model) {
        if (lowTermCheck.isAnswerTrue(answer)) {

            return putRemains(model);

        } else {
            model.addAttribute(AttributeNames.MESSAGE, "Your word is wrong</br>" + lowTermCheck.answerTip(answer) +
                    "<br/> Your answer is :<br/>" + lowTermCheck.getCurrentElem().toString());

            return putRemains(model);
        }
    }


    //auto from intellij. check how it work
    private String putRemains(Model model) {
        if (lowTermCheck.chooseElement()) {
            model.addAttribute(AttributeNames.CURRENT_ELEMENT_LOW_TERM, lowTermCheck.getCurrentElem());
            model.addAttribute(AttributeNames.REMAIN_ELEMENTS, "Remaining: " + lowTermCheck.getElements().size());
            return ViewNames.LOW_TERM_REALIZATION;
        } else {
            return ViewNames.GAME_OVER;
        }
    }

    @GetMapping(MyMappings.GAME_OVER)
    public String gameOver() {
        return ViewNames.GAME_OVER;
    }

    @GetMapping(MyMappings.LONG_TERM)
    public String long_term(Model model) {

        List<String> listFiles = longTermCheck.getTip().getListFiles();
        List<File> listFilesReal = new ArrayList<>();
        for (String listFile : listFiles) {
            File file = new File(listFile);
            listFilesReal.add(file);
        }

        listFilesReal.sort(new AlphaNumComparator());


        for (int i = 0; i < listFiles.size(); i++) {
            listFiles.set(i, listFilesReal.get(i).getName());
        }

        model.addAttribute(AttributeNames.FILE_NAMES, listFiles);
        return ViewNames.LONG_TERM;
    }

    //or I can try to make default value for parameters
    @GetMapping(MyMappings.LONG_TERM_REALIZATION)
    public String long_term_realization(@RequestParam String lowtfile,
                                        @RequestParam int countTr,
                                        Model model) {
//        if (lowtfile != null && countTr != 0) {
        if (longTermCheck.chooseBase(lowtfile, countTr) && longTermCheck.chooseElement()) {
            longTermCheck.displayForm(LongTermCheck.COUNT_OF_VARIANTS);
            model.addAttribute(AttributeNames.MESSAGE, "");
            model.addAttribute(AttributeNames.CURRENT_LIST_ELEMENT_LONG_TERM, longTermCheck.getDisplayList());
            model.addAttribute(AttributeNames.CURRENT_ELEMENT_LONG_TERM, longTermCheck.getCurrentElem());
            model.addAttribute(AttributeNames.REMAIN_ELEMENTS, "Remaining: " + longTermCheck.getElements().size());
            return ViewNames.LONG_TERM_REALIZATION;
        } else {
            model.addAttribute(AttributeNames.MESSAGE, "Choose another file. Some problem with this.");
            return ViewNames.LONG_TERM_REALIZATION;
        }
    }

    @GetMapping(MyMappings.CHECK_AND_REDIRECT)
    public String checkAndRedirectLongTerm(@RequestParam int guess, Model model) {
        if (longTermCheck.checkAnswer(guess)) {

            if (!longTermCheck.chooseElement())
                return ViewNames.GAME_OVER;
            log.info("Size of longTermCheck = {}", longTermCheck.getDisplayList().size());


            longTermCheck.displayForm(LongTermCheck.COUNT_OF_VARIANTS);
            model.addAttribute(AttributeNames.CURRENT_LIST_ELEMENT_LONG_TERM, longTermCheck.getDisplayList());
            model.addAttribute(AttributeNames.CURRENT_ELEMENT_LONG_TERM, longTermCheck.getCurrentElem());
            model.addAttribute(AttributeNames.MESSAGE, "");
            model.addAttribute(AttributeNames.REMAIN_ELEMENTS, "Remaining: " + longTermCheck.getElements().size());
            return ViewNames.LONG_TERM_REALIZATION;

        } else {

            //checking if guess will not create trouble
            String bufAnsw = "";
            if (guess > 0 && guess <= longTermCheck.getDisplayList().size())
                bufAnsw = longTermCheck.getDisplayList().get(guess - 1).toString();


            model.addAttribute(AttributeNames.MESSAGE, "Your answer is wrong<br/>" +
                    "Your answer means: <br/>" + bufAnsw + "<br/>Right Answer is:<br/>" + longTermCheck.getCurrentElem().toString());

            if (!longTermCheck.chooseElement())
                return ViewNames.GAME_OVER;
            log.info("Size of longTermCheck = {}", longTermCheck.getDisplayList().size());

            longTermCheck.displayForm(LongTermCheck.COUNT_OF_VARIANTS);
            model.addAttribute(AttributeNames.CURRENT_LIST_ELEMENT_LONG_TERM, longTermCheck.getDisplayList());
            model.addAttribute(AttributeNames.CURRENT_ELEMENT_LONG_TERM, longTermCheck.getCurrentElem());
            model.addAttribute(AttributeNames.REMAIN_ELEMENTS, "Remaining: " + longTermCheck.getElements().size());
            return ViewNames.LONG_TERM_REALIZATION;
        }

    }


}