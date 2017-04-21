package cmpl.web.factory.impl;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.impl.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BaseFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private BaseDisplayFactoryImpl displayFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testGetI18nValue() throws Exception {
    String value = "value";
    String key = "key";

    BDDMockito.doReturn(value).when(messageSource).getI18n(Mockito.eq(key), Mockito.eq(locale));

    String result = displayFactory.getI18nValue(key, locale);

    Assert.assertEquals(value, result);
  }
}