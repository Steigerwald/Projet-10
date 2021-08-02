package com.bibliotheque.service;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.User;
import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.repository.AttenteReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AttenteReservationService {

    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationService.class);

    @Autowired
    AttenteReservationRepository attenteReservationRepository;

    @Autowired
    LivreService livreService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    UserService userService;


    /*Methode pour avoir toutes les attentes de reservation actives de la base de données*/
    public List<AttenteReservation> findAllAttenteReservation() {
        return attenteReservationRepository.findAllByIsactifAttente(true);
    }

    /*Methode pour avoir toutes les attentes de reservation actives par user de la base de données*/
    public List<AttenteReservation> findAllAttenteReservationByUser(User user) {
        return attenteReservationRepository.findAllByUserAndIsactifAttente(user,true);
    }

    /*Methode pour avoir toutes les attentes de reservation actives par livre de la base de données*/
    public List<AttenteReservation> findAllAttenteReservationByLivre(Livre livre) {
        return attenteReservationRepository.findAllByLivreAndIsactifAttente(livre,true);
    }

    /*Methode pour trouver par son id une attente de reservation dans la base de données*/

    /*Methode pour trouver par son id une reservation dans la base de données*/
    public AttenteReservation findAttenteReservationById(int id) {
        return  attenteReservationRepository.findById(id).get();
    }


    /*Methode pour creer une attente de reservation pour un livre de la part d'un user dans la base de données*/
    public AttenteReservation createAttenteReservation(User user,Livre livre) throws RecordNotFoundException {
        int nombreExemplaire=(livreService.getAllExemplairesDUnLivre(livre.getIdLivre()).size());
        List<Livre> listeDExemplairesDisponibles=livreService.getAllExemplairesDidponiblesDUnLivre(livre.getIdLivre());
        List<AttenteReservation> listeDAttente = new ArrayList<AttenteReservation>();
        listeDAttente=findAllAttenteReservationByLivre(livre);
        AttenteReservation entity = new AttenteReservation();
        if (!verifierUserListeDAttente(user,livre)){
            if ((listeDAttente.size()<nombreExemplaire*2) && (nombreExemplaire!=0)){
                if (!(listeDExemplairesDisponibles.size()>0)){
                    logger.info(" création de l'attente dans la liste d'attente");
                    Date today = new Date();
                    entity.setDateAttenteReservation(today);
                    entity.setEtatAttenteReservation("Sur liste d'attente");
                    entity.setDatedelaiDepasse(false);
                    entity.setIsactifAttente(true);
                    entity.setUser(user);
                    //enregistrement de la reservation dans la basse de données
                    logger.info(" retour de l'entité newAttenteReservation de createAttenteReservation qui a été créée et sauvegardée");
                    attenteReservationRepository.save(entity);
                }
            }else{
                System.out.println("la liste d'attente est complète ou il n'y a pas ce livre dans la base");
            }
        }else{
            System.out.println("vous avez déjà une attente pour ce livre");
        }
        return entity;
    }


    /*Methode pour effacer une attente de réservation dans la base de données*/
    public void deleteAttenteReservationById(int id) throws RecordNotFoundException {
        Optional<AttenteReservation> attenteReservationAEffacer = attenteReservationRepository.findById(id);
        if (attenteReservationAEffacer.isPresent()){
            AttenteReservation attenteReservationTrouve = attenteReservationAEffacer.get();
            attenteReservationRepository.deleteById(attenteReservationTrouve.getIdAttenteReservation());
        }else{
            throw new RecordNotFoundException("Pas d'attente enregistrée avec cet Id");
        }
    }

    /*Methode pour annuler une attente de réservation */
    public AttenteReservation annulerAttenteReservation(AttenteReservation entity) throws RecordNotFoundException {
        AttenteReservation attenteReservationAModifier = findAttenteReservationById((entity.getIdAttenteReservation()));
        if (attenteReservationAModifier != null) {
            logger.info(" l'entité attenteReservation à modifier a été trouvée et peut être modifiée");
            attenteReservationAModifier.setIsactifAttente(false);
            return attenteReservationRepository.save(attenteReservationAModifier);
        } else {
            throw new RecordNotFoundException("Pas d'attente trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }


    /*Methode pour déterminer la premiere attente valide de la liste d'attente selon date*/
    public AttenteReservation trouverPremiereAttenteReservation(Livre livre){
        List<AttenteReservation> listeDAttente = new ArrayList<AttenteReservation>();
        listeDAttente=findAllAttenteReservationByLivre(livre);
        AttenteReservation attenteAChoisir=new AttenteReservation();
        for(int i=0;i<listeDAttente.size();i=i+1){
            Date result1=listeDAttente.get(i).getDateAttenteReservation();
            Date result2=listeDAttente.get(i+1).getDateAttenteReservation();

            if (result1.before(result2)) {
                attenteAChoisir= listeDAttente.get(i);
            }else{
                attenteAChoisir= listeDAttente.get(i+1);
            }
        }
        return attenteAChoisir;
    }

    /*Methode pour vérifier que le user n'est pas dans la liste d'attente pour ce livre*/
    public Boolean verifierUserListeDAttente (User user,Livre livre){
        Boolean result = true;
        List<User> listUsersPresent = new ArrayList<User>();
        List<AttenteReservation> listeDAttente = new ArrayList<AttenteReservation>();
        listeDAttente=findAllAttenteReservationByLivre(livre);
        if (listeDAttente.size()>0) {
            for (int i = 0; i < listeDAttente.size(); i = i + 1) {
                if (user.equals(listeDAttente.get(i).getUser())){
                    listUsersPresent.add(listeDAttente.get(i).getUser());
            }
                if (listUsersPresent.size()>0){
                    result=true;
                }else{
                    result=false;
                }
            }
        }else{
            result=false;
        }
        return result;
    }


    /*Methode pour vérifier la date de retrait et la date de disponibilité < 48H*/
    public boolean verfierDateDeRetraitAttente (AttenteReservation entity){
        Date today = new Date();

        return true;
    }


    /*Methode pour modifier l'etat de l'attente en fonction la validité de la date de retrait d'un livre et de la disponibilté*/


}
