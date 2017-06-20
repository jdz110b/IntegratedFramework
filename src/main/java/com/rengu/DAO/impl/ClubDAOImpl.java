package com.rengu.DAO.impl;

import com.rengu.DAO.ClubDAO;
import com.rengu.entity.RG_ClubEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by hanchangming on 2017/6/13.
 */
public class ClubDAOImpl extends SuperDAOImpl implements ClubDAO<RG_ClubEntity> {
    @Override
    public List<RG_ClubEntity> findAll() {
        return null;
    }

    @Override
    public List<RG_ClubEntity> findAllByUsername(String username) {
        return null;
    }

    @Override
    public RG_ClubEntity findAllById(String id) {
        try {
            Session session = SuperDAOImpl.getSession();
            String hql = "from RG_ClubEntity rg_clubEntity where rg_clubEntity.id =:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            if (!query.list().isEmpty()) {
                return (RG_ClubEntity) query.list().get(0);
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RG_ClubEntity> search(String keyWord) {
        return null;
    }
}
