package com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrador on 21/01/17.
 */

public class EntityResponse implements Serializable {
    public String kind;
    public ArrayList<DataMenu> dataMenu;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ArrayList<DataMenu> getDataMenu() {
        return dataMenu;
    }

    public void setDataMenu(ArrayList<DataMenu> dataMenu) {
        this.dataMenu = dataMenu;
    }
}
