// assume we always pull from master branch
// use build number as release suffix and tag
// use un/pw url for github repo to clone
// use job workspace git clone instead of file system repo
// move this script to github

//def strOriginBranchName = build.buildVariableResolver.resolve("Origin Branch Name")
def strOriginBranchName = "master"
def strBuildNumber = build.getNumber()
//def strReleaseBranchName = "release/$strOriginBranchName"
def strReleaseBranchName = "release/r$strBuildNumber"
//def strTagName = strOriginBranchName.toUpperCase()
def strTagName = "R$strBuildNumber"
def strOriginRemote = "origin"
def strReleaseRemote = "release"
//def strIntermediateRepoPath = "/home/admin/git/main-1/"
def strIntermediateRepoPath = build.getWorkspace().toURI()
//def strIntermediateRepoPath = "/var/lib/jenkins/jobs/freestyle-git-release/workspace"

println "\norigin branch: $strOriginBranchName\nrelease branch: $strReleaseBranchName\ntag: $strTagName\nintermediate repo: $strIntermediateRepoPath\n"

def workingDir = new File(strIntermediateRepoPath)

def execCommand(command, workingDir) {
  println "\ncommand to execute: $command"
  def process = command.execute(null, workingDir)
  //process.waitForProcessOutput(outputStream, System.err)
  process.waitFor()
  println "   exitValue: ${process.exitValue()}\n   text: ${process.text}\n   err.text: ${process.err.text}"
}
/*
execCommand("git checkout master", workingDir)
execCommand("git pull $strOriginRemote $strOriginBranchName", workingDir)
*/
//execCommand("git branch $strReleaseBranchName", workingDir)
//execCommand("git tag $strTagName", workingDir)

//execCommand("git remote add release 'https://jenkins-crlenz:system123@github.com/crlenz/release-1.git'", workingDir)
//execCommand("git remote add release 'git@github.com:crlenz/release-1.git'", workingDir)
//execCommand("git remote set-url release 'git@github.com:crlenz/release-1.git'", workingDir)

//execCommand("git push $strReleaseRemote $strReleaseBranchName", workingDir)
//execCommand("git push $strReleaseRemote $strTagName", workingDir)
