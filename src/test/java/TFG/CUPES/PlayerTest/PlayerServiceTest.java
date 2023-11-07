package TFG.CUPES.PlayerTest;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Test
    public void savePlayerTest(){
        Player p = new Player("joselillo", "$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG", true, LocalDate.of(2002, 01, 02), "jota@jota.com","Joselillo");
        this.playerService.save(p);
        Player p2 = this.playerService.getByUsername("joselillo");
        assertEquals(p.getUsername(),p2.getUsername());
        assertEquals(p.getBirthDate(),p2.getBirthDate());
        assertEquals(p.getPassword(),p2.getPassword());
        assertEquals(p.getMail(),p2.getMail());
        assertEquals(p.getName(),p2.getName());
    }

    @Test
    public void existsPlayerTest(){
        Boolean exists = this.playerService.exists("Guaje");
        assertEquals(exists, true);
    }

    @Test
    public void getByUserNameTest(){
        Player p = this.playerService.getByUsername("Guaje");
        assert(p.getUsername().equals("Guaje"));
        assertEquals(p.getBirthDate().toString(),"2002-01-02");
        assertEquals(p.getPassword(), "$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG");
        assertEquals(p.getMail(),"guaje@guaje.com");
        assertEquals(p.getName(),"Guaje");
    }

    @Test
    public void getByUserNameNegativeTest(){
        Player p = this.playerService.getByUsername("a");
        assertEquals(p, null);
    }

    @Test
    public void findByMailTest(){
        Player p = this.playerService.findByMail("guaje@guaje.com");
        assert(p.getUsername().equals("Guaje"));
        assertEquals(p.getBirthDate().toString(),"2002-01-02");
        assertEquals(p.getPassword(), "$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG");
        assertEquals(p.getMail(),"guaje@guaje.com");
        assertEquals(p.getName(),"Guaje");
    }

    @Test
    public void findByMailNegativeTest(){
        Player p = this.playerService.findByMail("a");
        assertEquals(p, null);
    }

    public static List<Player> readPlayersFromCSV(String csvFilePath) {
        List<Player> players = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath)).withSkipLines(1).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                String username = line[0];
                String password = line[1];
                boolean enabled = Boolean.parseBoolean(line[2]);
                LocalDate birthDate = LocalDate.parse(line[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String mail = line[4];
                String name = line[5];

                Player player = new Player(username, password, enabled, birthDate, mail, name);
                players.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }


    @Test
    public void checkPlayerRestrictionsTest1(){
        List<Player> players = readPlayersFromCSV("src/test/java/TFG/CUPES/PlayerTest/PlayerErrorsData.csv");
        for(int i = 0; i<players.size(); i++){
            List<String> errors = this.playerService.checkPlayerRestrictions(players.get(i));
            assertEquals(errors.size(), 1);
        }
    }
    
}
