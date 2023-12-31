package TFG.CUPES.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Image;
import TFG.CUPES.repositories.ImageRepository;

@Service
public class ImageService {

    private ImageRepository logoRepository;

    @Autowired
    public ImageService(ImageRepository logoRepo){
        this.logoRepository = logoRepo;
    }

    @Transactional(readOnly = true)
    public Integer getMaxId(){
        return this.logoRepository.maxId();
    }
    
    @Transactional(readOnly = true)
    public Image getRandomLogo(){
        List<Image> logos = (List<Image>) this.logoRepository.findAll();
        Random rand = new Random();
        return logos.get(rand.nextInt(logos.size()));
    }

    @Transactional(readOnly = true)
    public List<Image> getAllLogos(){
        return (List<Image>) this.logoRepository.findAllEnabled();
    }

    @Transactional(readOnly = true)
    public Image getLogoById(Integer id){
        return this.logoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Image image) {
        this.logoRepository.save(image);
    }

    @Transactional(readOnly = true)
    public Page<Image> getAllLogosPageable(PageRequest of) {
        return this.logoRepository.findAll(of);
    }

    public void delete(Image image) {
        this.logoRepository.delete(image);
    }

    @Transactional(readOnly = true)
    public Double getSuccessFromImage(Image image) {
        return this.logoRepository.findSuccesFromImage(image);
    }

    @Transactional(readOnly = true)
    public Double getGamesFromImage(Image image) {
        return this.logoRepository.findImageByGameAlone(image);
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromCountry(String country) {
        Double success = this.logoRepository.findSuccesFromCountryImage(country);
        Double games = this.logoRepository.findImageNumOfGamesFromCountry(country);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromCountryAndShifts(String country, Integer shifts) {
        Double success = this.logoRepository.findImageNumOfSuccessFromCountryAndShifts(country,shifts);
        Double games = this.logoRepository.findImageNumOfGamesFromCountry(country);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromGenre(String genre) {
        Double success = this.logoRepository.findNumOfSuccessFromGenre(genre);
        Double games = this.logoRepository.findNumOfGamesFromGenre(genre);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromGenreAndShifts(String genre, Integer shifts) {
        Double success = this.logoRepository.findNumOfSuccessFromGenreAndShifts(genre,shifts);
        Double games = this.logoRepository.findNumOfGamesFromGenre(genre);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromCategory(Integer category) {
        Double success = this.logoRepository.findNumOfSuccessFromCategory(category);
        Double games = this.logoRepository.findNumOfGamesFromCategory(category);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromCategoryAndShifts(Integer category, Integer shifts) {
        Double success = this.logoRepository.findNumOfSuccessFromCategoryAndShifts(category,shifts);
        Double games = this.logoRepository.findNumOfGamesFromCategory(category);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromName(Boolean hasName) {
        Double success = this.logoRepository.findNumOfSuccessWithName(hasName);
        Double games = this.logoRepository.findNumOfGamesWithName(hasName);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromNameAndShifts(Boolean hasName, Integer shifts) {
        Double success = this.logoRepository.findNumOfSuccessWithNameAndShifts(hasName,shifts);
        Double games = this.logoRepository.findNumOfGamesWithName(hasName);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromInitials(Boolean hasInitials) {
        Double success = this.logoRepository.findNumOfSuccessWithInitials(hasInitials);
        Double games = this.logoRepository.findNumOfGamesWithInitials(hasInitials);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromInitialsAndShifts(Boolean hasInitials, Integer shifts) {
        Double success = this.logoRepository.findNumOfSuccessWithInitialsAndShifts(hasInitials,shifts);
        Double games = this.logoRepository.findNumOfGamesWithInitials(hasInitials);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromYear(Boolean hasYear) {
        Double success = this.logoRepository.findNumOfSuccessWithYear(hasYear);
        Double games = this.logoRepository.findNumOfGamesWithYear(hasYear);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public Double getSuccesRateFromYearAndShifts(Boolean hasYear, Integer shifts) {
        Double success = this.logoRepository.findNumOfSuccessWithYearAndShifts(hasYear,shifts);
        Double games = this.logoRepository.findNumOfGamesWithYear(hasYear);
        return games>0?success/games:0.0;
    }

    @Transactional(readOnly = true)
    public List<GameAlone> getGamesFromSelected(Image image) {
        return this.logoRepository.findGamesBySelected(image);
    }

    @Transactional(readOnly = true)
    public Page<Image> getLogosByName(String name, PageRequest of) {
        return this.logoRepository.findByName(name, of);
    }
}