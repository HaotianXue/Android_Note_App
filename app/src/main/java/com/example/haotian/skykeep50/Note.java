package com.example.haotian.skykeep50;

/**
 * Created by xuehaotian on 19/03/2016.
 */

/**Author:HaotianXue u5689296**/

public class Note {

    public String content;
    private long noteID;


    public long getNotID() {
        return noteID;
    }

    public void setNotID(long notID) {
        this.noteID = notID;
    }

    public Note(String content,long noteID){
        this.content = content;
        this.noteID = noteID;
    }
    
    public void setContent(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString(){
        return content;
    }

}
