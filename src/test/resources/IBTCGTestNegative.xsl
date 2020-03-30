<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
<suite name="IBTCGTestNegativeTests"  >

    <listeners>
        <listener class-name="frameworkInfra.Listeners.TestListener" />
    </listeners>

    <test name="IBTCGTestNegativeTests">
        <classes>
            <class name ="Native.unitTesting.IBTCGTestNegativeTests" />
        </classes>
    </test>
</suite>