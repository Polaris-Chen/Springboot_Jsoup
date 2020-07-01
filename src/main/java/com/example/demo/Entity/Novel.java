package com.example.demo.Entity;

public class Novel {
    private String name;
    private String content;
    private int downloadtimes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDownloadtimes() {
        return downloadtimes;
    }

    public void setDownloadtimes(int downloadtimes) {
        this.downloadtimes = downloadtimes;
    }


    @Override
    public String toString() {
        return "Novel{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", downloadtimes=" + downloadtimes +
                '}';
    }
}
