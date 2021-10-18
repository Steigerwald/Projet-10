package serviceTest;

import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.User;
import com.bibliotheque.repository.AttenteReservationRepository;
import com.bibliotheque.service.AttenteReservationService;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.ReservationService;
import com.bibliotheque.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
public class AttenteReservationServiceTest {

    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationServiceTest.class);


    @Mock
    private AttenteReservationService attenteReservationService;

    @Mock
    private AttenteReservationRepository attenteReservationRepository;

    //@Mock
    //private User user;

    //@Mock
    //private Livre livre;

    @InjectMocks
    private User newUser;

    @InjectMocks
    private Livre newLivre;

    @Mock
    private AttenteReservation attenteReservation;


    @Before
    public void init () throws ParseException {

        newUser.setNomUser("Pierre");
        newUser.setPrenomUser("Jean");
        newLivre.setTitre("Dune");
        newLivre.setEtatLivre("normal");
        newLivre.setDisponibilite(false);
        attenteReservation.setIsactifAttente(true);
        attenteReservation.setDatedelaiDepasse(false);
        attenteReservation.setPositionUser(1);
        attenteReservation.setEtatAttenteReservation("Sur liste attente");
        Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-15");
        attenteReservation.setDateAttenteReservation(simpleDateFormat);
        attenteReservation.setTitreLivre(newLivre.getTitre());
        attenteReservation.setUser(newUser);
        List<AttenteReservation> allAttenteReservation = new ArrayList<>();
        allAttenteReservation.add(attenteReservation);
        logger.info(" valeur de allAttenteReservation "+allAttenteReservation);
    }

    @ Test
    public void verificationPositionUser(){
        // arrange

        // act
        int result = attenteReservationService.avoirPositionUser (newUser, newLivre);
        logger.info(" valeur de result de verificationPositionUser "+result);
        String resultString = "";
        resultString=resultString.valueOf(result);
        // assert
        //Assert.assertTrue("true",result);
        assertThat("0").isEqualTo(resultString);
    }


    @Test
    public void verificationUserListeDAttenteTrue() throws ParseException {

        // arrange

        // act
        Boolean result = attenteReservationService.verifierUserListeDAttente (newUser, newLivre);
        logger.info(" valeur de result de verificationUserListeDAttenteTrue "+result);
        // assert
        //Assert.assertTrue("true",result);
        assertThat("false").isEqualTo(result.toString());
    }

}
