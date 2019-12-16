package com.example.uasandroid.isi_menu.proses_menu;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class IsiRecipesCategory {
    private String Name;
    private String Image;

    public IsiRecipesCategory(){

    }

    public IsiRecipesCategory(String name, String image){
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", Name);
        result.put("Image", Image);
        return result;
    }
}
