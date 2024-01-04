package TFG.CUPES.controllers;


import java.security.Principal;
import java.text.DecimalFormat;

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
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat iformat = new DecimalFormat("#");

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
        res.addObject("userOnlineGames", iformat.format( this.onlineGameService.getNumOfGamesFromUser(username)));
        res.addObject("userWins",iformat.format( this.onlineGameService.getNumOfWinsFromUser(username)));
        res.addObject("userPerfectWins", iformat.format(this.onlineGameService.getNumOfPerfectWinsFromUser(username)));
        res.addObject("userAverageOfShiftsToWinByPlayer1", df.format(this.onlineGameService.getAverageOfShiftsToWinByPlayer1FromUser(username)));
        res.addObject("userWinRate",this.onlineGameService.getWinRate(username));
        res.addObject("perfectWinRate",this.onlineGameService.perfectWinsRate(username));
        return res;
    }

    private ModelAndView addOnlineGameGeneralStatistics(ModelAndView res) {
        res.addObject("totalOnlineGames", iformat.format(this.onlineGameService.getNumOfGames()));
        res.addObject("totalOnlineWinsByPlayer1", iformat.format(this.onlineGameService.getNumOfWinsByPlayer1()));
        res.addObject("totalOnlineWinsByPlayer2", iformat.format(this.onlineGameService.getNumOfWinsByPlayer2()));
        res.addObject("totalOnlinePerfectWins", iformat.format(this.onlineGameService.getNumOfPerfectWins()));
        res.addObject("totalOnlineAverageOfShiftsToWinByPlayer1", df.format(this.onlineGameService.getAverageOfShiftsToWinByPlayer1()));
        res.addObject("totalOnlineAverageOfShiftsToWinByPlayer2", df.format(this.onlineGameService.getAverageOfShiftsToWinByPlayer2()));
        return res;
    }

    public ModelAndView addGameAloneStatisticsByUser(ModelAndView res, String username){
        res.addObject("userGames", iformat.format(this.gameAloneService.getNumOfGamesFromUser(username)));
        res.addObject("userWins1", iformat.format(this.gameAloneService.getNumOfWinsFromUser(username)));
        res.addObject("userWinsWithOneShift", iformat.format(this.gameAloneService.getNumOfWinsWihtOneShiftFromUser(username)));
        res.addObject("userWinsWithTwoShifts", iformat.format(this.gameAloneService.getNumOfWinsWithTwoShiftsFromUser(username)));
        res.addObject("userWinsWithThreeShifts", iformat.format(this.gameAloneService.getNumOfWinsWithThreeShiftsFromUser(username)));
        res.addObject("userWinsWithFourShifts", iformat.format(this.gameAloneService.getNumOfWinsWithFourShiftsFromUser(username)));
        res.addObject("uwr", df.format(this.gameAloneService.getWinRateFromUser(username)*100));
        res.addObject("userAverageShiftsToWin", df.format(this.gameAloneService.getAverageShiftsTowinFromUser(username)));
        return res;
    }

    public ModelAndView addGameAloneGeneralStatistics(ModelAndView res){
        res.addObject("totalGames", iformat.format(this.gameAloneService.getNumOfFinishGames()));
        res.addObject("totalWins", iformat.format(this.gameAloneService.getNumOfWins()));
        res.addObject("totalWinsWithOneShift", iformat.format(this.gameAloneService.getNumOfWinsWihtOneShift()));
        res.addObject("totalWinsWithTwoShifts", iformat.format(this.gameAloneService.getNumOfWinsWithTwoShifts()));
        res.addObject("totalWinsWithThreeShifts", iformat.format(this.gameAloneService.getNumOfWinsWithThreeShifts()));
        res.addObject("totalWinsWithFourShifts", iformat.format(this.gameAloneService.getNumOfWinsWithFourShifts()));
        res.addObject("totalWinRate", df.format(this.gameAloneService.getWinRate() *100));
        res.addObject("totalAverageShiftsToWin", df.format(this.gameAloneService.getAverageShiftsTowin()));
        return res;
    }


    
}
