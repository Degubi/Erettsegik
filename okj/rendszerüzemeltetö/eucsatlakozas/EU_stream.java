import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class EU_stream {
    public static void main(String[] args) throws IOException {
        var csatlakozasok = Files.lines(Path.of("EUcsatlakozas.txt"), StandardCharsets.ISO_8859_1)
                                 .map(k -> k.split(";"))
                                 .collect(Collectors.toMap(k -> k[0], k -> LocalDate.parse(k[1].replace('.', '-'))));
        
        System.out.println("3. Feladat: 2018-ig EU �llamok sz�ma: " + csatlakozasok.size());
        
        var csatlakozott2007 = csatlakozasok.values().stream()
                                            .filter(k -> k.getYear() == 2007)
                                            .count();
        
        System.out.println("4. Feladat: 2007-ben csatlakozott orsz�gok sz�ma: " + csatlakozott2007);
        
        csatlakozasok.entrySet().stream()
                     .filter(k -> k.getKey().equals("Magyarorsz�g"))
                     .findFirst()
                     .ifPresent(k -> System.out.println("5. Feladat: Magyarorsz�g csatlakoz�sa: " + k.getValue()));
        
        if(csatlakozasok.values().stream().anyMatch(k -> k.getMonth() == Month.MAY)) {
            System.out.println("6. Feladat: Volt m�jusban csatlakoz�s");
        }else{
            System.out.println("6. Feladat: Nem volt m�jusban csatlakoz�s");
        }
        
        csatlakozasok.entrySet().stream()
                     .max(Comparator.comparing(Entry::getValue))
                     .ifPresent(k -> System.out.println("7. Feladat: Utolj�ra csatlakozott: " + k.getKey()));
        
        System.out.println("8. Feladat:");
        csatlakozasok.values().stream()
                     .collect(Collectors.groupingBy(k -> k.getYear(), Collectors.counting()))
                     .forEach((ev, db) -> System.out.println(ev + " - " + db + " db orsz�g"));
    }
}