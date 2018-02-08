function validateAndCreatePage() {
  var page = computePage();
  return page;
}

function cancelUpdatePage() {
  window.location.href = "/manager/pages";
}

function validateAndUpdatePage() {
  var page = computeUpdatePage();
  return page;
}

function computePage() {
  var page = {};

  var inputName = $("#name");
  var inputMenuTitle = $("#menuTitle");
  var inputLocaleCode = $("#localeCode");

  page.name = inputName.val();
  page.menuTitle = inputMenuTitle.val();
  page.body = computePageBody();
  page.header = computePageHeader();
  page.footer = computePageFooter();
  page.meta = computePageMeta();
  page.localeCode = inputLocaleCode.val();

  return page;

}

function computeUpdatePage() {
  var page = {};

  var inputName = $("#name");
  var inputMenuTitle = $("#menuTitle");
  var inputId = $("#id");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputLocaleCode = $("#localeCode");

  page.name = inputName.val();
  page.menuTitle = inputMenuTitle.val();
  page.body = computePageBody();
  page.header = computePageHeader();
  page.footer = computePageFooter();
  page.meta = computePageMeta();
  page.id = inputId.val();
  page.creationDate = inputCreationDate.val();
  page.modificationDate = inputModificationDate.val();
  page.localeCode = inputLocaleCode.val();
  return page;
}

function computePageBody() {
  if (codeMirrorBody) {
    return codeMirrorBody.getValue();
  }
  return $("#body").val();
}

function computePageHeader() {
  if (codeMirrorHeader) {
    return codeMirrorHeader.getValue();
  }
  return $("#header").val();
}

function computePageMeta() {
  if (codeMirrorMeta) {
    return codeMirrorMeta.getValue();
  }
  return $("#meta").val();
}

function computePageFooter() {
  if (codeMirrorFooter) {
    return codeMirrorFooter.getValue();
  }
  return $("#footer").val();
}

function postCreatePageForm() {
  var url = "/manager/pages";
  create($("#pageCreateForm"), $(".loader"), $(".card-loader"), url, url,
      validateAndCreatePage());
}

function postUpdatePageForm() {
  var pageToUpdate = validateAndUpdatePage();
  var url = "/manager/pages/" + pageToUpdate.id;
  var urlFallback = "/manager/pages/" + pageToUpdate.id;
  update($("#pageUpdateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, pageToUpdate, true);
}

$(document).ready(function () {
  $("#formInputWithNews").click(function () {
    $("#withNews").click();
  });
});
