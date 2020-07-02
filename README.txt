Migration to Java 13
04/06/20202

1. Create account in Oracle site
 1.1 Install JDK 13 for Windows x64 Installer
https://www.oracle.com/java/technologies/javase-jdk13-downloads.html
	• Uninstall older JDKs + 
	Stop all Java (Task manager -> Performance -> Open resource monitor: Associated Handles - search: java & end process

2. Add Environment variable to System Environment:
JAVA_HOME C:\Program Files\Java\jdk-13.0.2
 2.1 Restart machine
 2.2 Verify in System Environment - JAVA_HOME varaible exits

3. Upgrate InteliJ IDEA
 3.1 Uninstall old IntelliJ IDEA - mark only "delete...cache" (DON'T delete: setting & plugins)
 3.2 IntelliJ IDEA Community 2020.1.1 (Folder: JetBrains + pic attached with options to mark)
 3.3 In the Inteliji IDEA:
	* Project Structure -> Project Settings:
			-> Project -> Platform settings -> Project SDK: select version 13 (no new language)
			-> Modules: 
				-> Sources -> Language level: select version 13
				-> Dependencies: Modules SDK to 13.02 & select version 13
	** Project Structure ->	Platform Settings -> SDK's : select 13
	*** File -> Settings -> Build, Execution, Deployment:
			-> Build tools -> Gradle -> Gradle JVM : select 13
			-> Compiler -> Java Compiler -> Pre-Module bytecode version : update to 13 , add each Module & update version to 13
 
 3.4 Automation Project -> 
  3.4.1 Go to gradle-wraper.properties file
   \qa_automation\gradle\wrapper\gradle-wrapper.properties
   change value of distributionUrl to 
   https\://services.gradle.org/distributions/gradle-6.4.1-all.zip
  3.4.2 Go to build.gradle to change value of sourceCompatibility = 13 and update dependency
 3.5 Build project (option in Toolbar)
 
4. Jenkins - add slave-agent to shell:startup (Linux it became service)

