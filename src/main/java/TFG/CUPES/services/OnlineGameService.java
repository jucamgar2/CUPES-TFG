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

import TFG.CUPES.entities.OnlineGame;
import TFG.CUPES.repositories.OnlineGameRepository;

@Service
public class OnlineGameService {
    
    private OnlineGameRepository onlineGameRepository;

    @Autowired
    public OnlineGameService(OnlineGameRepository onlineGameRepository){
        this.onlineGameRepository = onlineGameRepository;
    }

    @Transactional
    public void save(OnlineGame onlineGame){
        this.onlineGameRepository.save(onlineGame);
    }

    @Transactional(readOnly = true)
    public Optional<OnlineGame> getOnlineGameByid(Integer gameId){
        return this.onlineGameRepository.findById(gameId);
    }

    @Transactional(readOnly = true)
    public List<OnlineGame> getNotStartedGames(){
        return this.onlineGameRepository.findNotStartedGames();
    }

    @Transactional
    public void delete(OnlineGame onlineGame){
        this.onlineGameRepository.delete(onlineGame);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfGames(){
        return this.onlineGameRepository.findNumOfGames();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsByPlayer1(){
        return this.onlineGameRepository.findNumOfWinsByPlayer1();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsByPlayer2(){
        return this.onlineGameRepository.findNumOfWinsByPlayer2();
    }

    @Transactional(readOnly = true)
    public Integer getNumOfPerfectWins(){
        return this.onlineGameRepository.findNumOfPerfectWins();
    }

    @Transactional(readOnly = true)
    public Double getAverageOfShiftsToWinByPlayer1(){
        Double res = this.onlineGameRepository.findAverageOfShiftsToWinByPlayer1();
        if(res == null){
            return 0.;
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Double getAverageOfShiftsToWinByPlayer2(){
        Double res =  this.onlineGameRepository.findAverageOfShiftsToWinByPlayer2();
        if(res == null){
            return 0.;
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Integer getNumOfGamesFromUser(String username){
        return this.onlineGameRepository.findNumOfGamesFromUser(username);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfWinsFromUser(String username){
        return this.onlineGameRepository.findNumOfWinsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Integer getNumOfPerfectWinsFromUser(String username){
        return this.onlineGameRepository.findNumOfPerfectWinsFromUser(username);
    }

    @Transactional(readOnly = true)
    public Double getAverageOfShiftsToWinByPlayer1FromUser(String username){
        Double res = this.onlineGameRepository.findAverageNumOfShiftsToWinByUser(username);
        if(res == null){
            return 0.;
        }
        return res;
    }

    @Transactional(readOnly = true)
    public String getWinRate(String username){
        Integer numOfGames = this.getNumOfGamesFromUser(username);
        Integer numOfWins = this.getNumOfWinsFromUser(username);
        if(numOfGames == 0){
            return "0%";
        }
        return String.format("%.2f", (double)numOfWins/numOfGames*100) + "%";
    }

    @Transactional(readOnly = true)
    public String perfectWinsRate(String username){
        Integer numOfGames = this.getNumOfGamesFromUser(username);
        Integer numOfPerfectWins = this.getNumOfPerfectWinsFromUser(username);
        if(numOfGames == 0){
            return "0%";
        }
        return String.format("%.2f", (double)numOfPerfectWins/numOfGames*100) + "%";
    }

    public Map<String,Long> getRankingGame() {
        Map<String,Long> res = new HashMap<>();
        Pageable pageable = PageRequest.of(0,10);
        List<Object[]> ranking = this.onlineGameRepository.getRankingGame(pageable);
        for(Object[] o : ranking){
            res.put((String)o[0], (Long)o[1]);
        }
        return res;
    }

    public Map<String,Long> getRankingWin() {
        Map<String,Long> res = new HashMap<>();
        Pageable pageable = PageRequest.of(0,10);
        List<Object[]> ranking = this.onlineGameRepository.getRankingWin(pageable);
        for(Object[] o : ranking){
            res.put((String)o[0], (Long)o[1]);
        }
        return res;
    }




    


}