package com.uygulama.model;

import com.uygulama.model.util.LicenceType;

import javax.persistence.*;

import java.util.*;

// Embeddable : 2 ayrı class, 1 tablo
@Entity
@Table(name = "userinfo")
public class UserInfo {

    /*@GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue(strategy = GenerationType.AUTO) //Otomatik artacak
    @Column(name = "user_id")
    private Integer userId;
    The @GeneratedValue only works for identifiers and so you can't use it. If you use MySQL, you are quite limited, since database sequences are not supported.*/

    @Id//pk
    @Column(name = "phone_number", length = 11, nullable = false)
    private Integer phoneNumber;

    @Column(name = "user_name")
    private String userName;

    @Temporal(TemporalType.TIMESTAMP) //timestamp tipinde satın alma tarıhı
    @Column(name = "buy_date")
    private Date buyDate;

    @Embedded //Classın buraya gömulmus oldugu belırtıyoruz
    //@AttributeOverride : Gömülü classtaki bir columnun ozellıklerını eziyoruz
    //@AttributeOverrides : Bircok columnu ezmek ıstedıgımızde kullanıyoruz
    //@AttributeOverride(name = "eMail", column = @Column(name = "phone_number", unique = true, length = 11, nullable = false))
    @AttributeOverrides({
            @AttributeOverride(name = "link", column = @Column(name = "link")),
            @AttributeOverride(name = "city", column = @Column(name = "city"))
    })
    private UserContact userContact; //UserInfo'nun ıcınde artık UserContact'e ait bilgiler de var

    //FetchType : Tablodaki veriler nasıl okunacak. Diğer tablodaki verinin gelip gelmeyecegını belırtırız. Performans acısından onemlı
    //FetchType.LAZY : Diğer tablodaki ilişkili verılerı cekmez
    //FetchType.EAGER : Diğer tablodaki iliskılı verılerı de ceker

    @ElementCollection //user birden cok e-mail adresine sahip olabilir. Liste döndurur.
    // Veritabanında bir tablo daha olusur. Bu bılgıyı tutar. Foreign key ile user tablosuna baglıdır
    @CollectionTable(name = "user_eMail", joinColumns = @JoinColumn(name = "fk_userPhone"))
    //Yeni olusacak olan tablonun ismini ve ForeignKey olan column adını degıstırdık
    @Column(name = "e_mail", nullable = false)
    //Column adı belırtıldı email ıcın
    private List<String> eMail;

    //Birden fazla adrese sahipse
    //Adreste sadece tek tip veri donmeyecek. Birden fazla farklı column donecegı ıcın eMail'den farklı olacak
    //Address icin embeddable bir class olusturduk
    @ElementCollection //User bircok adrese sahip olabılır diye
    @CollectionTable(name = "user_address", joinColumns = @JoinColumn(name = "fk_userPhone")) //Yeni olusacak tablodaki fk
    @Embedded //Embeddable class'ı gömduk buraya
    //Embeddable class'a column ozellıklerı atadık(ıstege baglı)
    @AttributeOverrides({
            @AttributeOverride(name = "district", column = @Column(name = "district")),
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "buildingNumber", column = @Column(name = "building_number")),
            @AttributeOverride(name = "postCode", column = @Column(name = "post_code"))
    })
    private List<UserAddress> addresses=new ArrayList<UserAddress>(); //NullPointerException yememek ıcın ArrayList olusturuyoruz

    //Bu map tipinde key'leri de elle gırıyoruz MapStringString
    /*@ElementCollection //User bırden cok surucu belgesıe sahip olabılır. Bunun ıcın verıtabanında otomatık olarak bır tablo olusacak, foreign key ile baglı olacak.
    @CollectionTable(name = "user_drivingLicence", joinColumns = @JoinColumn(name = "fk_userPhone")) // Yeni olusan tablonun adını ve fk column'unun adını degıstırdık
    @MapKeyColumn(name = "mapKey") //key column
    @Column(name = "mapValue") //value column
    //Map : Verileri veritabanında key-value seklınde tutar. İkisi de String turunde olacak.
    //Ev-is-cep tel nosunu da eklerken bu yontem kullanılabılır
    private Map<String,String> drivingLicence = new HashMap<String,String>();*/

    //Bu map tipince keyler enumdan alınacak MapEnumString
    @ElementCollection
    @CollectionTable(name = "user_drivingLicence", joinColumns = @JoinColumn(name = "fk_userPhone"))
    @MapKeyColumn(name = "mapKey")
    @MapKeyEnumerated(EnumType.STRING) //Default olarak EnumType.ORDINAL, enum ları veritabanında indexleriyle tutuyor (0,1,2). EnumType.STRING ise isimleriyle tutuyor
    @Column(name = "mapValue")
    private Map<LicenceType,String> drivingLicence = new HashMap<LicenceType, String>();

    public UserInfo() {
    }

    public UserInfo(Integer phoneNumber, String userName, Date buyDate, UserContact userContact, List<String> eMail, List<UserAddress> addresses) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.buyDate = buyDate;
        this.userContact = userContact;
        this.eMail = eMail;
        this.addresses = addresses;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public UserContact getUserContact() {
        return userContact;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }

    public List<String> geteMail() {
        return eMail;
    }

    public void seteMail(List<String> eMail) {
        this.eMail = eMail;
    }

    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddress> addresses) {
        this.addresses = addresses;
    }

    /*public Map<String, String> getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(Map<String, String> drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    //setter yerine metot olusturarak map tipi ekleme
    public void addDrivingLicence(String key, String value){
        this.drivingLicence.put(key,value);
    }*/

    public Map<LicenceType, String> getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(Map<LicenceType, String> drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    //Setter yerine metot kullanılabılır
    public void addDrivingLicence(LicenceType key, String value){
        this.drivingLicence.put(key, value);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "phoneNumber=" + phoneNumber +
                ", userName='" + userName + '\'' +
                ", buyDate=" + buyDate +
                ", userContact=" + userContact +
                ", eMail=" + eMail +
                ", addresses=" + addresses +
                '}';
    }
}
