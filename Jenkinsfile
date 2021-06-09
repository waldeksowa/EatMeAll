pipeline {
    agent any
    tools {
        maven "M3"
        jdk 'java-11-openjdk-amd64'
    }
    stages {
		stage('Get commit details') {
			steps {
				script {
				    try {
				        env.GIT_AUTHOR = sh (script: 'git log -1 --pretty=%cn ${GIT_COMMIT}', returnStdout: true).trim()
				    } catch(err) {
				        env.GIT_AUTHOR = "Pull Request"
				        env.DISCORD_FAILURE_MSG = ", probably you have some conflict to deal with"
				        echo'Getting git author error'
				    }
				}
				//sh 'printenv'
			}
		}
        stage('Build') {
            steps {
                dir('backend/project'){
                    sh "mvn clean compile"
                }
            }
        }       
    }
	post {
		failure {
			echo 'Build failure'
			script {
				if (env.GIT_BRANCH == "development") {
					echo 'Failure on development branch'
					sendDiscordMessage("${env.GIT_AUTHOR} ${env.DISCORD_FAILURE_DEVELOPMENT_MSG}", "${env.DISCORD_TAG_USER}")
				} else {
					echo 'Failure on non development branch'
					sendDiscordMessage("${env.GIT_AUTHOR} ${env.DISCORD_FAILURE_MSG}", "${env.DISCORD_TAG_USER}")
				}
			}			
		}
		success {
			echo 'Build success'
			sendDiscordMessage("${env.GIT_AUTHOR} ${env.DISCORD_SUCCESS_MSG}", "${env.DISCORD_TAG_USER}")
		}
	}
}


def sendDiscordMessage(description, userToTag){
    discordSend description: description,
                //notes: userToTag,
                title: "Build: ${currentBuild.currentResult} on ${env.JOB_NAME}",
                result: currentBuild.currentResult,
                link: env.BUILD_URL,
                webhookURL: "${env.DISCORD_WEBHOOK}"
}

def setDiscordUserToTag(tagAllUsers){
    script {
                if(tagAllUsers == "true"){
                    env.DISCORD_TAG_USER = env.DISCORD_TAG_ALL_USERS
                } else {
                    if (env.GIT_AUTHOR == "waldek" || env.GIT_AUTHOR == "Waldek Sowa") {
                        env.DISCORD_TAG_USER = "<@783652626566873098>"
                    } else if (env.GIT_AUTHOR == "adam" || env.GIT_AUTHOR == "Adam Rozwadowski"){
                        env.DISCORD_TAG_USER = "<@767772830720458783>"
                    } else if (env.GIT_AUTHOR == "kuba" || env.GIT_AUTHOR == "Jakub Przybyła"){
                        env.DISCORD_TAG_USER = "<@395305773636255745>"
                    } else if (env.GIT_AUTHOR == "marcin"){
                        env.DISCORD_TAG_USER = "<@330816028684713984>"
                    } else if (env.GIT_AUTHOR == "pawel"){
                        env.DISCORD_TAG_USER = "<@569212689599954969>"
                    } else if (env.GIT_AUTHOR == "przemek" || env.GIT_AUTHOR == "Przemysław Wieczorek" || env.GIT_AUTHOR == "Software-Wizard"){
                        env.DISCORD_TAG_USER = "<@642593713801134100> <@733012248368250983>"
                    }
                }
            }
}