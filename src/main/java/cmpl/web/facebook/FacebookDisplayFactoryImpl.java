package cmpl.web.facebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.factory.BackDisplayFactoryImpl;
import cmpl.web.core.model.BaseException;
import cmpl.web.footer.FooterFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.page.BACK_PAGE;

/**
 * Implementation de l'interface de factory pour le spages back de facebook
 * 
 * @author Louis
 *
 */
public class FacebookDisplayFactoryImpl extends BackDisplayFactoryImpl implements FacebookDisplayFactory {

  private final FacebookService facebookService;

  protected FacebookDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory, FacebookService facebookService) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
    this.facebookService = facebookService;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param menuFactory
   * @param footerFactory
   * @param messageSource
   * @param metaElementFactory
   * @param facebookService
   * @return
   */
  public static FacebookDisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory,
      FacebookService facebookService) {
    return new FacebookDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory,
        facebookService);
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookAccessPage(Locale locale) {

    boolean isConnected = isAlreadyConnected();
    if (isConnected) {
      LOGGER.info("Redirection vers l'import car déjà connecté");
      return computeModelAndViewForFacebookImportPage(locale);
    }

    ModelAndView facebookAccess = super.computeModelAndViewForBackPage(BACK_PAGE.FACEBOOK_ACCESS, locale);

    return facebookAccess;
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookImportPage(Locale locale) {

    ModelAndView facebookImport = super.computeModelAndViewForBackPage(BACK_PAGE.FACEBOOK_IMPORT, locale);

    boolean isConnected = isAlreadyConnected();
    if (!isConnected) {
      LOGGER.info("Redirection vers l'acces car connexion en timeout");
      return computeModelAndViewForFacebookAccessPage(locale);
    }
    facebookImport.addObject("feeds", computeRecentFeeds());
    return facebookImport;

  }

  List<ImportablePost> computeRecentFeeds() {
    try {
      return facebookService.getRecentFeed();
    } catch (BaseException e) {
      LOGGER.error("Impossible de récupérer le feed récent de l'utilisateur", e);
    }
    return new ArrayList<>();
  }

  boolean isAlreadyConnected() {
    try {
      facebookService.getRecentFeed();
    } catch (BaseException e) {
      LOGGER.debug("Utilisateur facebook non connecté", e);
      return false;
    }
    return true;
  }
}
