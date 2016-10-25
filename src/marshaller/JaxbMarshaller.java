package marshaller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import dao.PeopleStore;
import model.HealthProfile;
import model.Person;

public class JaxbMarshaller {

    public static PeopleStore people = new PeopleStore();

    public static void main(String[] args) throws Exception {

        initializeDB();

        JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        m.marshal(people, new File("newPeople.xml")); // marshalling into a file
        m.marshal(people, System.out);			  // marshalling into the system default output

    }

    private static void initializeDB() {

        Person john = new Person(new Long(1), "John", "Doe", "1985-03-20T21:18:22.174+02:00", new HealthProfile(68.0, 1.72));
        Person jack = new Person(new Long(2), "Jack", "Black", "1990-07-15T09:15:44.074+02:00", new HealthProfile(75.0, 1.83));
        Person kate = new Person(new Long(3), "Kate", "Finn", "1980-02-05T15:33:12.153+02:00", new HealthProfile(65.0, 1.53));

        people.getData().add(john);
        people.getData().add(jack);
        people.getData().add(kate);
    }

}
