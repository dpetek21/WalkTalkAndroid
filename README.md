
# Walk Talk: Hike with your favorite people

## Projektni tim

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Domagoj Petek | dpetek21@student.foi.hr | 1191242810 | dpetek21 | G02
Marin Puškadija | mpuskadij20@student.foi.hr | 0016149444 | mpuskadij | G02
Gabriel Vesel | gvesel20@student.foi.hr | 0016147135 | gvesel20 | G02

## Opis domene

Domena aplikacije obuhvaća planinarenje u smislu organiziranja grupnih događaja i komunikaciju između ljudi koji su zainteresirani za planinarenje u grupama.

## Specifikacija projekta

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Login i registracija korisnika | Za prvi pristup aplikaciji, potrebna je registracija korisnika pomoću e-maila, korisničkog imena i lozinke. Za pristup aplikaciji potrena je autentikacija korisnika pomoću login funkcionalnosti. Korisnik se logira s pomoću korisničkog imena i lozinke koju je odabrao prilikom registracije.  | Domagoj Petek
F02 | Upravljanje profilom | Korisnik može promijeniti display ime, dodati opis svojeg profila, promijeniti sliku profila, dodati dodatne pojedinosti o osobnom životu. Korisnik svojem prijatelju može dati preporuku.| Domagoj Petek
F03 | Upravljanje prijateljima | Korisnik može dodati pojedinačnog prijatelja, pregledati listu prijatelja i informacije o pojedinom prijatelju te izbrisati postojeće prijatelje. | Marin Puškadija
F04 | Praćenje statistike o korisniku | Aplikacija prati dostignuća korisnika u pozadini (broj koraka, prijeđen broj kilometara, itd.) putem pedmetra na dnevnoj, mjesečnoj i godišnjoj razini | Gabriel Vesel
F05 | Dodjeljivanje dostignuća ovisno o statistici te kompetitivna ljestvica | Aplikacija dodjeljuje dostignuća korisniku putem obavijesti temeljem statistike u obliku medalja koje mogu biti prikazane na profilu (1km prijeđen, 5 prijatelja dodano, dobivene 3 preporuke, itd.) Nadalje, statistika služi za rangiranje na kompetitvnoj ljestvici među prijateljima. Ljestvica radi na dnevnoj, mjesečnoj i godišnjoj razini. | Gabriel Vesel
F06 | Kreiranje i upravljanje rutama | Aplikacija omogućuje svim korisnicima da stvaraju rute: početna i završna točka planinarenja, težina rute, naziv rute. Moguća je naknadna izmjena i brisanje rute | Domagoj Petek
F07 | Upravljanje događajima | Aplikacija omogućuje svim korisnicima da kreiraju događaj: pridruživanje rute (obavezno), vidljivost događaja (svima ili prijateljima samo), ime događaja, vrijeme i datum događaja, očekivana brzina hoda. Moguća je naknadna izmjena i brisanje događaja, pozivanje prijatelja. Vlasnik može pozvati prijatelje na svoj događaj, a oni mogu prihvatiti pozivnicu ili odbiti. Vlasnik mora odobriti ili odbiti svakog korisnika koji njemu pošalje zahtjev za pridruživanje na događaj.  | Marin Puškadija
F08 | Privatni i grupni chat | Grupni chat je kreiran kad je događaj kreiran i sadrži sve korisnike koji su dodani na događaj. Privatni chat se može započeti sa bilo kojim drugim korisnikom. | Gabriel Vesel
F09 | Obavijesti | Aplikacija javlja obavijesti za: chat kad dođe nove poruka, događaj kad se promijeni ili izbriše, ostvareno postignuće, novi zahtjev za prijateljstvo, status prijave na događaj (odbijeno ili odobreno). | Marin Puškadija 

## Tehnologije i oprema

Android Studio - Kotlin

Androi mobitel

OpenStreetMap

## Baza podataka i web server
Trebamo bazu podataka i pristup serveru za PHP skripte.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
