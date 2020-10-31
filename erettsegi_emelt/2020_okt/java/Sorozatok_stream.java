import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class Sorozatok_stream {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("lista.txt"));
        var sorozatok = IntStream.iterate(0, k -> k < lines.size(), k -> k + 5)
                                 .mapToObj(i -> new Sorozat(lines, i))
                                 .toArray(Sorozat[]::new);

        var ismertDatumuakSzama = Arrays.stream(sorozatok)
                                        .filter(k -> k.adasbaKerulesiDatum != null)
                                        .count();

        System.out.println("2. Feladat: " + ismertDatumuakSzama + " db ismert dátumú epizód van");

        var keszitoAltalLatottakSzama = Arrays.stream(sorozatok)
                                              .filter(k -> k.lattaEMarAKeszito)
                                              .count();

        System.out.printf("3. Feladat: Látottak százaléka: %.2f%%\n", ((float) keszitoAltalLatottakSzama / sorozatok.length * 100));

        var osszesElpazaroltPerc = Arrays.stream(sorozatok)
                                         .filter(k -> k.lattaEMarAKeszito)
                                         .mapToInt(k -> k.epizodonkentiHossz)
                                         .sum();

        var elpazaroltNapok = TimeUnit.MINUTES.toDays(osszesElpazaroltPerc);
        var elpazaroltNapokPercekben = TimeUnit.DAYS.toMinutes(elpazaroltNapok);
        var elpazaroltOrak = TimeUnit.MINUTES.toHours(osszesElpazaroltPerc - elpazaroltNapokPercekben);
        var elpazaroltOrakPercekben = TimeUnit.HOURS.toMinutes(elpazaroltOrak);
        var elpazaroltPercek = osszesElpazaroltPerc - elpazaroltNapokPercekben - elpazaroltOrakPercekben;

        System.out.printf("4. Feladat: Eltöltött idő: %d nap, %d óra és %d perc\n", elpazaroltNapok, elpazaroltOrak, elpazaroltPercek);
        System.out.println("5. Feladat: Írj be 1 dátumot! (éééé.hh.nn)");

        try(var input = new Scanner(System.in)) {
            var bekertDatum = LocalDate.parse(input.nextLine().replace('.', '-'));

            Arrays.stream(sorozatok)
                  .filter(k -> k.adasbaKerulesiDatum != null)
                  .filter(k -> k.adasbaKerulesiDatum.isBefore(bekertDatum) || k.adasbaKerulesiDatum.isEqual(bekertDatum))
                  .filter(k -> !k.lattaEMarAKeszito)
                  .forEach(k -> System.out.println(k.evadokSzama + "x" + k.epizodokSzama + "\t" + k.cim));

            System.out.println("7. Feladat: Add meg 1 hét napját! (h, k, sze, cs, p, szo, v)");

            var bekertNap = input.nextLine();
            var bekertNapraEsok = Arrays.stream(sorozatok)
                                        .filter(k -> k.adasbaKerulesiDatum != null)
                                        .filter(k -> bekertNap.equals(hetnapja(k.adasbaKerulesiDatum.getYear(), k.adasbaKerulesiDatum.getMonthValue(), k.adasbaKerulesiDatum.getDayOfMonth())))
                                        .map(k -> k.cim)
                                        .distinct()
                                        .toArray(String[]::new);

            var kiirando = bekertNapraEsok.length == 0 ? "Az adott napon nem kerül adásba sorozat."
                                                       : String.join("\n", bekertNapraEsok);
            System.out.println(kiirando);
        }

        var stat = Arrays.stream(sorozatok)
                         .collect(Collectors.groupingBy(k -> k.cim,
                                  Collectors.teeing(Collectors.summingInt(k -> k.epizodonkentiHossz), Collectors.counting(), (hossz, db) -> hossz + " " + db)));

        var fileba = stat.entrySet().stream()
                         .map(e -> e.getKey() + " " + e.getValue())
                         .collect(Collectors.toList());

        Files.write(Path.of("summa.txt"), fileba);
    }


    public static final String[] napok = { "v", "h", "k", "sz", "cs", "p", "szo" };
    public static final int[] honapok = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };

    public static String hetnapja(int ev, int ho, int nap) {
        ev = ho < 3 ? ev - 1 : ev;

        return napok[(ev + ev / 4 - ev / 100 + ev / 400 + honapok[ho - 1] + nap) % 7];
    }
}