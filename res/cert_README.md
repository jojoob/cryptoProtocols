# Generate new certificate
## Self signed
```
openssl req -x509 -newkey rsa:2048 -nodes -keyout root.key -out root.crt -days 365
```

## signed with other certificate
```
openssl req -new -newkey rsa:2048 -nodes -keyout cert.key -out cert.csr
openssl x509 -req -days 365 -in cert.csr -signkey cert.key -out cert.crt
```

# aggregate certificates
```
cat root.crt cert.crt > certs
```

# show certificate
```
openssl x509 -in cert.crt -text -noout
```

