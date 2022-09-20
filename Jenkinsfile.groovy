@Library('jenkins-shared-library')_

    pipeline {
        agent any
        tools {
            maven 'maven'
        }
        stages {
            stage("init") {
                steps {
                    script {
                        gv = load "script.groovy"
                    }
                }
            }
            stage ("build jar") {
                steps {
                    script {
                        buildJar()
                    }
                }
            }
            stage ("build image and push image") {
                steps {
                    script {
                        buildImage '185.110.189.54:8085/java-maven-app:1.8'
                        dockerLogin()
                        dockerPush '185.110.189.54:8085/java-maven-app:1.8'
                    }
                }
            }
            stage ("deploy") {
                steps {
                    script {
                        gv.deployApp()
                    }
                }
            }
        }
    }