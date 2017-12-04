package edu.cs6440.fall2017;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ServiceResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceResult {
    @XmlElement(name = "ResponseStatus")
    private String responseStatus;

    @XmlElement(name = "FirstName", nillable=true)
    private String firstName = "";
    @XmlElement(name = "LastName", nillable=true)
    private String lastName = "";
    @XmlElement(name = "Gender", nillable=true)
    private String gender = "";
    @XmlElement(name = "DateOfBirth", nillable=true)
    private String dateOfBirth = "";
    @XmlElement(name = "MPI_ID", nillable=true)
    private String mpiId = "";
    @XmlElement(name = "Is_Deceased", nillable=true)
    private String isDeceased = "";
    @XmlElement(name = "DateOfDeath", nillable=true)
    private String dateOfDeath = "";


    @XmlElement(name = "StreetNumber")
    private String streetNumber;
    @XmlElement(name = "Address", nillable=true)
    private String streetName = "";
    @XmlElement(name = "ApartmentNumber")
    private String apartmentNumber;
    @XmlElement(name = "City", nillable=true)
    private String city = "";
    @XmlElement(name = "Zip", nillable=true)
    private String zip = "";

    @XmlElement(name = "Phone", nillable=true)
    private String phone = "";

    @XmlElement(name = "AddressLastChangedOn", nillable=true)
    private String lastChangedOn = "";
    @XmlElement(name = "ImmediateCause", nillable=true)
    private String immediateCause = "";
    @XmlElement(name = "AdditionalCause1", nillable=true)
    private String additionalCause1 = "";
    @XmlElement(name = "AdditionalCause2", nillable=true)
    private String additionalCause2 = "";
    @XmlElement(name = "MannerOfDeath", nillable=true)
    private String mannerOfDeath = "";
    @XmlElement(name = "UnderlyingCause", nillable=true)
    private String underlyingCause = "";
    @XmlElement(name = "UnderlyingCode", nillable=true)
    private String underlyingCode = "";
    @XmlElement(name = "ContribCode1", nillable=true)
    private String contribCode1 = "";
    @XmlElement(name = "ContribCode2", nillable=true)
    private String contribCode2 = "";
    @XmlElement(name = "ContribCode3", nillable=true)
    private String contribCode3 = "";
    @XmlElement(name = "ContribCode4", nillable=true)
    private String contribCode4 = "";
    @XmlElement(name = "ContribCode5", nillable=true)
    private String contribCode5 = "";
    @XmlElement(name = "ContribCode6", nillable=true)
    private String contribCode6 = "";
    @XmlElement(name = "ContribCode7", nillable=true)
    private String contribCode7 = "";
    @XmlElement(name = "ContribCode8", nillable=true)
    private String contribCode8 = "";
    @XmlElement(name = "ContribCode9", nillable=true)
    private String contribCode9 = "";

    public ServiceResult() {

    }

    public ServiceResult(String responseStatus){
        this.responseStatus = responseStatus;
    }

    public ServiceResult(
    String responseStatus,
    String firstName,
    String lastName,
    String gender,
    String dateOfBirth,
    String mpiId,
    String isDeceased,
    String dateOfDeath,
    String streetNumber,
    String streetName,
    String apartmentNumber,
    String city,
    String zip,
    String phone,
    String lastChangedOn
    )
    {
        this.responseStatus = responseStatus;

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.mpiId = mpiId;
        this.isDeceased = isDeceased;
        this.dateOfDeath = dateOfDeath;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.lastChangedOn = lastChangedOn;

    }


    public String getResponseStatus(){
        return responseStatus;
    }


    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getGender(){
        return gender;
    }

    public String getDateOfBirth(){
        return dateOfBirth;
    }

    public String getMpiId(){
        return mpiId;
    }


    public String getIsDeceased(){
        return isDeceased;
    }


    public String getDateOfDeath(){
        return dateOfDeath;
    }

    public String getStreetNumber(){
        return streetNumber;
    }

    public String getStreetName(){
        return streetName;
    }

    public String getApartmentNumber(){
        return apartmentNumber;
    }

    public String getCity(){
        return city;
    }

    public String getZip(){
        return zip;
    }

    public String getPhone() {
        return phone;
    }


    public String getLastChangedOn(){
        return lastChangedOn;
    }

    public String getImmediateCause(){
        return immediateCause;
    }

    public String getAdditionalCause1(){
        return additionalCause1;
    }

    public String getAdditionalCause2(){
        return additionalCause2;
    }

    public String getMannerOfDeath(){
        return mannerOfDeath;
    }


    public String getUnderlyingCause(){
        return underlyingCause;
    }

    public String getUnderlyingCode(){
        return underlyingCode;
    }

    public String getContribCode1(){
        return contribCode1;
    }

    public String getContribCode2(){
        return contribCode2;
    }

    public String getContribCode3(){
        return contribCode3;
    }

    public String getContribCode4(){
        return contribCode4;
    }

    public String getContribCode5(){
        return contribCode5;
    }

    public String getContribCode6(){
        return contribCode6;
    }

    public String getContribCode7(){
        return contribCode7;
    }

    public String getContribCode8(){
        return contribCode8;
    }

    public String getContribCode9(){
        return contribCode9;
    }



    public void setResponseStatus(String responseStatus){
        this.responseStatus = responseStatus;
    }

    public void setFirstName(String s){
        this.firstName = s;
    }

    public void setLastName(String s){
        this.lastName = s;
    }

    public void setGender(String s){
        this.gender = s;
    }

    public void setDateOfBirth(String s){
        this.dateOfBirth = s;
    }

    public void setMpiId(String s){
        this.mpiId = s;
    }

    public void setIsDeceased(String s){
        this.isDeceased = s;
    }

    public void setDateOfDeath(String s){
        this.dateOfDeath = s;
    }

    public void setStreetNumber(String s){
        this.streetNumber = s;
    }

    public void setStreetName(String s){
        this.streetName = s;
    }

    public void setApartmentNumber(String s){
        this.apartmentNumber = s;
    }

    public void setCity (String s){
        this.city = s;
    }

    public void setZip (String s){
        this.zip = s;
    }


    public void setPhone (String s){
        this.phone = s;
    }

    public void setLastChangedOn (String s){
        this.lastChangedOn = s;
    }

    public void setImmediateCause (String s){
        this.immediateCause = s;
    }

    public void setAdditionalCause1 (String s){
        this.additionalCause1 = s;
    }

    public void setAdditionalCause2 (String s){
        this.additionalCause2 = s;
    }

    public void setMannerOfDeath (String s){
        this.mannerOfDeath = s;
    }

    public void setUnderlyingCause (String s){
        this.underlyingCause = s;
    }

    public void setUnderlyingCode (String s){
        this.underlyingCode = s;
    }

    public void setContribCode1 (String s){
        this.contribCode1 = s;
    }

    public void setContribCode2 (String s){
        this.contribCode2 = s;
    }

    public void setContribCode3 (String s){
        this.contribCode3 = s;
    }

    public void setContribCode4 (String s){
        this.contribCode4 = s;
    }

    public void setContribCode5 (String s){
        this.contribCode5 = s;
    }

    public void setContribCode6 (String s){
        this.contribCode6 = s;
    }

    public void setContribCode7 (String s){
        this.contribCode7 = s;
    }

    public void setContribCode8 (String s){
        this.contribCode8 = s;
    }

    public void setContribCode9 (String s){
        this.contribCode9 = s;
    }





}
