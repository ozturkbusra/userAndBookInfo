package com.uygulama.service;

import com.uygulama.dao.MainDAO;
import com.uygulama.model.Book;
import com.uygulama.model.UserAddress;
import com.uygulama.model.UserContact;
import com.uygulama.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service //Servis sınıfı oldugunu belırtıyoruz
/*
 * Propagation (Yayılma) : Servis metodu çağrıldıgında,Transaction baslatılıp baslatılmayacagına,
 * mevcut transaction varsa bunun devam etmesını, yoksa yeni bır transaction olusmasına
 * göre davranıs sekıllerını belırlemeye yarayan bır enum sınıftır.
 * Propagation.REQUIRED : Default. Aktif bır transactıon yoksa yenı bır transactıon acar.
 *                        Aktif varsa buna katılır.
 *
 * RollBackFor : Transaction işlemı sırasında belırtılen hata olursa gerı al
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//Veritabanı ıslemlerı gerceklesecek oldugunu belırttık
public class MainService {

    //Dao sınıfıyla baglantı kurduk
    @Autowired
    private MainDAO MainDAO;

    //Belirtilen id'ye sahip kitabı getir
    public Book getirService(Integer bookNo){
        Book book = MainDAO.findBookbyBookNo(bookNo);
        return book;
    }

    //Belirtilen id'ye sahip kitabı sil
    @Transactional
    public Boolean deleteService(Integer bookNo){
        Book book= (Book) MainDAO.findObject(Book.class,bookNo);
        return MainDAO.deleteObject(book);
    }

    //Controllerdan parametrelerı alarak Book pojosuna ekleme veya guncelleme yapılacak
    @Transactional
    public Boolean saveOrUpdateServiceBook(int bookNoUnique, String name, int pageCount, String topic, String authorName, Date publishDate){
        Book book= (Book) MainDAO.findObject(Book.class, bookNoUnique); //Object turunde donen degerı Book turune donusturup Book nesnesıne atıyoruz
        //Bu no ya sahıp bır kıtap olup olmadıgını kontrol edıyoruz
        if(book == null){
            book= new Book(); //book'un ıcı bos oldugu ıcın, book'tan yenı bır Book nesnesı olusturduk
            book.setBookNoUnique(bookNoUnique);
        }
        book.setName(name);
        book.setPageCount(pageCount);
        book.setTopic(topic);
        book.setAuthorName(authorName);
        book.setPublishDate(publishDate);

        return MainDAO.saveOrUpdateObject(book);
    }

    //Controllerdan parametreleri alarak, UserInfo pojo classına ekleme veya güncelleme yapılacak
    @Transactional
    public Boolean saveOrUpdateServiceUser(String userName, Date buyDate, Integer phoneNumber, String link, String city, List<String> eMail, List<String> district, List<String> street, List<Integer> buildingNumber, List<Integer> postCode){

        UserContact userContact = new UserContact(); //Contact verilerini atamak icin nesnesini olusturuyoruz

        UserAddress address = new UserAddress();

        List<UserAddress> addressList = new ArrayList<>();

        UserInfo userInfo=(UserInfo) MainDAO.findObject(UserInfo.class, (phoneNumber)); //userinfo tablosunu kontrol edıyoruz. girilen phoneNumber var mı dıye
        //kontrol edilecek verinin int olması gerekli. String'i Integer'a cevırme : Integer.parseInt()

        if (userInfo == null){
            userInfo=new UserInfo();
            userInfo.setPhoneNumber(phoneNumber);
        }
        userContact.setLink(link);
        userContact.setCity(city);

        //Kac adres girildiyse o kadar donduruyoruz
        //Her bır nesne bir adres oluyor ve userInfo'daki adres listesine kayıt oluyor
        for (int i=0;i<district.size();i++){
            userInfo.getAddresses().add(new UserAddress(district.get(i),street.get(i),buildingNumber.get(i),postCode.get(i)));
        }

        //userInfo.getDrivingLicence().put(drivingLicence.keySet().toString(),drivingLicence.values().toString()); //girilen tüm degerlerı bir satıra liste olarak kaydeder
        //userInfo.setDrivingLicence(licenceType);

        userInfo.setUserName(userName);
        userInfo.setBuyDate(buyDate);
        userInfo.seteMail(eMail);
        userInfo.setUserContact(userContact);

        return MainDAO.saveOrUpdateObject(userInfo);
    }

    //Kitap listesini getir
    public List<Book> loadBookListService(){
        List<Book> bookList= MainDAO.loadBookList();
        return bookList;
    }

    //User listesini getir
    public List<UserInfo> loadUserListService(){
        List<UserInfo> userList = MainDAO.loadUserList();
        return userList;
    }
}
