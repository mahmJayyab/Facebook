package mahmjayyab.com.example.facebook;

/**
 * Created by acer on 7/12/2017.
 */

public class Pages {
    private String pageName;
    private String pagePic;
    private String pageCover;
    private String isSupscripe;
    private String link;

    public Pages(){

    }
    public Pages (String pageName,String isSupscripe ,String pagePic,String pageCover){
        this.pageName = pageName;
        this.pagePic  = pagePic;
        this.pageCover = pageCover;
        this.isSupscripe = isSupscripe;
    }
    public Pages (String pageName,String isSupscripe ,String pagePic,String pageCover,String link){
        this.pageName = pageName;
        this.pagePic  = pagePic;
        this.pageCover = pageCover;
        this.isSupscripe = isSupscripe;
        this.link = link;
    }
    public Pages (String pageName,String isSupscripe){
        this.pageName = pageName;
        this.isSupscripe = isSupscripe;
    }
    public Pages (String pageName,String pagePic,String pageCover){
        this.pageName = pageName;
        this.pagePic  = pagePic;
        this.pageCover = pageCover;
        this.isSupscripe = isSupscripe;
    }

    public String getPageName() {
        return pageName;
    }

    public String getPagePic() {
        return pagePic;
    }

    public String getPageCover() {
        return pageCover;
    }

    public String getIsSupscripe() {
        return isSupscripe;
    }

    public String getLink() {
        return link;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setPagePic(String pagePic) {
        this.pagePic = pagePic;
    }

    public void setPageCover(String pageCover) {
        this.pageCover = pageCover;
    }

    public void setIsSupscripe(String isSupscripe) {
        this.isSupscripe = isSupscripe;
    }

}
