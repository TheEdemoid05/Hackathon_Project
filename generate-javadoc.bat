@echo off
echo Generazione Javadoc per il Sistema di Gestione Hackathon...
echo.

REM Pulizia delle directory precedenti
if exist "docs\javadoc" rmdir /s /q "docs\javadoc"
mkdir docs\javadoc

REM Generazione Javadoc con Maven
mvn clean javadoc:javadoc -Dmaven.javadoc.failOnError=false

REM Copia dei file generati nella directory docs
if exist "target\site\apidocs" (
    echo Copiando i file Javadoc generati...
    xcopy /s /y "target\site\apidocs\*" "docs\javadoc\"
    echo.
    echo Javadoc generato con successo!
    echo I file sono disponibili in: docs\javadoc\index.html
) else (
    echo Errore nella generazione del Javadoc.
    echo Verificare che Maven sia installato e configurato correttamente.
)

echo.
echo Premere un tasto per continuare...
pause >nul
