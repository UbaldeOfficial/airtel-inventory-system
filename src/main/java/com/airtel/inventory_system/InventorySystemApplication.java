package com.airtel.inventory_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InventorySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorySystemApplication.class, args);
        System.out.println("\n_____________________________________________________________");
        System.out.println("                                                              ");
        System.out.println("        IMS - INVENTORY MANAGEMENT SYSTEM                     ");
        System.out.println("        Enterprise Asset Tracking Solution                    ");
        System.out.println("                                                              ");
        System.out.println("        Application Started Successfully!                     ");
        System.out.println("        Access URL: http://localhost:8080                     ");
        System.out.println("                                                              ");
        System.out.println("        System Users:                                         ");
        System.out.println("        SysAdmin  : 24RP05770 / 24RP03478                     ");
        System.out.println("        HR        : 24RP03478 / Aline123                      ");
        System.out.println("        Technician: 24RP05770tech / UbaldeTech123             ");
        System.out.println("                                                             ");
        System.out.println("        IBYIMANIKORA Ubalde (24RP05770)                       ");
        System.out.println("        CYUZUZO Aline (24RP03478)                             ");
        System.out.println("                                                              ");
        System.out.println("________________________________________________________________\n");
 }
}