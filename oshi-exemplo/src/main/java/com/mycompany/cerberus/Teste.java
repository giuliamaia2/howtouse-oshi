
package com.mycompany.cerberus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

public class Teste {
    //Classe de criação de logs
    private static final Logger logger = LoggerFactory.getLogger(Teste.class);
    
    public static void main(String[] args) {
        //    classe que coleta informações do sistema

        SystemInfo si = new SystemInfo();
        // classes responsáveis por coletar informações específicas
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        System.out.println("Memória Total:" + FormatUtil.formatBytes(hal.getMemory().getTotal()));
        System.out.println("Memoria Disponível: " + FormatUtil.formatBytes(hal.getMemory().getAvailable()));
        
        
        //Logs
//        logger.info("Componentes da cpu:");
//        StringBuilder novaString = new StringBuilder();
//        List<String> oshi = new ArrayList<>();
//        oshi = cpu.mostrarCpu(hal.getProcessor());
//        for(int i = 0; i < oshi.size(); i++){
//            novaString.append(oshi.get(i));
//            if(oshi.get(i) != null && !oshi.get(i).endsWith("\n")){
//                novaString.append('\n');
//            }
//        }
//        logger.info("Componentes:{}{}",'\n', novaString);
    }

}
