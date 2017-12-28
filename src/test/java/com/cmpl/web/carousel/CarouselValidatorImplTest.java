package com.cmpl.web.carousel;

import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.error.ErrorBuilder;
import com.cmpl.web.core.error.ErrorCauseBuilder;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;

@RunWith(MockitoJUnitRunner.class)
public class CarouselValidatorImplTest {

  @Spy
  @InjectMocks
  private CarouselValidatorImpl validator;

  @Test
  public void testValidateDelete_No_Error() throws Exception {

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateDelete("123456789", Locale.FRANCE));
  }

  @Test
  public void testValidateDelete_Error() throws Exception {

    Error error = new ErrorBuilder().build();
    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_CAROUSEL_ITEM_ID.getCauseKey())
        .message("empty_id").build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertEquals(error, validator.validateDelete("123456789", Locale.FRANCE));
  }

  @Test
  public void testValidateCarousel_No_Errors() throws Exception {
    CarouselCreateForm form = new CarouselCreateFormBuilder().name("someName").pageId("somePageId").build();

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateCarousel(form.getName(), form.getPageId(), Locale.FRANCE));
  }

  @Test
  public void testValidateCarousel_Empty_Name() throws Exception {
    CarouselCreateForm form = new CarouselCreateFormBuilder().name("someName").pageId("somePageId").build();

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getName()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_CAROUSEL_NAME.getCauseKey()).message("no_name")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCarousel(form.getPageId(), form.getName(), Locale.FRANCE));
  }

  @Test
  public void testValidateCarousel_Empty_Page() throws Exception {
    CarouselCreateForm form = new CarouselCreateFormBuilder().name("someName").pageId("somePageId").build();

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getName()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_CAROUSEL_NAME.getCauseKey()).message("no_name")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCarousel(form.getPageId(), form.getName(), Locale.FRANCE));
  }

  @Test
  public void testValidateCarousel_Empty_Name_Page() throws Exception {
    CarouselCreateForm form = new CarouselCreateFormBuilder().name("someName").pageId("somePageId").build();

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getName()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_CAROUSEL_NAME.getCauseKey()).message("no_name")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCarousel(form.getPageId(), form.getName(), Locale.FRANCE));
  }

  @Test
  public void testValidateCreateCarouselCreateFormLocale() throws Exception {
    CarouselCreateForm form = new CarouselCreateFormBuilder().name("someName").pageId("somePageId").build();
    BDDMockito.doReturn(null).when(validator)
        .validateCarousel(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.any(Locale.class));
    Assert.assertNull(validator.validateCreate(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateCarousel(BDDMockito.anyString(), BDDMockito.anyString(),
        BDDMockito.any(Locale.class));
  }

  @Test
  public void testValidateUpdate() throws Exception {
    CarouselUpdateForm form = new CarouselUpdateFormBuilder().name("someName").pageId("somePageId").build();
    BDDMockito.doReturn(null).when(validator)
        .validateCarousel(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.any(Locale.class));
    Assert.assertNull(validator.validateUpdate(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateCarousel(BDDMockito.anyString(), BDDMockito.anyString(),
        BDDMockito.any(Locale.class));
  }

  @Test
  public void testValidateCreateCarouselItemCreateFormLocale_No_Errors() throws Exception {
    CarouselItemCreateForm form = new CarouselItemCreateFormBuilder().mediaId("someMediaId")
        .carouselId("somearouselId").build();

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreateCarouselItemCreateFormLocale__Empty_Carousel() throws Exception {
    CarouselItemCreateForm form = new CarouselItemCreateFormBuilder().carouselId("somearouselId")
        .mediaId("someMediaId").build();

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getCarouselId()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getMediaId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_CAROUSEL_ID.getCauseKey()).message("no_carousel")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreateCarouselItemCreateFormLocale__Empty_Media() throws Exception {
    CarouselItemCreateForm form = new CarouselItemCreateFormBuilder().carouselId("somearouselId")
        .mediaId("someMediaId").build();

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getCarouselId()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getMediaId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_MEDIA_ID.getCauseKey()).message("no_media")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreateCarouselItemCreateFormLocale__Empty_Carousel_Media() throws Exception {
    CarouselItemCreateForm form = new CarouselItemCreateFormBuilder().carouselId("somearouselId")
        .mediaId("someMediaId").build();

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getCarouselId()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getMediaId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_MEDIA_ID.getCauseKey()).message("no_media")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

}