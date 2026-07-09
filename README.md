# OWASP Mitarbeiterverwaltung

Dieses Projekt ist eine Spring-Boot-Webapplikation zur Demonstration von OWASP-Sicherheitsrisiken.
Die App zeigt absichtlich unsichere Stellen und vergleicht sie mit sicheren Gegenmassnahmen.

## Start

```bash
mvn spring-boot:run
```

Danach im Browser öffnen:

```text
http://localhost:8080
```

## Logins

| Rolle | Benutzername | Passwort | Berechtigung |
| --- | --- | --- | --- |
| Admin | `admin` | `Admin123` | Darf alle Mitarbeiterprofile sehen |
| User | `user` | `User1234` | Darf nur das eigene Profil sehen |

CAPTCHA beim Login:

```text
7
```

## Umgesetzte OWASP-Risiken

In der Applikation wurden drei OWASP-Risiken umgesetzt:

1. A01 Broken Access Control
2. A07 Identification and Authentication Failures
3. A09 Security Logging and Monitoring Failures

## A01 Broken Access Control

### Unsichere Demo

Die unsichere Route zeigt, was passiert, wenn keine Autorisierung geprüft wird.
Durch Ändern der ID in der URL können fremde Mitarbeiterdaten geöffnet werden.

```text
/vulnerable/employees/1
/vulnerable/employees/2
/vulnerable/employees/3
```

Diese Route ist absichtlich verwundbar und dient nur zur Demonstration.

### Gegenmassnahme

Die sichere Route prüft, ob der eingeloggte Benutzer die angeforderten Daten sehen darf.

```text
/employees/1
/employees/2
/employees/3
```

Regeln:

- Normale User dürfen nur ihr eigenes Profil sehen.
- Admins dürfen alle Mitarbeiterprofile sehen.
- Fremde Profile werden für normale User blockiert.

Die Autorisierung wird mit Spring Security und rollenbasierter Zugriffskontrolle umgesetzt.

## A07 Identification and Authentication Failures

Der Login wurde gegen typische Authentifizierungsprobleme abgesichert.

Umgesetzte Gegenmassnahmen:

- Passwörter werden mit BCrypt gehasht.
- Beim Login muss ein CAPTCHA gelöst werden.
- Nach 5 falschen Login-Versuchen wird der Account blockiert.
- Fehlgeschlagene Login-Versuche werden protokolliert.

Demo:

1. Login-Seite öffnen.
2. Falsches CAPTCHA eingeben.
3. Falsches Passwort mehrfach testen.
4. Nach 5 Fehlversuchen ist der Account gesperrt.

## A09 Security Logging and Monitoring Failures

Sicherheitsrelevante Ereignisse werden mit SLF4J und Logback geloggt.

Geloggte Ereignisse:

- Erfolgreiche Logins
- Fehlgeschlagene Logins
- Zugriff auf Mitarbeiterdaten

Wichtig:

- Passwörter werden nicht geloggt.
- Nur sicherheitsrelevante Informationen wie Benutzername, IP-Adresse und Ereignistyp werden protokolliert.

Beispiele:

```text
LOGIN_SUCCESS user=user ip=...
LOGIN_FAILED user=user ip=... reason=...
EMPLOYEE_ACCESS user=user employeeId=...
```

## Demo-Ablauf für die Präsentation

1. Dashboard öffnen: `http://localhost:8080`
2. Unsichere Route zeigen: `/vulnerable/employees/1`
3. ID in der URL ändern: `/vulnerable/employees/2`
4. Erklären, dass dies Broken Access Control ist.
5. Sichere Route öffnen: `/employees`
6. Als `user` einloggen.
7. Zeigen, dass der User nur eigene Daten sehen darf.
8. Fremde Mitarbeiter-ID testen und Zugriff verweigert zeigen.
9. Als `admin` einloggen.
10. Zeigen, dass Admins alle Profile sehen dürfen.
11. Falsches CAPTCHA oder falsches Passwort testen.
12. Terminal-Logs zeigen.

## Architektur

```text
Browser
-> Spring Security Filter Chain
-> CAPTCHA Filter
-> Login Filter
-> CSRF Filter
-> Authorization Filter
-> Controller
-> Repository
-> H2 Database
```

## Sinn des Projekts

Der Sinn des Projekts ist nicht nur eine Mitarbeiterverwaltung zu bauen.
Der eigentliche Fokus liegt darauf, OWASP-Risiken praktisch zu zeigen:

- Zuerst wird die Schwachstelle demonstriert.
- Danach wird die passende Gegenmassnahme umgesetzt.
- Dadurch wird sichtbar, warum Autorisierung, sichere Logins und Logging wichtig sind.

Kurz gesagt:

```text
Unsichere Demo -> Angriff verstehen -> Sichere Lösung zeigen
```
