package marshaller;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;

public class JsonMarshaller {

    public static PeopleStore people = new PeopleStore();

    public static void main(String[] args) throws Exception {

        initializeDB();

        // Jackson Object Mapper 
        ObjectMapper mapper = new ObjectMapper();

        // Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();

        // configure as necessary
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File("newPeople.json"), people);
    }

    private static void initializeDB() {

        Person john = new Person(new Long(1), "John", "Doe", "1985-03-20", new HealthProfile(68.0, 1.72));
        Person jack = new Person(new Long(2), "Jack", "Black", "1990-07-15", new HealthProfile(75.0, 1.83));
        Person kate = new Person(new Long(3), "Kate", "Finn", "1980-02-05", new HealthProfile(65.0, 1.53));

        people.getData().add(john);
        people.getData().add(jack);
        people.getData().add(kate);
    }
}
