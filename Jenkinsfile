pipeline {
    agent any
    
    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 11'
    }
    
    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Wybierz przeglądarkę')
        string(name: 'TAGS', defaultValue: '', description: 'Tagi Cucumber (np. @smoke)')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    try {
                        sh """
                            mvn test -Dselenide.browser=${params.BROWSER} \
                            ${params.TAGS ? "-Dcucumber.filter.tags=\\"${params.TAGS}\\"" : ''}
                        """
                    } finally {
                        sh 'mvn verify -DskipTests'
                    }
                }
            }
        }
    }
    
    post {
        always {
            // Publikowanie raportów Cucumber
            cucumber buildStatus: 'UNSTABLE',
                    reportTitle: 'Cucumber Report',
                    fileIncludePattern: '**/cucumber.json',
                    trendsLimit: 10,
                    classifications: [
                        [
                            'key': 'Browser',
                            'value': "${params.BROWSER}"
                        ]
                    ]
            
            // Archiwizacja raportów HTML
            archiveArtifacts artifacts: 'target/cucumber-reports/**/*', fingerprint: true
            
            // Czyszczenie workspace
            cleanWs()
        }
        
        success {
            echo 'Testy zakończone sukcesem!'
        }
        
        failure {
            echo 'Testy zakończone niepowodzeniem!'
        }
    }
} 