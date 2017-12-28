package com.cmpl.web.core.builder;

import org.springframework.data.domain.Page;

import com.cmpl.web.core.model.PageWrapper;

public class PageWrapperBuilder<T> extends Builder<PageWrapper<T>> {

  private Page<T> page;
  private boolean isFirstPage;
  private boolean isLastPage;
  private int totalPages;
  private int currentPageNumber;
  private String pageBaseUrl;
  private String pageLabel;

  public PageWrapperBuilder<T> page(Page<T> page) {
    this.page = page;
    return this;
  }

  public PageWrapperBuilder<T> firstPage(boolean isFirstPage) {
    this.isFirstPage = isFirstPage;
    return this;
  }

  public PageWrapperBuilder<T> lastPage(boolean isLastPage) {
    this.isLastPage = isLastPage;
    return this;
  }

  public PageWrapperBuilder<T> totalPages(int totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  public PageWrapperBuilder<T> currentPageNumber(int currentPageNumber) {
    this.currentPageNumber = currentPageNumber;
    return this;
  }

  public PageWrapperBuilder<T> pageBaseUrl(String pageBaseUrl) {
    this.pageBaseUrl = pageBaseUrl;
    return this;
  }

  public PageWrapperBuilder<T> pageLabel(String pageLabel) {
    this.pageLabel = pageLabel;
    return this;
  }

  @Override
  public PageWrapper<T> build() {
    PageWrapper<T> wrapper = new PageWrapper<>();
    wrapper.setCurrentPageNumber(currentPageNumber);
    wrapper.setFirstPage(isFirstPage);
    wrapper.setLastPage(isLastPage);
    wrapper.setPage(page);
    wrapper.setPageBaseUrl(pageBaseUrl);
    wrapper.setPageLabel(pageLabel);
    wrapper.setTotalPages(totalPages);
    return wrapper;
  }

}