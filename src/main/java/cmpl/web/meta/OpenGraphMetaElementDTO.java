package cmpl.web.meta;

import cmpl.web.core.model.BaseDTO;

public class OpenGraphMetaElementDTO extends BaseDTO {

  private String pageId;
  private String content;
  private String property;

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

}