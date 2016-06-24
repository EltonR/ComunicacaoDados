
public class CRC {

    //guarda o crc;
    private int crc = 0;
    private static int[] tabela_CRC = criaTabelaCRC();

    //Cria a tabela do CRC;
    private static int[] criaTabelaCRC() {
        int[] tabela_CRC = new int[256];
        for (int i = 0; i < 256; i++) {
            int c = i;
            for (int k = 8; --k >= 0;) {
                if ((c & 1) != 0) {
                    c = 0xedb88320 ^ (c >>> 1);
                } else {
                    c = c >>> 1;
                }
            }
            tabela_CRC[i] = c;
        }
        return tabela_CRC;
    }

    //Retorna o CRC computado até o momento;
    public long getCRC() {
        return (long) crc & 0xffffffffL;
    }

    //Reinicia o CRC
    public void reset() {
        crc = 0;
    }

    public void atualiza(int bval) {
        int c = ~crc;
        c = tabela_CRC[(c ^ bval) & 0xff] ^ (c >>> 8);
        crc = ~c;
    }

    public void atualiza(byte[] dados, int off, int comprimento) {
        int c = ~crc;
        while (--comprimento >= 0) {
            c = tabela_CRC[(c ^ dados[off++]) & 0xff] ^ (c >>> 8);
        }
        crc = ~c;
    }

    //Adiciona todos os dados ao checksum.
    public void update(byte[] buf) {
        atualiza(buf, 0, buf.length);
    }
}
