package com.uygulama.dao;

import com.uygulama.model.Book;
import com.uygulama.model.UserInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

//Veritabanı islemlerı gerceklesecek
@Repository
public class MainDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    //Data bulma
    public Object findObject(Class whichClass, Serializable userPk){

        return getCurrentSession().get(whichClass,userPk);
    }

    //Data ekle
    public boolean saveOrUpdateObject(Object kaydedilecekObject){
        try {
            getCurrentSession().save(kaydedilecekObject);
            return true; //Ekleme başarılı
        }catch (Exception e){
            return false; //Basarısız
        }
    }

    //Data sil
    public boolean deleteObject(Object silinecekObject){
        try {
            getCurrentSession().remove(silinecekObject);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //Kitap no ile sorgu
    public Book findBookbyBookNo(Integer bookNo){
        Book book=null;

        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();

        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

        Root<Book> root=criteriaQuery.from(Book.class);

        Predicate predicate = criteriaBuilder.equal(root.get("bookNoUnique"),bookNo);
        criteriaQuery.select(root).where(predicate);

        Query<Book> query = getCurrentSession().createQuery(criteriaQuery);
        try {
            book = query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return book;
    }

    //Kitap listesini getir
    public List<Book> loadBookList(){
        CriteriaBuilder criteriaBuilder=getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery=criteriaBuilder.createQuery(Book.class);
        Root<Book> root=criteriaQuery.from(Book.class);

        criteriaQuery.select(root);

        Query<Book> query=getCurrentSession().createQuery(criteriaQuery);

        return query.getResultList();
    }

    //User listesini getir
    public List<UserInfo> loadUserList(){
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<UserInfo> criteriaQuery = criteriaBuilder.createQuery(UserInfo.class);
        Root<UserInfo> root = criteriaQuery.from(UserInfo.class);

        criteriaQuery.select(root);

        Query<UserInfo> query=getCurrentSession().createQuery(criteriaQuery);
        List<UserInfo> ınfo = query.getResultList();

        return ınfo;
    }
}
