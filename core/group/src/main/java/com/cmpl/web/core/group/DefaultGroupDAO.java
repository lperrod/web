package com.cmpl.web.core.group;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.BOGroup;
import com.cmpl.web.core.models.QBOGroup;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultGroupDAO extends DefaultBaseDAO<BOGroup> implements GroupDAO {

  public DefaultGroupDAO(GroupRepository entityRepository, ApplicationEventPublisher publisher) {
    super(BOGroup.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QBOGroup qboGroup = QBOGroup.bOGroup;
    return qboGroup.name.containsIgnoreCase(query);
  }
}