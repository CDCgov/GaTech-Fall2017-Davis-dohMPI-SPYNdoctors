package edu.cs6440.fall2017;


import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;


import javax.servlet.ServletContext;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;




/**
 * Created by  on 10/10/2017.
 */

@Path("/match")

public class UserResource {



    static SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/yyyy");


    @Context
    private ServletContext context;


    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/first_name/{first_name}/last_name/{last_name}/gender/{gender}/date_of_birth/{date_of_birth}")
    public ServiceResult getResult(@PathParam("first_name") String firstName,
                             @PathParam("last_name") String lastName,
                             @PathParam("gender") String gender,
                             @PathParam("date_of_birth") String dateOfBirth)

     {
         String dob = dateOfBirth.substring(0,2) + "/" + dateOfBirth.substring(2,4) + "/" + dateOfBirth.substring(4);
         System.out.println("dob: " + dob );
         return generateResult(firstName, lastName, gender, dob, "", "",
                 "", "", "", "");

    }






    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ServiceResult getResult(@FormParam("first_name") String firstName,
                              @FormParam("last_name") String lastName,
                              @FormParam("gender") String gender,
                                   @FormParam("date_of_birth") String dateOfBirth,
                                   @FormParam("street_number") String streetNumber ,
                                   @FormParam("street_name") String streetName,
                                   @FormParam("apartment") String apartment,
                                   @FormParam("city") String city,
                                   @FormParam("zip") String zip,
                                   @FormParam("phone") String phone
    ) {

        System.out.println("I am in match");
        return generateResult(firstName, lastName, gender, dateOfBirth, streetNumber, streetName,
                apartment, city, zip, phone);


    }


