package edu.umd.bferraro.personalblog;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.Serializable;

/**
 * Created by neelmistry on 4/27/16.
 */

public class ViewPost implements Serializable{
    private String title, textPost;
    private Bitmap photo;
    private Uri videoUri;

    public ViewPost(String titleParam, String textParam, Bitmap photoParam, Uri videoParam){
        title = titleParam;
        textPost = textParam;
        photo = photoParam;
        videoUri = videoParam;
    }

    public String getTitle(){
        return  title;
    }

    public String getTextPost(){
        return textPost;
    }

    public Bitmap getPhoto(){
        return photo;
    }

    public Uri getVideoUri(){
        return videoUri;
    }

    public void setTitle(String titleParam){
        title = titleParam;
    }

    public void setTextPost(String textParam){
        textPost = textParam;
    }

    public void setPhoto(Bitmap photoParam){
        photo = photoParam;
    }

    public void setVideoUri(Uri videoParam){
        videoUri = videoParam;
    }

}
