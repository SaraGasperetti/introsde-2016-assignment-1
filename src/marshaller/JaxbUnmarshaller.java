package marshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import dao.PeopleStore;
import model.Person;

public class JaxbUnmarshaller {

    public static void main(String[] args) throws JAXBException, FileNotFoundException, SAXException {

        JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
        System.out.println();
        System.out.println("Output from our XML File: ");
        Unmarshaller um = jc.createUnmarshaller();

        // Set xsd schema to validate the xml file
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = schemaFactory.newSchema(new File("people.xsd"));
        um.setSchema(schema);

        PeopleStore people = (PeopleStore) um.unmarshal(new FileReader("newPeople.xml"));
        List<Person> list = people.getData();
        for (Person person : list) {
            System.out.println("Person: " + person.getFirstname() + " " + person.getLastname()
                    + " born on " + person.getBirthdate().substring(0, 10) + ". Healthprofile: "
                    + person.getHProfile());
        }

    }

}
