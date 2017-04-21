package cmpl.web.factory.impl;

import java.util.Locale;

import cmpl.web.factory.FooterFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.footer.Footer;

public class FooterFactoryImpl extends BaseFactoryImpl implements FooterFactory {

  private FooterFactoryImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  public static FooterFactoryImpl fromMessageSource(WebMessageSourceImpl messageSource) {
    return new FooterFactoryImpl(messageSource);
  }

  @Override
  public Footer computeFooter(Locale locale) {

    Footer footer = new Footer();

    footer.setAdresse(getI18nValue("footer.address", locale));
    footer.setLibelle(getI18nValue("footer.label", locale));
    footer.setTelephone(getI18nValue("footer.phone", locale));

    return footer;
  }

}