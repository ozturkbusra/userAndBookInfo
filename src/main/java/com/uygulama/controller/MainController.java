package com.uygulama.controller;

import com.uygulama.model.Book;
import com.uygulama.model.UserInfo;
import com.uygulama.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller //Controller sınıfı oldugunu belırtıyoruz
//@RequestMapping(value = "/*", produces = "application/json;charset=utf-8") //Aynı anlama geliyor alttakiyle
@RequestMapping(value = "/*", produces = MediaType.APPLICATION_JSON_VALUE + ";charset = utf-8")
//value ile istek ust yolu belırtıldı. tum ısteklerı karsılayacak
//produces : Metod produces değerine göre Content-Type döndürür. ajax request işlemlerinde kullanılan application/json Content-Type header bilgisi döndürebiliriz.
//application/json : cıktıyı belırttıgımız sekılde verdı
//charset=utf-8 : Türkçe karakterleri göruntulemek ıcın
public class MainController {

    //Service sınıfı ıle bagımlılık saglandı
    @Autowired
    private MainService mainService;

    //Data'yı getir. Kitap numarası ile
    @GetMapping(value = "/getBook.ajax") //Istegın yolu belırtıldı
    //@ResponseBody annotasyonu ile String, application/json veya application/xml türü gibi birden çok türden değerler döndürebiliriz.
    public @ResponseBody String getBookController(@RequestParam Integer bookNo){

        Book book = mainService.getirService(bookNo); //Sınıftan nesne olusturup service metodundan donen degerı ıcıne atadık
        StringBuilder stringBuilder= new StringBuilder(); //String yapıcı nesne olusturduk
        stringBuilder.append(book); //sadece nesne adını yazdıgımız ıcın Book classındakı toString metodunu calıstıracak. Tüm degerler donecek

        return stringBuilder.toString();
    }

    //Data'yı sil. Kitap numarası ile
    @PostMapping(value = "/deleteBook.ajax")
    public @ResponseBody String deleteBookController(@RequestParam Integer bookNo){

        Boolean success = mainService.deleteService(bookNo);

        return success.toString();
    }

    //Book Data ekleme
    @PostMapping(value = "saveOrUpdateBook.ajax")
    public @ResponseBody String saveOrUpdateBookController(@RequestParam int bookNoUnique, @RequestParam String name, @RequestParam int pageCount, @RequestParam String topic, @RequestParam String authorName, @RequestParam(required = false) Date publishDate ){

        Boolean success = mainService.saveOrUpdateServiceBook(bookNoUnique,name,pageCount,topic, authorName, publishDate);

        return success.toString();
    }

    //Kitap Listesini getir
    @GetMapping(value = "/getBookList.ajax")
    public @ResponseBody String getBookListController(){

        List<Book> bookList = mainService.loadBookListService();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Kitap Listesi\n");
        for (Book book : bookList){
            stringBuilder.append(book);
        }
        return stringBuilder.toString();
    }

    //User Date ekleme
    @PostMapping(value = "/saveOrUpdateUser.ajax")
    public @ResponseBody String saveOrUpdateUserController(
            @RequestParam String userName, @RequestParam Integer phoneNumber, @RequestParam List<String> eMail, @RequestParam String link, @RequestParam String city, @RequestParam List<String> district, @RequestParam List<String> street, @RequestParam List<Integer> buildingNumber, @RequestParam List<Integer> postCode){

        Boolean success = mainService.saveOrUpdateServiceUser(userName,new java.util.Date(),phoneNumber,link,city,eMail, district, street, buildingNumber, postCode); //Suanın tarih ve saatini yolladık date'e

        return success.toString();
    }

    //User Listesini getir
    @GetMapping(value = "/loadUser.ajax")
    public @ResponseBody String getUserListController(){
        List<UserInfo> userList = mainService.loadUserListService();
        StringBuilder stringBuilder = new StringBuilder();
        for (UserInfo user : userList) {
            stringBuilder.append(user); // nesneyı dırekt yazınca toString metodu doner
            //stringBuilder.append("User Information\n"+"\tUsername : "+user.getUserName()); //Bu sekılde tek tek de yazılabılır
        }
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    //View JSP
    @RequestMapping("/") //Istegın yolu
    public String hiView(ModelMap modelMap){
        modelMap.put("name","Büşra"); //Jsp mızdekı name'e deger atadık
        return "first"; //Jsp dosyasının adını yazıyoruz gerı donus olarak
    }
}
