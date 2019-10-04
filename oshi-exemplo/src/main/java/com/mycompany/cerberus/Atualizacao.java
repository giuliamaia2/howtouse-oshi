
package com.mycompany.cerberus;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

public class Atualizacao {
    
    private String so;
    private String processador;
    private String ramTotal;
    private String ramDisp;
    private String cpu;
    


    
    
    public void atualizar(){
        SystemInfo si = new SystemInfo();
        OperatingSystem os =  si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();
        
        this.so = (String.valueOf(os));   
        
        CentralProcessor cp = hal.getProcessor();
        this.processador = cp.toString();
        
        GlobalMemory memory = hal.getMemory();
        this.ramTotal = FormatUtil.formatBytes(memory.getTotal()) ;     
        this.ramDisp = FormatUtil.formatBytes(memory.getAvailable()) ;
            
        long[] prevTicks = cp.getSystemCpuLoadTicks();
        this.cpu = String.format("%.1f%%", cp.getSystemCpuLoadBetweenTicks(prevTicks) * 100);
      
    }

    public String getSo() {
        return so;
    }


    public String getProcesso() {
        return processador;
    }


    public String getRamTotal() {
        return ramTotal;
    }

    public String getRamDisp() {
        return ramDisp;
    }


    public String getCpu() {
        return cpu;
    }

    
    
}
