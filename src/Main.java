import entities.Produto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre full file path: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Produto> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Produto(fields[0],Double.parseDouble((fields[1]))));
                line = br.readLine();
            }

            //Somando usando reduce
//            double avg = list.stream()
//                    .map(p -> p.getPrice())
//                    .reduce(0.0, (x,y) -> x + y) / list.size();

            //Somando usando o mapTODouble + sum()
            double avg = list.stream()
                      .mapToDouble(Produto::getPrice).sum() / list.size();

            System.out.println("Average price: " + String.format("%.2f", avg));

           // Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
           // Forma abreviada do Comparator
            Comparator<String> comp = Comparator.comparing(String::toUpperCase);

            List<String> names = list.stream()
                    .filter(p -> p.getPrice() < avg)
                    .map(p -> p.getName()).sorted(comp.reversed()).collect(Collectors.toList());

            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}