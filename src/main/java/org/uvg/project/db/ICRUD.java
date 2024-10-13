package org.uvg.project.db;

import org.uvg.project.Exceptions.DBException;

import java.util.List;

public interface ICRUD {

    // CREATE
     void saveObject(Object obj) throws DBException;

    // READ
    List<Object> getObjects () throws DBException;

    // UPDATE
    void updateObject (Object obj) throws DBException;

    // DELETE CON UN OBJETO
    void deleteObject(Object obj) throws DBException;

    // DELETE CON ID
    void deleteObjectById() throws DBException;

}
