package TFG.CUPES.services;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.repositories.GameAloneRepository;

@Service
public class GameAloneService {

    private GameAloneRepository gameRepository;

    @Autowired
    public GameAloneService(GameAloneRepository gameRepo){
        this.gameRepository = gameRepo;
    }
    @Transactional
    public void saveGame(GameAlone g){
        this.gameRepository.save(g);
    }

    @Transactional(readOnly = true)
    public Optional<GameAlone> getGameById(Integer id){
        return this.gameRepository.findById(id);
    }

    @Transactional
    public void deleteGame(GameAlone g){
        this.gameRepository.delete(g);
    }

    @Transactional(readOnly = true)
    public Optional<GameAlone> getGameByTokenAndId(String token, Integer id){
        return this.gameRepository.getGameByTokenAndId(token, id);
    }

    @Transactional(readOnly = true)
    public Double getNumOfGamesFromUser(String username){
        return this.gameRepository.findNumOfGamesFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsFromUser(String username){
        return this.gameRepository.findNumOfWinsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWihtOneShiftFromUser(String username){
        return this.gameRepository.findNumOfWinsWihtOneShiftFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWithTwoShiftsFromUser(String username){
        return this.gameRepository.findNumOfWinsWithTwoShiftsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWithThreeShiftsFromUser(String username){
        return this.gameRepository.findNumOfWinsWithThreeShiftsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWithFourShiftsFromUser(String username){
        return this.gameRepository.findNumOfWinsWithFourShiftsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getAverageShiftsTowinFromUser(String username){
        Double oneShiftWins = this.gameRepository.findNumOfWinsWihtOneShiftFromUser(username);
        Double twoShiftWins = this.gameRepository.findNumOfWinsWithTwoShiftsFromUser(username);
        Double threeShiftWins = this.gameRepository.findNumOfWinsWithThreeShiftsFromUser(username);
        Double fourShiftWins = this.gameRepository.findNumOfWinsWithFourShiftsFromUser(username);
        Double wins = this.getNumOfWinsFromUser(username);
        Double sum = (double) (4*fourShiftWins + 3*threeShiftWins + 2*twoShiftWins + oneShiftWins);
        Double res = (double) wins>0? sum/wins:0.;
        return res;
    }

    public Double getWinRateFromUser(String username){
        Double res=0.;
        Double games = this.gameRepository.findNumOfGamesFromUser(username);
        Double wins = this.gameRepository.findNumOfWinsFromUser(username);
        if(games>0){
            res = wins/games;
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Double getNumOfFinishGames(){
        return this.gameRepository.findNumOfFinishGames();
    }

    @Transactional(readOnly = true)
    public Double getNumOfWins(){
        return this.gameRepository.findNumOfWins();
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWihtOneShift(){
        return this.gameRepository.findNumOfWinsWihtOneShift();
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWithTwoShifts(){
        return this.gameRepository.findNumOfWinsWithTwoShifts();
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWithThreeShifts(){
        return this.gameRepository.findNumOfWinsWithThreeShifts();
    }

    @Transactional(readOnly = true)
    public Double getNumOfWinsWithFourShifts(){
        return this.gameRepository.findNumOfWinsWithFourShifts();
    }

    @Transactional(readOnly = true)
    public Double getAverageShiftsTowin(){
        Double oneShiftWins = this.gameRepository.findNumOfWinsWihtOneShift();
        Double twoShiftWins = this.gameRepository.findNumOfWinsWithTwoShifts();
        Double threeShiftWins = this.gameRepository.findNumOfWinsWithThreeShifts();
        Double fourShiftWins = this.gameRepository.findNumOfWinsWithFourShifts();
        Double wins = this.getNumOfWins();
        Double sum = (double) (4*fourShiftWins + 3*threeShiftWins + 2*twoShiftWins + oneShiftWins);
        Double res = (double) wins>0? sum/wins:0.;
        return res;
    }

    @Transactional(readOnly = true)
    public Double getWinRate(){
        Double res = 0.;
        Double games = this.getNumOfFinishGames();
        Double wins = this.getNumOfWins();
        if(games>0){
            res = (double) wins/games;
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getRankingGame() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> ls = this.gameRepository.getRankingGame(pageable);
        Map<String, Long> res = ls.stream()
                .filter(object -> {
                    String player = (String) object[0];
                    return player != null && !player.equals("draw");
                })
                .collect(Collectors.toMap(
                        object -> (String) object[0], 
                        object -> (Long) object[1]    
                ));

        res = res.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, 
                        LinkedHashMap::new 
                ));
        return res;
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getRankingWin() {
        Pageable pageable = PageRequest.of(0, 11);
        List<Object[]> ls = this.gameRepository.getRankingWin(pageable);
        Map<String, Long> res = ls.stream()
                .filter(object -> {
                    String player = (String) object[0];
                    return player != null && !player.equals("draw");
                })
                .collect(Collectors.toMap(
                        object -> (String) object[0], 
                        object -> (Long) object[1]   
                ));
        res = res.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, 
                        LinkedHashMap::new 
                ));
        return res;
    }

    @Transactional(readOnly = true)
    public List<GameAlone> getAllGames(){
        return (List<GameAlone>) this.gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<GameAlone> getAllGamesDateBeetwenn(LocalDateTime start, LocalDateTime end){
        return (List<GameAlone>) this.gameRepository.findAllByGameDateBetween(start, end);
    }
    public List<GameAlone> getAllGamesDateBefore(LocalDateTime atTime) {
        return this.gameRepository.findAllByGameDateBefore(atTime);
    }
    public List<GameAlone> getAllGamesDateAfter(LocalDateTime atStartOfDay) {
        return this.gameRepository.findAllByGameDateAfter(atStartOfDay);
    }

}
