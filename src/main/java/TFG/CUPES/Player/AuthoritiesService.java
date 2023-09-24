package TFG.CUPES.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesService {

    private  AuthoritiesRepository authoritiesRepository;

    @Autowired
    public AuthoritiesService(AuthoritiesRepository authoritiesRepository){
        this.authoritiesRepository = authoritiesRepository;
    }

    public void save(Authorities a){
        authoritiesRepository.save(a);
    }
    
}
