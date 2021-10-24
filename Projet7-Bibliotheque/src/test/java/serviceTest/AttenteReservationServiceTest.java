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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private AttenteReservationRepository attenteReservationRepository;

    @InjectMocks
    private AttenteReservationService objetTotest;

    @InjectMocks
    private User newUser;

    @InjectMocks
    private Livre newLivre;

    @InjectMocks
    private AttenteReservation attenteReservation;


    @Before
    public void init () throws ParseException {

        newUser = new User();
        newUser.setPrenomUser("Pierre");
        newUser.setNomUser("Jean");
        newLivre= new Livre();
        newLivre.setTitre("Dune");
        newLivre.setEtatLivre("normal");
        newLivre.setDisponibilite(false);

        attenteReservation = new AttenteReservation();
        attenteReservation.setIsactifAttente(true);
        attenteReservation.setDatedelaiDepasse(false);
        attenteReservation.setPositionUser(1);
        attenteReservation.setEtatAttenteReservation("Sur liste attente");
        Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-15");
        attenteReservation.setDateAttenteReservation(simpleDateFormat);
        attenteReservation.setTitreLivre(newLivre.getTitre());
        attenteReservation.setUser(newUser);


        /*
        Mockito.when(newUser.getPrenomUser()).thenReturn("Pierre");
        Mockito.when(newUser.getNomUser()).thenReturn("Jean");
        Mockito.when(newLivre.getTitre()).thenReturn("Dune");
        Mockito.when(newLivre.getEtatLivre()).thenReturn("normal");
        Mockito.when(newLivre.getDisponibilite()).thenReturn(false);

        Mockito.when(attenteReservation.getIsactifAttente()).thenReturn(true);
        Mockito.when(attenteReservation.getDatedelaiDepasse()).thenReturn(false);
        Mockito.when(attenteReservation.getPositionUser()).thenReturn(1);
        Mockito.when(attenteReservation.getEtatAttenteReservation()).thenReturn("Sur liste attente");
        Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-15");
        Mockito.when(attenteReservation.getDateAttenteReservation()).thenReturn(simpleDateFormat);
        Mockito.when(attenteReservation.getTitreLivre()).thenReturn(newLivre.getTitre());
        Mockito.when(attenteReservation.getUser()).thenReturn(newUser);

 */

        logger.info(" valeur de attenteReservation "+attenteReservation.toString());
    }


    @Test
    public void verificationFindAllAttenteReservationByTitreLivre(){
        // arrange
        List<AttenteReservation> listAttenteReservation =new ArrayList<>();
        listAttenteReservation.add(attenteReservation);
        Mockito.when(attenteReservationRepository.findAllByTitreLivreAndIsactifAttenteOrderByDateAttenteReservation(newLivre.getTitre(),true)).thenReturn(listAttenteReservation);

        // act
        List<AttenteReservation> result =objetTotest.findAllAttenteReservationByTitreLivre(newLivre);

        // assert
        assertThat(listAttenteReservation).isEqualTo(result);
    }


    @Test
    public void verificationFindAllAttenteReservationByUser(){
        // arrange
        List<AttenteReservation> listAttenteReservation =new ArrayList<>();
        listAttenteReservation.add(attenteReservation);
        Mockito.when(attenteReservationRepository.findAllByUserAndIsactifAttente(newUser,true)).thenReturn(listAttenteReservation);

        // act
        List<AttenteReservation> result =objetTotest.findAllAttenteReservationByUser(newUser);

        // assert
        assertThat(listAttenteReservation).isEqualTo(result);
    }


    @Test
    public void verificationFindAllAttenteReservationByTitreLivreAndIsactifAndUser(){
        // arrange
        Mockito.when(attenteReservationRepository.findByTitreLivreAndIsactifAttenteAndUser(newLivre.getTitre(),true,newUser)).thenReturn(attenteReservation);

        // act
        AttenteReservation result =objetTotest.findAllAttenteReservationByTitreLivreAndIsactifAndUser(newLivre,newUser);

        // assert
        assertThat(attenteReservation).isEqualTo(result);
    }


    @Test
    public void verificationPositionUser(){
        // arrange

        // act
        int result = objetTotest.avoirPositionUser (newUser, newLivre);
        logger.info(" valeur de result de verificationPositionUser "+result);
        String resultString = "";
        resultString=resultString.valueOf(result);

        // assert
        //Assert.assertTrue("true",result);
        assertThat("0").isEqualTo(resultString);
    }






}
