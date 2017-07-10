package com.rengu.DAO;

import java.util.List;

/**
 * Created by hanchangming on 2017/6/16.
 */
public interface AdjustProcessDAO<T> {
    List<T> findAll();

    T findAllById(String id);
}
