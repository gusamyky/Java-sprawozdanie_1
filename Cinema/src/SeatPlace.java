import java.io.Serial;
import java.io.Serializable;

public class SeatPlace implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private final Character row;
    private final int seat;
    private final boolean isFree;

    public SeatPlace(Character row, int seat, boolean isFree) {
        this.row = row;
        this.seat = seat;
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "\tRow:" + row + "\n\tSeat: " + seat + "\n\tIs free: " + isFree;
    }
}
