CAMEL OFBIZ RMI DEMO
====================

A Camel route demonstrating how to interact with Apache OFBiz using RMI.
====================

The route listens for tweets with keyword euro2012 using the Twitter component (and Twitter's real time API).
Then it uses the RMI component to call OFBiz createNote service and persist the tweet in OFBiz.

How to run the demo
====================
1. In framework/base/config/ofbiz-containers.xml rmi-container comment out the following lines

<property name="client-factory" value="org.ofbiz.service.rmi.socket.ssl.SSLClientSocketFactory"/>
<property name="server-factory" value="org.ofbiz.service.rmi.socket.ssl.SSLServerSocketFactory"/>

2. In framework/common/servicedef/services.xml make createNote service exported by adding export="true"

After these changes OFBiz createNote service will be available for RMI calls

3. Start OFBiz with: ant start

4. In order to run the Camel app, you need few jars from OFBiz.
In pom.xml update the path to ofbiz-service.jar, ofbiz-base.jar and javolution-5.4.3.jar

5. Start camel with: mvn camel:run and watch tweets stored in OFBiz