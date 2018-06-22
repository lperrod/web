package com.cmpl.web.core.widget;

import javax.validation.constraints.NotBlank;

public class WidgetCreateForm {

  @NotBlank(message = "empty.widget.type")
  private String type;
  @NotBlank(message = "empty.widget.name")
  private String name;
  private String localeCode;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocaleCode() {
    return localeCode;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }
}
