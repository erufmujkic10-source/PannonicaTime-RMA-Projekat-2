PannonicaTime
PannonicaTime je moderna Android aplikacija razvijena za digitalizaciju usluga kompleksa Panonskih jezera u Tuzli.
Aplikacijja pruža korisnicima sve potrebne informacije i alate za planiranje posjete, kupovinu ulaznica i upravljanje korisničkim nalogom.
Uz integraciju real-time podataka o vremenskim uslovima

Ključne funkcionalnosti:
1. Home Page
Centralno mjesto aplikacije koje sadrži:
a) Informacije o atrakcijama: Brzi pregled sadržaja unutar kompleksa
b) Vremenski uslovi: Integrisan API servis koji u realnom vremenu prikazuje trenutnu temperaturu i vlažnost vazduha, pomažući posjetiocima u planiranju boravka
c) Promocije: Sekcija posvećena aktuelnim popustima
d) Brza navigacija: Direktan pristup Shop modulu

2. Shop modul
Sistem za kupovinu koji obuhvata:
a) Ponuda: Karte, mobilijer (ležaljke, suncobrani) i specijalni paket aranžmani
b) Korpa: Upravljanje količinama sa automatskim proračunom cijena
c) QR Kod: Nakon potvrde kupovine, aplikacija generiše unikatni QR kod koji služi kao digitalna potvrda narudžbe

3. Korisnički profil
a) Lični podaci: Mogućnost izmjene ličnih informacija i promjene lozinke
b) Historija narudžbi: Detaljan pregled svih prethodnih kupovina korisnika

4. Settings
a) Dark Mode: Podrška za tamnu temu radi ugodnijeg korištenja palikacije u uslovima slabijeg osvjetljenja
b) Push notifikacije: Konfigurabilne notifikacije za obavještavanje

TEHNOLOŠKI STACK
Aplikacija koristi provjerene tehnologije za osiguranje visokih performansi:
1. Programski jezik: Java
2. Baza podataka: Room (za lokalno skladištenje podataka)
3. Cloud platforma: Firebase (za autentifikaciju korisnika, sinhromizaciju podataka i upravljanje nalogom)
4. API komunikacija: REST API servis za dobijanje podataka o vremenskim prilikama (temperatura i vlažnost)
5. QR Kod: Integrisana biblioteka za generisanje unukatnih kodova po narudžbi
6. Arhitektura: MVVM sa DAO(Data Access Object) objektima za čistiju strukturu koda

ODRŽAVANJE I PROŠIRENJE
Projekaat je modularno građen. Sistem je "offline-first" orijentisan, što znači da lokalna Room baza omogućava rad bez interneta, dok se podaci automatski sinhronizuju sa Firebase-om čim se uspostavi konekcija.
Arhitektura je spremna za dalju nadogradnju u smislu dodavanja novih metoda plaćanja ili širenja API servisa za još detaljnije informacije o stanju na jezerima.

NAPOMENA
Projekat je razvijen u svrhu edukacije.
