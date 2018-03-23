function cancelUpdateRole() {
  window.location.href = "/manager/roles";
}

function cancelCreateRole() {
  window.location.href = "/manager/roles";
}

function validateAndUpdateRole() {
  var role = computeRoleToUpdate();
  return role;
}

function validateAndCreateRole() {
  var role = computeRoleToCreate();
  return role;
}

function computeRoleToUpdate() {
  var role = {};

  var inputName = $("#name");
  var inputId = $("#id");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputCreationUser = $("#creationUser");
  var inputModificationUser = $("#modificationUser");

  role.name = inputName.val();
  role.description = computeRoleDescription();

  role.id = inputId.val();
  role.creationDate = formatDate(inputCreationDate.val());
  role.modificationDate = formatDate(inputModificationDate.val());
  role.creationUser = inputCreationUser.val();
  role.modificationUser = inputModificationUser.val();

  return role;

}

function computeRoleToCreate() {
  var role = {};

  var inputName = $("#name");

  role.name = inputName.val();
  role.description = computeRoleDescription();

  return role;

}

function computeRoleDescription() {
  var description = "";
  description = CKEDITOR.instances.description.getData();
  return description;
}

function postUpdateRoleForm() {
  var roleToUpdate = validateAndUpdateRole();
  var url = "/manager/roles/" + roleToUpdate.id;
  var urlFallback = "/manager/roles/" + roleToUpdate.id;
  update($("#roleUpdateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback,
      roleToUpdate).done(function (data) {
    handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
        $("#roleUpdateForm"), url)
  }).fail(function (error) {
    handleErrorPutResult(urlFallback);
  });
}

function postCreateRoleForm() {
  var roleToCreate = validateAndCreateRole();
  var url = "/manager/roles/";
  var urlFallback = "/manager/roles/";
  create($("#roleCreateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, roleToCreate).done(function (data) {
    handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
        $("#roleCreateForm"), url)
  }).fail(function (error) {
    handleErrorPostResult(urlFallback);
  });
}