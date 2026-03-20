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

Default value is `local`.

### -Dbrowser="${browser}"

Parameter specifies browser where tests will be run, where `${browser}` is the browser. Possible options are:
- `chrome`
- `firefox`
- `edge`

Default option is `firefox`.
