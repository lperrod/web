package cmpl.web.controller.back;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.dispatcher.FacebookDispatcher;
import cmpl.web.factory.FacebookDisplayFactory;
import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;
import cmpl.web.model.page.BACK_PAGE;

/**
 * Controller pour la gestion de l'import des posts facebook
 * 
 * @author Louis
 *
 */
@Controller
public class FacebookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookController.class);

  private final FacebookDisplayFactory facebookDisplayFactory;
  private final FacebookDispatcher facebookDispatcher;

  @Autowired
  FacebookController(FacebookDisplayFactory facebookDisplayFactory, FacebookDispatcher facebookDispatcher) {
    this.facebookDisplayFactory = facebookDisplayFactory;
    this.facebookDispatcher = facebookDispatcher;
  }

  /**
   * Mapping pour acceder a la partie facebook
   * 
   * @return
   */
  @GetMapping(value = "/manager/facebook")
  public ModelAndView printFacebookAccess() {
    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_ACCESS.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookAccessPage(Locale.FRANCE);
  }

  /**
   * Mapping pour l'affichage des imports possibles
   * 
   * @return
   */
  @GetMapping(value = "/manager/facebook/import")
  public ModelAndView printFacebookImport() {

    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_IMPORT.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookImportPage(Locale.FRANCE);
  }

  /**
   * Mapping pour l'import de posts
   * 
   * @param facebookImportRequest
   * @return
   */
  @PostMapping(value = "/manager/facebook/import")
  @ResponseBody
  public ResponseEntity<FacebookImportResponse> createNewsEntry(@RequestBody FacebookImportRequest facebookImportRequest) {

    LOGGER.info("Tentative de création d'entrées de blog venant de facebook");
    try {
      FacebookImportResponse response = facebookDispatcher.createEntity(facebookImportRequest, Locale.FRANCE);
      LOGGER.info("Entrées crées");
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (BaseException e) {
      LOGGER.info("Echec de l'import des posts facebook", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }
}
