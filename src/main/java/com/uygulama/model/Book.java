package com.uygulama.model;

import javax.persistence.*;
import java.util.Date;

@Entity //Bu class veritabanında bir table olacak
@Table(name = "book")
public class Book {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Otomatik artacak. yeni tablo olusmaz veritabanında
    //@GeneratedValue(strategy = GenerationType.AUTO) // Veritabanında hibernate_sequence tablosu olusacak ve icinde bir sonrakı id degerı ne olacaksa onu tutacak. next_value (Trigger gibi sanki)
    //@GeneratedValue(strategy = GenerationType.TABLE) //Veritabanında hibernate_sequence tablosu olusacak. Icınde her bır tablo ıcın verı tutulacak
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_no", unique = true)
    private int bookNoUnique;

    @Column(name = "book_name")
    private String name;

    @Column(name = "page_count")
    private int pageCount;

    @Column(name = "topic")
    private String topic;

    @Column(name = "author_name")
    private String authorName;

    @Temporal(TemporalType.DATE) //Tarih belirtiyoruz. Sadece gün ay yıl tutacak
    @Column(name = "publish_date")
    private Date publishDate;

    //Alt+Insert

    // Parametresiz constructor
    public Book() {
    }

    //Parametreli constructor
    public Book(int bookNoUnique, String name, int pageCount, String topic, String authorName, Date publishDate) {
        this.bookNoUnique= bookNoUnique;
        this.name = name;
        this.pageCount = pageCount;
        this.topic = topic;
        this.authorName = authorName;
        this.publishDate = publishDate;
    }

    //Tüm columnların getter ve setter'larını olusturuyoruz
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookNoUnique() {
        return bookNoUnique;
    }

    public void setBookNoUnique(int bookNoUnique) {
        this.bookNoUnique = bookNoUnique;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "\tKitap Bilgileri\n" +
                "\t\tKitap ID = " + bookId +
                "\n\t\tKitap Numarasi = " + bookNoUnique +
                "\n\t\tKitap Adi = " + name +
                "\n\t\tSayfa Sayisi = " + pageCount +
                "\n\t\tKonu = " + topic +
                "\n\t\tYazar Adi = " + authorName +
                "\n\t\tYayin Tarihi = " + publishDate +"\n";
    }
}
