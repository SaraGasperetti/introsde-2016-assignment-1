# introsde-2016-assignment-1
**First assignment | University of Trento**

Documentation about assignment 01: Reading/Writing objects to and from XML and JSON

## Project structure
The project is divided into 5 packages:

* ```default package```: it contains ```HealthProfileReader.java``` that loads the ```people.xml``` file and parses it through an xpath factory in order to retrieve some information;
* ```dao```: it contains ```PeopleStore.java```, which is used to store and retrieve a list of objects of type ```Person```. This class contains annotations in order to allow the class conversion into another format;
* ```model```: it contains ```HealthProfile.java``` and ```Person.java``` that represent the Person and HealthProfile objects. These classes contain annotations in order to allow the class conversion into another format;
* ```marshaller```: it contains ```JaxbMarshaller.java```, ```JaxbUnmarshaller.java``` and ```JsonMarshaller.java```. The first one marshalls the ```PersonStore``` (and therefore also ```HealthProfile.java``` and ```Person.java```) java object into the ```newPeople.xml``` file. The second one unmarshalls the data doing the opposite of the previous class. The third one is similar to the first one with the exception that the data are converted into the json format in the ```newPeople.json``` file;
* ```people/generated```: it contains autogenerated classes with JAXB XJC.

## Configuration files

The configuration files are:

* ```build.xml```: it contains all the targets to run the code;
* ```ivy.xml```: it contains all the dependencies needed to run the project and it downloads them.

## Tasks

The targets of the ```build.xml``` file are:

* ```execute.displayPeople```: prints all people in the list with detail;
* ```execute.healthProfile```: accepts id as parameter and prints the HealthProfile of the person with that id;
* ```execute.compareWeight```: accepts a weight and an operator (=, > , <) as parameters and prints people that fulfill that condition (i.e., >80Kg, =75Kg, etc.);
* ```execute.JAXBMarshaller```: does the marshalling from a list of persons object into an xml file;
* ```execute.JAXBUnmarshaller```: does the unmarshalling from an xml file into a list of persons object;
* ```execute.JsonMarshaller```: does the marshalling from a list of persons object into a json file;
 

## How to run

Navigate into the root directory and type ```ant execute.evaluation```. All the previous target will be automatically called.

