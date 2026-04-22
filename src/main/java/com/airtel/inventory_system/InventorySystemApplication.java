package com.airtel.inventory_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InventorySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorySystemApplication.class, args);
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║        AIRTEL INVENTORY MANAGEMENT SYSTEM                     ║");
        System.out.println("║        Enterprise Asset Tracking Solution                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║        Application Started Successfully!                      ║");
        System.out.println("║        Access URL: http://localhost:8080                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║        Default Users:                                         ║");
        System.out.println("║        ADMIN    : UbaldeAdmin / AdminUbalde123               ║");
        System.out.println("║        HR       : Alinehr / Alinehr123                       ║");
        System.out.println("║        TECHNICIAN: UbaldeTech / Alinetech123                 ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
    }
}