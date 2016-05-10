package edu.umd.bferraro.personalblog;

import java.io.Serializable;

/**
 * Created by neelmistry on 4/27/16.
 */

public class ViewPost implements Serializable{

    private String title, textPost, photoPath, videoPath, audioPath, locationString;

    public ViewPost(String titleParam, String textParam, String photoParam, String videoParam, String
                    audioParam, String locParam){
        title = titleParam;
        textPost = textParam;
        photoPath = photoParam;
        videoPath = videoParam;
        audioPath = audioParam;
        locationString = locParam;
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

    public String getAudioPath(){
        return audioPath;
    }

    public String getLocationString(){
        return locationString;
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

    public void setAudioPath(String audioParam){
        audioPath = audioParam;
    }

    public void setLocationString(String locParam){
        locationString = locParam;
    }

}
