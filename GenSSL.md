**Convertir le `.crt` en `.jks`**
-Télécharger openssl 
exporté d'abord le `.cert` en `.p12`
`openssl pkcs12 -export -name test -in test.crt -inkey test.key -out test.p12`

convertir le point .p12 en .jks

`keytool -importkeystore -destkeystore test.jks -srckeystore test.p12 -srcstoretype pkcs12 -alias servercert` (`-alias` n'est pas important)

https://www.sslsupportdesk.com/java-keytool-commands/