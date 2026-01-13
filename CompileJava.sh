#!/bin/bash 

# Définition des variables
DIRECTORY="src"
CLASSDIR="out"
LIBRARY="lib"
DRIVERDIR="driver"  # Ajout du dossier driver

# Nettoyer et recréer le répertoire de sortie
rm -rf $CLASSDIR
mkdir -p $CLASSDIR/$DIRECTORY

# Trouver TOUS les drivers JDBC dans le dossier driver
# Recherche de tous les fichiers JAR dans le dossier driver
JAR_FILES=$(find $DRIVERDIR -name "*.jar" 2>/dev/null)

# Vérifier si des drivers ont été trouvés
if [ -z "$JAR_FILES" ]; then
    echo "Erreur : Aucun fichier JAR trouvé dans le dossier $DRIVERDIR"
    exit 1
fi

# Compter le nombre de drivers trouvés
JAR_COUNT=$(echo "$JAR_FILES" | wc -l)
echo "Nombre de drivers trouvés : $JAR_COUNT"

# Afficher la liste des drivers
echo "Drivers disponibles :"
echo "$JAR_FILES" | while read -r jar; do
    echo "  - $jar"
done

# Construire le classpath avec tous les drivers
# Convertir la liste des fichiers en un seul chemin avec séparateur ':'
CLASS_PATH=""
for jar in $JAR_FILES; do
    if [ -z "$CLASS_PATH" ]; then
        CLASS_PATH="$jar"
    else
        CLASS_PATH="$CLASS_PATH:$jar"
    fi
done

echo "Classpath : $CLASS_PATH"
echo " "

# Compilation des fichiers avec tous les drivers JDBC
find $DIRECTORY -name *.java > source.txt
javac --module-path "$LIBRARY" --add-modules javafx.controls -cp "$CLASS_PATH" -d $CLASSDIR/$DIRECTORY @source.txt

# Vérifier si la compilation a réussi
if [ $? -ne 0 ]; then
    echo "Erreur lors de la compilation"
    exit 1
fi

# Trouver le main
cd $CLASSDIR/$DIRECTORY 
find * -name Main* > valiny.txt  
VALINY=valiny.txt

# Vérifier si un main a été trouvé
if [ ! -s "$VALINY" ]; then
    echo "Erreur : Aucune classe Main trouvée"
    exit 1
fi

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

    # Retourner le contenu modifié
    cat "$input_file"
}

# Récupérer le nom de la classe Main modifié
MAIN_CLASS=$(edit_valiny | head -1)

echo "Exécution avec $JAR_COUNT driver(s)..."
echo "Classe principale : $MAIN_CLASS"
echo " "

# Exécution avec tous les drivers JDBC
# Note: le chemin relatif est différent car on est dans $CLASSDIR/$DIRECTORY
# Construire le classpath pour l'exécution
EXEC_CLASS_PATH=""
for jar in $JAR_FILES; do
    EXEC_CLASS_PATH="../../$jar:$EXEC_CLASS_PATH"
done
EXEC_CLASS_PATH="${EXEC_CLASS_PATH}."

java --module-path "../../$LIBRARY" --add-modules javafx.controls -cp "$EXEC_CLASS_PATH" "$MAIN_CLASS"