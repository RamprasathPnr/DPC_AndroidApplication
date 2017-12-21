package com.omneagate.dpc.Model;

import android.graphics.drawable.Drawable;

import lombok.Data;


/**
 * Created by user on 27/6/16.
 */

@Data

public class MenuDataDto {
    String name;
    int id;
    String lName;

    public MenuDataDto(String name, int id, String lName) {
        this.name = name;
        this.id = id;
        this.lName = lName;
    }


}
