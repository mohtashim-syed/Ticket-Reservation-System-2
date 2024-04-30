public class Seat {
    private int row;
    private char seat;
    private char ticketType;

    public Seat(){
        row = row ;
        seat = seat;
        ticketType = ticketType;
    }

    public Seat(int row, char seat, char ticketType) {
        this.row = row;
        this.seat = seat;
        this.ticketType = ticketType;
    }

    // Getters and setters for row, seat, and ticketType
    public void setRow(int row){
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setSeat(char seat){
        this.seat = seat;
    }

    public char getSeat() {
        return seat;
    }

    public void setTicketType(char ticketType){
        this.ticketType = ticketType;
    }

    public char getTicketType() {
        return ticketType;
    }
}
