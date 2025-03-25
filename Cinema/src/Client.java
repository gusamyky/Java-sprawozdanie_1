import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.util.List;

public class Client {
    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final Seance seance;
    private final List<SeatPlace> seatPlaces;

    public Client(String name, String surname, String email, String phone, Seance seance, List<SeatPlace> seatPlaces) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.seance = seance;
        this.seatPlaces = seatPlaces;
    }

    public static String client2Xml(Client p) {
        XStream mapping = new XStream(new DomDriver());
        String xml = mapping.toXML(p);
        return xml;
    }

    public static Client xml2Client(String xml) {
        XStream mapping = new XStream(new DomDriver());
        mapping.addPermission(AnyTypePermission.ANY);
        return (Client) mapping.fromXML(xml);
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nSurname: " + surname + "\nEmail: " + email + "\nPhone: " + phone +
                "\nSeance: " + seance.toString() + "\nSeat places: " + seatPlaces.toString();
    }
}
