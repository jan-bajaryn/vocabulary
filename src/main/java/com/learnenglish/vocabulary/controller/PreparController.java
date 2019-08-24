package com.learnenglish.vocabulary.controller;

import com.learnenglish.vocabulary.logic.preparation.ConcatFilesFromTo;
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

@Controller
@Slf4j
public class PreparController {

    @GetMapping(MyMappings.CONCATENATION)
    public String concatenation(Model model) {
        model.addAttribute(AttributeNames.MESSAGE, " ");
        return ViewNames.CONCATENATION;
    }


    @PostMapping(MyMappings.CONCATENATION)
    public String concatenationPost(Model model,
                                    @RequestParam int numbInpF,
                                    @RequestParam int numbInpT,
                                    @RequestParam String textInp) {

        try {
            ConcatFilesFromTo.run(numbInpT, numbInpF, textInp);
            model.addAttribute(AttributeNames.MESSAGE, "File successfully created");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(AttributeNames.MESSAGE, "File not created, something went wrong");
        }

        return ViewNames.CONCATENATION;

    }

}
