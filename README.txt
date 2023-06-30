*****************************************************************************************************************************
	 			 __    __   _______ ___   ___      _______      ___      .___  ___.  _______ 		    *
				|  |  |  | |   ____|\  \ /  /     /  _____|    /   \     |   \/   | |   ____|               *
				|  |__|  | |  |__    \  V  /     |  |  __     /  ^  \    |  \  /  | |  |__   		    *
				|   __   | |   __|    >   <      |  | |_ |   /  /_\  \   |  |\/|  | |   __|  		    *
				|  |  |  | |  |____  /  .  \     |  |__| |  /  _____  \  |  |  |  | |  |____ 		    *
				|__|  |__| |_______|/__/ \__\     \______| /__/     \__\ |__|  |__| |_______|		    *
                                                                             						    *
*****************************************************************************************************************************

-𝙑𝙊𝙐𝙎 𝘿𝙀𝙑𝙀𝙕 𝘼𝙑𝙊𝙄𝙍 𝙅𝘼𝙑𝘼 𝙎𝙐𝙍 𝙑𝙊𝙏𝙍𝙀 𝙋𝘾 𝙋𝙊𝙐𝙍 𝙋𝙊𝙐𝙑𝙊𝙄𝙍 𝙇𝘼𝙉𝘾𝙀𝙍 𝙇𝙀 𝙅𝙀𝙐 𝘼𝙑𝙀𝘾 𝙇𝙀 𝙁𝙄𝘾𝙃𝙄𝙀𝙍 𝙃𝙀𝙓


CLASSES :
    Hex: *charToInt: renvoie l'ordre du charactere (en utilisant la table ascii)
	 *canPlay: verifie si on peut jouer cette case
	 *play: joue la case
	 *init_balayage: copie arene dans zone et initialise la vague
	 *voisin: marque tout les voisins de la meme couleur du jeton
	 *balayage_succes: verifie la derniere colonne/ligne si un chemin est arrivé
	 *verify: combinaison des trois précedentes pour verifier le gagnant 


   Game:*launchPVP: lance partie de deux joueurs
	*launch1P: lance partie d'un jouer (contre IA)
	*lanchOnline: lance notre IA contre un autre sur un channel
	*playTurn: teste la case jouer et donner 3 essai, verification apres
	*read: lecture d'un coup de la forme '1A'
	*nextTurn: print le jeu
	*swap: regle du SWAP
	*CPUPlay: random play sur la game
	*readOnline: lecture des coup sur le channel droit
	*onlinePlay: jouer le coup lu enligne et verification
	*CPUPlayOnline: Utilisation du CPUPlay + envoi des coord joué sur le channel gauche


   Player:*giveTurn: donner le tour et la couleur a un joueur


   Tournament:*start: lancer un enchainement de parties
	      *end: affichage des gagnants
	      *lookForNewGame: un affichage qui simule une recherche du prochain game
	      *matchEnd: un autre affichage qui affiche la fin du tournoi

   Online:*start: creation de deux channel(gauche/droite) a partir d'un nom de channel et lancement d'une partie online
