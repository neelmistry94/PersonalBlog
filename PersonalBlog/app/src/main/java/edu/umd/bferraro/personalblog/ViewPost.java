package edu.umd.bferraro.personalblog;

import java.io.Serializable;

/**
 * Created by neelmistry on 4/27/16.
 */

public class ViewPost implements Serializable{
    private String title, textPost, videoPath, photoPath;


    public ViewPost(String titleParam, String textParam, String photoParam, String videoParam){
        title = titleParam;
        textPost = textParam;
        photoPath = photoParam;
        videoPath = videoParam;
    }

    public String getTitle(){
        return  title;
    }

    public String getTextPost(){
        return textPost;
    }

    public String getPhotoPath(){
        return photoPath;
    }

    public String getVideoPath(){
        return videoPath;
    }

    public void setTitle(String titleParam){
        title = titleParam;
    }

    public void setTextPost(String textParam){
        textPost = textParam;
    }

    public void setPhotoPath(String photoParam){
        photoPath = photoParam;
    }

    public void setVideoPath(String videoParam){
        videoPath = videoParam;
    }

}
