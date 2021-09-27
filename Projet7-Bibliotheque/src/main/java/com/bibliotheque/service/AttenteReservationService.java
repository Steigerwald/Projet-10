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

    /*Methode pour avoir toutes les attentes de reservation actives et ordonnées par nom de livre de la base de données*/
    public List<AttenteReservation> findAllAttenteReservationByTitreLivre(Livre livre) {
        return attenteReservationRepository.findAllByTitreLivreAndIsactifAttenteOrderByDateAttenteReservation(livre.getTitre(),true);
    }

    /*Methode pour trouver une attente par id du livre et son user une attente de reservation dans la base de données*/
    public AttenteReservation findAllAttenteReservationByTitreLivreAndIsactifAndUser(Livre livre, User user) {
        return attenteReservationRepository.findByTitreLivreAndIsactifAttenteAndUser(livre.getTitre(),true,user);
    }


    /*Methode pour trouver par son id une reservation dans la base de données*/
    public AttenteReservation findAttenteReservationById(int id) {
        return  attenteReservationRepository.findById(id).get();
    }


    /*Methode pour creer une attente de reservation pour un livre de la part d'un user dans la base de données*/
    public AttenteReservation createAttenteReservation(User user,Livre livre) throws RecordNotFoundException {
        AttenteReservation entity = new AttenteReservation();
        List<AttenteReservation> listeAttenteReservationDeCeLivre =findAllAttenteReservationByTitreLivre(livre);
        logger.info(" valeur de verifierUserListeDAttente "+verifierUserListeDAttente(user,livre));
        logger.info(" valeur de verifierNombreListeAttente "+verifierNombreListeAttente(livre));
        if(!verifierUserListeDAttente(user,livre)&&(verifierNombreListeAttente(livre))&&(!reservationService.verifierReservationByUserBylivre(user,livre))){
            int nouvellePosition = listeAttenteReservationDeCeLivre.size()+1;
            logger.info(" création de l'attente dans la liste d'attente");
            Date today = new Date();
            entity.setDateAttenteReservation(today);
            entity.setEtatAttenteReservation("Sur liste d'attente");
            entity.setDatedelaiDepasse(false);
            entity.setIsactifAttente(true);
            entity.setTitreLivre(livre.getTitre());
            entity.setPositionUser(nouvellePosition);
            entity.setUser(user);
            //enregistrement de la reservation dans la basse de données
            logger.info(" retour de l'entité newAttenteReservation de createAttenteReservation qui a été créée et sauvegardée");
            attenteReservationRepository.save(entity);
            logger.info(" l'attente a été créee !! ");
            return entity;
        }else {
            logger.info(" l'attente n'a pas été créee !! ");
            return entity;
        }
    }

    /*Methode pour modifier une attente de réservation dans la base de données*/
    public AttenteReservation updateAttenteReservation(AttenteReservation entity) throws RecordNotFoundException {
        AttenteReservation reservationAttenteAModifier = findAttenteReservationById((entity.getIdAttenteReservation()));
        if (reservationAttenteAModifier != null) {
            logger.info(" l'entité attente réservation à modifier a été trouvée et peut être modifiée");
            //enregistrement de la reservation dans la basse de données
            logger.info(" retour de la nouvelle entité site de UpdateReservation qui a été sauvegardée et la reservationAModifier était existante");
            return attenteReservationRepository.save(entity);
        } else {
            throw new RecordNotFoundException("Pas d'attente de reservation trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }


    /*Methode pour effacer une attente de réservation dans la base de données*/
    public void deleteAttenteReservationById(int id) throws RecordNotFoundException {
        Optional<AttenteReservation> attenteReservationAEffacer = attenteReservationRepository.findById(id);

        if (attenteReservationAEffacer.isPresent()){
            List<Livre> livresConcernes = livreService.getAllLivresByTitre(attenteReservationAEffacer.get().getTitreLivre());
            AttenteReservation attenteReservationTrouve = attenteReservationAEffacer.get();
            attenteReservationRepository.deleteById(attenteReservationTrouve.getIdAttenteReservation());
            List<AttenteReservation> listAttenteReservation = findAllAttenteReservationByTitreLivre(livresConcernes.get(0));
            for (int i=0;i<listAttenteReservation.size();i=i+1){
                listAttenteReservation.get(i).setPositionUser(i);
                attenteReservationRepository.save(listAttenteReservation.get(i));
            }
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

    /*Methode pour avoir la liste d'attente des users dans l'ordre des priorités*/
    public List<User> ordonnerlisteAttenteUsers (Livre livre) {
        List<User> listeAttenteUser = new ArrayList<>();
        List<AttenteReservation> listeOrdonnee = findAllAttenteReservationByTitreLivre(livre);
        for (int i = 0; i < listeOrdonnee.size(); i = i + 1) {
            listeAttenteUser.add(listeOrdonnee.get(i).getUser());
        }
        return listeAttenteUser;
    }


    /*Methode pour vérifier que le user n'est pas dans la liste d'attente pour ce livre*/
    public Boolean verifierUserListeDAttente (User user,Livre livre){
        Boolean result;
        int position =avoirPositionUser(user,livre);
        if (position==0){
            result=false;
        }else{
            result=true;
        }
        return result;
    }


    /*Methode pour avoir la position d'user sur une liste d'attente d'un livre*/
    public int avoirPositionUser (User user, Livre livre){
        List<User> listeUsers = ordonnerlisteAttenteUsers(livre);
        int position=0;
        Boolean trouve=false;
        int i=0;
        while ((!trouve)&&(i<(listeUsers.size()))){

            if (user.getIdUser()==(listeUsers.get(i).getIdUser())){
                trouve=true;
                position =i+1;
            }
            i =i+1;
        }
        return position;
    }

    /*Methode pour vérifier que le nombre d'attente dans la liste d'attente < 2 fois le nombre d'exemplaires d'un livre*/
    public boolean verifierNombreListeAttente (Livre livre){
        boolean result;
        int nombreExemplaire=(livreService.getAllExemplairesDUnLivre(livre.getIdLivre()).size());
        List<AttenteReservation> listeDAttente = new ArrayList<AttenteReservation>();
        listeDAttente=findAllAttenteReservationByTitreLivre(livre);
        if (listeDAttente.size()<2*nombreExemplaire){
            result=true;
        }else{
            result=false;
        }
        return result;
    }


    /*Methode pour vérifier la date de retrait et la date de disponibilité < 48H*/

}
