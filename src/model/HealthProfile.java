package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "healthprofile")
@XmlType(propOrder = {"lastupdate", "weight", "height", "BMI"})
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthProfile {

    private String lastupdate;
    private double weight; // in kg
    private double height; // in m

    public HealthProfile(double weight, double height) {
        Date now = Calendar.getInstance().getTime(); //get current time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); //date formatter
        this.lastupdate = formatter.format(now);

        this.weight = weight;
        this.height = height;
    }

    public HealthProfile() {
        Date now = Calendar.getInstance().getTime(); //get current time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); //date formatter
        this.lastupdate = formatter.format(now);

        this.weight = 85.5;
        this.height = 1.72;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String toString() {
        return "Height=" + height + ", Weight=" + weight;
    }

    @XmlElement(name = "bmi")
    public double getBMI() {
        return this.weight / (Math.pow(this.height, 2));
    }
}
