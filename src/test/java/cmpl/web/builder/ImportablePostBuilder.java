package cmpl.web.builder;

import java.util.Date;

import org.springframework.social.facebook.api.Post.PostType;

import cmpl.web.model.facebook.ImportablePost;

public class ImportablePostBuilder {

  private String author;
  private String title;
  private PostType type;
  private String description;
  private String photoUrl;
  private String videoUrl;
  private String linkUrl;
  private String facebookId;
  private Date creationDate;
  private String formattedDate;
  private String objectId;
  private String onclick;

  public ImportablePostBuilder author(String author) {
    this.author = author;
    return this;
  }

  public ImportablePostBuilder title(String title) {
    this.title = title;
    return this;
  }

  public ImportablePostBuilder type(PostType type) {
    this.type = type;
    return this;
  }

  public ImportablePostBuilder description(String description) {
    this.description = description;
    return this;
  }

  public ImportablePostBuilder photoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
    return this;
  }

  public ImportablePostBuilder videoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public ImportablePostBuilder linkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    return this;
  }

  public ImportablePostBuilder facebookId(String facebookId) {
    this.facebookId = facebookId;
    return this;
  }

  public ImportablePostBuilder creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public ImportablePostBuilder formattedDate(String formattedDate) {
    this.formattedDate = formattedDate;
    return this;
  }

  public ImportablePostBuilder objectId(String objectId) {
    this.objectId = objectId;
    return this;
  }

  public ImportablePostBuilder onclick(String onclick) {
    this.onclick = onclick;
    return this;
  }

  public ImportablePost toImportablePost() {
    ImportablePost post = new ImportablePost();

    post.setAuthor(author);
    post.setCreationDate(creationDate);
    post.setDescription(description);
    post.setFacebookId(facebookId);
    post.setFormattedDate(formattedDate);
    post.setLinkUrl(linkUrl);
    post.setObjectId(objectId);
    post.setOnclick(onclick);
    post.setPhotoUrl(photoUrl);
    post.setTitle(title);
    post.setType(type);
    post.setVideoUrl(videoUrl);

    return post;
  }

}