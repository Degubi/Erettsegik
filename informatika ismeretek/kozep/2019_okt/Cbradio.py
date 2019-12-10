# -*- coding: UTF-8 -*-

class Bejegyzes:
    ora = 0
    perc = 0
    adasok = 0
    nev = ""
        
    def __init__(self, line):
        split = line.split(";")
        
        self.ora = int(split[0])
        self.perc = int(split[1])
        self.adasok = int(split[2])
        self.nev = split[3]

def atszamolPercre(ora, perc):
    return ora * 60 + perc

bejegyzesek = []
with open("cb.txt", "r") as file:
    lines = file.read().splitlines()
    
    for i in range(1, len(lines)):
        bejegyzesek.append(Bejegyzes(lines[i]))

print(f"3. Feladat: Bejegyz�sek sz�ma: {len(bejegyzesek)}")

volte4adasos = False
for bejegyzes in bejegyzesek:
    if bejegyzes.adasok == 4:
        voltE4Adasos = True
        break
        
if voltE4Adasos:
    print("4. Feladat: Volt 4 ad�st ind�t� sof�r")
else:
    print("4. Feladat: Nem volt 4 ad�st ind�t� sof�r")

bekertNev = input("5. Feladat: �rj be egy nevet")
bekertHasznalatok = 0
            
for bejegyzes in bejegyzesek:
    if bejegyzes.nev == bekertNev:
        bekertHasznalatok += bejegyzes.adasok
            
if bekertHasznalatok > 0:
    print(bekertNev + " " + bekertHasznalatok + "x haszn�lta a r�di�t")
else:
    print("Nincs ilyen nev� sof�r!")
    
with open("cb2.txt", "w") as file:
    file.write("Kezdes;Nev;AdasDb\n")
    
    for bejegyzes in bejegyzesek:
        file.write(str(atszamolPercre(bejegyzes.ora, bejegyzes.perc)) + ";" + bejegyzes.nev + ";" + str(bejegyzes.adasok) + "\n")
        
soforokAdasszamokkal = {}
for bejegyzes in bejegyzesek:
    soforNeve = bejegyzes.nev
            
    soforokAdasszamokkal[soforNeve] = soforokAdasszamokkal.get(soforNeve, 0) + bejegyzes.adasok

legtobbAdasBejegyzes = next(iter(soforokAdasszamokkal.items()))

for bejegyzes in soforokAdasszamokkal.items():
    if bejegyzes[1] > legtobbAdasBejegyzes[1]:
        legtobbAdasBejegyzes = bejegyzes
        
print("9. Feladat: Legt�bb ad�st ind�t� sof�r: " + legtobbAdasBejegyzes[0] + ", ad�sok: " + str(legtobbAdasBejegyzes[1]))