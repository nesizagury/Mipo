package com.example.mipo.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Sprintzin on 06/02/2016.
 */
@ParseClassName("BigPicture")
public class BigPicture extends ParseObject {

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getNum() {
        return getString("num");
    }

    public void setNum(String num) {
        put("num", num);
    }

    public ParseFile getPic() {
        return getParseFile("pic");
    }

    public void setPic(ParseFile file) {
        put("pic", file);
    }
}
