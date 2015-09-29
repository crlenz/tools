// assume we always pull from master branch
// use build number as release suffix and tag
// use un/pw url for github repo to clone
// use job workspace git clone instead of file system repo
// move this script to github

def strOriginBranchName = "master"
def strBuildNumber = build.getNumber()
def strReleaseBranchName = "release/r$strBuildNumber"
def strTagName = "R$strBuildNumber"
def strOriginRemote = "origin"
def strReleaseRemote = "release"
def strWorkspacePath = build.getWorkspace().toURI()

def workingDir = new File(strWorkspacePath)
def mainRepoDirPath = build.getWorkspace().getRemote() + "/main-1/"
def mainRepoDir = new File(mainRepoDirPath)

def mainRepoURL = "https://github.com/crlenz/main-1.git"
def releaseRepoURL = "https://crlenz:system123@github.com/crlenz/release-1.git"

println "\nmainRepoURL: $mainRepoURL"
println "releaseRepoURL: $releaseRepoURL"
println "workspace: $strWorkspacePath"
println "mainRepoDir: $mainRepoDirPath"
println "origin branch: $strOriginBranchName"
println "release branch: $strReleaseBranchName"
println "tag: $strTagName\n"

def execCommand(command, workingDir) {
  println "\ncommand to execute: $command"
  def process = command.execute(null, workingDir)
  //process.waitForProcessOutput(outputStream, System.err)
  process.waitFor()
  println "   exitValue: ${process.exitValue()}\n   text: ${process.text}\n   err.text: ${process.err.text}"
}

execCommand("rm -rf main-1", workingDir)
execCommand("git clone $mainRepoURL", workingDir)

execCommand("git checkout master", mainRepoDir)

execCommand("git branch $strReleaseBranchName", mainRepoDir)
execCommand("git tag $strTagName", mainRepoDir)

execCommand("git remote add release $releaseRepoURL", mainRepoDir)
execCommand("git remote set-url release $releaseRepoURL", mainRepoDir)

execCommand("git push $strReleaseRemote $strReleaseBranchName", mainRepoDir)
execCommand("git push $strReleaseRemote $strTagName", mainRepoDir)

// end ****************************************************

//execCommand("git pull $strOriginRemote $strOriginBranchName", workingDir)
//def releaseRepoURL = "crlenz:system123@github.com:crlenz/release-1.git"
//def releaseRepoURL = "git@github.com:crlenz/release-1.git"
//def strOriginBranchName = build.buildVariableResolver.resolve("Origin Branch Name")
//def strReleaseBranchName = "release/$strOriginBranchName"
//def strTagName = strOriginBranchName.toUpperCase()
//def strIntermediateRepoPath = "/home/admin/git/main-1/"
//def strIntermediateRepoPath = "/var/lib/jenkins/jobs/freestyle-git-release/workspace"
//execCommand("git remote add release 'https://jenkins-crlenz:system123@github.com/crlenz/release-1.git'", workingDir)
