package com.intellij.librarymanager.service.impl;

import com.intellij.librarymanager.dao.EmpruntDao;
import com.intellij.librarymanager.dao.impl.EmpruntDaoImpl;
import com.intellij.librarymanager.exception.DaoException;
import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.model.Membre;
import com.intellij.librarymanager.service.EmpruntService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntServiceImpl implements EmpruntService {
    //le design pattern Singleton
    private static EmpruntServiceImpl instance = new EmpruntServiceImpl();
    private EmpruntServiceImpl(){}
    public static EmpruntService getInstance(){return instance;}

    @Override
    public List<Emprunt> getList() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getList();
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrent();
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByMembre(idMembre);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByLivre(idLivre);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntDao.getById(id);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            empruntDao.create(idMembre,idLivre,dateEmprunt);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return;
    }

    @Override
    public void returnBook(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntDao.getById(id);
            LocalDate localDate = LocalDate.now();
            emprunt.setDateRetour(localDate);
            empruntDao.update(emprunt);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return;
    }

    @Override
    public int count() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int i = -1;
        try {
            i = empruntDao.count();
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return i;
    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try{
            if(empruntDao.getListCurrentByLivre(idLivre).isEmpty()==false){
                return true;
            }
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return false;
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int numbersOfEmprunt=-1;
        try {
            //defination by ourself
            switch (membre.getAbonnement())
            {
                case BASIC:numbersOfEmprunt=5;
                        if(empruntDao.getListCurrentByMembre(membre.getId()).size()==numbersOfEmprunt)
                        {return false;}break;
                case PREMIUM:numbersOfEmprunt=10;
                        if(empruntDao.getListCurrentByMembre(membre.getId()).size()==numbersOfEmprunt)
                        {return false;}break;
                case VIP:numbersOfEmprunt=15;
                        if(empruntDao.getListCurrentByMembre(membre.getId()).size()==numbersOfEmprunt)
                        {return false;}break;
                default:break;
            }

        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return true;
    }
}
