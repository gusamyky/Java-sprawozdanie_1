import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // tworzymy obiekt klasy ObjectOutputStream do zapisywania do pliku
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./output/seances.dat"));

        // tworzymy obiekt klasy Seance, który chcemy zapisać
        List<SeatPlace> seatPlaces1 = new ArrayList<SeatPlace>();
        seatPlaces1.add(new SeatPlace('A', 1, true));
        seatPlaces1.add(new SeatPlace('A', 2, true));
        seatPlaces1.add(new SeatPlace('A', 3, true));
        seatPlaces1.add(new SeatPlace('A', 4, true));
        seatPlaces1.add(new SeatPlace('A', 5, false));
        seatPlaces1.add(new SeatPlace('A', 6, true));
        seatPlaces1.add(new SeatPlace('A', 7, true));
        seatPlaces1.add(new SeatPlace('A', 8, true));
        seatPlaces1.add(new SeatPlace('A', 9, false));

        List<SeatPlace> seatPlaces2 = new ArrayList<SeatPlace>();
        seatPlaces2.add(new SeatPlace('A', 1, true));
        seatPlaces2.add(new SeatPlace('A', 2, false));
        seatPlaces2.add(new SeatPlace('A', 3, true));
        seatPlaces2.add(new SeatPlace('A', 4, true));
        seatPlaces2.add(new SeatPlace('A', 5, false));
        seatPlaces2.add(new SeatPlace('A', 6, true));
        seatPlaces2.add(new SeatPlace('A', 7, true));
        seatPlaces2.add(new SeatPlace('A', 8, true));
        seatPlaces2.add(new SeatPlace('A', 9, true));

        Seance seance = new Seance("Star Wars", LocalDate.of(2025, 3, 25),
                LocalTime.of(12, 30), 'R', seatPlaces1);


        // no i zapisujemy
        outputStream.writeObject(seance);
        outputStream.writeObject(new Seance("Ice Age", LocalDate.of(2025, 3, 26),
                LocalTime.of(11, 0), 'A', seatPlaces2));
        outputStream.close();
        //----------- a teraz odczyt -------------------------------------------

        // tworzymy obiekt klasy ObjectInputStream do odczytywania z pliku
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("./output/seances.dat"));

        // odczytujemy z pliku; (Seance) - to rzutowanie z Object na Pracownik
        Seance seance1 = (Seance) inputStream.readObject();
        Seance seance2 = (Seance) inputStream.readObject();

        // i ładnie na konsolę wyrzucamy wynik
        System.out.println(seance1.toString());
        System.out.println(seance2.toString());
        inputStream.close();


        //----------- a teraz zapis w XML -------------------------------------------
        FileOutputStream fileOutputStream = new FileOutputStream("./output/client.xml");

        List<SeatPlace> seatPlacesClient1 = new ArrayList<SeatPlace>();
        seatPlacesClient1.add(new SeatPlace('A', 1, false));

        String xml = Client.client2Xml(new Client("Jan", "Kowalski", "example@mail.com",
                "123456789", seance1, seatPlacesClient1));
        fileOutputStream.write(xml.getBytes(StandardCharsets.UTF_8));

        fileOutputStream.close();

        //----------- a teraz odczyt z XML -------------------------------------------
        FileInputStream fileInputStream = new FileInputStream("./output/client.xml");
        byte[] bytes = fileInputStream.readAllBytes();
        String xmlString = new String(bytes, StandardCharsets.UTF_8);
        Client client = Client.xml2Client(xmlString);
        System.out.println(client.toString());
    }
}