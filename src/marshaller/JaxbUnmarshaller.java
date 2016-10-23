package marshaller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import dao.PeopleStore;
import model.Person;

public class JaxbUnmarshaller {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

        JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
        System.out.println();
        System.out.println("Output from our XML File: ");
        Unmarshaller um = jc.createUnmarshaller();
        PeopleStore people = (PeopleStore) um.unmarshal(new FileReader("newPeople.xml"));
        List<Person> list = people.getData();
        for (Person person : list) {
            System.out.println("Person: " + person.getFirstname() + " born "
                    + person.getBirthdate());
        }

    }

}
