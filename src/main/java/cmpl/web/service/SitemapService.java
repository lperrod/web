package cmpl.web.service;

import java.util.Locale;

import cmpl.web.model.BaseException;

/**
 * Interface gerant le sitemap
 * 
 * @author Louis
 *
 */
public interface SitemapService {

  /**
   * Creer un sitemap et renvoyer le contenu dans un String
   * 
   * @param locale
   * @return
   * @throws BaseException
   */
  String createSiteMap(Locale locale) throws BaseException;

}
