package com.cmpl.web.configuration.core.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.core.events_listeners.RoleEventsListeners;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactoryImpl;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.role.Role;
import com.cmpl.web.core.role.RoleDAO;
import com.cmpl.web.core.role.RoleDAOImpl;
import com.cmpl.web.core.role.RoleDispatcher;
import com.cmpl.web.core.role.RoleDispatcherImpl;
import com.cmpl.web.core.role.RoleMapper;
import com.cmpl.web.core.role.RoleRepository;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.role.RoleServiceImpl;
import com.cmpl.web.core.role.RoleTranslator;
import com.cmpl.web.core.role.RoleTranslatorImpl;
import com.cmpl.web.core.role.privilege.Privilege;
import com.cmpl.web.core.role.privilege.PrivilegeDAO;
import com.cmpl.web.core.role.privilege.PrivilegeDAOImpl;
import com.cmpl.web.core.role.privilege.PrivilegeMapper;
import com.cmpl.web.core.role.privilege.PrivilegeRepository;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import com.cmpl.web.core.role.privilege.PrivilegeServiceImpl;

@Configuration
@EntityScan(basePackageClasses = {Role.class, Privilege.class})
@EnableJpaRepositories(basePackageClasses = {RoleRepository.class, PrivilegeRepository.class})
public class RoleConfiguration {

  @Bean
  public PrivilegeMapper privilegeMapper() {
    return new PrivilegeMapper();
  }

  @Bean
  public PrivilegeDAO privilegeDAO(ApplicationEventPublisher publisher, PrivilegeRepository privilegeRepository) {
    return new PrivilegeDAOImpl(privilegeRepository, publisher);
  }

  @Bean
  public PrivilegeService privilegeService(PrivilegeDAO privilegeDAO, PrivilegeMapper privilegeMapper) {
    return new PrivilegeServiceImpl(privilegeDAO, privilegeMapper);
  }

  @Bean
  public RoleMapper roleMapper(PrivilegeService privilegeService) {
    return new RoleMapper(privilegeService);
  }

  @Bean
  public RoleDAO roleDAO(ApplicationEventPublisher publisher, RoleRepository roleRepository) {
    return new RoleDAOImpl(roleRepository, publisher);
  }

  @Bean
  public RoleService roleService(RoleDAO roleDAO, RoleMapper roleMapper) {
    return new RoleServiceImpl(roleDAO, roleMapper);
  }

  @Bean
  public BackMenuItem roleBackMenuItem(BackMenuItem administration,
      com.cmpl.web.core.common.user.Privilege rolesReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.roles.href").label("back.roles.label").title("back.roles.title")
        .iconClass("fa fa-tasks").parent(administration).order(1).privilege(rolesReadPrivilege.privilege()).build();
  }

  @Bean
  public BreadCrumb roleBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_VIEW).build();
  }

  @Bean
  public BreadCrumb roleUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_UPDATE).build();
  }

  @Bean
  public BreadCrumb roleCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_CREATE).build();
  }

  List<BreadCrumbItem> roleBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.roles.title").href("back.roles.href").build());
    return items;
  }

  @Bean
  public RoleTranslator roleTranslator() {
    return new RoleTranslatorImpl();
  }

  @Bean
  public RoleDispatcher roleDispatcher(RoleService roleService, PrivilegeService privilegeService,
      RoleTranslator roleTranslator,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges) {
    return new RoleDispatcherImpl(roleService, privilegeService, roleTranslator, privileges);
  }

  @Bean
  public RoleManagerDisplayFactory roleManagerDisplayFactory(RoleService roleService, PrivilegeService privilegeService,
      ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges,
      Set<Locale> availableLocales) {
    return new RoleManagerDisplayFactoryImpl(roleService, privilegeService, contextHolder, menuFactory, messageSource,
        breadCrumbRegistry, privileges, availableLocales);
  }

  @Bean
  public RoleEventsListeners roleEventsListener(AssociationUserRoleService associationUserRoleService,
      PrivilegeService privilegeService) {
    return new RoleEventsListeners(associationUserRoleService, privilegeService);
  }

}
