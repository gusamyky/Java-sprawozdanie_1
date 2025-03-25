import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sławek Klasa implementująca kalkulator ONP
 */
public class ONP implements Serializable {
    @Serial
    private static final long serialVersionUID = 2;

    private final TabStack stack = new TabStack();
    private String currentResult;

    public static void main(String[] args) {
        ONP onp = new ONP();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Calculate expression");
            System.out.println("2. Show history (serialized objects)");
            System.out.println("3. Show history (xml)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter expression (e.g., (2+3)*(6-2)^2=): ");
                    String expression = scanner.nextLine();
                    try {
                        System.out.print(expression + " ");
                        String rownanieOnp = onp.przeksztalcNaOnp(expression);
                        System.out.print(rownanieOnp);
                        String wynik = onp.obliczOnp(rownanieOnp);
                        onp.setCurrentResult(wynik);
                        System.out.println(" " + wynik);
                        saveToFileSerialized(onp);
                        saveHistoryToXML(onp);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("History Serialized:");
                        List<ONP> history = onp.readFromFileSerialized();

                        for (ONP entry : history) {
                            System.out.println(entry.toString());
                        }
                    } catch (Exception e) {
                        System.out.println("Error reading history: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("History XML:");
                        List<ONP> history = readHistoryFromXML();

                        for (ONP entry : history) {
                            System.out.println(entry.toString());
                        }
                    } catch (Exception e) {
                        System.out.println("Error reading history: " + e.getMessage());
                    }
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    /**
     * Zapisuje historię obiektów ONP do pliku XML.
     * Metoda odczytuje dotychczasową historię, dodaje nowy wpis, a następnie zapisuje listę.
     *
     * @param newEntry nowy obiekt ONP do dodania do historii
     */
    public static void saveHistoryToXML(ONP newEntry) {
        // Odczytaj dotychczasową historię (jeśli plik nie istnieje, zwróci pustą listę)
        List<ONP> history = readHistoryFromXML();
        // Dodaj nowy wpis
        history.add(newEntry);

        // Utwórz instancję XStream z DomDriver
        XStream xstream = new XStream(new DomDriver());
        // Ustaw aliasy, aby wygenerowany XML był czytelny
        xstream.alias("onp", ONP.class);
        xstream.alias("history", List.class);

        // Konwersja listy na XML
        String xml = xstream.toXML(history);

        // Zapis do pliku XML
        try (FileWriter writer = new FileWriter("./output/history.xml")) {
            writer.write(xml);
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku XML: " + e.getMessage());
        }
    }

    /**
     * Odczytuje historię obiektów ONP z pliku XML.
     *
     * @return Listę obiektów ONP, lub pustą listę, jeśli plik nie istnieje lub wystąpi błąd.
     */
    public static List<ONP> readHistoryFromXML() {
        File file = new File("./output/history.xml");
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("onp", ONP.class);
        xstream.alias("history", List.class);

        if (!file.exists()) {
            try {
                // Upewnij się, że katalogi istnieją
                file.getParentFile().mkdirs();
                // Utwórz nowy plik
                file.createNewFile();
                // Zapisz pustą listę jako XML
                List<ONP> emptyHistory = new ArrayList<>();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(xstream.toXML(emptyHistory));
                }
                return emptyHistory;
            } catch (IOException e) {
                System.out.println("Błąd przy tworzeniu pliku: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        try (FileReader reader = new FileReader(file)) {
            // Konwersja XML do listy obiektów ONP
            List<ONP> history = (List<ONP>) xstream.fromXML(reader);
            // Jeśli plik jest pusty lub XML nie zawiera listy, zwróć pustą listę
            if (history == null) {
                return new ArrayList<>();
            }
            return history;
        } catch (IOException e) {
            System.out.println("Błąd odczytu z pliku XML: " + e.getMessage());
            return new ArrayList<>();
        }
    }



    /**
 * Metoda zapisuje obiekt do pliku w formie zserializowanej
 * */
    private static void saveToFileSerialized(ONP onp) {
        try {
            File file = new File("./output/history.dat");
            boolean append = file.exists();
            FileOutputStream fos = new FileOutputStream(file, true);
            ObjectOutputStream oos = append
                    ? new AppendableObjectOutputStream(fos)
                    : new ObjectOutputStream(fos);
            oos.writeObject(onp);
            oos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda odczytuje zserializowane obiekty z pliku
     */
    private List<ONP> readFromFileSerialized() {
        List<ONP> historyList = new ArrayList<>();
        File file = new File("./output/history.dat");

        if (!file.exists()) {
            System.out.println("No history available.");
            return historyList;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    ONP onpEntry = (ONP) ois.readObject();
                    historyList.add(onpEntry);
                } catch (EOFException eof) {
                    // Koniec pliku - wychodzimy z pętli
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading history: " + e.getMessage());
        }

        return historyList;
    }

    @Override
    public String toString() {
        return "ONP{" +
                "stack=" + stack +
                ", currentResult='" + currentResult + '\'' +
                '}';
    }



    /**
     * Metoda sprawdza czy równanie kończy się znakiem '='
     *
     * @param rownanie równanie do sprawdzenia
     * @return true jeśli równanie jest poprawne, false jeśli niepoprawne
     */
    boolean czyPoprawneRownanie(String rownanie) {
        return rownanie.endsWith("=");
    }

    /**
     * Metoda oblicza wartość wyrażenia zapisanego w postaci ONP
     *
     * @param rownanie równanie zapisane w postaci ONP
     * @return wartość obliczonego wyrażenia
     */
    public String obliczOnp(String rownanie) {
        if (czyPoprawneRownanie(rownanie)) {
            stack.setSize(0);
            String wynik = "";
            Double a = 0.0;
            Double b = 0.0;
            for (int i = 0; i < rownanie.length(); i++) {
                if (rownanie.charAt(i) >= '0' && rownanie.charAt(i) <= '9') {
                    wynik += rownanie.charAt(i);
                    if (!(rownanie.charAt(i + 1) >= '0' && rownanie.charAt(i + 1) <= '9')) {
                        stack.push(wynik);
                        wynik = "";
                    }

                } else if (rownanie.charAt(i) == '=') {
                    return stack.pop();
                } else if (rownanie.charAt(i) != ' ') {
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    switch (rownanie.charAt(i)) {
                        case ('+'): {
                            stack.push((a + b) + "");
                            break;
                        }
                        case ('-'): {
                            stack.push((a - b) + "");
                            break;
                        }
                        case ('x'):
                        case ('*'): {
                            stack.push((a * b) + "");
                            break;
                        }
                        case ('/'): {
                            if (b == 0) throw new ArithmeticException("Dzielenie przez 0");
                            stack.push((a / b) + "");
                            break;
                        }
                        case ('^'): {
                            stack.push(Math.pow(a, b) + "");
                            break;
                        }
                        default: {
                            throw new UnsupportedOperationException("Nieznany operator");
                        }
                    }
                }
            }
            return "0.0";
        } else {
            throw new IllegalArgumentException("Brak znaku '=' na końcu równania");
        }
    }

    /**
     * Metoda zamienia równanie na postać ONP
     *
     * @param rownanie równanie do zamiany na postać ONP
     * @return równanie w postaci ONP
     */
    public String przeksztalcNaOnp(String rownanie) {

        if (czyPoprawneRownanie(rownanie)) {
            String wynik = "";
            for (int i = 0; i < rownanie.length(); i++) {
                if (rownanie.charAt(i) >= '0' && rownanie.charAt(i) <= '9') {
                    wynik += rownanie.charAt(i);
                    if (!(rownanie.charAt(i + 1) >= '0' && rownanie.charAt(i + 1) <= '9')) wynik += " ";
                } else switch (rownanie.charAt(i)) {
                    case ('+'):
                    case ('-'): {
                        while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")) {
                            wynik = wynik + stack.pop() + " ";
                        }
                        String str = "" + rownanie.charAt(i);
                        stack.push(str);
                        break;
                    }
                    case ('x'):
                    case ('*'):
                    case ('/'): {
                        while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(") && !stack.showValue(stack.getSize() - 1).equals("+") && !stack.showValue(stack.getSize() - 1).equals("-")) {
                            wynik = wynik + stack.pop() + " ";
                        }
                        String str = "" + rownanie.charAt(i);
                        stack.push(str);
                        break;
                    }
                    case ('^'): {
                        while (stack.getSize() > 0 && stack.showValue(stack.getSize() - 1).equals("^")) {
                            wynik = wynik + stack.pop() + " ";
                        }
                        String str = "" + rownanie.charAt(i);
                        stack.push(str);
                        break;
                    }
                    case ('('): {
                        String str = "" + rownanie.charAt(i);
                        stack.push(str);
                        break;
                    }
                    case (')'): {
                        while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")) {
                            wynik = wynik + stack.pop() + " ";
                        }
                        // zdjęcie ze stosu znaku (
                        stack.pop();
                        break;
                    }
                    case ('='): {
                        while (stack.getSize() > 0) {
                            wynik = wynik + stack.pop() + " ";
                        }
                        wynik += "=";
                    }

                    case ('!'):
                    case ('√'):
                    case ('%'): {
                        while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(") && !stack.showValue(stack.getSize() - 1).equals("+") && !stack.showValue(stack.getSize() - 1).equals("-")) {
                            wynik = wynik + stack.pop() + " ";
                        }
                        String str = "" + rownanie.charAt(i);
                        stack.push(str);
                        break;
                    }
                    default: {
                        throw new UnsupportedOperationException("Nieobsługiwany operator: " + rownanie.charAt(i));
                    }
                }
            }
            return wynik;
        } else return "null";
    }

    public void setCurrentResult(String currentResult) {
        this.currentResult = currentResult;
    }
}