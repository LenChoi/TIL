package com.example.learningthymeleaf

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.util.GregorianCalendar

@Controller
class MainController {

    @RequestMapping("/")
    fun homePage(): ModelAndView {
        val model: MutableMap<String, Any> = mutableMapOf()
        model["username"] = "Matt Greencroft"
        model["id"] = 173

        return ModelAndView("homepage", "model", model)
    }

    @RequestMapping("/profile")
    fun viewProfile(): ModelAndView {
        val model: MutableMap<String, Any> = mutableMapOf()
        model["title"] = "mr"
        model["firstName"] = "Matt"
        model["surname"] = "Greencroft"
        model["dateOfBirth"] = GregorianCalendar(2006, 3, 9)
        model["description"] = "a <strong>fantastic</strong> Java Programmer"

        val languages = mutableListOf<String>()
        languages.add("English")
        languages.add("French")
        languages.add("Spanish")
        languages.add("Danish")

        model.put("languages", languages)

        return ModelAndView("profile", "model", model)
    }
}