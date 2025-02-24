pipeline {
    agent any
    
    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 21'
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
        
        stage('Build') {
            steps {
                sh 'mvn clean compile -Dmaven.compiler.release=21'
            }
        }
        
        stage('API Tests') {
            steps {
                sh 'mvn test -Dtest=com.example.api.RunApiTests -Dmaven.compiler.release=21'
            }
            post {
                always {
                    cucumber buildStatus: 'UNSTABLE',
                        reportTitle: 'API Test Report',
                        fileIncludePattern: '**/cucumber-reports/api/*.json',
                        trendsLimit: 10
                }
            }
        }
        
        stage('Web Tests') {
            steps {
                sh 'mvn test -Dtest=com.example.web.RunWebTests -Dmaven.compiler.release=21 -Dbrowser.headless=true -Djenkins.build=true'
            }
            post {
                always {
                    cucumber buildStatus: 'UNSTABLE',
                        reportTitle: 'Web Test Report',
                        fileIncludePattern: '**/cucumber-reports/web/*.json',
                        trendsLimit: 10
                }
            }
        }
    }
    
    post {
        always {
            // Publikuj raporty JUnit
            junit '**/target/cucumber-reports/**/*.xml'
            
            // Archiwizuj raporty i logi
            archiveArtifacts artifacts: '**/target/cucumber-reports/**/*', 
                           fingerprint: true, 
                           allowEmptyArchive: true
            
            // Wyślij maila z wynikami
            emailext body: '${DEFAULT_CONTENT}',
                     subject: '${DEFAULT_SUBJECT}',
                     to: '${DEFAULT_RECIPIENTS}'
            
            // Wyczyść workspace
            cleanWs()
        }
    }
} 