package com.cmpl.web.core.group;

import javax.validation.constraints.NotEmpty;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class GroupUpdateForm extends BaseUpdateForm<GroupDTO> {

  @NotEmpty(message = "empty.group.name")
  private String name;

  @NotEmpty(message = "empty.group.description")
  private String description;

  public GroupUpdateForm() {

  }

  public GroupUpdateForm(GroupDTO groupDTO) {
    super(groupDTO);
    this.name = groupDTO.getName();
    this.description = groupDTO.getDescription();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
