package TFG.CUPES.PlayerTest;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    
    @Test
    public void findPlayerByUsernamePositiveTest(){
        Player p = this.playerRepository.findByUsername("Guaje");
        assert(p.getUsername().equals("Guaje"));
        assertEquals(p.getBirthDate().toString(),"2002-01-02");
        assertEquals(p.getPassword(), "$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG");
        assertEquals(p.getMail(),"guaje@guaje.com");
        assertEquals(p.getName(),"Guaje");
    }

    @Test
    public void findPlayerByUsernameNegativeTest(){
        Player p = this.playerRepository.findByUsername("a");
        assertEquals(p, null);
    }
    
    @Test
    public void findPlayerByMailPositiveTest(){
        Player p = this.playerRepository.findByMail("guaje@guaje.com");
        assert(p.getUsername().equals("Guaje"));
        assertEquals(p.getBirthDate().toString(),"2002-01-02");
        assertEquals(p.getPassword(), "$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG");
        assertEquals(p.getMail(),"guaje@guaje.com");
        assertEquals(p.getName(),"Guaje");       
    }

    @Test
    public void findPlayerByMailNegativeTest(){
        Player p = this.playerRepository.findByMail("a");
        assertEquals(p, null);
    }
}
