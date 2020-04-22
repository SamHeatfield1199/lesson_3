import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Cryptography {

    public static Scanner in = new Scanner(System.in);
    public static final String DIR1 = "C:\\Users\\asmir\\Desktop\\5 семестр\\Программирование\\lesson_3-master\\examples";
    public static File keyfile;
    public static File textfile;
    public static String path;
    public static File alphabet;
    public static ArrayList<Character> arrayList;
    public static ArrayList<Character> arrayKey1;
    public static ArrayList<Character> arrayKey2;

    public static void reader(File textfile, ArrayList arrayList){

        try (FileReader reader = new FileReader(textfile)) { // читаем посимвольно текст
            int c;
            while ((c = reader.read()) != -1) {
                arrayList.add((char) c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }//для чтения текста

    public static void writer(File textfile, ArrayList<Character> arrayList) throws IOException {

        FileWriter writer = new FileWriter(textfile + ".encode");//записываем в файл зашифрованный текст
            for (char v : arrayList) {
                writer.write(v);
            }
            writer.close();
           // textfile.delete();
    }//для записи текста


    public static void shifrzameny() throws IOException {

        in.nextLine();
        arrayList = new ArrayList<>();//массив для текста
        arrayKey1 = new ArrayList<>();//массив для исходного алфавита
        arrayKey2 = new ArrayList<>();//массив для преображенного алфавита

        System.out.println("Укажите файл с текстом: ");
        path = in.nextLine();
        textfile = new File(DIR1 + "\\" + path);

        System.out.println("Укажите файл с ключом: ");
        path = in.nextLine();
        keyfile = new File(DIR1 + "\\" + path); //получили пути к файлам с текстом и ключами

        Optional<String> line = Files.lines(Paths.get(DIR1 + "\\" + path)).findFirst();//check first line

        if (line.get().contains("шифр замены")) {

            reader(textfile, arrayList);

            Scanner scan = new Scanner(keyfile);
            String b;
            while (scan.hasNextLine()) {//читаем буквы
                b = scan.nextLine();
                if (b != null && !b.isEmpty()) {
                    arrayKey1.add(b.charAt(2));
                    if (b.length() > 10)
                        arrayKey2.add(b.charAt(10));
                } else
                    break;
            }

            arrayKey1.remove(0);
            arrayKey1.remove(0);

            arrayKey2.remove(0);

            for (int i = 0; i < arrayList.size(); i++) {
                for (int j = 0; j < arrayKey1.size(); j++) {

                    if (arrayList.get(i).equals(arrayKey1.get(j))) {
                        arrayList.set(i, arrayKey2.get(j));
                        break;
                    }
                }
            }  //шифруем

            writer(textfile, arrayList);

        } else {
            System.out.println("Не тот шифр");
        }
    }

    public static void unshifrzameny() throws IOException {

        in.nextLine();
        System.out.println("Укажите файл с зашифрованным текстом: ");
        path = in.nextLine();

        if (path.contains(".encode")) {
            arrayList = new ArrayList<>();//массив для текста
            arrayKey1 = new ArrayList<>();//массив для исходного алфавита
            arrayKey2 = new ArrayList<>();//массив для преображенного алфавита

            textfile = new File(DIR1 + "\\" + path);
            String newPath = path.replace(".encode", "");

            System.out.println("Укажите файл с ключом: ");
            path = in.nextLine();
            keyfile = new File(DIR1 + "\\" + path); //получили пути к файлам с текстом и ключами

            Optional<String> line = Files.lines(Paths.get(DIR1 + "\\" + path)).findFirst();

            if (line.get().contains("шифр замены")) {

                reader(textfile, arrayList);

                Scanner scan = new Scanner(keyfile);
                String b;
                while (scan.hasNextLine()) {//читаем буквы
                    b = scan.nextLine();
                    if (b != null && !b.isEmpty()) {
                        arrayKey1.add(b.charAt(2));
                        if (b.length() > 10)
                            arrayKey2.add(b.charAt(10));
                    } else
                        break;
                }

                arrayKey1.remove(0);
                arrayKey1.remove(0);
                arrayKey2.remove(0);

                for (int i = 0; i < arrayList.size(); i++) {
                    for (int j = 0; j < arrayKey1.size(); j++) {

                        if (arrayList.get(i).equals(arrayKey2.get(j))) {
                            arrayList.set(i, arrayKey1.get(j));
                            break;
                        }
                    }
                }  //unшифруем

                FileWriter writer = new FileWriter(DIR1 + "\\" + newPath);//записываем в файл зашифрованный текст
                for (char v : arrayList) {
                    writer.write(v);
                }
                writer.close();

            } else {
                System.out.println("Не тот шифр");
            }
        } else {
            System.out.println("Неправильно введено название файла");
        }
    }

    public static void generationKeyZamena() throws IOException {

       // String alph = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        in.nextLine();

        System.out.println("Укажите файл алфавитом ");
        path = in.nextLine();
        alphabet = new File(DIR1 + "\\" + path);

        arrayList = new ArrayList<>();//массив для алфавита

        Scanner scan = new Scanner(alphabet);
        String b;
        while (scan.hasNextLine()) {//читаем буквы
            b = scan.nextLine();
            if (b != null && !b.isEmpty())
                arrayList.add(b.charAt(0));
        }

        ArrayList<Character> arrayKey = (ArrayList<Character>) arrayList.clone();
        Collections.shuffle(arrayKey);

        System.out.println("Введите название для файла с ключом ");
        path = in.nextLine();

        BufferedWriter writer = new BufferedWriter(new FileWriter(DIR1 + "\\" + path + ".key"));
        writer.write("alg: шифр замены");
        writer.newLine();
        writer.write("key:");
        writer.newLine();

        for (int i = 0; i < arrayList.size(); i++) {
            writer.write("\\\"" + arrayList.get(i) + "\\\" - \\\"" + arrayKey.get(i) + "\\\"");
            writer.newLine();
        }
        writer.close();
    }


    public static void shifrperestanovky() throws IOException {

        in.nextLine();
        arrayList = new ArrayList<Character>();//массив для текста
        ArrayList<Integer> arrayKey = new ArrayList<>();

        System.out.println("Укажите файл с текстом: ");
        path = in.nextLine();
        textfile = new File(DIR1 + "\\" + path);

        System.out.println("Укажите файл с ключом: ");
        path = in.nextLine();
        keyfile = new File(DIR1 + "\\" + path); //получили пути к файлам с текстом и ключами

        Optional<String> line = Files.lines(Paths.get(DIR1 + "\\" + path)).findFirst();
        if (line.get().contains("шифр перестановки")) {

            reader(textfile, arrayList);

            BufferedReader reader = new BufferedReader(new FileReader(keyfile));
            String line1;
            reader.readLine();
            reader.readLine();

            while ((line1 = reader.readLine()) != null) {
                    String[] n;
                    String d = " = "; // Разделитель
                    n = line1.split(d);
                    arrayKey.add(Integer.parseInt(n[1]));
            }

            Random r = new Random();
            char[] sAlphabet = "абвгдеёжзийклмнопрстуфхццшщъыьэюя".toCharArray();
            int sLength = sAlphabet.length;

            int n;
            int k = 0;
            int j;

            if (arrayList.size() % arrayKey.size() != 0) {
                while (arrayList.size() % arrayKey.size() != 0) {
                    arrayList.add(sAlphabet[r.nextInt(sLength)]);
                }
            }

            ArrayList<Character> arrayList1 = (ArrayList<Character>) arrayList.clone();

            for (int i = 0; i < arrayList.size(); i++) {
                n = i % arrayKey.size();
                if (i % arrayKey.size() == 0) {
                    k = i;
                }
                arrayList.set(i, arrayList1.get(arrayKey.get(n) + k));
            }//шифруем

            writer(textfile, arrayList);

        } else {
            System.out.println("Не тот шифр");
        }
    }//для шифра перестановки

    public static void unshifrperestanovky() throws IOException {

        in.nextLine();
        System.out.println("Укажите файл с зашифрованным текстом: ");
        path = in.nextLine();

        if (path.contains(".encode")) {

            arrayList = new ArrayList<>();//массив для текста
            ArrayList<Integer> arrayKey = new ArrayList<>();//массив для исходного алфавита

            textfile = new File(DIR1 + "\\" + path);

            String newPath = path.replace(".encode", "");

            System.out.println("Укажите файл с ключом: ");
            path = in.nextLine();

            keyfile = new File(DIR1 + "\\" + path); //получили пути к файлам с текстом и ключами

            Optional<String> line = Files.lines(Paths.get(DIR1 + "\\" + path)).findFirst();

            if (line.get().contains("шифр перестановки")) {

                reader(textfile, arrayList);


                BufferedReader reader = new BufferedReader(new FileReader(keyfile));
                reader.readLine();
                reader.readLine();

                String line1;
                while ((line1 = reader.readLine()) != null) {

                        String[] n;
                        String d = " = "; // Разделитель
                        n = line1.split(d);
                        arrayKey.add(Integer.parseInt(n[1]));

                } //читаем ключ

                ArrayList<Character> arrayList1 = (ArrayList<Character>) arrayList.clone();
                int n;
                int k = 0;
                for (int i = 0; i < arrayList1.size(); i++) {

                    n = i % arrayKey.size();
                    if (i % arrayKey.size() == 0) {
                        k = i;
                    }

                    arrayList.set((arrayKey.get(n) + k), arrayList1.get(i));
                }

                FileWriter writer = new FileWriter(DIR1 + "\\" + newPath);//записываем в файл зашифрованный текст
                for (char v : arrayList) {
                    writer.write(v);
                }
                writer.close();

            } else {
                System.out.println("Не тот шифр");
            }

        } else {
            System.out.println("Неправильно введено название файла");
        }

    }//для шифра перестановки

    public static void generationKeyP() throws IOException{

        in.nextLine();
        System.out.println("Укажите размер блока перестановки");
        int razmer = in.nextInt();
        in.nextLine();

        ArrayList<Integer> arrayKey = new ArrayList<>();//массив для ключа

        for (int i = 0; i < razmer; i++) {
            arrayKey.add(i);
        }

        Collections.shuffle(arrayKey);//перемешиваем


        System.out.println("Введите название для файла с ключом ");
        path = in.nextLine();

        BufferedWriter writer = new BufferedWriter(new FileWriter(DIR1 + "\\" + path + ".key"));
        writer.write("alg: шифр перестановки");
        writer.newLine();
        writer.write("key:");
        writer.newLine();

        for (int i = 0; i < arrayKey.size(); i++) {
            writer.write(i + " = " + arrayKey.get(i));
            writer.newLine();
        }
        writer.close();
    }


    public static void shifrgammirovaniya() throws IOException {

        char alph[] = " абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        in.nextLine();
        arrayList = new ArrayList<>();
        ArrayList<Character> arrayKey = new ArrayList<>();
        ArrayList<Character> arrayNew = new ArrayList<>();

        System.out.println("Укажите файл с текстом: ");
        path = in.nextLine();
        textfile = new File(DIR1 + "\\" + path);

        System.out.println("Укажите файл с ключом: ");
        path = in.nextLine();
        keyfile = new File(DIR1 + "\\" + path);
        Optional<String> line = Files.lines(Paths.get(DIR1 + "\\" + path)).findFirst();//check first line

        if (line.get().contains("шифр гаммирования")) {

            reader(textfile, arrayList);

            Scanner scan = new Scanner(keyfile);
            String b;
            while (scan.hasNextLine()) {//читаем буквы
                b = scan.nextLine();
                if (b != null && !b.isEmpty()) {
                    arrayKey.add(b.charAt(2));
                } else
                    break;
            }

            arrayKey.remove(0);
            arrayKey.remove(0);

            int k = 0;
            int l = 0;
            boolean flag1 = false;
            boolean flag2 = false;

            //System.out.println("lengt " + alph.length);

            for (int i = 0; i < arrayList.size(); i++) {
                for (int j =0; j < alph.length; j++){
                    if(arrayList.get(i).equals(alph[j])){
                        k+=j;
                        flag1 = true;
                    }
                    if (arrayKey.get(i).equals(alph[j])){
                            l+=j;
                            flag2 = true;
                    }

                    if(flag1 == true && flag2 == true){
                        arrayNew.add(alph[(k + l) % 34]);
//                            if (k + l > 33) {
//                                arrayNew.add(alph[(k + l) - 33]);
//
//                            } else{
//                                if(k+l == 0){
//                                    arrayNew.add(alph[k + l + 33]);
//                                }else {
//                                    arrayNew.add(alph[(k + l)]);
//                                }
//                            }



                        k=0;
                        l=0;
                        flag1 = false;
                        flag2 = false;
                        break;
                    }

                }

            }  //шифруем

            writer(textfile, arrayNew);

        } else {
            System.out.println("Не тот шифр");
        }
    }//для шифра гаммирования

    public static void unshifrgammirovaniya() throws IOException {
        in.nextLine();
        System.out.println("Укажите файл с зашифрованным текстом: ");
        path = in.nextLine();

        if (path.contains(".encode")) {

            char alph[] = " абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
            arrayList = new ArrayList<>();//массив для текста
            ArrayList <Character> arrayKey  = new ArrayList<>();//массив для исходного алфавита

            textfile = new File(DIR1 + "\\" + path);
            String newPath = path.replace(".encode", "");

            System.out.println("Укажите файл с ключом: ");
            path = in.nextLine();
            keyfile = new File(DIR1 + "\\" + path); //получили пути к файлам с текстом и ключами

            Optional<String> line = Files.lines(Paths.get(DIR1 + "\\" + path)).findFirst();

            if (line.get().contains("шифр гаммирования")) {

                reader(textfile, arrayList);

                Scanner scan = new Scanner(keyfile);
                String b;
                while (scan.hasNextLine()) {//читаем буквы
                    b = scan.nextLine();
                    if (b != null && !b.isEmpty()) {
                        arrayKey.add(b.charAt(2));
                    } else
                        break;
                }

                arrayKey.remove(0);
                arrayKey.remove(0);

                int k = 0;
                int l = 0;
                boolean flag1 = false;
                boolean flag2 = false;

                for (int i = 0; i < arrayList.size(); i++) {
                    for (int j = 0; j < alph.length; j++) {

                        if(arrayList.get(i).equals(alph[j])){
                            k+=j;
                            flag1 = true;
                        }

                        if (arrayKey.get(i).equals(alph[j])){
                            l+=j;
                            flag2 = true;
                        }

                        if(flag1 == true && flag2 == true){

                           // if( k - l <= 0){
                                //int rez = k - l + 33;
                                //arrayList.set(i, alph[rez]);
                            //}else{
                                arrayList.set(i, alph[(k-l + 34) % 34]);
                            //}

                            k=0;
                            l=0;
                            flag1 = false;
                            flag2 = false;
                            break;
                        }

                    }
                }  //unшифруем

                FileWriter writer = new FileWriter(DIR1 + "\\" + newPath);//записываем в файл зашифрованный текст
                for (char v : arrayList) {
                    writer.write(v);
                }
                writer.close();

            } else {
                System.out.println("Не тот шифр");
            }
        } else {
            System.out.println("Неправильно введено название файла");
        }
    }//для шифра гаммирования

    static void shuffleArray(char[] ar) {

        Random rnd = ThreadLocalRandom.current();

        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            char a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

    }

    public static void gammKey() throws IOException {

        in.nextLine();
        System.out.println("Укажите файл с текстом ");
        path = in.nextLine();
        textfile = new File(DIR1 + "\\" + path);
        arrayList = new ArrayList<>();

        reader(textfile, arrayList);

        char alph[] = " абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        System.out.println("Укажите файл алфавитом ");
        path = in.nextLine();
        alphabet = new File(DIR1 + "\\" + path);

        arrayList = new ArrayList<>();//массив для алфавита

        Scanner scan = new Scanner(alphabet);
        String b;
        while (scan.hasNextLine()) {//читаем буквы
            b = scan.nextLine();
            if (b != null && !b.isEmpty())
                arrayList.add(b.charAt(2));
        }

        System.out.println("Введите название для файла с ключом ");
        path = in.nextLine();
        System.out.println("введите длину ключа");
        int ooo = in.nextInt();
        BufferedWriter writer = new BufferedWriter(new FileWriter(DIR1 + "\\" + path + ".key"));
        writer.write("alg: шифр гаммирования");
        writer.newLine();
        writer.write("key:");
        writer.newLine();

        if(alph.length < ooo){

            for (int i = 0; i < ooo; i++) {
                int rnd = new Random().nextInt(alph.length);
                writer.write("\\\"" + alph[rnd] + "\\\"");
                writer.newLine();
            }
        }else{
            shuffleArray(alph);
        for (int i = 0; i < ooo; i++) {
            writer.write("\\\"" + alph[i] + "\\\"");
            writer.newLine();
        }
        }
        writer.close();


    }//для шифра гаммирования



    public static void main(String[] args) throws IOException {

        for (; ; ) {
            System.out.println("Главное меню:");
            System.out.println("1) Зашифровать/Расшифровать");
            System.out.println("2) Сгенерировать ключ");

            short num = in.nextShort();

            if (num == 1) {

                System.out.println("Выберите метод шифровки: ");
                System.out.println("1) Замена");
                System.out.println("2) Перестановка");
                System.out.println("3) Гаммирование");

                short num1 = in.nextShort();

                System.out.println(" Зашифровать/Расшифровать");
                System.out.println("1) Зашифровать");
                System.out.println("2) Расшифровать");

                short num2 = in.nextShort();

                if (num2 == 1) {
                    if (num1 == 1) {
                        shifrzameny();
                    }
                    if (num1 == 2) {
                        shifrperestanovky();
                    }
                    if (num1 == 3) {
                        shifrgammirovaniya();
                    }
                } else {
                    if (num1 == 1) {
                        unshifrzameny();
                    }
                    if (num1 == 2) {
                        unshifrperestanovky();
                    }
                    if (num1 == 3) {
                        unshifrgammirovaniya();
                    }
                }
            } else {
                if (num == 2) {
                    System.out.println("Выберите метод шифровки: ");
                    System.out.println("1) Замена");
                    System.out.println("2) Перестановка");
                    System.out.println("3) Гаммирование");

                    short num1 = in.nextShort();
                    if (num1 == 1) {
                        generationKeyZamena();
                    }
                    if (num1 == 2) {
                        generationKeyP();
                    }
                    if (num1 ==3){
                        gammKey();
                    }
                } else {
                    System.out.println("Неверный номер");
                }
            }
        }
    }
}
