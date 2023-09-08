pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Compile your Java code
                sh 'mvn clean install'

                // Run static code analysis (e.g., with SonarQube)
                sh 'mvn sonar:sonar'

                // Generate documentation (e.g., with Javadoc)
                sh 'mvn javadoc:javadoc'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dtest=sicrediApiTest.*'
            }
        }

        stage('Deploy to Staging') {
            steps {
                // Deploy to a staging environment (e.g., test server)
                sh './deploy-to-staging.sh'
            }
        }

        stage('Deploy to Production') {
            when {
                // Add a condition to decide when to deploy to production
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                // Deploy to production only if tests pass successfully
                sh './deploy-to-production.sh'
            }
        }
    }

    post {
        success {
            // Actions to be performed when the pipeline succeeds
            // For example, notify the team or perform additional tasks
            echo 'The pipeline was executed successfully!'
        }

        failure {
            // Actions to be performed when the pipeline fails
            // For example, notify the team or create bug-fixing tasks
            echo 'The pipeline failed. Check the logs for details.'
            
          }
   	   }
	}
           
