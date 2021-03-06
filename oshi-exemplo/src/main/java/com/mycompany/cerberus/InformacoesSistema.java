
package com.mycompany.cerberus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Display;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.hardware.SoundCard;
import oshi.hardware.UsbDevice;
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;
import oshi.SystemInfo;

public class InformacoesSistema {
    
     private static final Logger logger = LoggerFactory.getLogger(InformacoesSistema.class);

    static List<String> oshi = new ArrayList<>();

    public static void main(String[] args) {
        logger.info("Initializing System...");
        SystemInfo si = new SystemInfo();

        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

//        printOperatingSystem(os);

//        logger.info("Checking computer system...");
//        printComputerSystem(hal.getComputerSystem());

//        logger.info("Checking Processor...");
//        printProcessor(hal.getProcessor());

//        logger.info("Checking Memory...");
//        printMemory(hal.getMemory());

        logger.info("Checking CPU...");
        printCpu(hal.getProcessor());
//
//        logger.info("Checking Processes...");
//        printProcesses(os, hal.getMemory());
//
//        logger.info("Checking Sensors...");
//        printSensors(hal.getSensors());
//
//        logger.info("Checking Power sources...");
//        printPowerSources(hal.getPowerSources());
//
//        logger.info("Checking Disks...");
//        printDisks(hal.getDiskStores());
//
//        logger.info("Checking File System...");
//        printFileSystem(os.getFileSystem());
//
//        logger.info("Checking Network interfaces...");
//        printNetworkInterfaces(hal.getNetworkIFs());
//
//        logger.info("Checking Network parameters...");
//        printNetworkParameters(os.getNetworkParams());
//
//        // hardware: displays
//        logger.info("Checking Displays...");
//        printDisplays(hal.getDisplays());
//
//        // hardware: USB devices
//        logger.info("Checking USB Devices...");
//        printUsbDevices(hal.getUsbDevices(true));
//
//        logger.info("Checking Sound Cards...");
//        printSoundCards(hal.getSoundCards());

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < oshi.size(); i++) {
            output.append(oshi.get(i));
            if (oshi.get(i) != null && !oshi.get(i).endsWith("\n")) {
                output.append('\n');
            }
        }
        logger.info("Printing Operating System and Hardware Info:{}{}", '\n', output);
    }

    private static void printOperatingSystem(final OperatingSystem os) {
        oshi.add(String.valueOf(os));
//        oshi.add("Booted: " + Instant.ofEpochSecond(os.getSystemBootTime()));
//        oshi.add("Uptime: " + FormatUtil.formatElapsedSecs(os.getSystemUptime()));
//        oshi.add("Running with" + (os.isElevated() ? "" : "out") + " elevated permissions.");
//        oshi.add("\n");
    }

    private static void printComputerSystem(final ComputerSystem computerSystem) {
        oshi.add("system: " + computerSystem.toString());
        oshi.add(" firmware: " + computerSystem.getFirmware().toString());
        oshi.add(" baseboard: " + computerSystem.getBaseboard().toString());
        oshi.add("\n");
    }

    private static void printProcessor(CentralProcessor processor) {
        oshi.add(processor.toString());
        
        oshi.add("\n");
    }

    //Modelo de mostra da memória totalmente alterado
    //Adicionado modelo de formatação de bytes e novos métodos
    private static void printMemory(GlobalMemory memory) {
        String memTotal = FormatUtil.formatBytes(memory.getTotal());
        String memDispo = FormatUtil.formatBytes(memory.getAvailable());
        oshi.add("Memória total:" + memTotal);
        oshi.add("Memória disponível: " + memDispo);
        oshi.add("\n");
    }

    private static void printCpu(CentralProcessor processor) {
        oshi.add("Context Switches/Interrupts: " + processor.getContextSwitches() + " / " + processor.getInterrupts());

        long[] prevTicks = processor.getSystemCpuLoadTicks();
        long[][] prevProcTicks = processor.getProcessorCpuLoadTicks();
        oshi.add("CPU, IOWait, and IRQ ticks @ 0 sec:" + Arrays.toString(prevTicks));
        // Wait a second...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();

        oshi.add(String.format("CPU load: %.1f%%", processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100));
        double[] loadAverage = processor.getSystemLoadAverage(3);
        oshi.add("CPU load averages:" + (loadAverage[0] < 0 ? " N/A" : String.format(" %.2f", loadAverage[0]))
                + (loadAverage[1] < 0 ? " N/A" : String.format(" %.2f", loadAverage[1]))
                + (loadAverage[2] < 0 ? " N/A" : String.format(" %.2f", loadAverage[2])));
        // per core CPU
        StringBuilder procCpu = new StringBuilder("CPU load per processor:");
        double[] load = processor.getProcessorCpuLoadBetweenTicks(prevProcTicks);
        for (double avg : load) {
            procCpu.append(String.format(" %.1f%%", avg * 100));
        }
        oshi.add(procCpu.toString());
        long freq = processor.getVendorFreq();
        if (freq > 0) {
            oshi.add("Vendor Frequency: " + FormatUtil.formatHertz(freq));
        }
        freq = processor.getMaxFreq();
        if (freq > 0) {
            oshi.add("Max Frequency: " + FormatUtil.formatHertz(freq));
        }
        long[] freqs = processor.getCurrentFreq();
        if (freqs[0] > 0) {
            StringBuilder sb = new StringBuilder("Current Frequencies: ");
            for (int i = 0; i < freqs.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(FormatUtil.formatHertz(freqs[i]));
            }
            oshi.add("\n");
            oshi.add(sb.toString());
        }
    }

    private static void printProcesses(OperatingSystem os, GlobalMemory memory) {
        oshi.add("Processes: " + os.getProcessCount() + ", Threads: " + os.getThreadCount());
        // Sort by highest CPU
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, ProcessSort.CPU));

        oshi.add("   PID  %CPU %MEM       VSZ       RSS Name");
        for (int i = 0; i < procs.size() && i < 5; i++) {
            OSProcess p = procs.get(i);
            oshi.add(String.format(" %5d %5.1f %4.1f %9s %9s %s", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName()));
        }
        oshi.add("\n");
    }

    private static void printSensors(Sensors sensors) {
        oshi.add("Sensors: " + sensors.toString());
        oshi.add("\n");
    }

    private static void printPowerSources(PowerSource[] powerSources) {
        StringBuilder sb = new StringBuilder("Power Sources: ");
        if (powerSources.length == 0) {
            sb.append("Unknown");
        }
        
        for (PowerSource powerSource : powerSources) {
            sb.append("\n Nome: ").append(powerSource.getName());
            //Fazendo cálculo da bateria do notebook
            String bateria = String.format("%.0f" + "%%", powerSource.getRemainingCapacity() * 100);
            sb.append("\n Capacidade Restante: ").append(bateria);
            //Fazendo cálculo de tempo de bateria 
            Double time = powerSource.getTimeRemaining();
            String message;
            if(time == -2){
                sb.append("\n Tempo restante ilimitado, carregando...");
            }if(time < 0){
                sb.append("\n Impossível calcular tempo restante");
            }else{
                Integer hour = 0;
                Integer minutes = 0;
                while(time>=60){
                    minutes++;
                    time = time - 60;
                }
                while(minutes>=60){
                    hour++;
                    minutes = minutes - 60;
                }
                message = String.format("\n Tempo Restante: %dh %dmin %.0fs" ,
                        hour, minutes, time);
                sb.append(message);
            }           
        }
        oshi.add(sb.toString());
        oshi.add("\n");
    }

    private static void printDisks(HWDiskStore[] diskStores) {
        StringBuilder sb = new StringBuilder();
        for (HWDiskStore disk : diskStores) {
            sb.append("Tamanho: " + disk.getSize());
        }
        oshi.add("\n");

    }

    private static void printFileSystem(FileSystem fileSystem) {
        oshi.add("File System:");

        oshi.add(String.format(" File Descriptors: %d/%d", fileSystem.getOpenFileDescriptors(),
                fileSystem.getMaxFileDescriptors()));

        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            oshi.add(String.format(
                    " %s (%s) [%s] %s of %s free (%.1f%%), %s of %s files free (%.1f%%) is %s "
                            + (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s")
                            + " and is mounted at %s",
                    fs.getName(), fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
                    FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
                    FormatUtil.formatValue(fs.getFreeInodes(), ""), FormatUtil.formatValue(fs.getTotalInodes(), ""),
                    100d * fs.getFreeInodes() / fs.getTotalInodes(), fs.getVolume(), fs.getLogicalVolume(),
                    fs.getMount()));
        }
        oshi.add("\n");
    }

    private static void printNetworkInterfaces(NetworkIF[] networkIFs) {
        StringBuilder sb = new StringBuilder("Network Interfaces:");
        if (networkIFs.length == 0) {
            sb.append(" Unknown");
        }
        for (NetworkIF net : networkIFs) {
            sb.append("\n ").append(net.getName());
        }
        oshi.add(sb.toString());
        oshi.add("\n");
    }

    private static void printNetworkParameters(NetworkParams networkParams) {
        oshi.add("Network parameters:\n " + networkParams.toString());
        oshi.add("\n");
    }

    private static void printDisplays(Display[] displays) {
        oshi.add("Displays:");
        int i = 0;
        for (Display display : displays) {
            oshi.add(" Display " + i + ":");
            oshi.add(String.valueOf(display));
            i++;
        }
        oshi.add("\n");
    }

    private static void printUsbDevices(UsbDevice[] usbDevices) {
        oshi.add("USB Devices:");
        for (UsbDevice usbDevice : usbDevices) {
            oshi.add(String.valueOf(usbDevice));
        }
        oshi.add("\n");
    }

    private static void printSoundCards(SoundCard[] cards) {
        oshi.add("Sound Cards:");
        for (SoundCard card : cards) {
            oshi.add(" " + String.valueOf(card));
        }
        oshi.add("\n");
    }
}
