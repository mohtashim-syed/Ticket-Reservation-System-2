import java.io.*;
import java.util.Scanner;

public class Auditorium<T> {
    private Node<Seat> first = null; // Acts as the head pointer of the linked list
    public int rows = 0;
    public int cols = 0;
    char column = 'A';
    int no_of_seats;

    int bestR = -1;
    int bestC = -1;

    public Auditorium(String filename) {
        set_Auditorium(filename);
    }

    // Constructor to build the auditorium grid and fill in nodes based on file input
    private void set_Auditorium(String filename) {
        first = null;

        try {

            Scanner fileScanner = new Scanner(new File(filename));
            Node<Seat> currentRowFirst;
            Node<Seat> previousRowFirst = null;

            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Node<Seat> previousNode = null;
                cols = 0;
                currentRowFirst = null;

                for (char seatType : row.toCharArray()) {
                    Node<Seat> newNode = new Node<>();
                    Seat thisSeat = new Seat(rows+1,((char)(column + cols)),seatType);
                    newNode.setPayload(thisSeat);
                    //newNode.setNext();
                    cols++;

                    if (previousNode != null) {
                        previousNode.setNext(newNode);
                        newNode.setPrevious(previousNode);
                    }

                    if (currentRowFirst == null) {
                        currentRowFirst = newNode;
                    }

                    previousNode = newNode;
                }

                if (previousRowFirst == null) {
                    // This is the first row
                    first = currentRowFirst;
                } else {
                    previousRowFirst.setDown(currentRowFirst);
                }
                previousRowFirst = currentRowFirst;
                rows++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
        no_of_seats = rows*cols;
    }

    // Mutators and accessors for the Auditorium class
    public void setFirst(Node<Seat> first) {
        this.first = first;
    }

    public Node<Seat> getFirst() {
        return first;
    }

    public void print_Auditorium() {

        //This method will print the Auditorium , in the proper style

        Node<Seat> current = first;
        try{
            if(current==null) {
                System.out.println("Auditorium Object Empty"); //This should never happen but better to not
                return;
            }
        }catch(NullPointerException E) {
            System.out.println("print_Auditorium error");
        }

        Node<Seat> current_row = first; //this pointer will always point to the first Node of the current row

        int row_number= 0; //to keep track of the row_number being printed
        System.out.print("\n\nAuditorium Seating\n  ");
        //First Lets print the Characters to indicate Seat number
        for(int i=65;i<65+cols;i++) {
            System.out.print((char)i);
        }
        System.out.println();
        while(current_row !=null) {
            row_number++;
            System.out.print(row_number + " ");
            //print the proper row number with proper spacing

            while(current != null){
                Seat s = current.getPayload();
                if(s.getTicketType() != '.'){
                    System.out.print('#');
                } else{
                    System.out.print(s.getTicketType());
                }
                //we have printed the current seat lets go to the next
                current = current.getNext();
            }

            //Row printing is done, print a new line for the next row
            System.out.println();
            current_row = current_row.getDown();
            current = current_row;
        }
        //All Nodes have been printed into Lines.
    } //end of print_Auditorium

    public boolean check_Auditorium(int tt, int row, int seat) { //ticketType
        //This method will check if the required seats are free.
        if(first == null)
            return false;

        Node<Seat> current = first;

        //Let's go to the appropriate row and seat by jumping using down and next pointer.
        int i=1;
        while(i<row) {
            current= current.getDown(); //Getting to the correct row
            i++;
        }

        i =0; //Remember the seat contains the Ascii number of the character
        while(i<seat) {
            current = current.getNext();// Getting to the correct seat
            i++;
        }

        i=1;
        while(i<=tt) {

            //Checking if the seats are available for booking
            if(current.getPayload().getTicketType() != '.'){
                return false;
            }
            else {
                current= current.getNext();
                i++;
            }
        }
        return true;
    } //end of check Auditorium

    public void book_Auditorium(int row, int seat, int a, int c, int s ) {
        //This method will allocate the tickets in the proper location
        //First lets get to the correct row and seat by using down and next pointer. This is done very
        //similar to Check_Auditorium method.
        Node<Seat> current = first;

        //Let's go to the appropriate row and seat by jumping using down and next pointer.
        int i=1;
        while(i<row) {
            current= current.getDown(); //Getting to the correct row
            i++;
        }

        i =0; //Remember the seat contains the Ascii number of the character
        while(i<seat) {
            current = current.getNext();// Getting to the correct seat
            i++;
        }





        //Once the correct starting row and starting seat is reached booking begins.

        //Let's book the correct adult seats first
        i=1; //a pointer to keep track of number of seats assigned A
        while(i<=a) {
            current.getPayload().setTicketType('A');
            current = current.getNext();
            i++;
        }
        //Let's book the correct number of child seats
        i=1;
        while(i<=c) {
            current.getPayload().setTicketType('C');
            current = current.getNext();
            i++;
        }
        //Let's book the correct number of senior seats
        i=1;
        while(i<=s) {
            current.getPayload().setTicketType('S');
            current = current.getNext();
            i++;
        }
    } // end of book auditorium

    public void write_File() throws IOException {

        //Open a new file to write called "A1.txt".
        BufferedWriter outWrite;
        outWrite = new BufferedWriter(new FileWriter("A1.txt"));

        Node<Seat> current = first;
        Node<Seat> current_row = first; //this pointer will always point to the first Node of the current row.
        while(current_row !=null) {

            while(current != null){
                Seat s = current.getPayload();
                //Print the ticket type into the file
                outWrite.write("" + s.getTicketType());
                current = current.getNext();
            }

            //Row printing is done, print a new line for the next row.
            outWrite.write("\n");
            current_row = current_row.getDown(); //command to go to the next row.
            current = current_row;
        }

        //Close the file properly.
        outWrite.flush();
        outWrite.close();

    } //end of write File


    public void calculate_Output(){

        Node<Seat> current;
        Node<Seat> current_row;

        current = first;
        current_row = first; // this pointer will always point to the first Node of the current row
        //Initialize the following variable
        int sold_adult = 0;
        int sold_child = 0;
        int sold_senior = 0;

        while(current_row !=null) {
            while(current != null){
                char s = current.getPayload().getTicketType();
                if(s == 'A')
                    sold_adult++;
                else if(s == 'C')
                    sold_child++;
                else if(s == 'S')
                    sold_senior++;
                current = current.getNext();
            }

            //One row is done.
            current_row = current_row.getDown();
            current = current_row;
        }

        //Total sales price is calculated as follows
        double sales = sold_adult * 10 + sold_child * 5 + sold_senior * 7.5;

        //PRINT
        System.out.println("Total Seats: " + rows*cols);
        System.out.println("Total Tickets: " + (sold_adult + sold_child + sold_senior));
        System.out.println("Adult Tickets: " + sold_adult);
        System.out.println("Child Tickets: " + sold_child);
        System.out.println("Senior Tickets: " + sold_senior);
        System.out.format("Total Sales: $%.2f", sales);

        //Total Sales is printed appropriately.

    } //end of calculate_Output

    public boolean bestAvailable(int numTickets) {
        double midAudiR = (double)(rows+1)/2;
        //System.out.println("Mid R:" + midAudiR);
        double midAudiC = (double)(cols-1)/2;
        //System.out.println("Mid C: " + midAudiC);
        double minDistance = 100;

        bestR = -1;
        bestC = -1;

        for(Node<Seat> currRow = first; currRow != null; currRow = currRow.getDown()){
            for(Node<Seat> currSeat = currRow; currSeat != null; currSeat = currSeat.getNext()){
                if(!checkAvailability(currSeat, numTickets)){
                    continue;
                }
                double startR = currSeat.getPayload().getRow();
                double startC = currSeat.getPayload().getSeat()-'A';
                double middleC = (((startC+numTickets-1)+startC)/2.0);
                double distance = Math.sqrt(Math.pow(startR - midAudiR, 2) + Math.pow(middleC - midAudiC, 2));

                if(distance < minDistance){
                    //if distance calculated is less than current minimum distance
                    //update minimum distance
                    minDistance = distance;
                    bestC = (int)startC;
                    bestR = (int)startR;
                } else if (distance == minDistance){
                    //tie breakers
                    if(Math.abs(startR - midAudiR) < Math.abs(bestR - midAudiR)){
                        //if distance between row in question is less than current best row,
                        //change best seats to the lesser row (row in question)
                        minDistance = distance;
                        bestC = (int)startC;
                        bestR = (int)startR;
                    } else if (Math.abs(startR - midAudiR) == Math.abs(bestR - midAudiR)){
                        //if distance between row in question is equal to current best row
                        if(startR < bestR){
                            //if row in question is less than (closer to the front) current best row
                            //change best seat to seats in question
                            minDistance = distance;
                            bestC = (int)startC;
                            bestR = (int)startR;
                        } else if (startR == bestR && startC < bestC){
                            // if row in question and current best row are equal
                            // &&
                            // the columns in question is less than current best column
                            //update best seats to seats in question
                            minDistance = distance;
                            bestC = (int)startC;
                            bestR = (int)startR;
                        }
                    }
                }
            }
        }

        return bestC != -1 && bestR != -1;
    }

    public boolean checkAvailability(Node<Seat> curr, int numTickets){
        //goes through the number of tickets chose by the user and makes sure it is available
        for(int i = 0; i < numTickets; i++){
            if(curr == null || curr.getPayload().getTicketType() != '.') {
                return false;
            }
            curr = curr.getNext();
        }
        return true;
    }
} //end of the class Auditorium
