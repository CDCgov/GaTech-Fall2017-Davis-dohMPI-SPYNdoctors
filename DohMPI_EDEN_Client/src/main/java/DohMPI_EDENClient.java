
//import com.sun.xml.internal.txw2.Document;
import net.miginfocom.swing.MigLayout;
import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.Document;
import xmleditorkit.XMLEditorKit;

import javax.swing.*;
//import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyleConstants;
//import javax.swing.text.StyledDocument;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.Writer;

public class DohMPI_EDENClient extends JFrame implements ActionListener {

    private JButton Close;
    private JButton Submit;
    private JButton SubmitP;
    private JTabbedPane tabbedPane;

    JTextField txtFirstName;
    JTextField txtLastName ;
    JTextField txtDateOfBirth;
    JComboBox cbGender;
    JTextField txtFirstNameP;
    JTextField txtLastNameP;
    JTextField txtDateOfBirthP;
    JComboBox cbGenderP;
    JTextField txtPhone;
    JTextField txtStreetNumber;
    JTextField txtStreetName;
    JTextField txtApartment;
    JTextField txtZip;
    JTextField txtCity;
    JTextField txtBaseURL;
    DocumentBuilder db;


    ClientConfig config = new ClientConfig();




    private Color myColor=new Color(200, 102, 204);


