package service;
import config.DataBaseConfig;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
public class BookMyMovieSys {
    Scanner sc = new Scanner(System.in);
    // display movies
    public void displayMovies() {
        try {
            Connection con = DataBaseConfig.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from movies");
            while(rs.next()){
                System.out.println(rs.getInt("movie_Id")+"."+rs.getString("title")+"("+rs.getString("genre")+")");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    // show theater in a city
    public void displayTheaters(String city)
    {
        try {
            Connection con = DataBaseConfig.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from theaters where city = ?");
            stmt.setString(1,city);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Threaters in "+city+ ":");
            while (rs.next())
            {
                System.out.println(rs.getInt("theater_id")+". "+rs.getString("name"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
   // display show
   public void displayShows(int movieId, int theaterId) {
       try {
          Connection con = DataBaseConfig.getConnection();
         PreparedStatement stmt =  con.prepareStatement("select * from shows where movie_id = ? AND theater_id = ?");
          stmt.setInt(1,movieId);
          stmt.setInt(2,theaterId);
          ResultSet rs = stmt.executeQuery();
           System.out.println("Available shows");
           while(rs.next()){
               System.out.println(rs.getInt("show_id")+"."+rs.getString("timing")+"-seats available :"+ rs.getString("available_seats"));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

   // Book Ticket
    public void bookTicket(int userId, int showId, List<String> selectedSeats){
        try{
            Connection con = DataBaseConfig.getConnection();
            con.setAutoCommit(false);
            // check if seats are available
            boolean alreadyBookedSeat = false;
            for(String seat : selectedSeats){ // for loop because ek ek karke seat ki availibility check karenge
                PreparedStatement stmt = con.prepareStatement("select * from seat where seat_number = ? and show_id = ? ");
                stmt.setString(1,seat);
                stmt.setInt(2,showId);
                ResultSet rs = stmt.executeQuery();
                if(rs.next() && rs.getBoolean("is_booked")){ // loop nahi laga kyuki eak hi data aa raha
                    alreadyBookedSeat = true;
                    System.out.println("seat"+seat + "is already booked. Choose another seat");
                }
            }
            if(alreadyBookedSeat){
                System.out.println("Booking Failed! Some seats are already Booked.");
                con.rollback();
                return;
            }
            for(String seat : selectedSeats){ // i.e seat khali hai than booking aur seat dono ko update bhi karna hoga
                PreparedStatement stmt = con.prepareStatement("update seat set is_booked=TRUE where seat_number = ? AND show_id = ?");
                stmt.setString(1,seat);
                stmt.setInt(2,showId);
                stmt.executeUpdate();
            }
            double seatPrice = 200.0;
            double tp =selectedSeats.size()*seatPrice;// tp = total price

            PreparedStatement stmt = con.prepareStatement("insert into bookings (user_id,show_id,seats_booked,total_price) values (?,?,?,?)");
            stmt.setInt(1,userId);
            stmt.setInt(2,showId);
            stmt.setString(3,String.join(",",selectedSeats)); // join method saare string ko comma seperate karke likh dega
            stmt.setDouble(4,tp);
            stmt.executeUpdate(); // yaha execureUpdate likhenge kyuki update ho raha
            con.commit(); // now commit the changes
            System.out.println("Booking successful, Seats : "+ selectedSeats+ " | Total price : "+ tp);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
