package TFG.CUPES.PlayerTest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import TFG.CUPES.entities.Player;
import TFG.CUPES.repositories.PlayerRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    
    @Test
    public void findPlayerByUsernamePositiveTest(){
        Player p1 = new Player();
        p1.setUsername("Guaje");
        p1.setBirthDate(LocalDate.of(2002, 01, 02));
        p1.setPassword("1111");
        p1.setMail("guaje@guaje.com");
        p1.setName("Guaje");
        this.playerRepository.save(p1);
        Player p = this.playerRepository.findByUsername("Guaje");
        assert(p.getUsername().equals("Guaje"));
        assertEquals(p.getBirthDate().toString(),"2002-01-02");
        assertEquals(p.getPassword(), "1111");
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
        Player p1 = new Player();
        p1.setUsername("Guaje");
        p1.setBirthDate(LocalDate.of(2002, 01, 02));
        p1.setPassword("1111");
        p1.setMail("guaje@guaje.com");
        p1.setName("Guaje");
        this.playerRepository.save(p1);
        Player p = this.playerRepository.findByMail("guaje@guaje.com");
        assert(p.getUsername().equals("Guaje"));
        assertEquals(p.getBirthDate().toString(),"2002-01-02");
        assertEquals(p.getPassword(), "1111");
        assertEquals(p.getMail(),"guaje@guaje.com");
        assertEquals(p.getName(),"Guaje");       
    }

    @Test
    public void findPlayerByMailNegativeTest(){
        Player p = this.playerRepository.findByMail("a");
        assertEquals(p, null);
    }
}
