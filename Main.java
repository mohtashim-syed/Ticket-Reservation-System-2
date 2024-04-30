/*  NAME: Mohtashim Syed
*/

import java.util.*;
import java.io.*;


public class Main
{
    public static void main(String[] args) throws IOException{
        Scanner scnr = new Scanner(System.in); //regular scanner

        System.out.println("Please enter filename: ");
        String filename = scnr.next();

        Auditorium<Seat> audi = new Auditorium<>(filename);

        int aT = 0; //adult ticket count
        int cT = 0; //child ticket count
        int sT = 0; //senior ticket count

        boolean exit = false;

        do {
            int total = 0;
            System.out.println("\nMain Menu:");
            System.out.println("1. Reserve Seats");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");

            String option = scnr.next();

            switch (option) {
                case "1":
                    audi.print_Auditorium();

                    System.out.println("Which row would you like to reserve the seat?");
                    int firstRow = scnr.nextInt();

                    if(firstRow > audi.rows){                         //input Validation
                        System.out.println("row out of bounds");
                        firstRow = scnr.nextInt();
                    }

                    System.out.println("Which seat would you like to reserve in the row?");
                    char firstSeat = scnr.next().charAt(0);
                    int n1Seat = firstSeat-'A';

                    if(firstSeat < 65 || firstSeat > (audi.cols+64)){
                        System.out.println("column out of bounds");
                        firstSeat = scnr.next().charAt(0);
                        n1Seat = firstSeat-'A';
                    }

                    if(n1Seat > audi.cols){                           //input Validation
                        System.out.println("column out of bounds");
                        firstSeat = scnr.next().charAt(0);
                        n1Seat = firstSeat-'A';
                    }

                        boolean flag;
                    do {
                        try {
                            System.out.println("How many adults?");
                            aT = scnr.nextInt();
                            total += aT;

                            if (aT < 0 || aT > audi.cols) { //input validation
                                System.out.println("no seats available");
                                throw new IOException();
                            }

                            flag = true;
                        } catch (Exception e) {
                            System.out.println("Invalid option. Please try again.");
                            scnr.nextLine();
                            flag = false;
                        }

                    } while(!flag);

                    do {
                        try {
                            System.out.println("How many children?");
                            cT = scnr.nextInt();
                            total += cT;

                            if (cT < 0 || cT > audi.cols) { //input validation
                                System.out.println("no seats available");
                                throw new IOException();
                            }

                            flag = true;
                        } catch (Exception e) {
                            System.out.println("Invalid option. Please try again.");
                            scnr.nextLine();
                            flag = false;
                        }

                    } while(!flag);

                    do {
                        try {
                            System.out.println("How many seniors?");
                            sT = scnr.nextInt();
                            total += sT;

                            if (sT < 0 || sT > audi.cols) { //input validation
                                System.out.println("no seats available");
                                throw new IOException();
                            }

                            flag = true;
                        } catch (Exception e) {
                            System.out.println("Invalid option. Please try again.");
                            scnr.nextLine();
                            flag = false;
                        }

                    } while(!flag);

                    boolean available = audi.check_Auditorium(total, firstRow, n1Seat); //CHECK

                    if(available){
                        //book seats and update
                        audi.book_Auditorium(firstRow, n1Seat, aT, cT, sT);
                        audi.write_File();
                    }
                    else{ //finding the best available
                        boolean bestSeat = audi.bestAvailable(total);

                        if(!bestSeat){ //if no best seat available
                            System.out.println("no seats available");

                            audi.print_Auditorium();
                            audi.write_File();

                            break;
                        }
                        else{ //prints out the best available w the option to select to reserve
                            //System.out.println("Best available: " + (char)(audi.bestC) + " - "  + (char)(audi.bestC + total - 1));
                            System.out.print("Best available: ");
                            System.out.print(audi.bestR + "" + ((char)(65+audi.bestC)));
                            System.out.print(" - ");
                            System.out.println(audi.bestR + "" + ((char)(65 + audi.bestC + total - 1)));

                            System.out.println("Reserve? Y/N");
                            char input = scnr.next().charAt(0);

                            if(input == 'Y'){
                                audi.book_Auditorium(audi.bestR, audi.bestC, aT, cT, sT); //CHECK
                                audi.write_File();
                            }
                        }
                    }
                    audi.print_Auditorium();
                    break;

                case "2":
                    exit = true; //"EXIT"
                    audi.print_Auditorium();
                    audi.calculate_Output();
                    audi.write_File();
                    break;

                default:
                    //any inputs that don't == "1" || "2"
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (!exit);
    }
}
