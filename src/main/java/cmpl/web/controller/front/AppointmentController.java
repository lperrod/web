package cmpl.web.controller.front;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class AppointmentController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  AppointmentController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/rendez-vous")
  public ModelAndView printAppointments() {

    LOGGER.info("Accès à la page " + PAGE.APPOINTMENT.name());
    return displayFactory.computeModelAndViewForPage(PAGE.APPOINTMENT, Locale.FRANCE);
  }

}
