package com.cmpl.web.news;

import java.util.Locale;

import com.cmpl.web.core.model.BaseException;
import com.cmpl.web.core.model.Error;

/**
 * Implementation du dispatcher pour les NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryDispatcherImpl implements NewsEntryDispatcher {

  private final NewsEntryRequestValidator validator;
  private final NewsEntryTranslator translator;
  private final NewsEntryService newsEntryService;

  public NewsEntryDispatcherImpl(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService) {
    this.validator = validator;
    this.translator = translator;
    this.newsEntryService = newsEntryService;
  }

  @Override
  public NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, Locale locale) {

    Error error = validator.validateCreate(newsEntryRequest, locale);

    if (error != null) {
      NewsEntryResponse response = new NewsEntryResponse();
      response.setError(error);
      return response;
    }

    NewsEntryDTO entityToCreate = translator.fromRequestToDTO(newsEntryRequest);
    NewsEntryDTO entityCreated = newsEntryService.createEntity(entityToCreate);

    return translator.fromDTOToResponse(entityCreated);
  }

  @Override
  public NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId, Locale locale) {

    Error error = validator.validateUpdate(newsEntryRequest, newsEntryId, locale);

    if (error != null) {
      NewsEntryResponse response = new NewsEntryResponse();
      response.setError(error);
      return response;
    }

    newsEntryRequest.setId(Long.parseLong(newsEntryId));
    NewsEntryDTO entityToUpdate = translator.fromRequestToDTO(newsEntryRequest);

    NewsEntryDTO entityUpdated = newsEntryService.updateEntity(entityToUpdate);

    return translator.fromDTOToResponse(entityUpdated);

  }

  @Override
  public void deleteEntity(String newsEntryId, Locale locale) throws BaseException {
    Error error = validator.validateDelete(newsEntryId, locale);
    if (error != null) {
      throw new BaseException(error.getCauses().get(0).getMessage());
    }
    newsEntryService.deleteEntity(Long.parseLong(newsEntryId));
  }

}