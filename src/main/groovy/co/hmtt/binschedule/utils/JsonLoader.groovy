package co.hmtt.binschedule.utils

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class JsonLoader {

    @Value('${wheelie.persist.dir}')
    private String homeDir

    static def jsonSlurper = new JsonSlurper()

    static def readFile(def directory, def name) {
        def customer = new File(String.format("%s/%s", directory, name))
        jsonSlurper.parseText(customer.text)
    }

    static def readClasspath(def name) {
        def resource = new ClassPathResource(name).getFile()
        jsonSlurper.parseText(resource.text)
    }

    static def fetchCouncils() {
        readClasspath('dates/councils.json')
    }

    def fetchCustomer() {
        populateCustomerFileIfNotAlreadyPresent()
        readFile("${homeDir}" as String, 'customer.json')
    }

    private void populateCustomerFileIfNotAlreadyPresent() {

        File wheelieDir = new File("${homeDir}" as String)

        if (!wheelieDir.exists()) wheelieDir.mkdir()

        def custFile = new File("${homeDir}" as String, 'customer.json')
        if (!custFile.exists()) {
            write(readClasspath('persisted/customer.json'), 'customer.json')
        }
    }

    def write(def text, def fileName) {
        final File file = new File("${homeDir}/${fileName}")
        file.write(JsonOutput.prettyPrint(JsonOutput.toJson(text)))
    }
}
