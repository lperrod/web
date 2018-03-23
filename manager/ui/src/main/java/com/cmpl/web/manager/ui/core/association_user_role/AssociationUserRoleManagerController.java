package com.cmpl.web.manager.ui.core.association_user_role;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.association_user_role.AssociationUserRoleCreateForm;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDispatcher;
import com.cmpl.web.core.association_user_role.AssociationUserRoleResponse;
import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
public class AssociationUserRoleManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssociationUserRoleManagerController.class);

  private final AssociationUserRoleDispatcher dispatcher;

  public AssociationUserRoleManagerController(AssociationUserRoleDispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  @PostMapping(value = "/manager/responsibilities", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:responsibilities:create')")
  public ResponseEntity<AssociationUserRoleResponse> createAssociationUserRole(
      @RequestBody AssociationUserRoleCreateForm createForm, Locale locale) {

    LOGGER.info("Tentative de création d'une association user/role");
    try {
      AssociationUserRoleResponse response = dispatcher.createEntity(createForm, locale);
      if (response.getAssociationUserRoleDTO() != null) {
        LOGGER.info("Entrée crée, id " + response.getAssociationUserRoleDTO().getId());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (BaseException e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/manager/responsibilities/{userId}/{roleId}", produces = "application/json")
  @PreAuthorize("hasAuthority('administration:responsibilities:delete')")
  public ResponseEntity<AssociationUserRoleResponse> deleteAssociationUserRole(
      @PathVariable(name = "userId") String userId, @PathVariable(name = "roleId") String roleId, Locale locale) {
    LOGGER.info("Tentative de suppression d'un widgetPage");
    try {
      dispatcher.deleteEntity(userId, roleId, locale);
    } catch (BaseException e) {
      LOGGER.error("Echec de la suppression de l'association user/role pour  l'association entre l'utilisateur d'id "
          + userId + " et le role d'id " + roleId, e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

}