    private ServiceResult generateResult(String firstName,
                                         String lastName,
                                         String gender,
                                         String dateOfBirth,
                                         String streetNumber,
                                         String streetName,
                                         String apartment,
                                         String city,
                                         String zip,
                                         String phone){


        try {


            boolean genderProvided = true;
            String result = "";
            ServiceResult sr = new ServiceResult();
            if (firstName.trim().length() == 0) result += "First Name not provided.";
            if (lastName.trim().length() == 0) result += "Last Name not provided.";
            if (!gender.trim().toUpperCase().equals("M") && !gender.trim().toUpperCase().equals("F")
                    && !gender.trim().toUpperCase().equals("UNK")) {

                genderProvided = false;
            }
            System.out.println("before dob");
            dateOfBirth = dateOfBirth.trim();
            boolean goodDOB = true;
            if(dateOfBirth.length() != 10 || dateOfBirth.charAt(2) != '/' ||  dateOfBirth.charAt(5) != '/' ) goodDOB = false;
            try {
                java.util.Date dd = sdf.parse(dateOfBirth);
            } catch (Exception ex) {
                goodDOB = false;
            }
            if (!goodDOB) {
                result += "Date of birth format is invalid." ;
            }


            if(!genderProvided && (streetNumber.trim().length() == 0 || streetName.trim().length() == 0)) {
                result += "Either gender or both street number and street name are required";

            }


            System.out.println("before zip");
            zip = zip.trim();
            boolean goodZip = true;
            if(zip.length() > 0) {
                if (zip.length() != 5) goodZip = false;
                try {
                    int jzip = Integer.parseInt(zip);
                } catch (Exception ex) {
                    goodZip = false;
                }
                if (!goodZip) {
                    result += "Zip format is invalid.";
                }
            }

            System.out.println("before phone");
            boolean goodPhone = true;
            phone = phone.trim();
            if (phone.length() > 0) {
                phone = phone.replaceAll("\\s+", "");

                if (phone.length() != 10) {
                    goodPhone = false;
                }
                try {
                    System.out.println("phone before parseLong: " + phone);
                    long lphone = Long.parseLong(phone);
                } catch (Exception ex) {
                    System.out.println("long not parsed " + ex.toString());
                    goodPhone = false;
                }

            }
            if (!goodPhone) {
                result += "Phone format is invalid.";

            }
            System.out.println("first name: " + firstName);
            System.out.println("last name: " + lastName);
            System.out.println("phone: " + phone);

            if (result.trim().length() > 0) {

                System.out.println("result: " + result);

                sr.setResponseStatus("Incorrect input. " + result);

                return sr;
            }


            BasicDataSource dsDohMPI = (BasicDataSource) context.getAttribute("DohMPIDataSource");




            Connection conn = dsDohMPI.getConnection();



            String queryMPI = "select if.id, d.identity_id, if.identifier_type, if.datasource_id, " +
                    "if.identifier_value, id.mpi_id, d.is_deceased, d.deceased_date, d.gender from demographics d, name fn, name ln, identity id, " +
                    "identifier if where fn.name_type = 'F' and upper(fn.name_word) = upper(?) and ln.name_type = 'L' " +
                    "and upper(ln.name_word) = upper(?) and d.gender like ? and d.birth_date = ? and " +
                    "d.identity_id = fn.identity_id and d.identity_id = ln.identity_id " +
                    "and d.identity_id = id.identity_id and d.identity_id = if.identity_id";


            PreparedStatement pstmt = conn.prepareStatement(queryMPI);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            if(!genderProvided){
                pstmt.setString(3, "%");
            } else pstmt.setString(3, gender);
            pstmt.setString(4, dateOfBirth);

            ResultSet rs = pstmt.executeQuery();

            ArrayList<Integer> identity_ids = new ArrayList<Integer>();

            HashMap<Integer,Integer> mpi_ids = new HashMap<Integer, Integer>();
            HashMap<Integer,String> genders = new HashMap<Integer, String>();
            HashMap<Integer,Boolean> is_deceaseds = new HashMap<Integer, Boolean>();
            HashMap<Integer, String> deceased_dates = new HashMap<Integer, String>();
            HashMap<Integer, String> eden_identities = new HashMap<Integer, String>();


            while(rs.next()){
                int id = rs.getInt(1);

                int identity_id = rs.getInt(2);
                if(!identity_ids.contains(identity_id))identity_ids.add(identity_id);

                String identifier_type = rs.getString(3);
                int datasource_id = rs.getInt(4);
                String identifier_value = rs.getString(5);

                mpi_ids.put(identity_id, rs.getInt(6));
                boolean is_deceased = false;
                boolean dbval = rs.getBoolean(7);
                if(!rs.wasNull()){
                    is_deceased = dbval;
                }
                is_deceaseds.put(identity_id, is_deceased);
                String deceased_date = rs.getString(8);
                String genderFound = rs.getString(9);
                deceased_dates.put(identity_id,deceased_date);
                genders.put(identity_id, genderFound);
                System.out.println("identifier_type = " + identifier_type);
                System.out.println("identity_id = " + identity_id);
                System.out.println("identifiler_value = " +identifier_value);


                if(identifier_type.equals("DSFN") && datasource_id == 6)eden_identities.put(identity_id, identifier_value);



            }

            HashMap<Integer, String> street1s = new HashMap<Integer, String>();
            HashMap<Integer, String> street2s = new HashMap<Integer, String>();

            HashMap<Integer, String> cities = new HashMap<Integer, String>();
            HashMap<Integer, String> zips = new HashMap<Integer, String>();
            HashMap<Integer, String> phones = new HashMap<Integer, String>();
            HashMap<Integer, String> last_changes = new HashMap<Integer, String>();

            Statement stmt = conn.createStatement();

            for(int identity_id : identity_ids){
                rs = stmt.executeQuery("select street1, street2, city, zip5, last_changed_on" +
                        " from address where identity_id = " + identity_id);

                while(rs.next()){
                    street1s.put(identity_id, rs.getString(1));
                    street2s.put(identity_id, rs.getString(2));
                    cities.put(identity_id, rs.getString(3));
                    zips.put(identity_id, rs.getString(4));
                    last_changes.put(identity_id, rs.getString(5));


                }
                rs = stmt.executeQuery("select phone_number from phone where identity_id = " + identity_id);
                while(rs.next()){
                    phones.put(identity_id, rs.getString(1));
                }



            }




            pstmt.close();


            conn.close();





            boolean addressProvided = false;

            System.out.println("identities before addresses: " + identity_ids);

            if(streetName.trim().length() > 0 || streetNumber.trim().length() > 0 || apartment.trim().length() > 0 || zip.trim().length() >0
                    || city.trim().length() > 0){

                addressProvided = true;
                ArrayList<Integer> new_identity_ids = new ArrayList<Integer>();
                for(int identity_id: identity_ids){
                    String combAddress = "";
                    if(street1s.get(identity_id) != null)combAddress += street1s.get(identity_id);
                    if(street2s.get(identity_id) != null)combAddress += street2s.get(identity_id);
                    combAddress = combAddress.toUpperCase();
                    boolean goodRec = true;
                    if(streetName.trim().length() > 0
                            && !combAddress.contains(streetName.toUpperCase().trim()))goodRec = false;
                    if(streetNumber.trim().length() > 0
                            && !combAddress.contains(streetNumber.toUpperCase().trim()))goodRec = false;
                    if(apartment.trim().length() > 0
                            && !combAddress.contains(apartment.toUpperCase().trim()))goodRec = false;
                    if(zip.trim().length() >0 && zips.get(identity_id) != null && !zips.get(identity_id).equals(zip.trim()))goodRec = false;
                    if(city.trim().length() >0 && cities.get(identity_id) != null && !cities.get(identity_id).equalsIgnoreCase(city.trim()))goodRec = false;

                    if(goodRec)new_identity_ids.add(identity_id);


                }

                identity_ids = new_identity_ids;




            }

            System.out.println("identities before phones: " + identity_ids);

            if(identity_ids.size() > 1 && phone.trim().length() > 0){
                ArrayList<Integer> new_identity_ids = new ArrayList<Integer>();
                for(int identity_id: identity_ids){
                    boolean goodRec = false;
                    if(phones.get(identity_id) != null && phones.get(identity_id).equalsIgnoreCase(phone.trim()))goodRec = true;
                    if(goodRec)new_identity_ids.add(identity_id);

                }

                identity_ids = new_identity_ids;


            }

            int numrec = identity_ids.size();
            if(numrec != 1){




                 BasicDataSource dsUnmatched = (BasicDataSource) context.getAttribute("UnmatchedDataSource");


                Connection conn3 = dsUnmatched.getConnection();




                String insert3 = "insert into unmatched_requests (first_name, last_name," +
                        "gender, birth_date, street_number, street_name, apartment, city, zip, phone," +
                        "match_outcome) values (?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement pstmt3 = conn3.prepareStatement(insert3);

                pstmt3.setString(1, firstName);
                pstmt3.setString(2, lastName);
                if(genderProvided) pstmt3.setString(3, gender);
                else pstmt3.setNull(3,Types.VARCHAR);
                pstmt3.setString(4, dateOfBirth);
                if(streetNumber.trim().length() > 0){
                    pstmt3.setString(5, streetNumber);
                } else {
                    pstmt3.setNull(5, Types.VARCHAR);
                }
                if(streetName.trim().length() > 0){
                    pstmt3.setString(6, streetName);
                } else {
                    pstmt3.setNull(6, Types.VARCHAR);
                }
                if(apartment.trim().length() > 0){
                    pstmt3.setString(7, apartment);
                } else {
                    pstmt3.setNull(7, Types.VARCHAR);
                }
                if(city.trim().length() > 0){
                    pstmt3.setString(8, city);
                } else {
                    pstmt3.setNull(8, Types.VARCHAR);
                }
                if(zip.trim().length() > 0){
                    pstmt3.setString(9, zip);
                } else {
                    pstmt3.setNull(9, Types.VARCHAR);
                }
                if(phone.trim().length() > 0){
                    pstmt3.setString(10, phone);
                } else {
                    pstmt3.setNull(10, Types.VARCHAR);
                }
                if(numrec == 0)pstmt3.setString(11, "No match");
                else pstmt3.setString(11, "Multiple match");

                int numInserted = pstmt3.executeUpdate();
                if(numInserted != 1){
                    System.out.println("Inserted " + numInserted + " records");
                }

                pstmt3.close();
                conn3.close();


            }


            if(identity_ids.size() == 0){

                System.out.println("result: " + result);

                sr.setResponseStatus("DohMPI: No match.");

                return sr;


            }
            System.out.println("identities before mult check: " + identity_ids);

            if(identity_ids.size() > 1){

                System.out.println("result: " + result);

                if(addressProvided) sr.setResponseStatus("DohMPI: Multiple match.");
                else sr.setResponseStatus("DohMPI: Multiple match. Please, try to narrow down by adding address or at least some part of it.");

                return sr;


            }



            sr.setFirstName(firstName);
            sr.setLastName(lastName);
            sr.setDateOfBirth(dateOfBirth);





                int identity_id = identity_ids.get(0);

                if(is_deceaseds.get(identity_id) != null){
                    if(is_deceaseds.get(identity_id))sr.setIsDeceased("true");
                }
                if(deceased_dates.get(identity_id) != null){
                    sr.setDateOfDeath(deceased_dates.get(identity_id));

                }
                if(genders.get(identity_id) != null){
                    sr.setGender(genders.get(identity_id));
                } else sr.setGender("");

                String combAddress = "";
                if(street1s.get(identity_id) != null)combAddress += street1s.get(identity_id);
                if(street2s.get(identity_id) != null)combAddress += street2s.get(identity_id);


                sr.setStreetName(combAddress);
                if(phones.get(identity_id) != null)sr.setPhone(phones.get(identity_id));
                if(cities.get(identity_id) != null)sr.setCity(cities.get(identity_id));
                if(zips.get(identity_id) != null)sr.setZip(zips.get(identity_id));
                if(last_changes.get(identity_id) != null)sr.setLastChangedOn(last_changes.get(identity_id));
                if(mpi_ids.get(identity_id) != null)sr.setMpiId(mpi_ids.get(identity_id) + "");
                if(eden_identities.get(identity_id) == null){
                    sr.setResponseStatus("DohMPI: Match successful. No EDEN StateFileNumber");
                    return sr;

                } else

               {
                String stateFileNumber = eden_identities.get(identity_id);





                   BasicDataSource dsEDEN = (BasicDataSource) context.getAttribute("EDENDataSource");



                   Connection conn1 = dsEDEN.getConnection();



                Statement stmt1 = conn1.createStatement();
                String query1 = "select ImmediateCause, AdditionalCause1, AdditionalCause2, MannerOfDeath, UnderlyingCause," +
                        "UnderlyingCode, ContribCode1, ContribCode2, ContribCode3, ContribCode4, ContribCode5, ContribCode6, ContribCode7, " +
                        "ContribCode8, ContribCode9 from edenmaster where StateFileNumber = '" + stateFileNumber + "';";
                System.out.println(query1);
                ResultSet rs1 = stmt1.executeQuery(query1);
                boolean found = false;

                while(rs1.next()){
                    found = true;
                    sr.setImmediateCause(rs1.getString(1));
                    sr.setAdditionalCause1(rs1.getString(2));
                    sr.setAdditionalCause2(rs1.getString(3));
                    sr.setMannerOfDeath(rs1.getString(4));
                    sr.setUnderlyingCause(rs1.getString(5));
                    sr.setUnderlyingCode(rs1.getString(6));
                    sr.setContribCode1(rs1.getString(7));
                    sr.setContribCode2(rs1.getString(8));
                    sr.setContribCode3(rs1.getString(9));
                    sr.setContribCode4(rs1.getString(10));
                    sr.setContribCode5(rs1.getString(11));
                    sr.setContribCode6(rs1.getString(12));
                    sr.setContribCode7(rs1.getString(13));
                    sr.setContribCode8(rs1.getString(14));
                    sr.setContribCode9(rs1.getString(15));





                }

                stmt1.close();

                conn1.close();



                if (found)  sr.setResponseStatus("DohMPI: Match successful. EDEN: Match successful.");
                else sr.setResponseStatus("DohMPI: Match successful. EDEN: No match");




            }

            return sr;



        }catch(Exception myex){
            System.out.println("i'm here in myex: " + myex.toString());

            myex.printStackTrace();
        }





        return new ServiceResult("");
    }





}
