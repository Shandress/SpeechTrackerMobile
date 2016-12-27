package com.example.dark.androidtest.DataModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark on 12/21/2016.
 */

public class Post {
    private String author;
    private Timestamp date;
    private String content;
    private String subject;
    private int id;

    public Post(String author, Timestamp date, String content, int id, String subject) {
        setSubject(subject);
        setId(id);
        setAuthor(author);
        setContent(content);
        setDate(date);
    }

    public Post() {
        setSubject("");
        setId(-1);
        setDate(new Timestamp(0));
        setContent("");
        setAuthor("");
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
