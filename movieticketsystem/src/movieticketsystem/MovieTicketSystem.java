package movieticketsystem;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MovieTicketSystem {
    private JFrame frame;
    private JComboBox<String> movieComboBox;
    private JTextField numSeatsField;
    private JTextArea recordsArea;
    private JButton bookButton;
    private JButton cancelButton;

    private ArrayList<Movie> movies;
    private HashMap<String, Integer> movieIndexMap;

    public MovieTicketSystem() {
        movies = new ArrayList<>();
        movieIndexMap = new HashMap<>();

        movies.add(new Movie("Movie 1", 50));
        movies.add(new Movie("Movie 2", 60));
        movies.add(new Movie("Movie 3", 70));

        for (int i = 0; i < movies.size(); i++) {
            movieIndexMap.put(movies.get(i).getName(), i);
        }

        frame = new JFrame("Movie Ticket System");
        frame.setLayout(new FlowLayout());

        movieComboBox = new JComboBox<>();
        for (Movie movie : movies) {
            movieComboBox.addItem(movie.getName());
        }
        frame.add(movieComboBox);

        numSeatsField = new JTextField(10);
        frame.add(numSeatsField);

        bookButton = new JButton("Book Seats");
        frame.add(bookButton);
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookSeats();
            }
        });

        cancelButton = new JButton("Cancel Seats");
        frame.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelSeats();
            }
        });

        recordsArea = new JTextArea(10, 30);
        frame.add(new JScrollPane(recordsArea));

        updateRecords();

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void bookSeats() {
        String selectedMovie = (String) movieComboBox.getSelectedItem();
        int numSeats = Integer.parseInt(numSeatsField.getText());

        int movieIndex = movieIndexMap.get(selectedMovie);
        Movie movie = movies.get(movieIndex);

        if (movie.bookSeats(numSeats)) {
            updateRecords();
            JOptionPane.showMessageDialog(frame, numSeats + " seats booked for " + selectedMovie);
        } else {
            JOptionPane.showMessageDialog(frame, "Not enough seats available for " + selectedMovie);
        }
    }

    private void cancelSeats() {
        String selectedMovie = (String) movieComboBox.getSelectedItem();
        int numSeats = Integer.parseInt(numSeatsField.getText());

        int movieIndex = movieIndexMap.get(selectedMovie);
        Movie movie = movies.get(movieIndex);

        movie.cancelSeats(numSeats);
        updateRecords();
        JOptionPane.showMessageDialog(frame, numSeats + " seats canceled for " + selectedMovie);
    }

    private void updateRecords() {
        recordsArea.setText("Movie Booking Records:\n\n");
        for (Movie movie : movies) {
            recordsArea.append(movie.getName() + " - Available: " + movie.getAvailableSeats() +
                    " Booked: " + movie.getBookedSeats() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MovieTicketSystem();
            }
        });
    }
}

