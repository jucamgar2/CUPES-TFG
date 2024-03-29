package TFG.CUPES.services;
import java.text.DecimalFormat;
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
        List<OnlineGame> wins = this.onlineGameRepository.findWinsByUsername(username);
        if (wins.isEmpty()){
            return 0.;
        }else{
            Double res = 0.;
            for(OnlineGame g : wins){
                if(username.equals(g.getPlayer1().getUsername())){
                    res += g.getPlayer1Shifts();
                }else{
                    res += g.getPlayer2Shifts();
                }
            }
            return res/wins.size();
        }
    }

    @Transactional(readOnly = true)
    public String getWinRate(String username){
        Integer numOfGames = this.getNumOfGamesFromUser(username);
        Integer numOfWins = this.getNumOfWinsFromUser(username);
        if(numOfGames == 0){
            return "0%";
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format((double)numOfWins/numOfGames*100) + "%";
    }

    @Transactional(readOnly = true)
    public String perfectWinsRate(String username){
        Integer numOfGames = this.getNumOfGamesFromUser(username);
        Integer numOfPerfectWins = this.getNumOfPerfectWinsFromUser(username);
        if(numOfGames == 0){
            return "0%";
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format((double)numOfPerfectWins/numOfGames*100) + "%";
    }

    public Map<String,Long> getRankingGame() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> ranking = this.onlineGameRepository.getRankingGame(pageable);
        Map<String, Long> res = ranking.stream()
                .collect(Collectors.toMap(
                        o -> (String) o[0],
                        o -> (Long) o[1]    
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

    public Map<String,Long> getRankingWin() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> ranking = this.onlineGameRepository.getRankingWin(pageable);


        Map<String, Long> res = ranking.stream()
                .collect(Collectors.toMap(
                        o -> (String) o[0], 
                        o -> (Long) o[1]    
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
    public List<OnlineGame> getStaleGames(){
        return this.onlineGameRepository.findStaleGames();
    }

    public List<OnlineGame> getAllOnlineGames() {
        return (List<OnlineGame>) this.onlineGameRepository.findAll();
    }

}