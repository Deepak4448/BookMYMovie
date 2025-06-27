package service;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookMyMovieApp {
    public static void main(String[] args) {
        BookMyMovieSys bmm = new BookMyMovieSys();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Ciy :- ");
        String city = sc.next();
        bmm.displayTheaters(city);

        System.out.println("Enter Theater Id and Movie Id:");
        int theaterId = sc.nextInt();
        int movieId=sc.nextInt();
        bmm.displayShows(movieId,theaterId);

        System.out.println("Enter show Id and Number of seats");
        int showId = sc.nextInt();
        List<String> ss = Arrays.asList("A3","A4");// number of seats user input nahi liye hai
        bmm.bookTicket(1,showId,ss);

    }
}
