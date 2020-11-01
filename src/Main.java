
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static int index_of_char_in_text=0;


    public static void main(String[] args) throws Exception {
        String pathname ="input_text";
        String input_text = get_file_strings(pathname).concat(" ");
        ArrayList<Token> tokens_array=new ArrayList<>();
        char[] ch =new char[input_text.length()+1];
        input_text.getChars(0,input_text.length(),ch,0);

        while (index_of_char_in_text<ch.length-1){
            Token t=getToken(ch);
            if(t!=null ) {
                System.out.println(t.classToken + " - < " + t.token+" >");
                tokens_array.add(t);
            }
            else break;
        }
        writeFileresult(tokens_array);
    }

    public static Token getToken(char[] ch_arr) {
        Token resultToken = new Token();
        StringBuilder ch = new StringBuilder();
        int state = 0;
        char symbol;
        while (true) {
            if (index_of_char_in_text == ch_arr.length - 1) return null;
            symbol = ch_arr[index_of_char_in_text];
            boolean b = symbol == '\n' || symbol == '\t' || symbol == '\r' || symbol == '\f' || symbol == ' ' || symbol == ';' || symbol == ',';
            switch (state) {
                case 0: {
                    if (symbol == '\n' || symbol == '\t' || symbol == '\r' || symbol == '\f' || symbol == ' ')
                        state = 0;
                    else if (((int) symbol >= 65 && (int) symbol <= 90) || ((int) symbol >= 97 && (int) symbol <= 122) || (int) symbol == 95) {
                        ch.append(symbol);
                        state = 1;
                    } else if (symbol == '.' || symbol == ',' || symbol == ';' || symbol == ':' || symbol == '(' || symbol == ')' || symbol == '[' || symbol == ']' || symbol == '{' || symbol == '}') {
                        ch.append(symbol);
                        state = 3;
                    } else if ((int) symbol > 48 && (int) symbol <= 57) {
                        ch.append(symbol);
                        state = 4;
                    } else if ((int) symbol == 48) {
                        ch.append(symbol);
                        state = 20;
                    } else if (symbol == '=' || symbol == '*' || symbol == '%' || symbol == '!') {
                        ch.append(symbol);
                        state = 7;
                    } else if (symbol == '<' || symbol == '>') {
                        ch.append(symbol);
                        state = 24;
                    } else if (symbol == '/') {
                        ch.append(symbol);
                        state = 7;
                    } else if (symbol == '+' || symbol == '-') {
                        ch.append(symbol);
                        state = 8;
                    } else if (symbol == '?') {
                        ch.append(symbol);
                        state = 23;
                    } else if (symbol == '&' || symbol == '|') {
                        ch.append(symbol);
                        state = 22;
                    } else if ((int) symbol == 39) {
                        ch.append(symbol);
                        state = 11;
                    } // '
                    else if ((int) symbol == 34) {
                        ch.append(symbol);
                        state = 13;
                    } // "
                    else{
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 1: {
                    if (((int) symbol >= 65 && (int) symbol <= 90) || ((int) symbol >= 97 && (int) symbol <= 122) || (int) symbol == 95 || ((int) symbol >= 48 && (int) symbol <= 57)) {
                        state = 1;
                        ch.append(symbol);
                    } else {
                        state = 2;
                        index_of_char_in_text--;
                    }
                    break;
                }
                case 2: {
                    if (check_for_reserved_word(ch.toString())) {
                        resultToken.classToken = "<Клас: Зарезервовані слова>";
                    } else {
                        resultToken.classToken = "<Клас: Ідентифікатори>";
                    }
                    resultToken.token = ch.toString();
                    return resultToken;
                }
                case 3: {
                    resultToken.classToken = "<Клас: Розділові знаки>";
                    resultToken.token = ch.toString();
                    return resultToken;
                }
                case 4: {
                    if ((int) symbol >= 48 && (int) symbol <= 57) {
                        ch.append(symbol);
                        state = 4;
                    }else if (symbol == '.') {
                        ch.append(symbol);
                        state = 5;
                    } else if ((int) symbol == 69 || (int) symbol == 101) {// e or E
                        ch.append(symbol);
                        state = 17;
                    } else if (b) {
                        resultToken.classToken = "<Клас: Числа>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 5: {
                    if ((int) symbol >= 48 && (int) symbol <= 57) {
                        ch.append(symbol);
                        state = 6;
                    }
                    else{
                        if(symbol!='\r' )ch.append(symbol);
                        if(symbol ==' ') index_of_char_in_text--;
                        state = 10;
                    }
                    break;
                }
                case 6: {
                    if ((int) symbol >= 48 && (int) symbol <= 57) {
                        ch.append(symbol);
                        state = 6;
                    } else if ((int) symbol == 69 || (int) symbol == 101) {// e or E
                        ch.append(symbol);
                        state = 17;
                    } else if(b) {
                        resultToken.classToken = "<Клас: Числа>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 7: {
                    if (symbol == '=') {
                        ch.append(symbol);
                        state = 9;
                    } else if (symbol == '*') {
                        ch.append(symbol);
                        state = 15;
                    } else if (symbol == '/') {
                        ch.append(symbol);
                        state = 14;
                    } else {
                        resultToken.classToken = "<Клас: Оператори>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    }
                    break;
                }
                case 8: {
                    if (symbol == '=' || symbol == '+' || symbol == '-') {
                        ch.append(symbol);
                        state = 9;
                    } else {
                        resultToken.classToken = "<Клас: Оператори>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    }
                    break;
                }
                case 9: {
                    resultToken.classToken = "<Клас: Оператори>";
                    resultToken.token = ch.toString();
                    return resultToken;
                }
                case 10: {
                    if (symbol == '\n' || symbol == '\t' || symbol == '\r' || symbol == '\f' || symbol == ' ') {
                        resultToken.classToken = "<Нерозпізнавані символи>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
            }
                case 11: {
                    if ((int) symbol != 39) {
                        ch.append(symbol);
                        state = 12;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 12: {
                    if ((int) symbol == 39) {
                        ch.append(symbol);
                        resultToken.classToken = "<Клас: Символьні константи>";
                        resultToken.token = ch.toString();
                        return resultToken;

                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
            }
                case 13: {
                    if ((int) symbol == 34) {
                        index_of_char_in_text++;
                        ch.append(symbol);
                        resultToken.classToken = "<Клас: Рядкові константи>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else if (symbol == '\n' || symbol == '\r') {
                        index_of_char_in_text++;
                        resultToken.classToken = "<Нерозпізнавані символи>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 13;
                    }
                    break;
                }
                case 14: {
                    if (symbol == '\n' || symbol == '\r') {
                        index_of_char_in_text++;
                        resultToken.classToken = "<Клас: Коментар>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 14;
                    }
                    break;
                   }
                case 15: {
                    ch.append(symbol);
                    state = 15;
                    if (symbol == '*') {
                        ch.append(symbol);
                        state = 16;
                    }
                    break;
                }
                case 16: {
                    if (symbol == '/') {
                        index_of_char_in_text++;
                        ch.append(symbol);
                        resultToken.classToken = "<Клас: Коментар>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 15;
                    }
                    break;
                }
                case 17: {
                    if (symbol == '-' || symbol == '+') {
                        ch.append(symbol);
                        state = 18;
                    } else if ((int) symbol >= 48 && (int) symbol <= 57) {
                        ch.append(symbol);
                        state = 19;
                    } else {

                        if (!((int) symbol == 13)) {
                            ch.append(symbol);
                        }
                        state = 10;
                    }
                    break;
                }
                case 18: {
                    if ((int) symbol >= 48 && (int) symbol <= 57) {
                        ch.append(symbol);
                        state = 19;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 19: {
                    if ((int) symbol >= 48 && (int) symbol <= 57) {
                        ch.append(symbol);
                        state = 19;
                    }
                    else if (symbol == '\n' || symbol == '\t' || symbol == '\r' || symbol == '\f' || symbol == ' ') {
                        resultToken.classToken = "<Клас: Числа>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    }
                    else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                    }
                case 20: {
                    if ((int) symbol == 88 || (int) symbol == 120) {
                        ch.append(symbol);
                        state = 21;
                    } else if (symbol == '.') {
                        ch.append(symbol);
                        state = 5;
                    } else if (symbol == '\n' || symbol == '\t' || symbol == '\r' || symbol == '\f' || symbol == ' ' ) {
                        resultToken.classToken = "<Клас: Числа>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else if (symbol == ';'  ) {
                        resultToken.classToken = "<Клас: Числа>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 21: {
                    if (((int) symbol >= 48 && (int) symbol <= 57) || ((int) symbol >= 65 && (int) symbol <= 70) || ((int) symbol >= 97 && (int) symbol <= 102)) {
                        ch.append(symbol);
                        state = 21;
                    } else if (b) {
                        resultToken.classToken = "<Клас: Числа>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 22: {
                    if (symbol == '=' || symbol == '&' || symbol == '|') {
                        ch.append(symbol);
                        state = 9;
                    } else {
                        resultToken.classToken = "<Клас: Оператори>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    }
                    break;
                }
                case 23: {
                    if (symbol == ':') {
                        ch.append(symbol);
                        state = 9;
                    } else {
                        ch.append(symbol);
                        state = 10;
                    }
                    break;
                }
                case 24: {
                    if (symbol == '=' || symbol == '<' || symbol == '>') {
                        ch.append(symbol);
                        state = 24;
                    } else {
                        resultToken.classToken = "<Клас: Оператори>";
                        resultToken.token = ch.toString();
                        return resultToken;
                    }
                    break;
                }
            }
            index_of_char_in_text++;
        }
    }
    public static String get_file_strings(String pathname) throws Exception {
        File f = new File(pathname);
        final int length = (int) f.length();
        if (length != 0) {
            char[] words = new char[length];
            InputStreamReader stream = new InputStreamReader(new FileInputStream(f), "CP1251");
            final int read = stream.read(words);
            return new String(words, 0, read);
        }
        else{
            throw new Exception("Помилка у файлі");
        }
    }
    public static void writeFileresult(ArrayList<Token> tokens_array){
        try(FileWriter writer = new FileWriter("output", false))
        {
            for (Token t:tokens_array
                 ) {if(t!=null && !t.classToken.equals("<Клас: Коментар>")) {
                writer.write(t.classToken);
                writer.write(" - < " + t.token + " >");
                writer.append('\n');
            }
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static  boolean check_for_reserved_word(String lex){
        String[] reserved = new String[]{
                "abstract","assert",
                "boolean", "Boolean", "break", "byte",
                "case", "catch", "char","Character", "class", "const", "continue",
                "do", "double","default",
                "else","enum","extends",
                "false","final", "	finally","float", "for",
                "goto",
                "if", "import", "int","Integer", "implements", "instanceof","interface",
                "long",
                "native", "new","null",
                "package", "private", "public","protected",
                "return",
                "static","String", "switch","short","super","synchronized",
                "this", "throw", "throws", "try", "true","transient",
                "void",
                "while"};
        for (String s : reserved) {
            if (lex.equals(s)) return true;
        }
            return false;
    }

}
