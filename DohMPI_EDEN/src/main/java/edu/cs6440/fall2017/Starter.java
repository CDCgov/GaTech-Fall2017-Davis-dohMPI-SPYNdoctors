package edu.cs6440.fall2017;


import org.apache.tomcat.dbcp.dbcp2.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.io.*;
import java.sql.*;

import java.util.Properties;

/**
 * Created on 10/12/2017.
 */

@WebListener
public class Starter implements ServletContextListener{

    private Connection conn = null;
    private BasicDataSource dsDohMPI = null;
    private BasicDataSource dsEDEN = null;
    private BasicDataSource dsUnmatched = null;
    private Connection conn1 = null;
    private Connection conn3 = null;

     public void contextDestroyed(ServletContextEvent e){
        try {
          if(conn != null) conn.close();
          if(conn1 != null)  conn1.close();
            if (conn3 != null) conn3.close();


        } catch(Exception ex1){
            ex1.printStackTrace();
        }

    }

    public void contextInitialized(ServletContextEvent e){

        System.out.println(" context initializing");
        ServletContext context = e.getServletContext();

        try {


             System.out.println("Strating version with db connections persisitent and db pooling");
            String propFilePath = System.getProperty("MatcherPropertiesFilePath");
            Properties matcherProps = new Properties();
            matcherProps.load(new FileInputStream(propFilePath));

            String sepLine = System.getProperty("line.separator");


            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("/eden.sql");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String sqlCreateString = "";
            String sqlInsertString = "insert into edenmaster values (";

            String buff = null;
            int count = 0;
            while((buff=in.readLine()) != null){
                sqlCreateString += buff.trim() + sepLine;
                if(buff.trim().startsWith("`")){
                    sqlInsertString += "?,";
                    count++;

                }
            }
            in.close();

            sqlInsertString = sqlInsertString.substring(0, sqlInsertString.lastIndexOf("?")+1);
            sqlInsertString += ");";



            Class.forName("org.postgresql.Driver");
            java.util.Properties props = new java.util.Properties();


            props.setProperty("user", matcherProps.getProperty("dohMPIuser"));
            props.setProperty("password", matcherProps.getProperty("dohMPIpassword"));



            props.setProperty("prepareThreshold", "0");

               String url = "jdbc:postgresql://" + matcherProps.getProperty("dohMPIhost")
                    +":" + matcherProps.getProperty("dohMPIport") + "/" +
                    matcherProps.getProperty("dohMPIdatabase");


            int countsec = 0;
            System.out.println("getting there postgres");
            while(countsec < 3000) {
                boolean connected = true;
                try {

                    conn = DriverManager.getConnection(url, props);
                } catch(Exception ex){
                    System.out.println("no connection postgres. sec: " + countsec);
                    connected = false;
                }
                if(connected) break;
               Thread.currentThread().sleep(1000);
               countsec++;
            }

            conn.close();



            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("org.postgresql.Driver");

            ds.setUrl(url);

            ds.setUsername(matcherProps.getProperty("dohMPIuser"));

            ds.setPassword(matcherProps.getProperty("dohMPIpassword"));





            ds.setMinIdle(5);

            ds.setMaxIdle(10);

            ds.setMaxOpenPreparedStatements(100);


            dsDohMPI = ds;




            conn = dsDohMPI.getConnection();
            context.setAttribute("DohMPIDataSource", dsDohMPI);

            Statement stmt = conn.createStatement();


            PreparedStatement pstmt = null;


            Class.forName("com.mysql.jdbc.Driver").newInstance();


            String url1 = "jdbc:mysql://" + matcherProps.getProperty("EDENhost") +
                    ":" + matcherProps.getProperty("EDENport") + "/"  + matcherProps.getProperty("EDENdatabase");




            countsec = 0;
            System.out.println("getting there mysql");
            while(countsec < 3000) {
                boolean connected = true;
                try {


            conn1 = DriverManager.getConnection(url1, matcherProps.getProperty("EDENuser"),
                    matcherProps.getProperty("EDENpassword"));
                } catch(Exception ex){
                    System.out.println("no connection mysql. sec: " + countsec);
                    connected = false;
                }
                if(connected) break;
                Thread.currentThread().sleep(1000);
                countsec++;
            }

           conn1.close();

            BasicDataSource ds1 = new BasicDataSource();
            ds1.setDriverClassName("com.mysql.jdbc.Driver");

            ds1.setUrl(url1);

            ds1.setUsername(matcherProps.getProperty("EDENuser"));

            ds1.setPassword(matcherProps.getProperty("EDENpassword"));





            ds1.setMinIdle(5);

            ds1.setMaxIdle(10);

            ds1.setMaxOpenPreparedStatements(100);


            dsEDEN = ds1;



            context.setAttribute("EDENDataSource", dsEDEN);





            java.util.Properties props3 = new java.util.Properties();


             props3.setProperty("user", matcherProps.getProperty("dohUnmatchedUser"));
            props3.setProperty("password", matcherProps.getProperty("dohUnmatchedPassword"));

            props3.setProperty("prepareThreshold", "0");

            String url3 = "jdbc:postgresql://" + matcherProps.getProperty("dohUnmatchedHost")
                    +":" + matcherProps.getProperty("dohUnmatchedPort") + "/" +
                    matcherProps.getProperty("dohUnmatchedDatabase");


            countsec = 0;
            System.out.println("getting there postgres");
            while(countsec < 3000) {
                boolean connected = true;
                try {

                    conn3 = DriverManager.getConnection(url3, props);
                } catch(Exception ex){
                    System.out.println("no connection postgres. sec: " + countsec);
                    connected = false;
                }
                if(connected) break;
                Thread.currentThread().sleep(1000);
                countsec++;
            }



            conn3.close();

            BasicDataSource ds3 = new BasicDataSource();
            ds3.setDriverClassName("org.postgresql.Driver");

            ds3.setUrl(url3);

            ds3.setUsername(matcherProps.getProperty("dohUnmatchedUser"));

            ds3.setPassword(matcherProps.getProperty("dohUnmatchedPassword"));





            ds3.setMinIdle(5);

            ds3.setMaxIdle(10);

            ds3.setMaxOpenPreparedStatements(100);


            dsUnmatched = ds3;



            context.setAttribute("UnmatchedDataSource", dsUnmatched);







        }catch(Exception ex){
            ex.printStackTrace();
        }

    }


}
