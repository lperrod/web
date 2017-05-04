package cmpl.web.controller.back;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.builder.NewsEntryRequestBuilder;
import cmpl.web.dispatcher.NewsEntryDispatcher;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.model.BaseException;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;
import cmpl.web.model.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class NewsManagerControllerTest {

  @Mock
  private NewsManagerDisplayFactory newsManagerDisplayFactory;
  @Mock
  private NewsEntryDispatcher dispatcher;

  @Spy
  @InjectMocks
  private NewsManagerController controller;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testDeleteNewsEntry() throws Exception {
    ResponseEntity<NewsEntryResponse> result = controller.deleteNewsEntry("666");

    Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  @Test
  public void testGetNewsEntity_Ok() throws Exception {

    ModelAndView model = new ModelAndView("back/news/edit_news_entry");

    BDDMockito
        .doReturn(model)
        .when(newsManagerDisplayFactory)
        .computeModelAndViewForOneNewsEntry(Mockito.eq(BACK_PAGE.NEWS_UPDATE), Mockito.eq(Locale.FRANCE),
            Mockito.eq("666"));

    ModelAndView result = controller.getNewsEntity("666");

    Assert.assertEquals(model, result);
  }

  @Test
  public void testUpdateNewsEntry_Ok() throws Exception {
    NewsEntryRequest request = new NewsEntryRequestBuilder().toNewsEntryRequest();

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(response).when(dispatcher)
        .updateEntity(Mockito.eq(request), Mockito.eq("666"), Mockito.eq(locale));
    ResponseEntity<NewsEntryResponse> result = controller.updateNewsEntry("666", request);

    Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    Assert.assertEquals(response, result.getBody());
  }

  @Test
  public void testUpdateNewsEntry_Ko() throws BaseException {

    NewsEntryRequest request = new NewsEntryRequestBuilder().toNewsEntryRequest();

    BDDMockito.doThrow(new BaseException("")).when(dispatcher)
        .updateEntity(Mockito.eq(request), Mockito.eq("666"), Mockito.eq(locale));

    ResponseEntity<NewsEntryResponse> result = controller.updateNewsEntry("666", request);

    Assert.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());

  }

  @Test
  public void testCreateNewsEntry_Ok() throws Exception {
    NewsEntryRequest request = new NewsEntryRequestBuilder().toNewsEntryRequest();

    NewsEntryDTO entryDTO = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();
    NewsEntryResponse response = new NewsEntryResponse();
    response.setNewsEntry(entryDTO);

    BDDMockito.doReturn(response).when(dispatcher).createEntity(Mockito.eq(request), Mockito.eq(locale));
    ResponseEntity<NewsEntryResponse> result = controller.createNewsEntry(request);

    Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assert.assertEquals(response, result.getBody());
  }

  @Test
  public void testCreateNewsEntry_Ko() throws BaseException {

    NewsEntryRequest request = new NewsEntryRequestBuilder().toNewsEntryRequest();

    BDDMockito.doThrow(new BaseException("")).when(dispatcher).createEntity(Mockito.eq(request), Mockito.eq(locale));

    ResponseEntity<NewsEntryResponse> result = controller.createNewsEntry(request);

    Assert.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());

  }

  @Test
  public void testPrintViewNews() throws Exception {
    ModelAndView model = new ModelAndView("back/news/view_news");

    BDDMockito.doReturn(model).when(newsManagerDisplayFactory)
        .computeModelAndViewForBackPage(Mockito.eq(BACK_PAGE.NEWS_VIEW), Mockito.eq(locale));

    ModelAndView result = controller.printViewNews();

    Assert.assertEquals(model, result);
  }
}
