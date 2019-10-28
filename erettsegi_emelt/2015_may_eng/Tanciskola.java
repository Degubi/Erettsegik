import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Tanciskola {
    
    public static void main(String... args) throws IOException {
        List<String> file = Files.readAllLines(Paths.get("tancrend.txt"));
        ArrayList<Tanc> tancok = new ArrayList<>();
        
        for(int k = 0; k < file.size(); k += 3) {
            tancok.add(new Tanc(file.get(k), file.get(k + 1), file.get(k + 2)));
        }
        
        System.out.println("Els� t�nc neve: " + tancok.get(0).category + ", az utols��: " + tancok.get(tancok.size() - 1).category);
        
        int szambaCounter = 0;
        for(Tanc dansz : tancok) {
            if(dansz.category.equals("samba")) {
                ++szambaCounter;
            }
        }
        System.out.println("�sszesen " + szambaCounter + "-an szamb�sztak");
        
        ArrayList<String> viliCat = new ArrayList<>();
        for(Tanc dansz : tancok) {
            if(dansz.woman.equals("Vilma") && !viliCat.contains(dansz.category)) {
                viliCat.add(dansz.category);
            }
        }
        System.out.println("Vilma �ltal t�ncolt kateg�ri�k: " + viliCat);
        System.out.println("�rj be 1 kateg�ri�t!");
        
        try(Scanner input = new Scanner(System.in)){
            String readCat = input.nextLine();
            boolean hasPrinted = false;
            for(Tanc dansz : tancok) {
                if(dansz.woman.equals("Vilma") && dansz.category.equals(readCat)) {
                    System.out.println("Vilma a " + readCat + " kateg�ri�ban " + dansz.man + "-val t�ncolt");
                    hasPrinted = true;
                }
            }
            if(!hasPrinted) {
                System.out.println("Vilma a " + readCat + " kateg�ri�ban nem t�ncolt");
            }
        }
        HashSet<String> csajok = new HashSet<>();
        HashSet<String> skacok = new HashSet<>();
        ArrayList<Szereplo> lanyok = new ArrayList<>();
        ArrayList<Szereplo> fiuk = new ArrayList<>();

        PrintWriter output = new PrintWriter("szereplok.txt");
        for(Tanc dans : tancok) {
            csajok.add(dans.woman);
            skacok.add(dans.man);
        }
        for(String cs : csajok) {
            lanyok.add(new Szereplo(cs));
        }
        for(String fik : skacok) {
            fiuk.add(new Szereplo(fik));
        }
        output.print("L�nyok: ");
        
        for(int k = 0; k < lanyok.size(); ++k) {
            if(k != lanyok.size() - 1) {
                output.print(lanyok.get(k) + ", ");
            }else{
                output.print(lanyok.get(k));
            }
        }
        output.println();
        output.print("Fi�k: ");
        for(int k = 0; k < fiuk.size(); ++k) {
            if(k != fiuk.size() - 1) {
                output.print(fiuk.get(k) + ", ");
            }else{
                output.print(fiuk.get(k));
            }
        }
        output.close();
        
        for(Tanc dan : tancok) {
            for(Szereplo csaj : lanyok) {
                if(dan.woman.equals(csaj.name)) {
                    ++csaj.alkalmak;
                }
            }
            for(Szereplo fiu : fiuk) {
                if(dan.man.equals(fiu.name)) {
                    ++fiu.alkalmak;
                }
            }
        }
        lanyok.sort(Comparator.comparingInt(Szereplo::getAlkalmak).reversed());
        fiuk.sort(Comparator.comparingInt(Szereplo::getAlkalmak).reversed());
        
        int lanyMax = lanyok.get(0).alkalmak;
        int fiuMax = fiuk.get(0).alkalmak;
        
        System.out.print("A legt�bbet t�ncolt l�nyok: ");
        for(Szereplo la : lanyok) {
            if(la.alkalmak == lanyMax) {
                System.out.print(la.name + " ");
            }
        }
        System.out.println();
        System.out.print("A legt�bbet t�ncolt fi�k: ");
        for(Szereplo fi : fiuk) {
            if(fi.alkalmak == fiuMax) {
                System.out.print(fi.name + " ");
            }
        }
    }
    
    static final class Tanc{
        String category, woman, man;
        
        public Tanc(String cat, String wom, String ma) {
            category = cat;
            woman = wom;
            man = ma;
        }
    }
    
    static final class Szereplo{
        String name;
        int alkalmak = 0;
        
        public Szereplo(String neim) {
            name = neim;
        }
        @Override
        public String toString() {
            return name;
        }
        public int getAlkalmak() {
            return alkalmak;
        }
    }
}