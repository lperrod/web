package com.cmpl.web.meta;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.model.BaseException;

@Controller
public class MetaManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MetaManagerController.class);

  private final MetaElementDispatcher dispatcher;

  @Autowired
  public MetaManagerController(MetaElementDispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  @PostMapping(value = "/manager/pages/{pageId}/metas", produces = "application/json")
  @ResponseBody
  public ResponseEntity<MetaElementResponse> createMetaElement(@PathVariable(name = "pageId") String pageId,
      @RequestBody MetaElementCreateForm createForm) {

    LOGGER.info("Tentative de création d'un meta element");
    try {
      MetaElementResponse response = dispatcher.createEntity(pageId, createForm, Locale.FRANCE);
      if (response.getMetaElement() != null) {
        LOGGER.info("Entrée crée, id " + response.getMetaElement().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/manager/pages/{pageId}/metas/{metaId}", produces = "application/json")
  public ResponseEntity<MetaElementResponse> deleteMetaElement(@PathVariable(name = "metaId") String metaId) {
    LOGGER.info("Tentative de création d'un meta element");
    try {
      dispatcher.deleteMetaEntity(metaId, Locale.FRANCE);
    } catch (BaseException e) {
      LOGGER.error("Echec de la suppression de la balise meta " + metaId, e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/manager/pages/{pageId}/openGraphMetas", produces = "application/json")
  @ResponseBody
  public ResponseEntity<MetaElementResponse> createOpenGraphMetaElement(@PathVariable(name = "pageId") String pageId,
      @RequestBody OpenGraphMetaElementCreateForm createForm) {

    LOGGER.info("Tentative de création d'un open graph meta element");
    try {
      MetaElementResponse response = dispatcher.createEntity(pageId, createForm, Locale.FRANCE);
      if (response.getOpenGraphMetaElement() != null) {
        LOGGER.info("Entrée crée, id " + response.getOpenGraphMetaElement().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/manager/pages/{pageId}/openGraphMetas/{openGraphMetaId}", produces = "application/json")
  public ResponseEntity<MetaElementResponse> deleteOpenGraphMetaElement(
      @PathVariable(name = "openGraphMetaId") String openGraphMetaId) {

    LOGGER.info("Tentative de création d'un meta element");
    try {
      dispatcher.deleteOpenGraphMetaEntity(openGraphMetaId, Locale.FRANCE);
    } catch (BaseException e) {
      LOGGER.error("Echec de la suppression de la balise open graph meta " + openGraphMetaId, e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);

  }

}