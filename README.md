# ADS4-Dessin
Projet de ADS4 (JFlex)

##Résumé
Le but du projet était de créé à l'aide d'un analyseur syntaxique un interpréteur permettant la création d'image.
L'interpréteur va prendre ses instructions dans un fichier texte et dessiner à l'écran une image construite à partir de ces instructions.
*Voir fichier Exemple*

##Compilation


Le fichier .flex est déjà compilé, mais en cas de besoin :

```jflex image.flex```

Pour compiler le programme :

```javac *.java```

Pour executer le programme:

```java Main```

Pour interpréter un fichier, il faut se rendre dans :

Fichier > Ouvrir

Il est également possible d’ouvrir un fichier directement dans le terminal avec

```java Main <fichier>```


##Syntaxe

```Var <String>;```

Crée la variable <String>

```
Debut 
…
Fin
```
Délimite les instructions

``` BasPinceau;/HautPinceau; ```

Active/Désactive le dessin

```<Variable> = <Int>;```

Assigne la valeur <Int> à la variable <Variable>

```Tourne <Variable>/<Int>;```

Faire pivoter le curseur

```Avance <Int>/<Variable>;```

Faire avancer le curseur

```SI <Expression> ALORS <Instructions> SINON <Instructions>;```

Effectue une condition.
Attention, chacune des instructions doit être séparées d’un ; .

```COLOR <red|blue|green|yellow|black>;```

Change la couleur du curseur

```Taille <Int>;```

Change la taille du curseur

```TANT QUE <Expression> FAIRE <Instructions>;```

Effectue une boucle
Attention, chacune des instructions doit être séparées d’un ; .
