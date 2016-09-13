#Gallows

An application the has a server and client in multiproject structure.
Currently it is limited to sending messages between clients.

#####Project structure
```
/client
    contains the source code specific to the client application
/server
    contains the source code specific to the server application
/common
    contains classes that both the server and client require to function
```

####3rd party libraries used
1. Esoteric Software Kryonet
    * TCP/UDP client/server library for Java, based on Kryo
    * https://github.com/EsotericSoftware/kryonet
2. Google Guava
    * Google Core Libraries for Java 6+
    * https://github.com/google/guava
3. Slf4j and Log4 for logging
    * http://www.slf4j.org/
    * http://logging.apache.org/log4j/2.x/

    
    


