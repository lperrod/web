package com.cmpl.web.core.role;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class PrivilegeServiceImpl extends BaseServiceImpl<PrivilegeDTO, Privilege> implements PrivilegeService {

  private final PrivilegeDAO privilegeDAO;

  public PrivilegeServiceImpl(PrivilegeDAO privilegeDAO, PrivilegeMapper privilegeMapper) {
    super(privilegeDAO, privilegeMapper);
    this.privilegeDAO = privilegeDAO;
  }

  @Override
  public List<PrivilegeDTO> findByRoleId(String roleId) {
    return mapper.toListDTO(privilegeDAO.findByRoleId(roleId));
  }
}
