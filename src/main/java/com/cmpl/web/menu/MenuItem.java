package com.cmpl.web.menu;

import java.util.List;

/**
 * Objet representant un element du menu
 * 
 * @author Louis
 *
 */
public class MenuItem {

  private String title;
  private String href;
  private String label;
  private String customCssClass;
  private String iconClass;

  List<MenuItem> subMenuItems;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<MenuItem> getSubMenuItems() {
    return subMenuItems;
  }

  public void setSubMenuItems(List<MenuItem> subMenuItems) {
    this.subMenuItems = subMenuItems;
  }

  public String getCustomCssClass() {
    return customCssClass;
  }

  public void setCustomCssClass(String customCssClass) {
    this.customCssClass = customCssClass;
  }

  public String getIconClass() {
    return iconClass;
  }

  public void setIconClass(String iconClass) {
    this.iconClass = iconClass;
  }

}
