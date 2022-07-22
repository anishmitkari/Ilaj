package com.userdoctor.ui.common.model;

public class TopHomeSpeciallist {

String Categoryname,Imageicon;
    String Id;
    public TopHomeSpeciallist(String Id,String Categoryname, String Imageicon) {
         this.Id=Id;
        this.Categoryname = Categoryname;
        this.Imageicon = Imageicon;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategoryname() {
        return Categoryname;
    }

    public void setCategoryname(String categoryname) {
        Categoryname = categoryname;
    }

    public String getImageicon() {
        return Imageicon;
    }

    public void setImageicon(String imageicon) {
        Imageicon = imageicon;
    }
}
