package cmpl.web.model.news.error;

import java.util.List;

public class Error extends ErrorCause {

  private List<ErrorCause> causes;

  public List<ErrorCause> getCauses() {
    return causes;
  }

  public void setCauses(List<ErrorCause> causes) {
    this.causes = causes;
  }

}