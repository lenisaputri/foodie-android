package com.example.uasandroid.isi_menu.proses_menu;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class IsiRecipesCategoryDetail {
    private String Name;
    private String Image;
    private String Ingridient;
    private String Method;

    public IsiRecipesCategoryDetail(){

    }

    public IsiRecipesCategoryDetail(String name, String image, String ingridient, String method){
        Name = name;
        Image = image;
        Ingridient = ingridient;
        Method = method;

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

    public String getIngridient() {
        return Ingridient;
    }

    public void setIngridient(String ingridient) {
        Ingridient = ingridient;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", Name);
        result.put("Image", Image);
        result.put("Ingridient",Ingridient);
        result.put("Method", Method);
        return result;
    }


}
