package entity;
import java.util.List;
public class Booking {
    private int bookingId;
    private int showId;
    private int userId;
    private List<String> seatBooked;
    private double totalPrice;

    public Booking(int bookingId, int showId, int userId, List<String> seatBooked, double totalPrice) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.userId = userId;
        this.seatBooked = seatBooked;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getSeatBooked() {
        return seatBooked;
    }

    public void setSeatBooked(List<String> seatBooked) {
        this.seatBooked = seatBooked;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
