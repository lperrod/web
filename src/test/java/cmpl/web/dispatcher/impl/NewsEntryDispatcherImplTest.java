package cmpl.web.dispatcher.impl;

import java.util.Locale;

import org.assertj.core.util.Lists;
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

import cmpl.web.model.BaseException;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.error.ErrorCause;
import cmpl.web.model.news.error.NEWS_ERROR;
import cmpl.web.model.news.error.NEWS_ERROR_CAUSE;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;
import cmpl.web.service.NewsEntryService;
import cmpl.web.translator.NewsEntryTranslator;
import cmpl.web.validator.NewsEntryRequestValidator;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryDispatcherImplTest {

  @Mock
  private NewsEntryRequestValidator validator;
  @Mock
  private NewsEntryTranslator translator;
  @Mock
  private NewsEntryService newsEntryService;

  @InjectMocks
  @Spy
  private NewsEntryDispatcherImpl dispatcher;

  private Locale locale;

  @Before
  public void setUpd() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testDeleteEntity_Ok() throws Exception {

    BDDMockito.doReturn(null).when(validator).validateDelete(Mockito.anyString(), Mockito.eq(locale));

    dispatcher.deleteEntity(String.valueOf(1L), locale);

    Mockito.verify(validator, Mockito.times(1)).validateDelete(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(newsEntryService, Mockito.times(1)).deleteEntity(Mockito.anyLong());
  }

  @Test
  public void testDeleteEntity_Ko() {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(NEWS_ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(NEWS_ERROR.INVALID_REQUEST.toString());
    error.setCauses(Lists.newArrayList(errorCause));

    BDDMockito.doReturn(error).when(validator).validateDelete(Mockito.anyString(), Mockito.eq(locale));

    try {
      dispatcher.deleteEntity(String.valueOf(1L), locale);
    } catch (BaseException e) {
      Assert.assertEquals(e.getMessage(), errorCause.getMessage());
    }

    Mockito.verify(validator, Mockito.times(1)).validateDelete(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(newsEntryService, Mockito.times(0)).deleteEntity(Mockito.anyLong());
  }

  @Test
  public void testUpdateEntity_Ok() throws Exception {

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(null).when(validator)
        .validateUpdate(Mockito.any(NewsEntryRequest.class), Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(response).when(translator).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

    NewsEntryResponse result = dispatcher.updateEntity(new NewsEntryRequest(), String.valueOf(1L), locale);

    Assert.assertEquals(response, result);

    Mockito.verify(validator, Mockito.times(1)).validateUpdate(Mockito.any(NewsEntryRequest.class),
        Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(1)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(1)).updateEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(1)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testUpdateEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(NEWS_ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(NEWS_ERROR.INVALID_REQUEST.toString());
    error.setCauses(Lists.newArrayList(errorCause));

    NewsEntryResponse response = new NewsEntryResponse();
    response.setError(error);

    BDDMockito.doReturn(error).when(validator)
        .validateUpdate(Mockito.any(NewsEntryRequest.class), Mockito.anyString(), Mockito.eq(locale));

    NewsEntryResponse result = dispatcher.updateEntity(new NewsEntryRequest(), String.valueOf(1L), locale);

    Assert.assertEquals(response.getError(), result.getError());

    Mockito.verify(validator, Mockito.times(1)).validateUpdate(Mockito.any(NewsEntryRequest.class),
        Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(0)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(0)).updateEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(0)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testCreateEntity_Ok() throws Exception {

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(null).when(validator)
        .validateUpdate(Mockito.any(NewsEntryRequest.class), Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(response).when(translator).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

    NewsEntryResponse result = dispatcher.createEntity(new NewsEntryRequest(), locale);

    Assert.assertEquals(response, result);

    Mockito.verify(validator, Mockito.times(1)).validateCreate(Mockito.any(NewsEntryRequest.class), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(1)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(1)).createEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(1)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testCreateEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(NEWS_ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(NEWS_ERROR.INVALID_REQUEST.toString());
    error.setCauses(Lists.newArrayList(errorCause));

    NewsEntryResponse response = new NewsEntryResponse();
    response.setError(error);

    BDDMockito.doReturn(error).when(validator).validateCreate(Mockito.any(NewsEntryRequest.class), Mockito.eq(locale));

    NewsEntryResponse result = dispatcher.createEntity(new NewsEntryRequest(), locale);

    Assert.assertEquals(response.getError(), result.getError());

    Mockito.verify(validator, Mockito.times(1)).validateCreate(Mockito.any(NewsEntryRequest.class), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(0)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(0)).createEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(0)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }
}