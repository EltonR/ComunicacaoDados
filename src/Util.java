public class Util {
    
    public static void main(String[] args) {
        System.out.println(converteMensagem("Alguma coisa!"));
    }
    
    
    public static String converteMensagem(String mensagem){
        String s = "";
        for(int i=0; i<mensagem.length(); i++){
            int n = (int) mensagem.charAt(i);
            String aux = Integer.toBinaryString(n);
            while(aux.length()<8)
                aux = "0"+aux;
            s += aux+" ";
        }
        return s;
    }
    
    public static boolean checkSum(String mensagem){
        
        
        return true;
    }
    
}
