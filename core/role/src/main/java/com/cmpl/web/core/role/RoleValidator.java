package com.cmpl.web.core.role;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.role.privilege.PrivilegeForm;

public interface RoleValidator {

  Error validateCreate(RoleCreateForm form, Locale locale);

  Error validateUpdate(RoleUpdateForm form, Locale locale);

  Error validateUpdate(PrivilegeForm form, Locale locale);

}
