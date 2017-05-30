#!/bin/bash

function usage {
	echo "usage: create-new-analyzer <create|remove> <name>"
    exit
}

if [[ $# -ne 2 ]] ; then
    usage
fi

function create {
	echo "Making a folder copy..."
	cp -R "../maverick-analyzer-tutorial"  "../maverick-analyzer-$NAME"
	echo "Getting into the created folder..."
	cd ../maverick-analyzer-$NAME
	echo "Removing useless files/folders..."
	rm -rf ./target create-new-analyzer.sh ./manage-analyzer.sh
	echo "## $NAME_UPPER property" > ./Readme.md
	echo "Replacing name in config files..."
	find -type f -exec sed -i "s/tutorial/$NAME/g" -- {} \; 2> /dev/null
	echo "Replacing names classes..."
	find -type f -exec sed -i "s/Tutorial/$NAME_UPPER/g" -- {} \; 2> /dev/null
	echo "Renaming classes..."
	find . -name '*Tutorial*.java' -type f -exec bash -c 'mv "$1" "${1/Tutorial/$NAME_UPPER}"' -- {} \; 2> /dev/null
	echo "Erasing old implementations"
	find . -name ${NAME_UPPER}Property.java -type f -exec sed -i '/process(MaverickSymptom symptom)/ {:r;/\n}/!{N;br}; s/\n.*\n/\n\t\t\/\/TODO: Auto Generated method stup\n\t}\n/}' -- {} \; 2> /dev/null
	echo "Renaming folders..."
	find -type d -iname 'tutorial' -exec bash -c 'mv "$1" "${1/tutorial/$NAME}"' -- {} \; 2> /dev/null
	echo "New project created with sucess!"
	echo "Adding maverick-analyzer-$NAME in Maverick's pom.xml..."
	# using tac to add new module at end of dependencies; tac is opposite of cat
	tac ../pom.xml | awk -v "var=        <module>./maverick-analyzer-$NAME</module>" '/<module>.*<\/module>/ && !x {print var; x=1} 1' > ../.bak 
	tac ../.bak > ../pom.xml && rm ../.bak
	echo "Done!"
}

function remove {
	echo "Removing project maverick-analyzer-$NAME..."
	rm -rf ../maverick-analyzer-$NAME
	echo "Removing maverick-analyzer-$NAME from Maverick's pom.xml..."
	sed -i "/<module>.\/maverick-analyzer-$NAME<\/module>/d" ../pom.xml
	echo "Done!"
}

export NAME=$2
# upper case first letter of property name
#export NAME_UPPER="$(tr '[:lower:]' '[:upper:]' <<< ${NAME:0:1})${NAME:1}"
# dash to upper camel case
export NAME_UPPER=$(echo $2 | sed -r 's/(^|-)([a-z])/\U\2/g')

if [[ $1 = create ]] ; then
    create
elif [[ $1 = remove ]] ; then
    remove 
else
	usage
fi
