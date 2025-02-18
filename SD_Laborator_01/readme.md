# SD Laborator 01

Pentru a usura lucrul la acest laborator am pregatit un proiect de IntelliJ pe care-l puteti doar deschide si rula proiectul.
Server-ul glassfish va fi instalat automat de IDE si rulat.

Inainte de asta trebuie setate totusi urmatoarele:

## config.properties
Setati in fisierul `src/main/resources/config.properties` calea catre un director unde se vor serializa studentii stocati de aplicatie.

```java
students.path=D:\\SD\\SD_Laborator_01\\students
```

## Build Artifacts

Din IntelliJ, intrati in Build -> Build Artifacts si selectati All Artifacts. Ulterior dupa build, in `target/` ar trebui sa fi aparut un fisier `JEE-Test.war`. Aceasta este arhiva cu codul nostru, care va fi trimisa la glassfish si servita.

![alt text](images/image.png)


## Rularea serverului de Glassfish

In proiect este deja disponibila o configuratie pentru a porni server-ul si a da deploy la fisierul `war` in cadrul serverului.
![alt text](images/image2.png)

Ulterior pe http://localhost:8080 va fi disponibil server-ul de glassfish.
Pe http://localhost:4848 avem disponibila consola de administrator accesibila doar cu username-ul `admin`.

![alt text](images/image3.png)

Consola de admin ne indica ca aplicatia noastra este servita de glassfish la ruta `/JEE-Test`

![alt text](images/image4.png)

De aici putem interactiona cu aplicatia noastra. 

Atunci cand modificam codul trebuie doar sa urmam iar pasii de la [Build Artifacts](#build-artifacts) si sa folosim configuratia de `Redeploy`.

Spor!