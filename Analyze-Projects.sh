#!bin/bash

#Checking if the scanner was downloaded
if test -a sonar-scanner/bin/sonar-scanner.bat || test -a sonar-scanner/bin/sonar-scanner
then    
    scanner=$(ls sonar-scanner/bin | grep -Fx "sonar-scanner");
    if test -z "$scanner"
    then    
        scanner="../../sonar-scanner/bin/sonar-scanner.bat";
    else
        scanner="../../sonar-scanner/bin/sonar-scanner";
    fi

    cd "$3";

    echo "Sonar JUploader - Análisis de Sonar Cloud con SonarScanner"
    echo "La organización es: " $1
    echo "El token es: " $2
    echo "La carpeta de proyectos se encuentra en: " $3
    printf "\n"

    for project in $(ls)
    do
        cd ${project};

        #Executing the analysis
        $scanner -Dsonar.organization=$1 -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$2 2>&1 | tee ../../tmp;
        search=$(grep 'EXECUTION SUCCESS' ../../tmp);

        #Testing if in the output of the analysis it cannot find 'EXECUTION SUCCESS'
        if test -z "$search"
        then    
            #Registers the time and the error returned by the analysis
            date "+%d/%m/%y    %H:%M:%S" >> ../../results.txt;
            cat ../../tmp >> ../../results.txt;
            lineBreak="\n\n<===============================================================================================>\n\n\n";
            printf $lineBreak >> ../../results.txt; 
        fi 
        rm  ../../tmp;
        cd ..;
    done

else
    echo "Cannot run the analysis if you do not have the sonar-scanner!!";
fi

$SHELL