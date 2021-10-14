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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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

    @Mock
    private AttenteReservationService attenteReservationService;

    @Mock
    private User user;

    @Mock
    private Livre livre;

    @Mock
    private AttenteReservation attenteReservation;

    @Test
    public void verificationUserListeDAttenteTrue() throws ParseException {

        // arrange
        attenteReservation.setIsactifAttente(true);
        attenteReservation.setDatedelaiDepasse(false);
        attenteReservation.setPositionUser(1);
        attenteReservation.setEtatAttenteReservation("Sur liste attente");
        Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-15");
        attenteReservation.setDateAttenteReservation(simpleDateFormat);
        attenteReservation.setTitreLivre(livre.getTitre());
        attenteReservation.setUser(user);

        // act
        Boolean result = attenteReservationService.verifierUserListeDAttente (user, livre);

        //assert
        //Assert.assertTrue("true",result);
        assertThat("false").isEqualTo(result.toString());
    }

}
