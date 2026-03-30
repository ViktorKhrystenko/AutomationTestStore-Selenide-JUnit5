# Automation Test Store

The test automation framework for `automationteststore.com` written on Selenium/TestNG with Allure Reports and Jenkins integrations.

## Allure

Run `mvn allure:serve` in the terminal from the root of the project.

## Maven parameters

### -DsuiteXmlFile="${suiteXmlFile}"

Parameter specifies .xml file, that will be used to run test suite, where `${suiteXmlFile}` is .xml file with TestNG suites. .xml files are stored in `src/test/resources/testng/xml` directory.

Default value is `src/test/resources/testng/xml/testng.xml`. It will run all tests.

### -Dgroups="${groups}"

Parameter specifies groups, that will be run, where `${groups}` is groups. You can specify more than one group, separating them by comma.

By default, all groups are included.

### -Drun.target="${run.target}"

Parameter specifies environment where tests will be run, where `${run.target}` is the environment. Possible options are:
- `local`
- `jenkins-docker-agent`

Default option is specified in `src/main/resources/config/config.properties` file.

### -Dbrowser="${browser}"

Parameter specifies browser where tests will be run, where `${browser}` is the browser. Possible options are:
- `chrome`
- `firefox`
- `edge`

Default option is specified in `src/main/resources/config/config.properties` file.

## Jenkins

For this project Jenkins is supposed to run in Docker container.

There are steps to launch and setup Jenkins:

1. Navigate to `dockerfiles/jenkins/${os}` folder, where `${os}` is an operating system of our machine;
2. Run:
    ```shell
    docker compose up -d --build
    ```
    This command will build images required for Jenkins (the image with Jenkins itself and `alpine/socat` for Dicker plugin), create Docker network and launch containers;
3. Open `https://localhost:8080` in browser and walk through Jenkins setup. `Allure Jenkins Plugin`, `Docker plugin` and `Eclipse Temurin installer Plugin` plugins will be preinstalled (specified in `dockerfiles/jenkins/${os}/Dockerfile`);
4. In Tools page setup these tools:
   - JDK: 21.0.10. Check `Install automatically` checkbox and select `Install from adoptium.net` option;
   - Maven: 3.9.14;
   - Allure Commandline: 2.33.0.
5. Create Docker cloud:
   1. Check `Docker` checkbox and set the name for the cloud. Click `Create cloud`;
   2. Set up Docker cloud details:
      1. Specify Docker Host URI:
         ```text
         tcp://docker:2375
         ```
      2. Check `Enabled` checkbox;
      3. Test connection. It should return something similar this:
         ```text
         Version = 28.3.3, API Version = 1.51
         ```
   3. Set up three Docker agent templates, for `${browser}` equals to `chrome`, `firefox`, `edge`:
      1. Label: `docker-${browser}`;
      2. Any name;
      3. Check `Enabled` checkbox;
      4. Select preferable Docker image, which should be used for Docker agent container. You can build an image from docker files in `dockerfiles/jenkins-agent/${browser}` folder and push it to your Docker Hub repository or use `viktorkhrystenko/jenkins-agent:selenium-${browser}` from mine;
      5. Specify `Remote File System Root` as the home directory in your Docker image (for all images form `viktorkhrystenko/jenkins-agent` it is `/home/seluser`).
6. Create a new job:
   1. `Pipeline`, any job name;
   2. In `Pipeline` section select `Pipeline script from SCM` -> `Git`;
   3. Enter URL of this repository into `Repository URL` field;
   4. Enter `*/main` into `Branch Specifier` field;
   5. Enter `Jenkinsfile` into `Script Path` field (should be by default).

Created pipeline will launch tests in Chrome, Firefox and Edge browsers simultaneously and generate Allure reports for tests. Also, if smoke tests fail, execution will not go further.
