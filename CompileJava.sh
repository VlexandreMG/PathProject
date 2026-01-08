#!/bin/bash 

# Définition ana variable
DIRECTORY="src"
CLASSDIR="out"

# Fafana le out de manao vaovao
rm -rf $CLASSDIR
mkdir -p $CLASSDIR/$DIRECTORY

# Compilation des fichiers
find $DIRECTORY -name *.java > source.txt
javac -d $CLASSDIR/$DIRECTORY @source.txt

# Mandefa anle izy 
cd $CLASSDIR/$DIRECTORY 
find * -name Main* > valiny.txt  
VALINY=valiny.txt
VAL=$(cat $VALINY)
echo "Le main : $VAL "
echo " "

# Manamboatra ny valiny 
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

    # Manamboatra valiny.txt
    # 2. Remplacer '/' par '.'
    # 3. Supprimer l'extension .class
    sed 's|/|.|g' "$input_file" | 
    sed 's|.class||g' > "$temp_file"

    # Remplacer l'ancien fichier par le nouveau
    mv "$temp_file" "$input_file"

    # Afficher le contenu final
    cat "$input_file"
}

java "$(edit_valiny)"

