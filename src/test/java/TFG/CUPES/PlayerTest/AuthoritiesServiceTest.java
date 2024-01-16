package TFG.CUPES.PlayerTest;

import static org.junit.Assert.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import TFG.CUPES.entities.Authorities;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.AuthoritiesService;
import TFG.CUPES.services.PlayerService;

@DataJpaTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class AuthoritiesServiceTest {

    @Autowired
    AuthoritiesService authoritiesService;

    @Autowired
    PlayerService playerService;

    @Test
    public void saveAuthorityTest(){
        Player p = new Player("María","1111",true,"maria@maria.com","María");
        this.playerService.save(p);
        Authorities a = new Authorities();
        Integer id = 1;
        a.setAuthority("player");
        a.setId(1);
        a.setPlayer(p);
        this.authoritiesService.save(a);
        assertEquals(p, a.getPlayer());
        assertEquals(id, a.getId());
        assertEquals("player", a.getAuthority());
    }
}
