package mahmjayyab.com.example.facebook;

/**
 * Created by acer on 7/12/2017.
 */

public class Pages {
    private String pageName;
    private String pagePic;
    private String pageCover;
    private String isSupscripe;

    public Pages(){

    }
    public Pages (String pageName,String isSupscripe ,String pagePic,String pageCover){
        this.pageName = pageName;
        this.pagePic  = pagePic;
        this.pageCover = pageCover;
        this.isSupscripe = isSupscripe;
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
