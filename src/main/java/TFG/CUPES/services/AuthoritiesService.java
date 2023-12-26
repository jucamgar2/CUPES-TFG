package TFG.CUPES.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TFG.CUPES.entities.Authorities;
import TFG.CUPES.repositories.AuthoritiesRepository;

@Service
public class AuthoritiesService {

    private  AuthoritiesRepository authoritiesRepository;

    @Autowired
    public AuthoritiesService(AuthoritiesRepository authoritiesRepository){
        this.authoritiesRepository = authoritiesRepository;
    }

    @Transactional
    public void save(Authorities a){
        authoritiesRepository.save(a);
    }

    @Transactional(readOnly=true)
    public Integer findMaxId(){
        return authoritiesRepository.findMaxId();
    }

    @Transactional(readOnly=true)
    public Authorities findByUsername(String username){
        return authoritiesRepository.findByUsername(username);
    }

    
    
}
