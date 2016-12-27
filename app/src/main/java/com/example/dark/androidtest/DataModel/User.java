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
import java.util.regex.Pattern;

/**
 * Created by Dark on 12/21/2016.
 */

public class User {
    private String login;
    private int password;
    private List<Post> posts;
    private String token;
    private String name;
    private String email;
    private String userGroup;

    public User(String login, String name, String email, int password, String userGroup) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userGroup = userGroup;
        posts = new ArrayList<>();
    }

    public User() {
        posts = new ArrayList<>();
    }

    public User(User other) {
        setToken(other.token);
        setLogin(other.login);
        setEmail(other.email);
        setName(other.name);
        setPassword(other.password);
        posts = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return new ArrayList<>(posts);
    }

    public void setPosts(List<Post> posts) {
        this.posts = new ArrayList<>(posts);
    }
    public void setPosts(String xml) {
        this.posts = postsFromXML(xml);
    }





    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setData(String XML) {
        String data = "";
        XmlPullParserFactory xmlFactoryObject = null;
        XmlPullParser myParser;
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(new ByteArrayInputStream(XML.getBytes("UTF-8")), null);
            int event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)  {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        data = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("login")){
                            setLogin(data);
                        }
                        if(name.equals("name")){
                            setName(data);
                        }
                        if(name.equals("email")){
                            setEmail(data);
                        }
                        if(name.equals("token")){
                            setToken(data);
                        }
                        if(name.equals("userGroup")){
                            setUserGroup(data);
                        }
                        if(name.equals("passHash")) {
                            setPassword(Integer.parseInt(data));
                        }
                        if(name.equals("token")) {
                            setToken(data);
                        }
                        break;
                }
                event = myParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Post> postsFromXML(String xml) {
        List<Post> res = new ArrayList<>();
        String data = "";
        XmlPullParserFactory xmlFactoryObject = null;
        XmlPullParser myParser;
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(new ByteArrayInputStream(xml.getBytes("UTF-8")), null);
            Post post = new Post();
            int event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)  {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        if(name.equals("Post")) {
                            post = new Post();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        data = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("id")){
                            post.setId(Integer.parseInt(data));
                        }
                        if(name.equals("author")) {
                            post.setAuthor(data);
                        }
                        if(name.equals("text")) {
                            post.setContent(data);
                        }
                        if(name.equals("subject")) {
                            post.setSubject(data);
                        }
                        if(name.equals("date")) {
                            post.setDate(Timestamp.valueOf(data));
                        }
                        if(name.equals("Post")) {
                            res.add(post);
                        }
                        break;
                }
                event = myParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    public void userFromXML(String userData) {
        String parts[] = userData.split(Pattern.quote("+"));
        String postsXML = parts[0];
        String userDataXML = parts[1];
        setData(userDataXML);
        setPosts(postsXML);
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
