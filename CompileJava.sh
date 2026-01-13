#!/bin/bash 

# Définition des variables
DIRECTORY="src"
CLASSDIR="out"
LIBRARY="lib"
DRIVERDIR="driver"  # Ajout du dossier driver

# Nettoyer et recréer le répertoire de sortie
rm -rf $CLASSDIR
mkdir -p $CLASSDIR/$DIRECTORY

# Trouver le driver JDBC Oracle dans le dossier driver
# Recherche du fichier JAR dans le dossier driver
JAR_FILE=$(find $DRIVERDIR -name "*.jar" | head -1)

# Vérifier si le driver a été trouvé
if [ -z "$JAR_FILE" ]; then
    echo "Erreur : Aucun fichier JAR trouvé dans le dossier $DRIVERDIR"
    exit 1
fi

echo "Utilisation du driver: $JAR_FILE"

# Compilation des fichiers avec le driver JDBC
find $DIRECTORY -name *.java > source.txt
javac --module-path "$LIBRARY" --add-modules javafx.controls -cp "$JAR_FILE" -d $CLASSDIR/$DIRECTORY @source.txt

# Trouver le main
cd $CLASSDIR/$DIRECTORY 
find * -name Main* > valiny.txt  
VALINY=valiny.txt
VAL=$(cat $VALINY)
echo "Le main : $VAL "
echo " "

# Fonction pour éditer le fichier valiny.txt
function edit_valiny() {
    local input_file="valiny.txt"
    local temp_file="temp.txt"
    
    # Vérifier si le fichier existe
    if [ ! -f "$input_file" ]; then
        echo "Erreur : le fichier $input_file n'existe pas"
        return 1
    fi
    
    # Vérifier si le fichier est vide
    if [ ! -s "$input_file" ]; then
        echo "Erreur : le fichier $input_file est vide"
        return 1
    fi

    # Transformer le chemin
    sed 's|/|.|g' "$input_file" | 
    sed 's|.class||g' > "$temp_file"

    # Remplacer l'ancien fichier par le nouveau
    mv "$temp_file" "$input_file"

    # Afficher le contenu final
    cat "$input_file"
}

# Exécution avec le driver JDBC Oracle
# Note: le chemin relatif est différent car on est dans $CLASSDIR/$DIRECTORY
java --module-path "../../$LIBRARY" --add-modules javafx.controls -cp "../../$JAR_FILE:." "$(edit_valiny)"