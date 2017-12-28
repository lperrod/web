package com.cmpl.web.page;

import com.cmpl.web.menu.BACK_MENU;

/**
 * Enumeration pour les pages du back office du site
 * 
 * @author Louis
 *
 */
public enum BACK_PAGE {

  LOGIN("login.tile", ""),
  NEWS_VIEW("news.view", BACK_MENU.BACK_NEWS.getTitle()),
  NEWS_CREATE("news.create", BACK_MENU.BACK_NEWS.getTitle()),
  NEWS_UPDATE("news.update", BACK_MENU.BACK_NEWS.getTitle()),
  FACEBOOK_ACCESS("facebook.access.tile", "facebook.access.title"),
  FACEBOOK_IMPORT("facebook.import.tile", "facebook.access.title"),
  MENUS_VIEW("menus.view", BACK_MENU.BACK_MENUS.getTitle()),
  MENUS_CREATE("menus.create", BACK_MENU.BACK_MENUS.getTitle()),
  MENUS_UPDATE("menus.update", BACK_MENU.BACK_MENUS.getTitle()),
  PAGES_VIEW("pages.view", BACK_MENU.BACK_PAGES.getTitle()),
  PAGES_CREATE("pages.create", BACK_MENU.BACK_PAGES.getTitle()),
  PAGES_UPDATE("pages.update", BACK_MENU.BACK_PAGES.getTitle()),
  MEDIA_VIEW("medias.view", BACK_MENU.BACK_MEDIAS.getTitle()),
  MEDIA_UPLOAD("medias.upload", BACK_MENU.BACK_MEDIAS.getTitle()),
  CAROUSELS_VIEW("carousels.view", BACK_MENU.BACK_CAROUSELS.getTitle()),
  CAROUSELS_UPDATE("carousels.update", BACK_MENU.BACK_CAROUSELS.getTitle()),
  CAROUSELS_CREATE("carousels.create", BACK_MENU.BACK_CAROUSELS.getTitle()),
  STYLES_VIEW("styles.view", BACK_MENU.BACK_STYLE.getTitle()),
  STYLES_UPDATE("styles.update", BACK_MENU.BACK_STYLE.getTitle());

  private String tile;
  private String title;

  private BACK_PAGE(String tile, String title) {
    this.tile = tile;
    this.title = title;
  }

  public String getTile() {
    return tile;
  }

  public String getTitle() {
    return title;
  }

}