package cmpl.web.facebook;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.util.StringUtils;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.model.BaseException;
import cmpl.web.news.NewsEntryService;

/**
 * Implementation de l'interface qui va recuperer les posts facebook d'unutilisateur
 * 
 * @author Louis
 *
 */
public class FacebookServiceImpl implements FacebookService {

  private final Facebook facebookConnector;
  private final ConnectionRepository connectionRepository;
  private final ContextHolder contextHolder;
  private final NewsEntryService newsEntryService;

  public FacebookServiceImpl(ContextHolder contextHolder, Facebook facebookConnector,
      ConnectionRepository connectionRepository, NewsEntryService newsEntryService) {
    this.facebookConnector = facebookConnector;
    this.connectionRepository = connectionRepository;
    this.contextHolder = contextHolder;
    this.newsEntryService = newsEntryService;
  }

  @Override
  public List<ImportablePost> getRecentFeed() throws BaseException {

    if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
      throw new BaseException();
    }

    PagedList<Post> recentPosts = getFeedOperations().getPosts();

    return computeImportablePosts(recentPosts);
  }

  FeedOperations getFeedOperations() {
    return facebookConnector.feedOperations();
  }

  List<ImportablePost> computeImportablePosts(PagedList<Post> recentPosts) {
    List<ImportablePost> importablePosts = new ArrayList<>();
    for (Post recentPost : recentPosts) {
      ImportablePost post = computeImportablePost(recentPost, contextHolder.getDateFormat());
      if (canImportPost(post)) {
        importablePosts.add(post);
      }
    }
    return importablePosts;
  }

  boolean canImportPost(ImportablePost post) {
    if (PostType.STATUS.equals(post.getType()) && !StringUtils.hasText(post.getDescription())) {
      return false;
    }
    if (newsEntryService.isAlreadyImportedFromFacebook(post.getFacebookId())) {
      return false;
    }
    return true;
  }

  ImportablePost computeImportablePost(Post feed, DateTimeFormatter formatter) {

    ImportablePost post = new ImportablePost();
    post.setAuthor(computeAuthor(feed));
    post.setDescription(computeDescription(feed));
    post.setPhotoUrl(computePhotoUrl(feed));
    post.setLinkUrl(computeLink(feed));
    post.setVideoUrl(computeVideoUrl(feed));
    post.setTitle(computeTitle(feed));
    post.setType(computeType(feed));
    post.setFacebookId(computeId(feed));
    post.setOnclick(computeOnclick(feed));
    post.setCreationDate(computeCreatedTime(feed));
    post.setObjectId(computeObjectId(feed));
    post.setFormattedDate(computeFormattedDate(feed, formatter));

    return post;
  }

  String computeFormattedDate(Post feed, DateTimeFormatter formatter) {
    return formatter.format(computeCreatedTime(feed));
  }

  String computeObjectId(Post feed) {
    return feed.getObjectId();
  }

  LocalDate computeCreatedTime(Post feed) {
    return feed.getCreatedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  String computeId(Post feed) {
    return feed.getId();
  }

  PostType computeType(Post feed) {
    return feed.getType();
  }

  String computeLink(Post feed) {
    return feed.getLink();
  }

  String computeAuthor(Post feed) {
    return feed.getFrom().getName();
  }

  String computePhotoUrl(Post feed) {
    if (!PostType.PHOTO.equals(computeType(feed))) {
      return "";
    }
    return feed.getPicture();
  }

  String computeVideoUrl(Post feed) {
    String videoUrl = feed.getSource();
    if (!StringUtils.hasText(videoUrl)) {
      return videoUrl;
    }
    videoUrl = makeVideoNotAutoplay(videoUrl);
    return videoUrl;
  }

  String makeVideoNotAutoplay(String videoUrl) {
    return videoUrl.replace("autoplay=1", "autoplay=0");
  }

  String computeOnclick(Post feed) {
    return "toggleImport('" + computeId(feed) + "')";
  }

  String computeTitle(Post feed) {
    String title = feed.getName();
    if (!StringUtils.hasText(title)) {
      title = feed.getCaption();
    }
    if (!StringUtils.hasText(title)) {
      title = "Type " + computeType(feed).name();
    }
    return title;
  }

  String computeDescription(Post feed) {
    String message = feed.getMessage();
    if (!StringUtils.hasText(message)) {
      return feed.getDescription();
    }
    return message;
  }

}