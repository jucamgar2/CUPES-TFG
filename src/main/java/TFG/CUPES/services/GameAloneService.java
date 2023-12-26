package TFG.CUPES.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Integer getNumOfGamesFromUser(String username){
        return this.gameRepository.findNumOfGamesFromUser(username);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsFromUser(String username){
        return this.gameRepository.findNumOfWinsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWihtOneShiftFromUser(String username){
        return this.gameRepository.findNumOfWinsWihtOneShiftFromUser(username);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWithTwoShiftsFromUser(String username){
        return this.gameRepository.findNumOfWinsWithTwoShiftsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWithThreeShiftsFromUser(String username){
        return this.gameRepository.findNumOfWinsWithThreeShiftsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWithFourShiftsFromUser(String username){
        return this.gameRepository.findNumOfWinsWithFourShiftsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getAverageShiftsTowinFromUser(String username){
        Integer oneShiftWins = this.gameRepository.findNumOfWinsWihtOneShiftFromUser(username);
        Integer twoShiftWins = this.gameRepository.findNumOfWinsWithTwoShiftsFromUser(username);
        Integer threeShiftWins = this.gameRepository.findNumOfWinsWithThreeShiftsFromUser(username);
        Integer fourShiftWins = this.gameRepository.findNumOfWinsWithFourShiftsFromUser(username);
        Integer wins = this.getNumOfWinsFromUser(username);
        Double sum = (double) (4*fourShiftWins + 3*threeShiftWins + 2*twoShiftWins + oneShiftWins);
        Double res = (double) wins>0? sum/wins:0.;
        return res;
    }

    public Double getWinRateFromUser(String username){
        Double res=0.;
        Integer games = this.getNumOfGamesFromUser(username);
        Integer wins = this.getNumOfWinsFromUser(username);
        if(games>0){
            res = (double) wins/games;
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Integer getNumOfFinishGames(){
        return this.gameRepository.findNumOfFinishGames();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWins(){
        return this.gameRepository.findNumOfWins();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWihtOneShift(){
        return this.gameRepository.findNumOfWinsWihtOneShift();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWithTwoShifts(){
        return this.gameRepository.findNumOfWinsWithTwoShifts();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWithThreeShifts(){
        return this.gameRepository.findNumOfWinsWithThreeShifts();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsWithFourShifts(){
        return this.gameRepository.findNumOfWinsWithFourShifts();
    }

    @Transactional(readOnly = true)
    public Double getAverageShiftsTowin(){
        Integer oneShiftWins = this.gameRepository.findNumOfWinsWihtOneShift();
        Integer twoShiftWins = this.gameRepository.findNumOfWinsWithTwoShifts();
        Integer threeShiftWins = this.gameRepository.findNumOfWinsWithThreeShifts();
        Integer fourShiftWins = this.gameRepository.findNumOfWinsWithFourShifts();
        Integer wins = this.getNumOfWins();
        Double sum = (double) (4*fourShiftWins + 3*threeShiftWins + 2*twoShiftWins + oneShiftWins);
        Double res = (double) wins>0? sum/wins:0.;
        return res;
    }

    @Transactional(readOnly = true)
    public Double getWinRate(){
        Double res = 0.;
        Integer games = this.getNumOfFinishGames();
        Integer wins = this.getNumOfWins();
        if(games>0){
            res = (double) wins/games;
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Map<String,Long> getRankingGame() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> ls = this.gameRepository.getRankingGame(pageable);
        Map<String, Long> res = new HashMap<String,Long>();
        for (Object[] object:ls){
            String player = (String) object[0];
            if(player!=null && player!="draw"){
                Long num = (Long) object[1];
                res.put(player,num);
            }
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Map<String,Long> getRankingWin() {
        Pageable pageable = PageRequest.of(0,11);
        List<Object[]> ls = this.gameRepository.getRankingWin(pageable);
        Map<String, Long> res = new HashMap<String,Long>();
        for (Object[] object:ls){
            String player = (String) object[0];
            if(player!=null && player!="draw"){
                Long num = (Long) object[1];
            res.put(player,num);
            }
        }
        return res;
    }


}
