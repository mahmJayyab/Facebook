package mahmjayyab.com.example.facebook;

/**
 * Created by mahmo on 7/4/2017.
 */

public class Video  {
    private String pageName = null;
    private String source = null;
    private String description = null;
    private String title = null;
    private String id = null;
    private String picture = null;
    private String created_date = null;
    private String likes;
    private String favorite = "false";
    private String page_pic = null;
    private String image;
    public  int pos;

    public Video(String source, String description, String title, String id, String picture,
                 String created_date, String summary, String pageName){
        this.setSource(source);
        this.setDescription(description);
        this.setTitle(title);
        this.setId(id);
        this.setPicture(picture);
        this.setCreated_date(created_date);
        this.setLikes(summary);
        this.setPageName(pageName);

    }
    public Video(String source, String description, String title, String id, String picture,
                 String created_date, String likes, String pageName, String favorite, String page_pic) {
        this.setSource(source);
        this.setDescription(description);
        this.setTitle(title);
        this.setId(id);
        this.setPicture(picture);
        this.setCreated_date(created_date);
        this.setLikes(likes);
        this.setPageName(pageName);
        this.setFavorite(favorite);
        this.setPage_pic(page_pic);
    }

    public Video(String pageName, String title, String source, String picture ){
        this.setSource(source);
        //this.setDescription(description);
        this.setTitle(title);
        //this.setId(id);
        this.setPicture(picture);
        //this.setCreated_date(created_date);
       // this.setSummary(summary);
        this.setPageName(pageName);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String summary) {
        this.likes = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getPage_pic() {
        return page_pic;
    }

    public void setPage_pic(String page_pic) {
        this.page_pic = page_pic;
    }
}
