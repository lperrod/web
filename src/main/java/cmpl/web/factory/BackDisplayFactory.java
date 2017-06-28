package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.BACK_PAGE;

/**
 * Interface commune de factory pour le back
 * 
 * @author Louis
 *
 */
public interface BackDisplayFactory {

  /**
   * Calcule le model and view commun pour les pages du back
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale);

}
