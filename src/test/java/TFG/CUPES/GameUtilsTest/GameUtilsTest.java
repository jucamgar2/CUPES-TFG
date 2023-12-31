package TFG.CUPES.GameUtilsTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.services.PositionService;
import TFG.CUPES.components.GameUtils;
import TFG.CUPES.entities.Position;
import TFG.CUPES.repositories.PositionRepository;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameUtilsTest {
    @Autowired
    private PositionService positionService;

    @Autowired 
    private PositionRepository positionRepository;

    private GameUtils GameUtils = new GameUtils();

    @Test
    public void randomImagePortionTest() {
        Position p1 = new Position(0,0);
        Position p2 = new Position(125,0);
        Position p3 = new Position(250,0);
        Position p4 = new Position(375,0);
        Position p5 = new Position(0,125);
        Position p6 = new Position(125,125);
        Position p7 = new Position(250,125);
        Position p8 = new Position(375,125);
        Position p9 = new Position(0,250);
        Position p10 = new Position(125,250);
        Position p11 = new Position(250,250);
        Position p12 = new Position(375,250);
        Position p13 = new Position(0,375);
        Position p14 = new Position(125,375);
        Position p15 = new Position(250,375);
        Position p16 = new Position(375,375);
        List<Position> toSave = List.of(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16);
        positionRepository.saveAll(toSave);

        List<Position> gamePositions = new ArrayList<>();
        List<Position> positions = positionService.findAll();
        Random rand = new Random();
        gamePositions.add(positions.get(rand.nextInt(positions.size())));
        for(int i = 0; i < 4; i++){
            Position p = this.GameUtils.randomImagePortion( gamePositions, positions);
            assert(!gamePositions.contains(p));
            gamePositions.add(p);
        }
    }

    @Test
    public void generateImageStyleTest(){
        String imageSeelected = "imageSelected";
        Position position = new Position(0,0);
        String style = this.GameUtils.generateImageStyle(imageSeelected, position);
        assertEquals(style,
        "backgroud-color: white;background-image: url('imageSelected'); width: 500px; height: 500px; background-position: -0px -0px;");
    }

    @Test
    public void expelPlayerTest(){
        ModelAndView expel = this.GameUtils.expelPlayer();
        assert(expel.getViewName().equals("game/selectMode"));
        assert(expel.getModel().containsKey("errors"));
    }

    @Test
    public void generateImageStyleBlockTest(){
        Position p1 = new Position(0,0);
        Position p2 = new Position(125,0);
        Position p3 = new Position(250,0);
        Position p4 = new Position(375,0);
        Position p5 = new Position(0,125);
        Position p6 = new Position(125,125);
        Position p7 = new Position(250,125);
        Position p8 = new Position(375,125);
        Position p9 = new Position(0,250);
        Position p10 = new Position(125,250);
        Position p11 = new Position(250,250);
        Position p12 = new Position(375,250);
        Position p13 = new Position(0,375);
        Position p14 = new Position(125,375);
        Position p15 = new Position(250,375);
        Position p16 = new Position(375,375);
        List<Position> toSave = List.of(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16);
        positionRepository.saveAll(toSave);
        List<Position> gamePositions = new ArrayList<>();
        List<Position> positions = this.positionService.findAll();
        Comparator<Position> c1 = Comparator.comparing(x->x.getY());
        Comparator<Position> c2 = Comparator.comparing(x->x.getX());
        positions.sort(c1.thenComparing(c2));
        gamePositions.add(positions.get(0));
        String style = this.GameUtils.generateImageStyle(positions, gamePositions);
        String res = "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:0px;left: 125px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:0px;left: 250px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:0px;left: 375px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:125px;left: 0px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:125px;left: 125px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:125px;left: 250px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:125px;left: 375px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:250px;left: 0px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:250px;left: 125px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:250px;left: 250px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:250px;left: 375px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:375px;left: 0px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:375px;left: 125px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:375px;left: 250px;'><img src='/images/int.png'></img></div>";
        res += "<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:375px;left: 375px;'><img src='/images/int.png'></img></div>";
        assertEquals(style, res);
    }

    @Test
    public void checkImageHasMoreThan1ColorTest(){
        String imageSelected = "/images/Logo/OL.jpg";
        List<Position> positions = this.positionService.findAll();
        for(Position p : positions){
            try{
                assert(this.GameUtils.checkImageHasMoreThan1Color(imageSelected, p));
            }catch(Exception e){
                assertEquals(1, 2);
            }
        }
    }

    @Test
    public void checkImageHasOnly1ColorTest(){
        String imageSelected = "/images/Logo/Celta.jpg";
        Position position = new Position();
        position.setX(0);
        position.setY(0);
        Position position2 = new Position(0,250);
        Position position3 = new Position(0,375);
        try{
            assert(!this.GameUtils.checkImageHasMoreThan1Color(imageSelected, position));
            assert(!this.GameUtils.checkImageHasMoreThan1Color(imageSelected, position2));
            assert(!this.GameUtils.checkImageHasMoreThan1Color(imageSelected, position3));
        }catch(Exception e){
            assertEquals(1, 2);
        }
    }

}
