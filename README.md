# OWASP Mitarbeiterverwaltung

## Start

```bash
mvn spring-boot:run
```

Danach öffnen:

```text
http://localhost:8080
```

## Logins

```text
admin / Admin123
user / User1234
CAPTCHA: 7
```

## Umgesetzte OWASP-Risiken

### A01 Broken Access Control

Unsichere Demo:

```text
/vulnerable/employees/1
/vulnerable/employees/2
/vulnerable/employees/3
```

Gegenmassnahme:

```text
/employees/1
/employees/2
/employees/3
```

Normale User dürfen nur ihr eigenes Profil sehen. Admins dürfen alle Profile sehen.

### A07 Authentication Failures

Nach 5 falschen Login-Versuchen wird der Account blockiert. Zusätzlich gibt es ein CAPTCHA beim Login.

### A09 Security Logging and Alerting Failures

Erfolgreiche und fehlgeschlagene Logins werden mit SLF4J und Logback geloggt. Passwörter werden nicht geloggt.

## Architektur

Browser -> Spring Security Filter Chain -> CAPTCHA Filter -> Login Filter -> CSRF Filter -> Authorization Filter -> Controller -> Repository -> H2 Database
