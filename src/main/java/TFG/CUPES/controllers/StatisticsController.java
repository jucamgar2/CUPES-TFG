package TFG.CUPES.controllers;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.services.GameAloneService;
import TFG.CUPES.services.OnlineGameService;


@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final String STATISTICS_VIEW = "statistics/statistics";
    private final String STATISTICS_RANKING_VIEW_GA = "statistics/statisticsRankingGA";

    private  OnlineGameService onlineGameService;
    private  GameAloneService gameAloneService;

    @Autowired
    public StatisticsController(OnlineGameService onlineGameService, GameAloneService gameAloneService) {
        this.onlineGameService = onlineGameService;
        this.gameAloneService = gameAloneService;
    }

    @GetMapping("")
    public ModelAndView statistics(Principal principal) {
        ModelAndView res = new ModelAndView(STATISTICS_VIEW);
        if(principal!= null){
            String username = principal.getName();
            res = addGameAloneStatisticsByUser(res, username); 
            res = addOnlineGameStatisticsByUser(res, username);
        }
        res = addGameAloneGeneralStatistics(res);
        res = addOnlineGameGeneralStatistics(res);
        
        return res;
    }

    @GetMapping("/GA/ranking")
    public ModelAndView gameAloneRanking(){
        ModelAndView res = new ModelAndView(STATISTICS_RANKING_VIEW_GA);
        res.addObject("msg","Ranking de partidas en solitario");
        res.addObject("rankingGame", this.gameAloneService.getRankingGame());
        res.addObject("rankingWin", this.gameAloneService.getRankingWin());
        return res;
    }

    @GetMapping("/GO/ranking")
    public ModelAndView onlineGameRanking(){
        ModelAndView res = new ModelAndView(STATISTICS_RANKING_VIEW_GA);
        res.addObject("msg","Ranking de partidas en línea");
        res.addObject("rankingGame", this.onlineGameService.getRankingGame());
        res.addObject("rankingWin", this.onlineGameService.getRankingWin());
        return res;
    }



    public ModelAndView addOnlineGameStatisticsByUser(ModelAndView res, String username) {
        res.addObject("userOnlineGames", this.onlineGameService.getNumOfGamesFromUser(username));
        res.addObject("userWins", this.onlineGameService.getNumOfWinsFromUser(username));
        res.addObject("userPerfectWins", this.onlineGameService.getNumOfPerfectWinsFromUser(username));
        res.addObject("userAverageOfShiftsToWinByPlayer1", this.onlineGameService.getAverageOfShiftsToWinByPlayer1FromUser(username));
        res.addObject("userWinRate",this.onlineGameService.getWinRate(username));
        res.addObject("perfectWinRate",this.onlineGameService.perfectWinsRate(username));
        return res;
    }

    private ModelAndView addOnlineGameGeneralStatistics(ModelAndView res) {
        res.addObject("totalOnlineGames", this.onlineGameService.getNumOfGames());
        res.addObject("totalOnlineWinsByPlayer1", this.onlineGameService.getNumOfWinsByPlayer1());
        res.addObject("totalOnlineWinsByPlayer2", this.onlineGameService.getNumOfWinsByPlayer2());
        res.addObject("totalOnlinePerfectWins", this.onlineGameService.getNumOfPerfectWins());
        res.addObject("totalOnlineAverageOfShiftsToWinByPlayer1", this.onlineGameService.getAverageOfShiftsToWinByPlayer1());
        res.addObject("totalOnlineAverageOfShiftsToWinByPlayer2", this.onlineGameService.getAverageOfShiftsToWinByPlayer2());
        return res;
    }

    public ModelAndView addGameAloneStatisticsByUser(ModelAndView res, String username){
        res.addObject("userGames", this.gameAloneService.getNumOfGamesFromUser(username));
        res.addObject("userWins1", this.gameAloneService.getNumOfWinsFromUser(username));
        res.addObject("userWinsWithOneShift", this.gameAloneService.getNumOfWinsWihtOneShiftFromUser(username));
        res.addObject("userWinsWithTwoShifts", this.gameAloneService.getNumOfWinsWithTwoShiftsFromUser(username));
        res.addObject("userWinsWithThreeShifts", this.gameAloneService.getNumOfWinsWithThreeShiftsFromUser(username));
        res.addObject("userWinsWithFourShifts", this.gameAloneService.getNumOfWinsWithFourShiftsFromUser(username));
        res.addObject("userWinRate", this.gameAloneService.getWinRateFromUser(username) *100);
        res.addObject("userAverageShiftsToWin", this.gameAloneService.getAverageShiftsTowinFromUser(username));
        return res;
    }

    public ModelAndView addGameAloneGeneralStatistics(ModelAndView res){
        res.addObject("totalGames", this.gameAloneService.getNumOfFinishGames());
        res.addObject("totalWins", this.gameAloneService.getNumOfWins());
        res.addObject("totalWinsWithOneShift", this.gameAloneService.getNumOfWinsWihtOneShift());
        res.addObject("totalWinsWithTwoShifts", this.gameAloneService.getNumOfWinsWithTwoShifts());
        res.addObject("totalWinsWithThreeShifts", this.gameAloneService.getNumOfWinsWithThreeShifts());
        res.addObject("totalWinsWithFourShifts", this.gameAloneService.getNumOfWinsWithFourShifts());
        res.addObject("totalWinRate", this.gameAloneService.getWinRate() *100);
        res.addObject("totalAverageShiftsToWin", this.gameAloneService.getAverageShiftsTowin());
        return res;
    }


    
}
