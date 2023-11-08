package TFG.CUPES.PlayerTest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;

import TFG.CUPES.Player.Authorities;
import TFG.CUPES.Player.AuthoritiesService;
import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;

@DataJpaTest(includeFilters = @Filter(Service.class))
public class AuthoritiesServiceTest {

    @Autowired
    AuthoritiesService authoritiesService;

    @Autowired
    PlayerService playerService;

    @Test
    public void saveAuthorityTest(){
        Player p = new Player("María","1111",true,LocalDate.of(2015,1,1),"maria@maria.com","María");
        this.playerService.save(p);
        Authorities a = new Authorities();
        Integer id = this.authoritiesService.findMaxId();
        a.setAuthority("player");
        a.setId(id);
        a.setPlayer(p);
        this.authoritiesService.save(a);
        assertEquals(p, a.getPlayer());
        assertEquals(id, a.getId());
        assertEquals("player", a.getAuthority());
    }

    
}
