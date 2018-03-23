package com.cmpl.web.core.user;

import java.util.Locale;

public interface UserDispatcher {

  UserResponse createEntity(UserCreateForm form, Locale locale);

  UserResponse updateEntity(UserUpdateForm form, Locale locale);

}