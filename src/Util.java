
import java.util.ArrayList;

public class Util {
    
    final private static String gerador16 = "1000100000010001";
    //final public static String flag = "10100011"; // £
    final public static String flag = "01000000"; // @
    //final public static String escape = "10100010"; // ¢
    final public static String escape = "00100011"; // #
    

    public static void main(String[] args) {
        String s = "0123456789";
        System.out.println(s.substring(8));
    }
    
    public static String calculaCRC(String s){
        
        s += "0000000000000000";
        String resto="";
        while(s.length()>0){
            if(!resto.equalsIgnoreCase("")){
                while(resto.length()<16){
                    if(s.length()<1){
                        while(resto.length()<16)
                            resto = "0"+resto;
                        return resto;
                    }
                    resto += s.charAt(0);
                    s=s.substring(1);
                }
                if(s.length()<1){
                    while(resto.length()<16)
                        resto = "0"+resto;
                    return resto;
                }
                String resto2="";
//                System.out.println("S: "+s);
//                System.out.println("D: "+gerador16);
//                System.out.println("D: "+resto);
                for(int i=0; i<16; i++){
                    if(resto.charAt(i) == gerador16.charAt(i))
                        resto2 += "0";
                    else
                        resto2 += "1";
                }
//                System.out.println("R: "+resto2);
                while(resto2.startsWith("0"))
                    resto2 = resto2.substring(1);
                resto = resto2;
            }else{
//                System.out.println("S: "+s);
//                System.out.println("D: "+gerador16);
                for(int i=0; i<16; i++){
                    if(s.charAt(i) == gerador16.charAt(i))
                        resto += "0";
                    else
                        resto += "1";
                }
//                System.out.println("R: "+resto);
                while(resto.startsWith("0"))
                    resto = resto.substring(1);
                s = s.substring(resto.length());
            }
//            System.out.println("");
        }
        return resto;
    }
    
    public static String descalculaCRC(String s){
        
        String resto="";
        while(s.length()>0){
            if(!resto.equalsIgnoreCase("")){
                while(resto.length()<16){
                    if(s.length()<1){
                        while(resto.length()<16)
                            resto = "0"+resto;
                        return resto;
                    }
                    resto += s.charAt(0);
                    s=s.substring(1);
                }
                String resto2="";
//                System.out.println("S: "+s);
//                System.out.println("D: "+gerador16);
//                System.out.println("D: "+resto);
                for(int i=0; i<16; i++){
                    if(resto.charAt(i) == gerador16.charAt(i))
                        resto2 += "0";
                    else
                        resto2 += "1";
                }
//                System.out.println("R: "+resto2);
                while(resto2.startsWith("0"))
                    resto2 = resto2.substring(1);
                resto = resto2;
            }else{
                if(s.replaceAll("0", "").length() == 0){
                    return "0000000000000000";
                }
//                System.out.println("S: "+s);
//                System.out.println("D: "+gerador16);
                for(int i=0; i<16; i++){
                    if(s.charAt(i) == gerador16.charAt(i))
                        resto += "0";
                    else
                        resto += "1";
                }
//                System.out.println("R: "+resto);
                while(resto.startsWith("0"))
                    resto = resto.substring(1);
                s = s.substring(resto.length());
            }
//            System.out.println("");
        }
        return resto;
    }

    public static String converteStrBin(String mensagem) {
        byte[] bytes = mensagem.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    public static String converteBinStr(String mensagem) {
        String str = "";
        for (int i = 0; i < mensagem.length() / 8; i++) {
            int a = Integer.parseInt(mensagem.substring(8 * i, (i + 1) * 8), 2);
            str += (char) (a);
        }
        return str;
    }

    public static String escapaMensagem(String mensagem){
        if(mensagem.contains(escape))
            mensagem = mensagem.replaceAll(escape, escape+escape);
        if(mensagem.contains(flag))
            mensagem = mensagem.replaceAll(flag, escape+flag);
        return mensagem;
    }
    
    public static String desescapaMensagem(String mensagem){
        if(mensagem.contains(escape+flag))
            mensagem = mensagem.replaceAll(escape+flag, flag);
        if(mensagem.contains(escape+escape))
            mensagem = mensagem.replaceAll(escape+escape, escape);
        return mensagem;
    }

    public static String desflagaMensagem(String mensagem){
        if(mensagem.startsWith(flag) && mensagem.endsWith(flag)){
            mensagem = mensagem.substring(8);
            mensagem = mensagem.substring(0, mensagem.length()-8);
        }else{
            return "-";
        }
        return mensagem;
    }
}
