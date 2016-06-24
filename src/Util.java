
import java.util.ArrayList;

public class Util {
    
    public static void main(String[] args) {
        ArrayList<String> fraseBinaria = converteMensagem("Alguma Coisa muuuuuuuuito massa!");
        System.out.println(fraseBinaria.toString());
    }
    
    
    public static ArrayList<String> converteMensagem(String mensagem){
        ArrayList<String> fraseBinaria = new ArrayList<>();
        String[] vetor = mensagem.split(" ");
        for(int i=0; i<vetor.length; i++){
            String palavra = "";
            for(int j=0; j<vetor[i].length(); j++){
                int n = (int) vetor[i].charAt(j);
                String aux = Integer.toBinaryString(n);
                while(aux.length()<8)
                    aux = "0"+aux;
                palavra += aux+"";
            }
            fraseBinaria.add(palavra);
        }
        return fraseBinaria;
    }
    
    public static ArrayList<String> addCheckSum(ArrayList<String> mensagem){
        long soma = 0;
        for(int i=0; i<mensagem.size(); i++){
            Long l = Long.parseLong(mensagem.get(i), 2);
            soma += l;
        }
        // Maior valor de soma possível: 9223372036854775807;
        //System.out.println("SUM: "+Long.toBinaryString(soma));
        mensagem.add(Long.toBinaryString(~soma));
        return mensagem;
    }
    
    
   
}
