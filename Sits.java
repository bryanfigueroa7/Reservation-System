public class Sits {
    private String section;
    private int row;
    private int seatNum;

    //Constructor
    public Sits (String section , int row, int seatNum){
        this.section = section;
        this.row = row;
        this.seatNum = seatNum;
    }

    //Getters
    public String getSection(){
        return this.section;
    }
    public int getRow(){
        return this.row;
    }
    public int getSeatNum(){
        return this.row;
    }

    //Setters
    public void setSection(String section){
        this.section = section;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setSeatNum(int sitNum){
        this.seatNum = seatNum;
    }

}