    public DohMPI_EDENClient(String [] args) {
        super("DohMPI-EDEN Client Application");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                System.exit(0);
            }


        });

        try {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.getLookAndFeelDefaults()
                .put("defaultFont", new Font("Arial", Font.BOLD, 14));


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false);
        try {

            db = dbf.newDocumentBuilder();

        } catch(Exception ex){
            ex.printStackTrace();
        }





        JPanel buttonPanel = new JPanel();
        JPanel baseURLPanel = new JPanel();
        JLabel lblBaseURL = new JLabel("Enter service base URL: ");
        txtBaseURL = new JTextField(25);
        baseURLPanel.add(lblBaseURL);
        baseURLPanel.add(txtBaseURL);
        if(args.length > 0){
            txtBaseURL.setText(args[0]);
        }

        Close = new JButton("Close");
        Close.addActionListener(this);

        buttonPanel.add(Close);

        tabbedPane = new JTabbedPane();

        JComponent postPanel = makePostPanel();
        tabbedPane.add("POST", postPanel );
        JComponent getPanel = makeGetPanel();
        tabbedPane.add("GET", getPanel );



        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(baseURLPanel, BorderLayout.NORTH);
        c.add(tabbedPane, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);

        this.setSize(600,300);
        this.setLocation(200,300);

        this.setVisible(true);



    }


    private JComponent makePostPanel() {

        JLabel lblFirstName = new JLabel("First Name: ");
        lblFirstName.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblLastName = new JLabel("Last Name: ");
        lblLastName.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblDateOfBirth = new JLabel("DOB(MM/DD/YYYY): ");
        lblDateOfBirth.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblGender = new JLabel("Gender: ");
        lblGender.setHorizontalAlignment(JLabel.CENTER);
        txtFirstNameP = new JTextField(10);
        txtLastNameP = new JTextField(10);
        txtDateOfBirthP = new JTextField(10);
        cbGenderP = new JComboBox();
        cbGenderP.addItem("Any");
        cbGenderP.addItem("F");
        cbGenderP.addItem("M");
        cbGenderP.addItem("UNK");

        txtPhone = new JTextField(10);
        txtStreetNumber  = new JTextField(10);
        txtStreetName = new JTextField(10);
        txtApartment = new JTextField(10);
        txtZip = new JTextField(10);
        txtCity = new JTextField(10);

        JLabel lblPhone = new JLabel("Phone: ");
        lblPhone.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblStreetNumber= new JLabel("Street Number: ");
        lblStreetNumber.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblStreetName = new JLabel("Street Name: ");
        lblStreetName.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblApartment = new JLabel("Apartment: ");
        lblApartment.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblCity = new JLabel("City: ");
        lblCity.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblZip = new JLabel("Zip: ");
        lblZip.setHorizontalAlignment(JLabel.CENTER);
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        JPanel submitPanel = new JPanel();
        SubmitP = new JButton("Submit");
        SubmitP.addActionListener(this);
        submitPanel.add(SubmitP);


        JPanel myPanel;
        myPanel=new JPanel();
        myPanel.setOpaque(true);


        myPanel.setBackground(myColor);

        myPanel.setLayout(new MigLayout());
        myPanel.add(lblFirstName);
        myPanel.add(txtFirstNameP);
        myPanel.add(lblStreetNumber);
        myPanel.add(txtStreetNumber, "wrap");
        myPanel.add(lblLastName);
        myPanel.add(txtLastNameP);
        myPanel.add(lblStreetName);
        myPanel.add(txtStreetName, "wrap");
        myPanel.add(lblDateOfBirth);
        myPanel.add(txtDateOfBirthP);
        myPanel.add(lblApartment);
        myPanel.add(txtApartment, "wrap");
        myPanel.add(lblGender);
        myPanel.add(cbGenderP);
        myPanel.add(lblCity);
        myPanel.add(txtCity, "wrap");
        myPanel.add(lblPhone);
        myPanel.add(txtPhone);
        myPanel.add(lblZip);
        myPanel.add(txtZip, "wrap");

        basePanel.add(myPanel);
        basePanel.setBackground(myColor);
        basePanel.add(submitPanel, BorderLayout.SOUTH);







        return basePanel;
    }


    private JComponent makeGetPanel() {

        JLabel lblFirstName = new JLabel("First Name: ");
        lblFirstName.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblLastName = new JLabel("Last Name: ");

        lblLastName.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblDateOfBirth = new JLabel("DOB(MM/DD/YYYY): ");
        lblDateOfBirth.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblGender = new JLabel("Gender: ");
        lblGender.setHorizontalAlignment(JLabel.CENTER);
        txtFirstName = new JTextField(10);
        txtLastName = new JTextField(10);
        txtDateOfBirth = new JTextField(10);
        cbGender = new JComboBox();
        cbGender.addItem("F");
        cbGender.addItem("M");
        cbGender.addItem("UNK");
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        JPanel submitPanel = new JPanel();
        Submit = new JButton("Submit");
        Submit.addActionListener(this);
        submitPanel.add(Submit);




        JPanel myPanel;
        myPanel=new JPanel();
        myPanel.setOpaque(true);

        myPanel.setBackground(myColor);

        myPanel.setLayout(new MigLayout("", "", "[]35[]"));
        myPanel.add(lblFirstName);
        myPanel.add(txtFirstName);
        myPanel.add(lblLastName);
        myPanel.add(txtLastName, "wrap");
        myPanel.add(lblDateOfBirth);
        myPanel.add(txtDateOfBirth);
        myPanel.add(lblGender);
        myPanel.add(cbGender, "wrap");
        myPanel.add(submitPanel, "span4, center");

        basePanel.add(myPanel);
        basePanel.setBackground(myColor);




        return basePanel;
    }


    public void actionPerformed(ActionEvent ae){
        if(ae.getSource().equals(Close)){
            System.exit(0);
        }

        if(ae.getSource().equals(SubmitP)){

            String baseURI = txtBaseURL.getText().trim();
            if(baseURI.trim().length() == 0){
                JOptionPane.showMessageDialog(this, " BaseURI must be provided");
                return;

            }

            if(!baseURI.toUpperCase().startsWith("HTTP://")){
                baseURI = "http://" + baseURI;
            }

            Client client = ClientBuilder.newClient(config);
            WebTarget service = client.target(baseURI);
            String firstName = txtFirstNameP.getText();
            String lastName = txtLastNameP.getText();
            String gender = (String) cbGenderP.getSelectedItem();
            String dateOfBirth = txtDateOfBirthP.getText();
            String streetNumber = txtStreetNumber.getText();
            String streetName = txtStreetName.getText();
            String apartment = txtApartment.getText();
            String city = txtCity.getText();
            String zip = txtZip.getText();
            String phone = txtPhone.getText();

            Form form =new Form();
            form.param("first_name", firstName);
            form.param("last_name", lastName);
            form.param("gender", gender);
            form.param("date_of_birth", dateOfBirth);
            form.param("street_number", streetNumber);
            form.param("street_name", streetName);
            form.param("apartment", apartment);
            form.param("city", city);
            form.param("zip", zip);
            form.param("phone", phone);




            Response response = service.path("/match").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED),
                    Response.class);
            System.out.println("Form response " + response.getStatus());
            String xml = response.readEntity(String.class);

            try {
                Document doc = db.parse(new ByteArrayInputStream(xml.getBytes()));

                String myxml = prettyPrint(doc);

               System.out.println( myxml);

                JEditorPane editorPane=new JEditorPane();
                editorPane.setEditorKit(new XMLEditorKit());
                editorPane.setText(myxml);
                JFrame jf = new JFrame();
                Container c = jf.getContentPane();
                c.add( new JScrollPane(editorPane));
                jf.setSize(500,600);
                jf.setVisible(true);
            }catch(Exception ex){
                ex.printStackTrace();
            }




        }

        if(ae.getSource().equals(Submit)){

            String baseURI = txtBaseURL.getText().trim();
            if(baseURI.trim().length() == 0){
                JOptionPane.showMessageDialog(this, " BaseURI must be provided");
                return;

            }
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String dateOfBirth = txtDateOfBirth.getText().trim();
            String gender = (String)cbGender.getSelectedItem();
            String dob = "";
            for(int j=0; j< dateOfBirth.length(); j++){
                if(dateOfBirth.charAt(j) == '/')continue;
                else dob += dateOfBirth.charAt(j);
            }
            if(firstName.length() == 0 || lastName.length() == 0 || dob.length() == 0 ){
                JOptionPane.showMessageDialog(this, " All fields are required for GET");
                return;

            }

            if(!baseURI.toUpperCase().startsWith("HTTP://")){
                baseURI = "http://" + baseURI;
            }

            Client client = ClientBuilder.newClient(config);
            WebTarget service = client.target(baseURI);

            WebTarget webTarget = client.target(baseURI).path("/match").path("/first_name")
                    .path("/" + firstName).path("/last_name").path("/" + lastName).path("/gender")
                    .path("/" + gender).path("/date_of_birth").path("/" + dob);
            Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_XML);
            Response response = invocationBuilder.get();

            String xml = response.readEntity(String.class);

            try {
                Document doc = db.parse(new ByteArrayInputStream(xml.getBytes()));

                String myxml = prettyPrint(doc);

                System.out.println( myxml);

                 JEditorPane editorPane=new JEditorPane();

                editorPane.setEditorKit(new XMLEditorKit());

                editorPane.setText(myxml);

                JFrame jf = new JFrame();
                Container c = jf.getContentPane();
                c.add( new JScrollPane(editorPane));
                jf.setSize(500,600);
                jf.setVisible(true);
            }catch(Exception ex){
                ex.printStackTrace();
            }





        }

    }

    public static final String prettyPrint(Document xml) throws Exception {

        Transformer tf = TransformerFactory.newInstance().newTransformer();

        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        tf.setOutputProperty(OutputKeys.INDENT, "yes");

        Writer out = new StringWriter();

        tf.transform(new DOMSource(xml), new StreamResult(out));

   
        return out.toString();

    }




    public static void main(String[] args) {
        new DohMPI_EDENClient(args);
    }

}
