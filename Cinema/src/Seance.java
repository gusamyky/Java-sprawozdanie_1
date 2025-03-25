import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Seance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private final String title;
    private final LocalDate date;
    private final LocalTime time;
    private final Character ageCategory;
    private final List<SeatPlace> seatPlaces;

    public Seance(String title, LocalDate date, LocalTime time, Character ageCategory, List<SeatPlace> seatPlaces) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.ageCategory = ageCategory;
        this.seatPlaces = seatPlaces;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nDate: " + date + "\nTime: " + time + "\nAge category: "
                + ageCategory + "\nSeat places: " + seatPlaces.toString() + "\n\n\n";
    }
}
