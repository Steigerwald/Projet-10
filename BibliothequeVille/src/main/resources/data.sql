USE bibliotheque;

SET FOREIGN_KEY_CHECKS=0;

TRUNCATE TABLE bibliotheque.TBL_ROLE;
TRUNCATE TABLE bibliotheque.TBL_USER;
TRUNCATE TABLE bibliotheque.TBL_BIBLIOTHEQUE;
TRUNCATE TABLE bibliotheque.TBL_BIBLIOTHEQUE_USERS;
TRUNCATE TABLE bibliotheque.TBL_LIVRE;
TRUNCATE TABLE bibliotheque.TBL_RESERVATION;


INSERT INTO bibliotheque.TBL_ROLE (id_role, actif_role,nom_role) VALUES
(1,true, 'ROLE_ADMIN'),
(2,true, 'ROLE_USER');

INSERT INTO bibliotheque.TBL_USER (id_user,actif_user, mail_user, mot_passe, nom_user,prenom_user,role_id_role) VALUES
(1,true, 'admin@gmail.com', '$2a$10$GQXfIS9n2oJuE1lVI70yFeu5iJn/PEm5B0CUrfGEUT7bSRcsuqCnS', 'Steigerwald','Brice',1),
(2,true, 'user@gmail.com', '$2a$10$GQXfIS9n2oJuE1lVI70yFeu5iJn/PEm5B0CUrfGEUT7bSRcsuqCnS', 'Steigerwald','Jacques',2),
(3,true, 'tara@gmail.com', '$2a$10$GQXfIS9n2oJuE1lVI70yFeu5iJn/PEm5B0CUrfGEUT7bSRcsuqCnS', 'Hot','Tara',2),
(4,true, 'jean@gmail.com', '$2a$10$GQXfIS9n2oJuE1lVI70yFeu5iJn/PEm5B0CUrfGEUT7bSRcsuqCnS', 'Pierre','Jean',2),
(5,true, 'anne@gmail.com', '$2a$10$GQXfIS9n2oJuE1lVI70yFeu5iJn/PEm5B0CUrfGEUT7bSRcsuqCnS', 'Priska','Anne',2);

INSERT INTO bibliotheque.TBL_BIBLIOTHEQUE (id_bibliotheque, adresse, lieu, nom_bibliotheque) VALUES
(1, '140 AVENUE CHARLES DE GAULLE', 'BRUGES', 'ESPACE DE BRUGES'),
(2, '38 RUE EMILE COUDORD', 'BORDEAUX', 'ESPACE CULTUREL DE BORDEAUX'),
(3, '50 AVENUE GENERAL LECLERC', 'LE BOUSCAT', 'TECHNOSPACE CULTUREL');

INSERT INTO bibliotheque.TBL_BIBLIOTHEQUE_USERS (bibliotheques_id_bibliotheque, users_id_user) VALUES
(1,1),
(2,2),
(3,1),
(1,4),
(2,5);

INSERT INTO bibliotheque.TBL_LIVRE (id_livre, auteur, date_achat,description,disponibilite,etat_livre,nom_categorie,nombre_pages,prix_location,titre, bibliotheque_id_bibliotheque,reservation_id_reservation) VALUES
(1, 'FRANK HERBERT', '2018-04-28 02:45:30','livre de science fiction inspirée des space opéras et qui mélangent l"action, les intrigues et amour',false,'neuf','Science fiction',320,10,'Dune',1,1),
(2, 'ISAAC ASIMOV', '2019-04-28 02:45:30','livre de science fiction qui mélange les sciences et les intrigues',true,'normal','Science fiction',290,10,'Fondation',1,null),
(3, 'AGATHA CHRISTIE', '2011-04-28 02:45:30','livre d"enquête policière',false,'normal','Policier',242,10,'Une poignée de seigle',1,2),
(4, 'REGINE DEFORGES', '2015-04-28 02:45:30','Roman qui raconte la vie d"une petite gamine cummulant les fragiltés d"une adolescente et les intrigues',false,'normal','Roman',463,15,'la bicyclette bleue',2,3),
(5, 'AGATHA CHRISTIE', '2007-04-28 02:45:30','livre d"enquête policière de Hercule Poirot',false,'normal','Policier',247,10,'Le couteau sur la nuque',2,3),
(6, 'AGATHA CHRISTIE', '2005-04-28 02:45:30',' livre d"enquête policière',true,'normal','Policier',246,10,'Pourquoi pas Evans?',1,null),
(7, 'IRENE ADLER', '2018-04-28 02:45:30','livre d"enquête policière à la Sherlock',true,'neuf','Policier',259,10,'El Trio de la Dama Negra',2,null),
(8, 'ANTOINE GALLAND', '2001-04-28 02:45:30','livre pour enfants des contes des Mille et une Nuits',false,'vieux','Contes',169,10,'Histoire d"Aladdin ou la lampe merveilleuse',3,4),
(9, 'MERIMEE', '2000-04-28 02:45:30','livre de nouvelles de Mérimée dans l"ambiance du Sud de la France',false,'normal','Nouvelles',479,10,'Colomba et dix autres nouvelles',3,4),
(10, 'FREDERIC DART', '1980-04-28 02:45:30','livre de 5 histoires de San Antonio',true,'vieux','Policier',635,15,'SAN ANTONIO Oeuvres Complètes Tome 2',3,null),
(11, 'FRANK HERBERT', '2018-06-28 02:45:30','livre de science fiction inspirée des space opéras et qui mélangent l"action, les intrigues et amour',true,'neuf','Science fiction',320,10,'Dune',1,null),
(12, 'FRANK HERBERT', '2018-05-28 02:45:30','livre de science fiction inspirée des space opéras et qui mélangent l"action, les intrigues et amour',true,'normal','Science fiction',320,10,'Dune',2,null);


INSERT INTO bibliotheque.TBL_RESERVATION (id_reservation, date_retrait, date_reservation,delai_location,etat_reservation,user_id_user,isactif) VALUES
(1,'2020-06-28 02:45:30','2020-06-20 02:45:30',15,'en cours de pret',1,true),
(2,'2020-07-20 02:45:30','2020-07-15 02:45:30',15,'en cours de pret',1,true),
(3,'2020-05-28 02:45:30','2020-05-20 02:45:30',15,'en cours de pret',2,true),
(4,'2020-07-27 02:45:30','2020-07-20 02:45:30',15,'en cours de pret',3,true);


SET FOREIGN_KEY_CHECKS=1;