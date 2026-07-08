node {

    stage('4.7. DevOps - SCM') {
        checkout scm
    }

    stage('4.3. Automatización de Builds/Pruebas') {
        // Envolvemos la ruta con comillas dobles para que el espacio en el nombre no rompa el directorio
        bat 'echo sdk.dir="C:/Users/Gabriel Alvarado/AppData/Local/Android/Sdk" > local.properties'
        bat 'gradlew.bat clean assembleDebug testDebugUnitTest'
    }

    stage('4.4. & 4.5. Análisis Estático y Métricas (SonarQube)') {
        def scannerHome = tool 'SonarScanner'
        
        withSonarQubeEnv('Mi_Servidor_SonarQube') { 
            bat "${scannerHome}\\bin\\sonar-scanner.bat " +
                "-Dsonar.projectKey=Gabriel-UPSJB_sid_share " +
                "-Dsonar.projectName=sid_share " +
                "-Dsonar.sources=app/src/main/java,app/src/main/kotlin " +
                "-Dsonar.java.binaries=app/build/intermediates/javac/debug/classes,app/build/tmp/kotlin-classes/debug " +
                "-Dsonar.language=java,kotlin"
        }
    }
}
