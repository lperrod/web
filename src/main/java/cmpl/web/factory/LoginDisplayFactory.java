package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.BACK_PAGE;

/**
 * Interface pour la factory de la page de login
 * 
 * @author Louis
 *
 */
public interface LoginDisplayFactory {

  /**
   * Creer le model and view de la page de login
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale);

}
