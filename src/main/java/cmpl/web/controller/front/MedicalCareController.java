package cmpl.web.controller.front;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class MedicalCareController {

  private final DisplayFactory displayFactory;

  @Autowired
  MedicalCareController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/soins_medicaux")
  public ModelAndView printFacialInjections() {
    return displayFactory.computeModelAndViewForPage(PAGE.MEDICAL_CARE, Locale.FRANCE);
  }

}