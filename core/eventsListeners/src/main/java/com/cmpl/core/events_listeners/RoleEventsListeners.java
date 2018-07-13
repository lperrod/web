package com.cmpl.core.events_listeners;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.Role;
import com.cmpl.web.core.role.RoleDTO;
import com.cmpl.web.core.role.privilege.PrivilegeService;

public class RoleEventsListeners {

  private final ResponsibilityService responsibilityService;
  private final PrivilegeService privilegeService;

  public RoleEventsListeners(ResponsibilityService responsibilityService, PrivilegeService privilegeService) {
    this.responsibilityService = responsibilityService;
    this.privilegeService = privilegeService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (RoleDTO.class.equals(clazz)) {
      Role deletedRole = (Role) deletedEvent.getEntity();
      if (deletedRole != null) {
        String roleId = String.valueOf(deletedRole.getId());
        responsibilityService.findByRoleId(roleId)
            .forEach(associationUserRoleDTO -> responsibilityService.deleteEntity(associationUserRoleDTO.getId()));
        privilegeService.findByRoleId(roleId)
            .forEach(privilegeDTO -> privilegeService.deleteEntity(privilegeDTO.getId()));
      }

    }
  }

}
