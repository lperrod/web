package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.Builder;

public class WidgetCreateFormBuilder extends Builder<WidgetCreateForm> {

  private WIDGET_TYPE type;
  private String name;

  private WidgetCreateFormBuilder(){

  }

  public WidgetCreateFormBuilder type(WIDGET_TYPE type) {
    this.type = type;
    return this;
  }


  public WidgetCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public WidgetCreateForm build() {
    WidgetCreateForm form = new WidgetCreateForm();
    form.setName(name);
    form.setType(type);
    return form;
  }

  public static WidgetCreateFormBuilder create(){
    return new WidgetCreateFormBuilder();
  }
}