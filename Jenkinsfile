pipeline {
    agent any

    stages {
        stage('4.7. DevOps - SCM') {
            steps {
                // Descarga el código actualizado desde GitHub
                checkout scm
            }
        }

        stage('4.3. Automatización de Builds y Pruebas') {
            steps {
                // Quitamos el espacio antes del '>' para evitar el espacio en blanco al final de la ruta
                bat 'echo sdk.dir=C:\\\\Sdk> local.properties'
                
                // Compila la aplicación omitiendo las pruebas unitarias
                bat 'gradlew.bat clean assembleDebug -x testDebugUnitTest'
            }
        }

        stage('4.4. & 4.5. Análisis Estático (SonarQube)') {
            steps {
                // Ejecuta el análisis estático y lo sube directamente usando tu token
                bat 'gradlew.bat sonar ' +
                    '-Dsonar.projectKey=sid_share ' +
                    '-Dsonar.host.url=http://localhost:9000 ' +
                    '-Dsonar.token=sqp_9433ea46c1ae8e1b2f9319e8bd5d4e04a02f0288' 
            }
        }
    }
}
