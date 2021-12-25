# OpenClassRoom projet 10

Projet de conception d'un site Bibliothèque

## 1. Comment lancer l'application:
```
git clone https://github.com/Steigerwald/Projet-10/Projet7-Bibliotheque.git
cd Projet7-Bibliotheque
mvn spring-boot:run
```
## 2. Comment lancer la base de données
L'application nécessite une base de données MySQL avec les informations suivantes:
- nom de la base de données: bibliotheques
- port: 9090
- user: root
- password: Admin!1973

lancer ensuite l'application pour qu'elle crée les tables gràce aux entités

## 3. Remplir la base de données avec un jeu de données Test

le fichier data.sql situé dans le dossier ressources s'exécute automatiquement.

informations de connection:

role admin: 
- identifiant: admin@gmail.com 
- password: coco

role user: 
- identifiant: user@gmail.com
- password: coco

## 5. Il faudra lancer les applications
L'application nécessite une application front end pour visualiser les données de l'application avec les informations suivantes:
Voir https://github.com/Steigerwald/Projet-10/FrontEnd.git
- nom de l'application: frontEnd
- port: 3030
depuis le net : localhost:3030/home

L'application est aussi upgradée par une application batch pour traiter des données de façon horodatée et automatique:
Voir https://github.com/Steigerwald/Projet-10/batchMail.git
- nom de l'application: batchmail
