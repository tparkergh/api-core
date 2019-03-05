@Library('jenkins-pipeline-utils') _

def notifyBuild(String buildStatus, Exception e) {
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = """*${buildStatus}*: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':\nMore detail in console output at <${env.BUILD_URL}|${env.BUILD_URL}>"""
  def details = """${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':\n
    Check console output at ${env.BUILD_URL} """
  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
    details +="<p>Error message ${e.message}, stacktrace: ${e}</p>"
    summary +="\nError message ${e.message}, stacktrace: ${e}"
  }

  // Send notifications

  slackSend channel: "#cals-api", baseUrl: 'https://hooks.slack.com/services/', tokenCredentialId: 'slackmessagetpt2', color: colorCode, message: summary
  emailext(
      subject: subject,
      body: details,
      attachLog: true,
      recipientProviders: [[$class: 'DevelopersRecipientProvider']],
      to: "Leonid.Marushevskiy@osi.ca.gov, Alex.Kuznetsov@osi.ca.gov, Oleg.Korniichuk@osi.ca.gov, alexander.serbin@engagepoint.com, vladimir.petrusha@engagepoint.com"
    )
}

node ('tpt2-slave'){
   def serverArti = Artifactory.server 'CWDS_DEV'
   def rtGradle = Artifactory.newGradleBuild()
   properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), disableConcurrentBuilds(), [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
   parameters([
      string(defaultValue: 'master', description: '', name: 'branch'),
      string(defaultValue: '', description: 'Used for mergerequest default is empty', name: 'refspec'),
      booleanParam(defaultValue: false, description: 'Default release version template is: <majorVersion>_<buildNumber>-RC', name: 'RELEASE_PROJECT'),
      string(defaultValue: "", description: 'Fill this field if need to specify custom version ', name: 'OVERRIDE_VERSION'),
      ])])
  try {
   stage('Preparation') {
		  checkout([$class: 'GitSCM', branches: [[name: '$branch']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '433ac100-b3c2-4519-b4d6-207c029a103b', refspec: '$refspec', url: 'git@github.com:ca-cwds/api-core.git']]])
		  rtGradle.tool = "Gradle_35"
		  rtGradle.resolver repo:'repo', server: serverArti
		  rtGradle.useWrapper = true
   }
   stage('Build'){
     if (params.RELEASE_PROJECT) {
         echo "!!!! BUILD RELEASE VERSION "
         def buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'clean jar -DRelease=$RELEASE_PROJECT -DBuildNumber=$BUILD_NUMBER -DCustomVersion=$OVERRIDE_VERSION'
     } else {
         echo "!!!! BUILD SNAPSHOT VERSION"
         def buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'clean jar'
     }
   }
   stage('Unit Tests') {
       buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'test jacocoMergeTest', switches: '--stacktrace'
   }
   stage('License Report') {
      		buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'libLicenseReport -DRelease=$RELEASE_PROJECT -DBuildNumber=$BUILD_NUMBER -DCustomVersion=$OVERRIDE_VERSION'

      }
   stage('SonarQube analysis'){
		 lint(rtGradle)
    }
 } catch (Exception e)    {
 	   errorcode = e
  	   currentBuild.result = "FAIL"
  	   notifyBuild(currentBuild.result,errorcode)
  	   throw e;
 } finally {
     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'api-core-common/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report Common', reportTitles: 'JUnit Report Common'])
     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'api-core-rest/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report REST', reportTitles: 'JUnit Report REST'])
     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'api-core-cms/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report CMS', reportTitles: 'JUnit Report CMS'])
     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'api-core-abac/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report ABAC', reportTitles: 'JUnit Report ABAC'])
     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'legacy-data-access/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report legacy-data-access', reportTitles: 'JUnit Report Legacy Data Access'])
     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'legacy-data-access-services/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report legacy-data-access-services', reportTitles: 'JUnit Report Legacy Data Access Services'])
     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/dependency-license', reportFiles: 'licenses.html', reportName: 'License Report', reportTitles: 'License summary'])
     cleanWs()
 }
}

