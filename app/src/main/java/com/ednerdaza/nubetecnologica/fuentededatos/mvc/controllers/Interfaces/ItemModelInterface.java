package com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.Interfaces;

/**
 * Created by administrador on 9/06/16.
 */
public interface ItemModelInterface<T> {

    public void completeSuccess(T entity);
    public void completeFail(String message);

}
