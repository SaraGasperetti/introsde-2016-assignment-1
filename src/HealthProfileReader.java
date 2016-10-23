
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HealthProfileReader {

    private Document doc;
    private XPath xpath;

    public static void main(String[] args) throws XPathExpressionException {
        HealthProfileReader reader = new HealthProfileReader();
        reader.loadXMLAndXPathFactory();

        int argCount = args.length;
        String op = args[0];

        switch (op) { //decide which operation
            case "printPeople":
                if (argCount == 1) {
                    reader.printPeople();
                } else {
                    System.out.println("Wrong number of parameters");
                }
                break;
            case "showHealthProfile":
                if (argCount == 2) {
                    String id = args[1];
                    System.out.print(reader.getFirstname(id) + " " + reader.getLastname(id) + " --> ");
                    System.out.println(reader.getHProfile(id));
                } else {
                    System.out.println("Wrong number of parameters");
                }
                break;
            case "compareWeight":
                if (argCount == 3) {
                    double weight = Double.parseDouble(args[1]);
                    String compareType = args[2]; //the operator for the comparison
                    reader.compareWeight(weight, compareType);
                } else {
                    System.out.println("Wrong number of parameters");
                }
                break;
            default:
                System.out.println("Unrecognized action");
        }
    }

    /**
     * Load the xml file of the people and get the reference to the xpath
     * factory
     */
    private void loadXMLAndXPathFactory() {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            doc = builder.parse("people.xml");

            // creating xpath object
            XPathFactory factory = XPathFactory.newInstance();
            xpath = factory.newXPath();

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(HealthProfileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Print all the people in the database (the xml file)
     *
     * @throws XPathExpressionException
     */
    private void printPeople() throws XPathExpressionException {
        XPathExpression expr = xpath.compile("//@id");
        NodeList people = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < people.getLength(); i++) {
            System.out.println("Person " + (i + 1) + ": " + this.getFirstname(people.item(i).getTextContent()) + " "
                    + this.getLastname(people.item(i).getTextContent()) + " was born on "
                    + this.getBirthdate(people.item(i).getTextContent()) + ". "
                    + this.getHProfile(people.item(i).getTextContent()));

        }
    }

    /**
     * Get information about a single person
     *
     * @param id id of the person
     * @param element tag name of the person info starting from per person tag
     * @return the string containing the piece of information
     * @throws XPathExpressionException
     */
    private String getInfo(String id, String element) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/" + element);
        Node info = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return info.getTextContent();
    }

    /**
     * Get firstname of the person
     *
     * @param id id of the person
     * @return the firstname of the person
     * @throws XPathExpressionException
     */
    private String getFirstname(String id) throws XPathExpressionException {
        return this.getInfo(id, "firstname");
    }

    /**
     * Get lastname of the person
     *
     * @param id id of the person
     * @return the lastname of the person
     * @throws XPathExpressionException
     */
    private String getLastname(String id) throws XPathExpressionException {
        return this.getInfo(id, "lastname");
    }

    /**
     * Get birthday of the person
     *
     * @param id id of the person
     * @return the birthday of the person
     * @throws XPathExpressionException
     */
    private String getBirthdate(String id) throws XPathExpressionException {
        return this.getInfo(id, "birthdate").substring(0, 10);
    }

    /**
     * Get weight of the person
     *
     * @param id id of the person
     * @return the weight of the person
     * @throws XPathExpressionException
     */
    private String getWeight(String id) throws XPathExpressionException {
        return this.getInfo(id, "healthprofile/weight");
    }

    /**
     * Get height of the person
     *
     * @param id id of the person
     * @return the height of the person
     * @throws XPathExpressionException
     */
    private String getHeight(String id) throws XPathExpressionException {
        return this.getInfo(id, "healthprofile/height");
    }

    /**
     * Get bmi of the person
     *
     * @param id id of the person
     * @return the bmi of the person
     * @throws XPathExpressionException
     */
    private String getBmi(String id) throws XPathExpressionException {
        return this.getInfo(id, "healthprofile/bmi");
    }

    /**
     * Get health profile of the person
     *
     * @param id id of the person
     * @return the health profile of the person
     * @throws XPathExpressionException
     */
    private String getHProfile(String id) throws XPathExpressionException {
        return "Weight of " + this.getWeight(id) + " kg, height of " + this.getHeight(id) + " and bmi of "
                + this.getBmi(id);
    }

    /**
     * Print all people that verify a certain weight condition
     *
     * @param weight value of the weight to compare
     * @param op type of the operator to use for the comparison
     * @throws XPathExpressionException
     */
    private void compareWeight(double weight, String op) throws XPathExpressionException {
        // get list of people that satisfy the weight condition
        XPathExpression expr = xpath.compile("/people/person[healthprofile[weight " + op + " " + weight + "]]");
        NodeList people = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        System.out.println("People with weight " + op + " " + weight + ": ");

        //iterate over the people list to get personal info
        for (int i = 0; i < people.getLength(); i++) {
            NodeList personInfo = people.item(i).getChildNodes();

            Node current = null;
            for (int j = 0; j < personInfo.getLength(); j++) {
                current = personInfo.item(j);
                if (current.getNodeType() == Node.ELEMENT_NODE) { //if the child is an element
                    Element element = (Element) current;
                    if (element.getTagName().equalsIgnoreCase("firstname")) { //if it contains the firstname
                        System.out.print(element.getTextContent() + " ");
                    }
                    if (element.getTagName().equalsIgnoreCase("lastname")) { //if it contains the lastname
                        System.out.println(element.getTextContent());
                    }
                }
            }
        }
    }

}
