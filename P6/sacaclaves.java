import java.util.Arrays;

public class sacaclaves extends Thread{
    public static int numClav;
    public int j;
    public static void main(String[] args) {
        sacaclaves.numClav = Integer.parseInt(args[0]);
        sacaclaves[] hilos = new sacaclaves[100];
        for (int i = 1; i <= 1; i++) {
            hilos[i-1] = new sacaclaves(i);
            hilos[i-1].start();
        }

    }
    sacaclaves(int nvec){
        j = nvec;
    }
    @Override
    public void run() {
        int[] arr1 = new int[1024];
        int[] arr2 = new int[1024];
        long[] claveBin = new long[300];
            
        
           for (int i = 0; i < numClav; i++) {
                generaAleatorio(arr1,2);
                conversorKesimo(i,"2",claveBin);
                kColores(arr1,arr2,1000,claveBin,2,true,j,499,i);
            
           }    

        
    }
    private static void kColores(int[]arr1,int[]arr2,int generaciones,long[]claveBin,int nCol,boolean cFront,int Rvecinos,int selectedCell,long key){
        double HTt =0;
        int[] cuentaCasosT = new int[5];
        int[] cuentaCasos = new int[5];
        int suma = 0;
        double H = 0;
        int Dham = 0;
        int[]keys = new int[numClav];
        int[]values = new int[numClav];
        
        for (int i = 0; i < generaciones; i++) {
            for (int j = 0; j < arr1.length; j++) {
                if(cFront){
                    for (int k = 0; k <= Rvecinos; k++) {
                        suma += arr1[(k+j)%arr1.length];
                    }
                    for(int k=1,aux = 0;k <= Rvecinos && aux < Rvecinos ;k++,aux++){
                        if(j-k<0){
                            k = -(arr1.length - j - 1);
                        }
                        suma += arr1[j-k]%arr1.length;
                    }
                }
                else{
                    for (int k = 0; k <= Rvecinos && k+j < arr1.length; k++) {
                        suma += arr1[k+j]%arr1.length;
                    }
                    for(int k=1;k <= Rvecinos && j-k>=0;k++){
                        suma += arr1[k+j]%arr1.length;
                    }
                }
                arr2[j] = (int)claveBin[suma];
                suma = 0;
            }
            cuentaCasosT[arr2[selectedCell]]++; 
            for (int k = 0; k < arr2.length; k++) {
                cuentaCasos[arr2[k]]++;
            }

            if(true){
                H += entropiaEspacial(cuentaCasos, generaciones, i, H);
            }
            if(true){
                Dham += distanciaHamming(arr1, arr2, i, Dham);
            }

            if(true){
                double HT = 0;
                for (int k = 0; k < cuentaCasosT.length; k++){
                    if(cuentaCasosT[k]!=0){

                        HT += ((double)((double)cuentaCasosT[k]/generaciones) * (double)(logConversion((double)cuentaCasosT[k]/generaciones)));
                    }
                
                }
            
                HT = -HT;
                HTt +=HT; 
            }
            for (int j = 0; j < cuentaCasos.length; j++) {
                cuentaCasos[j] = 0;
            }
            /*for (int z = 0; z < arr1.length; z++) {
               arr1[z] = arr2[z]; 
            }*/
            arr1 = Arrays.copyOf(arr2, arr2.length);

        }
        if(Dham/generaciones>300 && HTt/generaciones>0.8 && H/generaciones>0.8){

            System.out.println(key);}
    }
    public static  double entropiaEspacial(int[] cuentaCasos,int generaciones,int x, double y){
        double item = 1024;
        double H = 0;
        for (int k = 0; k < cuentaCasos.length; k++) {
            if(cuentaCasos[k]!=0){
                H += ((double)((double)cuentaCasos[k]/item) * (double)(logConversion((double)cuentaCasos[k]/item)));
            }
        }
        H = -H;
        return H;


    }
    public static double logConversion(double x){
        return(Math.log(x)/Math.log(2));
    }
    public static int distanciaHamming(int[]arr1,int[]arr2,int x, int y){
        int contador = 0;
        for (int i = 0; i < arr2.length; i++) {
            if(arr1[i]!=arr2[i]){
                contador++;
            }
        }
        return contador;
    }
    private static void conversorKesimo(long clave, String nCol,long[] inputOutput){
        long[] Arraux = new long[inputOutput.length];
        long aux;
        int i;
        switch (nCol) {
            case "2":
                aux = clave;
                i = 0;
                while((aux)>0){
                    Arraux[i]=aux%2;
                    i++;
                    aux = aux/2;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;

            case "3":
                aux = clave;
                i = 0;
                while((aux/3)!=0){
                    if((aux/3)<3){
                        Arraux[i]= aux%3;
                        aux = aux/3;
                        i++;
                    }
                    Arraux[i]=aux%3;
                    i++;
                    aux = aux/3;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;
            case "4":
                aux = clave;
                i = 0;
                while((aux/4)!=0){
                    if((aux/4)<4){
                        Arraux[i]=aux%4;
                        aux = aux/4;
                        i++;
                    }
                    Arraux[i]=aux%4;
                    i++;
                    aux = aux/4;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;
            case "5":
                aux = clave;
                i = 0;
                while((aux/5)!=0){
                    if((aux/5)<5){
                        Arraux[i]=aux%5;
                        aux = aux/5;
                        i++;
                    }
                    Arraux[i]=aux%5;
                    i++;
                    aux = aux/5;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;
        }
    } 


    public static void generaAleatorio(int[]arr1,int nCol){
        int rand = 7;
        for (int i = 0; i < arr1.length; i++) {
            rand = (int)randomGenerator.Randu((long)rand);
            arr1[i] = rand%nCol;
        }
    }


}
