import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Szamok {
    
    public static void main(String[] args) throws IOException {
        //Beolvas�s + t�rol�s
        Random rand = new Random();
        ArrayList<Feladat> feladatok = new ArrayList<>();
        List<String> minden = Files.readAllLines(Paths.get("felszam.txt"), StandardCharsets.ISO_8859_1);
        for(int k = 0; k < minden.size() - 1; k += 2) {
            String[] split = minden.get(k + 1).split(" ");
            feladatok.add(new Feladat(minden.get(k), Integer.parseInt(split[0]), Integer.parseInt(split[1]), split[2]));
        }
        
        //2. Feladat
        System.out.println(feladatok.size() + " Feladat van a f�jlban!");
        
        ArrayList<String> temakorok = new ArrayList<>();
        int[] matekCounters = new int[4];
        for(Feladat all : feladatok) {
            if(!temakorok.contains(all.temakor))
                temakorok.add(all.temakor);
            if(all.temakor.equals("matematika")) {
                ++matekCounters[0];
                if(all.pont == 1) {
                    ++matekCounters[1];
                }else if(all.pont == 2) {
                    ++matekCounters[2];
                }else {
                    ++matekCounters[3];
                }
            }
        }
        
        //3. Feladat ki�rat�s, ez felett a sz�ml�l�s
        System.out.println("Az adatfajlban " + matekCounters[0] + " matematika feladat van,\n1 pontot er: " + matekCounters[1] + ""
                + " feladat, 2 pontot er " + matekCounters[2] + " feladat, 3 pontot er " + matekCounters[3] + " feladat. ");
        
        feladatok.sort(new Comparator<Feladat>() {
            @Override
            public int compare(Feladat o1, Feladat o2) {
                return Integer.compare(o1.valasz, o2.valasz);
            }
        });
        
        //4. Feladat ki�rat�s, felette sorbarendez�s
        System.out.println("A legkisebb v�lasz� feladat: " + feladatok.get(0).valasz + ", a legnagyobb: " + feladatok.get(feladatok.size() - 1).valasz);
        
        //5. Feladat ki�rat�s, a meghat�roz�s a 2. feladat feletti list�ba t�rol�ssal van
        System.out.println("El�fordul� t�mak�r�k: " + temakorok);
        
        Scanner input = new Scanner(System.in);
        System.out.println("�rj be 1 t�mak�rt!");
        String readCat = input.nextLine();
        ArrayList<Feladat> categoriz�lt = new ArrayList<>();
        
        for(Feladat all : feladatok)
            if(all.temakor.equals(readCat))
                categoriz�lt.add(all);
        
        Feladat chosen = categoriz�lt.get(rand.nextInt(categoriz�lt.size()));
        
        //6. feladat ki�rat�s, fent a random kiv�laszt�s logika, elt�roljuk h�tha kell m�g k�s�bb a random feladat
        System.out.println(chosen.kerdes);
        
        //6. feladat 2. r�sze, k�rd�s ellen�rz�s, pontoz�s
        if(input.nextInt() == chosen.valasz) {
            System.out.println("Kapott pontsz�m: " + chosen.pont);
        }else{
            System.out.println("Rossz v�lasz, 0 pont...\nA helyes v�lasz: " + chosen.valasz);
        }
        input.close();
        
        //7. feladat, 10 k�l�nb�z� random feladat gener�l�s
        ArrayList<Feladat> generalt = new ArrayList<>();
        for(int k = 0; k < 10; ++k) {
            Feladat randomFeladat = feladatok.get(rand.nextInt(feladatok.size()));
            if(generalt.contains(randomFeladat)) {
                --k;
            }else{
                generalt.add(randomFeladat);
            }
        }
        
        //7. Feladat, �sszpont kisz�mol�s �s a k�rd�sek file-ba �r�sa
        int osszpont = 0;
        try(PrintWriter output = new PrintWriter("tesztfel.txt")){
            for(Feladat toPrint : generalt) {
                osszpont += toPrint.pont;
                output.println(toPrint.pont + " " + toPrint.kerdes);
            }
            output.print(osszpont);
        }
    }
    
    static class Feladat{
        String kerdes, temakor;
        int pont, valasz;
        
        public Feladat(String quest, int answ, int points, String cat) {
            kerdes = quest;
            valasz = answ;
            pont = points;
            temakor = cat;
        }
    }
}