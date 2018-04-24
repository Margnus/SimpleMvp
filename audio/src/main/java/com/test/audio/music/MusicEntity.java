package com.test.audio.music;

public class MusicEntity {
    private String url;
    private String singer;
    private String musicTitle;
    private String album;

    public MusicEntity(String url, String musicTitle, String singer) {
        this.url = url;
        this.singer = singer;
        this.musicTitle = musicTitle;
    }

    public MusicEntity() {
    }

    public MusicEntity(String url, String singer, String musicTitle, String album) {
        this.url = url;
        this.singer = singer;
        this.musicTitle = musicTitle;
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
