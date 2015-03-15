# Generate new certificate
## Self signed
```
openssl req -x509 -newkey rsa:2048 -nodes -keyout ca.key -out ca.crt -days 365
```

## signed with other certificate
```
openssl req -new -newkey rsa:2048 -nodes -keyout client.key -out client.csr
openssl x509 -req -days 365 -in client.csr -signkey ca.key -out client.crt
```

# aggregate certificates
```
cat ca.crt client.crt > certs
```

# show certificate
```
openssl x509 -in client.crt -text -noout
```

