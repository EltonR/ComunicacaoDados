public class Util {
    
    final private static String GERADOR16 = "1000100000010001";
    //final public static String flag = "10100011"; // £
    final public static String FLAG = "01000000"; // @
    final public static String FLAG_ACK = "11111111";
    //final public static String escape = "10100010"; // ¢
    final public static String ESCAPE = "00100011"; // #
    //tamanho da janela
    final public static int TAMJANELA = 4;
    final public static int TIMEOUT = 10000000;
    

    
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
                for(int i=0; i<16; i++){
                    if(resto.charAt(i) == GERADOR16.charAt(i))
                        resto2 += "0";
                    else
                        resto2 += "1";
                }
                while(resto2.startsWith("0"))
                    resto2 = resto2.substring(1);
                resto = resto2;
            }else{
                for(int i=0; i<16; i++){
                    if(s.charAt(i) == GERADOR16.charAt(i))
                        resto += "0";
                    else
                        resto += "1";
                }
                while(resto.startsWith("0"))
                    resto = resto.substring(1);
                s = s.substring(resto.length());
            }
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
                    if(resto.charAt(i) == GERADOR16.charAt(i))
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
                    if(s.charAt(i) == GERADOR16.charAt(i))
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
        if(mensagem.contains(ESCAPE))
            mensagem = mensagem.replaceAll(ESCAPE, ESCAPE+ESCAPE);
        if(mensagem.contains(FLAG))
            mensagem = mensagem.replaceAll(FLAG, ESCAPE+FLAG);
        return mensagem;
    }
    
    public static String desescapaMensagem(String mensagem){
        if(mensagem.contains(ESCAPE+FLAG))
            mensagem = mensagem.replaceAll(ESCAPE+FLAG, FLAG);
        if(mensagem.contains(ESCAPE+ESCAPE))
            mensagem = mensagem.replaceAll(ESCAPE+ESCAPE, ESCAPE);
        return mensagem;
    }

    public static String desflagaMensagem(String mensagem){
        if(mensagem.startsWith(FLAG) && mensagem.endsWith(FLAG)){
            mensagem = mensagem.substring(8);
            mensagem = mensagem.substring(0, mensagem.length()-8);
        }else{
            return "-";
        }
        return mensagem;
    }
    
    public static String geraID(int i){
        switch(i){
            case 0:
                return "0000";
            case 1:
                return "0001";
            case 2:
                return "0010";
            case 3:
                return "0011";
            case 4:
                return "0100";
            case 5:
                return "0101";
            case 6:
                return "0110";
            case 7:
                return "0111";
            case 8:
                return "1000";
            case 9:
                return "1001";
            case 10:
                return "1010";
            case 11:
                return "1011";
            case 12:
                return "1100";
            case 13:
                return "1101";
            case 14:
                return "1110";
            case 15:
                return "1111";
        }
        return null;
    }
    
}
