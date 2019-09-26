
package com.mycompany.cerberus;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;


public class Exemplo {
         
    public static void main(String[] args) {
        //Instâncias necessárias, no caso só utilizamos a "hal"
        SystemInfo si = new SystemInfo();
        OperatingSystem os =  si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();
        
        //Chamando método verMemoria()
        System.out.println(verMemoria(hal.getMemory()));
    }
            
    
    //O String refere-se ao retorno do método
    public static String verMemoria(GlobalMemory memoria){
        //Declaração das variáveis que receberão os valores da memória
        long disponivel = memoria.getAvailable();
        long total = memoria.getTotal();
        
        //Junção dos valores da String
        String exemplo = String.format("Memória Total: %s \n"
                + "Memória Disponível: %s", 
                //Conversão dos valores para String
                FormatUtil.formatBytes(disponivel), 
                FormatUtil.formatBytes(total));
        //retornando string
        return exemplo;
    }
    
}
