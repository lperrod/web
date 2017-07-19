package cmpl.web.model.facebook;

import java.util.List;

import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.rest.BaseResponse;

/**
 * Reponse a la requete d'import de post facebook
 * 
 * @author Louis
 *
 */
public class FacebookImportResponse extends BaseResponse {

  private List<NewsEntryDTO> createdNewsEntries;

  public List<NewsEntryDTO> getCreatedNewsEntries() {
    return createdNewsEntries;
  }

  public void setCreatedNewsEntries(List<NewsEntryDTO> createdNewsEntries) {
    this.createdNewsEntries = createdNewsEntries;
  }

}