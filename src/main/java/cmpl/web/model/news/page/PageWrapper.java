package cmpl.web.model.news.page;

import org.springframework.data.domain.Page;

import cmpl.web.model.news.display.NewsEntryDisplayBean;

public class PageWrapper {

  private Page<NewsEntryDisplayBean> page;
  private boolean isFirstPage;
  private boolean isLastPage;
  private int totalPages;
  private int currentPageNumber;
  private String pageBaseUrl;
  private String pageLabel;

  public Page<NewsEntryDisplayBean> getPage() {
    return page;
  }

  public void setPage(Page<NewsEntryDisplayBean> page) {
    this.page = page;
  }

  public boolean isFirstPage() {
    return isFirstPage;
  }

  public void setFirstPage(boolean isFirstPage) {
    this.isFirstPage = isFirstPage;
  }

  public boolean isLastPage() {
    return isLastPage;
  }

  public void setLastPage(boolean isLastPage) {
    this.isLastPage = isLastPage;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getCurrentPageNumber() {
    return currentPageNumber;
  }

  public void setCurrentPageNumber(int currentPageNumber) {
    this.currentPageNumber = currentPageNumber;
  }

  public String getPageBaseUrl() {
    return pageBaseUrl;
  }

  public void setPageBaseUrl(String pageBaseUrl) {
    this.pageBaseUrl = pageBaseUrl;
  }

  public String getPageLabel() {
    return pageLabel;
  }

  public void setPageLabel(String pageLabel) {
    this.pageLabel = pageLabel;
  }

}
