package com.uygulama.model;

import javax.persistence.Embeddable;

//Bu bilgiler UserInfo class'ına gömulu halde gelecek
//Burası bir @Entity degıl veya bir @Id'si yok
@Embeddable //Bu tablonun gömulebılır olması ıcın gereklı olan anotation
public class UserContact {

    private String link;

    private String city;

    public UserContact() {
    }

    public UserContact(String link, String city) {
        this.link = link;
        this.city = city;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
