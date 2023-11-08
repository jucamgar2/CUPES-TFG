package TFG.CUPES.Player;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepo;

    @Transactional
    public void save(Player p){
        this.playerRepo.save(p);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return this.playerRepo.existsById(username);
    }

    public Player getByUsername(String name) {
        return this.playerRepo.findByUsername(name);
    }

    public Player findByMail(String mail){
        return this.playerRepo.findByMail(mail);
    }

    public List<String> checkPlayerRestrictions(Player p){
        List<String> errors = new ArrayList<String>();
        String pattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern2 = Pattern.compile(pattern);
        if(p.getUsername().contains(" ")){
            errors.add("El nombre de usuario no debe contener espacios en blanco");
        }
        if(p.getPassword().contains(" ")){
            errors.add("La contraseña no debe contener espacios en blanco");
        }
        if(exists(p.getUsername())){
            errors.add("Ya existe un usuario con ese nombre");
        }
        if(p.getUsername().length()<5 || p.getUsername().length()>30){
            errors.add("La longitud del nombre de usuario debe tener entre 5 y 30 caracteres");
        }
        if(p.getPassword().length()<4 || p.getPassword().length()>30){
            errors.add("La longitud de la contraseña debe tener entre 4 y 30 caracteres");
        }
        if(p.getName() ==null){
            errors.add("El nombre no puede estar vacío");
        }else{
            if(p.getName().length()<3 || p.getName().length()>30){
                errors.add("La longitud del nombre debe tener entre 3 y 30 caracteres");
            }
        }
        if(p.getMail()==null){
            errors.add("El correo no puede estar vacío");
        }else{
            Matcher matcher = pattern2.matcher(p.getMail());
            if(matcher.matches()==false){
                errors.add("El correo no es válido");
            }
            if (p.getMail().length()>50){
            errors.add("La longitud del correo debe tener menos de 50 caracteres"); 
            }
            if(findByMail(p.getMail())!=null){
                errors.add("Ya existe un usuario con ese correo");
            }
        }
        
        if(p.getBirthDate()==null){
            errors.add("La fecha de nacimiento no puede estar vacía");
        }
        if(p.getBirthDate()!=null && p.getBirthDate().isAfter(LocalDate.now())){
            errors.add("La fecha de nacimiento no puede ser posterior a la fecha actual");
        }
        return errors;
    }
}
