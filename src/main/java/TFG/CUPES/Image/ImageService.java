package TFG.CUPES.Image;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageService {

    private ImageRepository logoRepository;

    @Autowired
    public ImageService(ImageRepository logoRepo){
        this.logoRepository = logoRepo;
    }
    
    @Transactional(readOnly = true)
    public Image getRandomLogo(){
        List<Image> logos = (List<Image>) this.logoRepository.findAll();
        Random rand = new Random();
        return logos.get(rand.nextInt(logos.size()));
    }

    @Transactional(readOnly = true)
    public List<Image> getAllLogos(){
        return (List<Image>) this.logoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Image getLogoById(Integer id){
        return this.logoRepository.findById(id).orElse(null);
    }
}