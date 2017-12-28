package com.cmpl.web.news;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.model.BaseException;

;

@RunWith(MockitoJUnitRunner.class)
public class NewsManagerControllerTest {

  @Mock
  private NewsManagerDisplayFactory newsManagerDisplayFactory;
  @Mock
  private NewsEntryDispatcher dispatcher;

  @Spy
  @InjectMocks
  private NewsManagerController controller;

  @Test
  public void testDeleteNewsEntry() throws Exception {
    ResponseEntity<NewsEntryResponse> result = controller.deleteNewsEntry("666");

    Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  @Test
  public void testGetNewsEntity_Ok() throws Exception {

    ModelAndView model = new ModelAndView("back/news/edit_news_entry");

    BDDMockito.doReturn(model).when(newsManagerDisplayFactory)
        .computeModelAndViewForOneNewsEntry(BDDMockito.eq(Locale.FRANCE), BDDMockito.eq("666"));

    ModelAndView result = controller.getNewsEntity("666");

    Assert.assertEquals(model, result);
  }

  @Test
  public void testUpdateNewsEntry_Ok() throws Exception {
    NewsEntryRequest request = new NewsEntryRequestBuilder().build();

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(response).when(dispatcher)
        .updateEntity(BDDMockito.eq(request), BDDMockito.eq("666"), BDDMockito.eq(Locale.FRANCE));
    ResponseEntity<NewsEntryResponse> result = controller.updateNewsEntry("666", request);

    Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    Assert.assertEquals(response, result.getBody());
  }

  @Test
  public void testUpdateNewsEntry_Ko() throws BaseException {

    NewsEntryRequest request = new NewsEntryRequestBuilder().build();

    BDDMockito.doThrow(new BaseException("")).when(dispatcher)
        .updateEntity(BDDMockito.eq(request), BDDMockito.eq("666"), BDDMockito.eq(Locale.FRANCE));

    ResponseEntity<NewsEntryResponse> result = controller.updateNewsEntry("666", request);

    Assert.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());

  }

  @Test
  public void testCreateNewsEntry_Ok() throws Exception {
    NewsEntryRequest request = new NewsEntryRequestBuilder().build();

    NewsEntryDTO entryDTO = new NewsEntryDTOBuilder().id(1L).build();
    NewsEntryResponse response = new NewsEntryResponse();
    response.setNewsEntry(entryDTO);

    BDDMockito.doReturn(response).when(dispatcher).createEntity(BDDMockito.eq(request), BDDMockito.eq(Locale.FRANCE));
    ResponseEntity<NewsEntryResponse> result = controller.createNewsEntry(request);

    Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assert.assertEquals(response, result.getBody());
  }

  @Test
  public void testCreateNewsEntry_Ko() throws BaseException {

    NewsEntryRequest request = new NewsEntryRequestBuilder().build();

    BDDMockito.doThrow(new BaseException("")).when(dispatcher)
        .createEntity(BDDMockito.eq(request), BDDMockito.eq(Locale.FRANCE));

    ResponseEntity<NewsEntryResponse> result = controller.createNewsEntry(request);

    Assert.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());

  }

  @Test
  public void testPrintViewNews() throws Exception {
    ModelAndView model = new ModelAndView("back/news/view_news");

    BDDMockito.doReturn(model).when(newsManagerDisplayFactory)
        .computeModelAndViewForBackPage(BDDMockito.eq(Locale.FRANCE), BDDMockito.anyInt());

    ModelAndView result = controller.printViewNews(0);

    Assert.assertEquals(model, result);
  }
}