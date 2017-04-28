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
	cp -R "../maverick-analyzer-mock"  "../maverick-analyzer-$NAME"
	echo "Getting into the created folder..."
	cd ../maverick-analyzer-$NAME
	echo "Removing useless files/folders..."
	rm -rf ./target create-new-analyzer.sh
	echo "Replacing name in config files..."
	find -type f -exec sed -i "s/mock/$NAME/g" -- {} \; 2> /dev/null
	echo "Renaming folders..."
	find -type d -iname 'mock' -exec bash -c 'mv "$1" "${1/mock/$NAME}"' -- {} \; 2> /dev/null
	echo "New project created with sucess!"
	echo "Adding maverick-analyzer-$NAME in Maverick's pom.xml..."
	cat ../pom.xml | awk -v "var=                <module>./maverick-analyzer-$NAME</module>" '/<module>.*<\/module>/ && !x {print var; x=1} 1' > ../.bak 
	cat ../.bak > ../pom.xml && rm ../.bak
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

if [[ $1 = create ]] ; then
    create
elif [[ $1 = remove ]] ; then
    remove 
else
	usage
fi