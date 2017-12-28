package com.cmpl.web.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementCreateForm;
import com.cmpl.web.meta.MetaElementDTO;
import com.cmpl.web.meta.MetaElementService;
import com.cmpl.web.meta.OpenGraphMetaElementCreateForm;
import com.cmpl.web.meta.OpenGraphMetaElementDTO;
import com.cmpl.web.meta.OpenGraphMetaElementService;

public class PageManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<PageDTO> implements
    PageManagerDisplayFactory {

  private final PageService pageService;
  private final OpenGraphMetaElementService openGraphMetaElementService;
  private final MetaElementService metaElementService;
  private final ContextHolder contextHolder;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String META_ELEMENTS = "metaElements";

  public PageManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PageService pageService, ContextHolder contextHolder, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService) {
    super(menuFactory, messageSource);
    this.pageService = pageService;
    this.contextHolder = contextHolder;
    this.metaElementService = metaElementService;
    this.openGraphMetaElementService = openGraphMetaElementService;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllPages(Locale locale, int pageNumber) {
    ModelAndView pagesManager = super.computeModelAndViewForBackPage(BACK_PAGE.PAGES_VIEW, locale);
    LOGGER.info("Construction des pages pour la page " + BACK_PAGE.PAGES_VIEW.name());

    PageWrapper<PageDTO> pagedPageDTOWrapped = computePageWrapper(locale, pageNumber);

    pagesManager.addObject("wrappedPages", pagedPageDTOWrapped);

    return pagesManager;
  }

  @Override
  protected Page<PageDTO> computeEntries(Locale locale, int pageNumber) {
    List<PageDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<PageDTO> pagedPageDTOEntries = pageService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedPageDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedPageDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedPageDTOEntries.getTotalElements());
  }

  PageCreateForm computeCreateForm() {
    return new PageCreateForm();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePage(Locale locale, String pageId) {
    ModelAndView pageManager = super.computeModelAndViewForBackPage(BACK_PAGE.PAGES_UPDATE, locale);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreatePage(Locale locale) {
    ModelAndView pageManager = super.computeModelAndViewForBackPage(BACK_PAGE.PAGES_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des pages ");
    pageManager.addObject(CREATE_FORM, computeCreateForm());
    return pageManager;
  }

  PageUpdateForm createUpdateForm(PageDTO page) {
    return new PageUpdateForm(page);
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMain(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_main");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageBody(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_body");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMeta(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_meta");
    List<MetaElementDTO> metaElements = metaElementService.findMetaElementsByPageId(pageId);
    pageManager.addObject(META_ELEMENTS, metaElements);
    pageManager.addObject(CREATE_FORM, createMetaElementCreateForm(pageId));
    return pageManager;
  }

  MetaElementCreateForm createMetaElementCreateForm(String pageId) {
    MetaElementCreateForm createForm = new MetaElementCreateForm();
    createForm.setPageId(pageId);
    return createForm;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageOpenGraphMeta(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_open_graph_meta");
    List<OpenGraphMetaElementDTO> metaElements = openGraphMetaElementService.findOpenGraphMetaElementsByPageId(pageId);
    pageManager.addObject(META_ELEMENTS, metaElements);
    pageManager.addObject(CREATE_FORM, createOpenGraphMetaElementCreateForm(pageId));
    return pageManager;
  }

  OpenGraphMetaElementCreateForm createOpenGraphMetaElementCreateForm(String pageId) {
    OpenGraphMetaElementCreateForm createForm = new OpenGraphMetaElementCreateForm();
    createForm.setPageId(pageId);
    return createForm;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageHeader(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_header");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageFooter(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_footer");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/pages";
  }

}